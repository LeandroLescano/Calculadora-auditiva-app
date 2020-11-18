package utn.frgp.tusi.tpintegrador_grupo7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos.ConfiguracionDao;
import utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos.HistorialDao;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Configuracion;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Operacion;
import utn.frgp.tusi.tpintegrador_grupo7.Utilidades.AyudaAuditiva;
import utn.frgp.tusi.tpintegrador_grupo7.Utilidades.ComandosVoz;
import utn.frgp.tusi.tpintegrador_grupo7.Utilidades.Utilidades;
import utn.frgp.tusi.tpintegrador_grupo7.Utilidades.Vibracion;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.os.Vibrator;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class CalculadoraBasica extends AppCompatActivity {

    private TextView resultado;
    private EditText operacion;
    private Float cuenta = 0f;
    private String Numero = "";
    private String MuestraVieja = "";
    private String buttonText = "";
    private String signo = "";
    private Integer posActual;
    private TextToSpeech mTTS;
    private AyudaAuditiva audio;
    private ComandosVoz voz;
    private ConfiguracionDao config;
    private Configuracion cfgActual;
    private HistorialDao hist;
    private Button alertaGrabando, alertaProcesando;
    private ConstraintLayout fondoProcesando;
    private LinearLayout layout;
    private Vibrator vibrator;
    private Vibracion vibra;
    private boolean vibrarBoton = true;
    private ArrayList<View> botones, botonesTam, botonesImg;
    private DecimalFormat formatter;

    @Override
    public void onResume() {
        super.onResume();
        cargarConfig();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basica);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        config = new ConfiguracionDao();
        hist = new HistorialDao();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibra = new Vibracion();
        vibra = vibra.getManager(this);
        alertaGrabando = findViewById(R.id.alerta_grabando);
        alertaProcesando = findViewById(R.id.alerta_procesando);
        layout = findViewById(R.id.layout_basica);
        fondoProcesando = findViewById(R.id.background_procesando);
        operacion = findViewById(R.id.txtOperacion);
        resultado = findViewById(R.id.txtResultado);
        resultado.setAlpha((float) 0.5);
        resultado.setText("0");
        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            operacion.setShowSoftInputOnFocus(false);
        }
        operacion.requestFocus();
        posActual = operacion.getSelectionEnd();

        operacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                posActual = operacion.getSelectionEnd();
            }
        });

        String opHistorial = getIntent().getStringExtra("operacion");
        if(opHistorial != null && !opHistorial.isEmpty()){
            operacion.setText(opHistorial.substring(0,opHistorial.indexOf("=")));
            resultado.setText(opHistorial.substring(opHistorial.indexOf("=")+1));
            operacion.setSelection(operacion.length());
        }
        //Consultar configuración para cambiar aspecto.
        //formatearAspecto();
        audio = new AyudaAuditiva(this);

        //Cargar configuración
        cargarConfig();

    }

    public void ingresarDigito(View view){
        if (resultado.getAlpha() == 1f) {
            if(resultado.getText().equals("Error matemático") || resultado.getText().equals("Error de sintaxis")){
                operacion.setText("");
                resultado.setText("0");
            }
            resultado.setAlpha(0.5f);
        }
        Button digit = (Button)view;
        buttonText = digit.getText().toString();
        posActual = operacion.getSelectionEnd();
        MuestraVieja = operacion.getText().toString();
        audio.emitirAudio(buttonText);
        if(!ObtenerOperador()){
            operacion.setText(MuestraVieja.substring(0,posActual).concat(buttonText.concat(MuestraVieja.substring(posActual))));
            Numero = Numero + buttonText;
            if(vibrarBoton){
                vibra.VibracionBoton();
            }
        }
        if(posActual < operacion.getText().length() && view.getId() != R.id.btnPorcentaje){
            posActual++;
            operacion.setSelection(posActual);
        }

        Calcular();

    }

    //Borra un solo dígito a la izquierda del cursor
    public void borrarDigito(View view){
        String opActual = operacion.getText().toString();
        if(posActual != operacion.getSelectionEnd()){
            posActual = operacion.getSelectionEnd();
        }
        if(opActual.length() > 0 && posActual > 0){
            operacion.setText(opActual.substring(0, posActual-1).concat(opActual.substring(posActual)));
            posActual--;
            operacion.setSelection(posActual);
        }
        if(operacion.getText().length() <= 0){
            resultado.setText("0");
        }
        Calcular();
        audio.emitirAudio("borrar");
        if(vibrarBoton){
            vibra.VibracionBoton();
        }
    }

    //Coloca en pantalla el último número ingresado dividido 100
    public void porcentajeNumero(View view){
        audio.emitirAudio("%");
        String muestra = operacion.getText().toString();
        String opActual = operacion.getText().toString().substring(0, posActual);
        Integer ultimaPos=0, numeroInt = 0, posNumero;
        Float charCode, numeroFloat;
        for (int x=0; x<opActual.length(); x++){
            charCode = (float) opActual.charAt(x);
            if(charCode != 46 && (charCode < 48 || charCode > 57)){
                ultimaPos=x+1;
            }
        }
        try{
            numeroFloat = Float.parseFloat(opActual.substring(ultimaPos))/100;
            posNumero = muestra.lastIndexOf(opActual.substring(ultimaPos));
            String finalOperacion = muestra.substring(posNumero + opActual.substring(ultimaPos).length());
            if(numeroFloat%1 == 0){
                numeroInt = Math.round(numeroFloat);
                operacion.setText(muestra.substring(0, posNumero).concat(numeroInt.toString()).concat(finalOperacion));
                posActual = operacion.getText().toString().lastIndexOf(numeroInt.toString())+numeroFloat.toString().length()-2;
            }else{
                if(numeroFloat.toString().contains(("E"))){
                    NumberFormat formatter = new DecimalFormat("###########.##########");
                    String numFloatExp = formatter.format(numeroFloat);
                    operacion.setText(muestra.substring(0, posNumero).concat(numFloatExp).concat(finalOperacion));
                    posActual = operacion.getText().toString().lastIndexOf(numFloatExp)+numFloatExp.length();
                }else {
                    operacion.setText(muestra.substring(0, posNumero).concat(numeroFloat.toString()).concat(finalOperacion));
                    posActual = operacion.getText().toString().lastIndexOf(numeroFloat.toString()) + numeroFloat.toString().length();
                }
            }
            operacion.setSelection(posActual);
        }catch (NumberFormatException e){

        }
        Calcular();
        if(vibrarBoton){
            vibra.VibracionBoton();
        }
    }

    //Mueve el cursor hacia la izquierda o derecha.
    public void moverCursor(View view){
        AppCompatImageButton flecha = (AppCompatImageButton) view;
        posActual = operacion.getSelectionEnd();
        switch(flecha.getId()){
            case R.id.btnDerecha:
                if(posActual < operacion.getText().length()){
                    posActual++;
                    operacion.setSelection(posActual);
                }
                audio.emitirAudio("derecha");
                break;
            case R.id.btnIzquierda:
                if(posActual-1 >= 0){
                    posActual--;
                    operacion.setSelection(posActual);
                }
                audio.emitirAudio("izquierda");
                break;
        }
        if(vibrarBoton){
            vibra.VibracionBoton();
        }
    }

    //Borra toda la operación
    public void eliminarOperacion(View view){
        operacion.setText("");
        resultado.setText("0");
        resultado.setAlpha((float) 0.5);
        cuenta = 0f;
        Numero = "";
        audio.emitirAudio("Borrar todo");
        if(vibrarBoton){
            vibra.VibracionBoton();
        }
    }

    public void calcularOperacion(View view){
        Calcular();
        if(vibra == null){
            vibra = vibra.getManager(this);
        }

        if (!resultado.getText().equals("Error matemático") && !resultado.getText().equals("Error de sintaxis"))
        {
        hist.cargarOperacion(operacion.getText() + "=" + resultado.getText(), this);

        if (cfgActual.getVibracion().getDescripcion().equals("Siempre") || cfgActual.getVibracion().getDescripcion().equals("Solo resultados"))
        {
            vibra.VibracionResultado();
        }

            if (cfgActual.getSonido().getDescripcion().equals("Siempre") || cfgActual.getSonido().getDescripcion().equals("Solo resultados"))
            {
                audio.emitirAudio("Resultado: " + resultado.getText() );
            }
        }
        else
        {
            if (cfgActual.getVibracion().getDescripcion().equals("Siempre") || cfgActual.getVibracion().getDescripcion().equals("Solo errores"))
            {
                vibra.VibracionError();
            }

            if (cfgActual.getSonido().getDescripcion().equals("Siempre") || cfgActual.getSonido().getDescripcion().equals("Solo errores"))
            {
                audio.emitirAudio(resultado.getText().toString());
            }
        }
        resultado.setAlpha(1f);
    }

    public boolean ObtenerOperador(){
        String[] Operadores = { "+", "-", "x", "/", "="};
        boolean Error = false;
//        if (Arrays.asList(Operadores).contains(buttonText)) {
//            if(MuestraVieja.isEmpty()){
//                if(buttonText.equals("-")) {
//                    Error = false;
//                }
//                else{
//                    Error = true;
//                }
//            }
//            else {
//                String UltimoCaracter;
//                if(operacion.getSelectionEnd() <= operacion.length()-1){
//                    UltimoCaracter = MuestraVieja.substring(operacion.getSelectionEnd()-1,operacion.getSelectionEnd());
//                }else{
//                    UltimoCaracter= MuestraVieja.substring(MuestraVieja.length()-1);
//                }
//                if(UltimoCaracter.equals("x") || UltimoCaracter.equals("/")){
//                    if(!buttonText.equals("-") && !buttonText.equals("+")){
//                        Error = false;
//                    }
//                }else if(UltimoCaracter.equals(buttonText)){
//                    Error = false;
//                }
//            }
//        }
//        else{
//            Error = false;
//        }
        return  Error;
    }

    public void Calcular(){
        String operacionACalcular = operacion.getText().toString();

        //Agregar multiplicación entre parentesis
        operacionACalcular = Operacion.agregarMultiplicaciones(operacionACalcular);

        //Detección de posición de paréntisis
        operacionACalcular = Operacion.sacarParentesis(operacionACalcular);

        //Calculo de operación
        Float resultadoOperacion = Operacion.calcularOperacionBasica(operacionACalcular);
        if(resultadoOperacion != null && resultadoOperacion%1 == 0){
            if(resultadoOperacion.toString().contains("E")){
                resultado.setText(formatter.format(new BigDecimal(resultadoOperacion)));
            }else{
                resultado.setText(formatter.format(Math.round(resultadoOperacion)));
            }
        }else if(resultadoOperacion != null && resultadoOperacion.isNaN()){
            resultado.setText("Error matemático");
        }else if(resultadoOperacion != null && resultadoOperacion.isInfinite()){
            resultado.setText("Infinito");
        }else if (resultadoOperacion != null){
            resultado.setText(formatter.format(resultadoOperacion));
        }else{
            resultado.setText("Error de sintaxis");
        }
    }

    public void comandoDeVoz(View view){
        ImageView micButton = (ImageView) view;
        if(voz == null){
            voz = new ComandosVoz(this, this, operacion, resultado, alertaGrabando, alertaProcesando, fondoProcesando, layout);
        }
        voz.startStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item= menu.findItem(R.id.action_basica);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_configuracion) {
            Intent intent = new Intent(this, ConfiguracionActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_basica){
            Intent intent = new Intent(this, utn.frgp.tusi.tpintegrador_grupo7.CalculadoraBasica.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_cientifica){
            Intent intent = new Intent(this, utn.frgp.tusi.tpintegrador_grupo7.CalculadoraCientifica.class);
            SharedPreferences preferences = this.getSharedPreferences("calculadora", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("ultima", "cientifica");
            editor.apply();
            startActivity(intent);
        } else if(id == R.id.action_historial){
            Intent intent = new Intent(this, utn.frgp.tusi.tpintegrador_grupo7.HistorialOperaciones.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){
        if(mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

    //Agregar botones a lista modificables en tamaño

    public ArrayList<View> agregarBotones ()
    {
        ArrayList<View> listaCompleta = new ArrayList<View>();
        listaCompleta.add(findViewById(R.id.btn0));
        listaCompleta.add(findViewById(R.id.btn1));
        listaCompleta.add(findViewById(R.id.btn2));
        listaCompleta.add(findViewById(R.id.btn3));
        listaCompleta.add(findViewById(R.id.btn4));
        listaCompleta.add(findViewById(R.id.btn5));
        listaCompleta.add(findViewById(R.id.btn6));
        listaCompleta.add(findViewById(R.id.btn7));
        listaCompleta.add(findViewById(R.id.btn8));
        listaCompleta.add(findViewById(R.id.btn9));
        listaCompleta.add(findViewById(R.id.btnPunto));
        listaCompleta.add(findViewById(R.id.btnIgual));
        listaCompleta.add(findViewById(R.id.btnSumar));
        listaCompleta.add(findViewById(R.id.btnResta));
        listaCompleta.add(findViewById(R.id.btnEliminar));
        listaCompleta.add(findViewById(R.id.btnBorrar));
        listaCompleta.add(findViewById(R.id.btnDividir));
        listaCompleta.add(findViewById(R.id.btnMultiplicar));
        listaCompleta.add(findViewById(R.id.btnPorcentaje));
        return listaCompleta;
    }

    //Agregar image buttons a lista

    public ArrayList<View> agregarBotonesImg ()
    {
        ArrayList<View> listaCompleta = new ArrayList<View>();
        listaCompleta.add(findViewById(R.id.btnIzquierda));
        listaCompleta.add(findViewById(R.id.btnDerecha));
        listaCompleta.add(findViewById(R.id.btnMic));

        return listaCompleta;
    }

    //Carga configuracion actual en las listas de todos los botones

    public void cargarConfig() {
        config = new ConfiguracionDao();
        cfgActual = new Configuracion();
        cfgActual = config.traerConfiguracion(this);
        botonesImg = agregarBotonesImg();
//        botonesTam = agregarBotones();
        botones = layout.getTouchables();

        for(View v : botones){
            if(v.getId() != R.id.txtOperacion && v.getId() != R.id.btnDerecha && v.getId() != R.id.btnIzquierda && v.getId() != R.id.btnMic){
                Button boton = (Button) v;
                boton.setBackgroundColor(config.setearColorBoton(this));
                boton.setTextColor(config.setearColorTexto(this));
                boton.setTypeface(config.setearTipografia(this));
            }
        }
        for(View i : botonesImg){
            ImageButton boton = (ImageButton) i;
            boton.setColorFilter(config.setearColorTexto(this));
            boton.setBackgroundColor(config.setearColorBoton(this));
        }
        for(View b : botones){
            if(b.getId() == R.id.btnDerecha || b.getId() == R.id.btnIzquierda || b.getId() == R.id.btnMic){
                ImageButton img = (ImageButton) b;
                int tamano = config.setearTamano(this)-12;
                if(b.getId() == R.id.btnDerecha){
                    switch (tamano){
                        case 16:
                            img.setImageResource(R.drawable.ic_flecha_der_16);
                            break;
                        case 24:
                            img.setImageResource(R.drawable.ic_flecha_der_24);
                            break;
                        case 32:
                            img.setImageResource(R.drawable.ic_flecha_der_32);
                            break;
                        case 40:
                            img.setImageResource(R.drawable.ic_flecha_der_40);
                            break;
                        case 48:
                            img.setImageResource(R.drawable.ic_flecha_der_48);
                            break;
                        default:
                            break;
                    }
                }else if(b.getId() == R.id.btnIzquierda){
                    switch (tamano){
                        case 16:
                            img.setImageResource(R.drawable.ic_flecha_izq_16);
                            break;
                        case 24:
                            img.setImageResource(R.drawable.ic_flecha_izq_24);
                            break;
                        case 32:
                            img.setImageResource(R.drawable.ic_flecha_izq_32);
                            break;
                        case 40:
                            img.setImageResource(R.drawable.ic_flecha_izq_40);
                            break;
                        case 48:
                            img.setImageResource(R.drawable.ic_flecha_izq_48);
                            break;
                        default:
                            break;
                    }
                }else{
                    switch (tamano){
                        case 16:
                            img.setImageResource(R.drawable.ic_mic_16);
                            break;
                        case 24:
                            img.setImageResource(R.drawable.ic_mic_24);
                            break;
                        case 32:
                            img.setImageResource(R.drawable.ic_mic_32);
                            break;
                        case 40:
                            img.setImageResource(R.drawable.ic_mic_40);
                            break;
                        case 48:
                            img.setImageResource(R.drawable.ic_mic_48);
                            break;
                        default:
                            break;
                    }
                }
            }else if(b.getId() != R.id.txtOperacion){
                Button boton = (Button) b;
                boton.setTextSize(config.setearTamano(this));
            }
        }
        if(cfgActual.getVibracion().getDescripcion().equals("Siempre")){
            vibrarBoton = true;
        }else{
            vibrarBoton = false;
        }
    }
}
