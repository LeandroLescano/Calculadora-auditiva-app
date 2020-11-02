package utn.frgp.tusi.tpintegrador_grupo7;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import utn.frgp.tusi.tpintegrador_grupo7.Utilidades.AyudaAuditiva;
import utn.frgp.tusi.tpintegrador_grupo7.Utilidades.ComandosVoz;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Button alertaGrabando, alertaProcesando;
    private ConstraintLayout fondoProcesando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basica);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alertaGrabando = findViewById(R.id.alerta_grabando);
        alertaProcesando = findViewById(R.id.alerta_procesando);
        fondoProcesando = findViewById(R.id.background_procesando);
        operacion = findViewById(R.id.txtOperacion);
        resultado = findViewById(R.id.txtResultado);
        resultado.setAlpha((float) 0.5);
        resultado.setText("0");

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

        //Consultar configuración para cambiar aspecto.
        //formatearAspecto();
        audio = new AyudaAuditiva(this);
    }

    public void ingresarDigito(View view){
        Button digit = (Button)view;
        buttonText = digit.getText().toString();
        posActual = operacion.getSelectionEnd();
        MuestraVieja = operacion.getText().toString();
        audio.emitirAudio(buttonText);
        if(!ObtenerOperador()){
            operacion.setText(MuestraVieja.substring(0,posActual).concat(buttonText.concat(MuestraVieja.substring(posActual))));
            Numero = Numero + buttonText;
        }
        if(posActual < operacion.getText().length() && view.getId() != R.id.btnPorcentaje){
            posActual++;
            operacion.setSelection(posActual);
        }
    }

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
        audio.emitirAudio("borrar");
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
            if(charCode < 48 || charCode > 57){
                ultimaPos=x+1;
            }
        }
        try{
            numeroFloat = Float.parseFloat(opActual.substring(ultimaPos))/100;
            posNumero = muestra.lastIndexOf(opActual.substring(ultimaPos));
            String finalOperacion = muestra.substring(posNumero + opActual.substring(ultimaPos).length());
            if(numeroFloat%2 == 0){
                numeroInt = Math.round(numeroFloat);
                operacion.setText(muestra.substring(0, posNumero).concat(numeroInt.toString()).concat(finalOperacion));
                posActual = operacion.getText().toString().lastIndexOf(numeroInt.toString())+numeroFloat.toString().length()-2;
            }else{
                operacion.setText(muestra.substring(0, posNumero).concat(numeroFloat.toString()).concat(finalOperacion));
                posActual = operacion.getText().toString().lastIndexOf(numeroFloat.toString())+numeroFloat.toString().length();
            }
            operacion.setSelection(posActual);
        }catch (NumberFormatException e){

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
    }

    public void eliminarOperacion(View view){
        operacion.setText("");
        resultado.setText("0");
        resultado.setAlpha((float) 0.5);
        cuenta = 0f;
        Numero = "";
        audio.emitirAudio("Borrar todo");
    }

    public void calcularOperacion(View view){
        Calcular(Numero);
        signo = "=";
        Calcular(Numero);
        Numero = "0";
    }

    public boolean ObtenerOperador(){
        String[] Operadores = { "+", "-", "x", "/", "="};
        boolean Error = true;
        if (Arrays.asList(Operadores).contains(buttonText)) {
            if(MuestraVieja.isEmpty()){
                if(buttonText.equals("-")) {
                    Error = false;
                }
                else{
                    Error = true;
                }
            }
            else {
                    String UltimoCaracter= MuestraVieja.substring(MuestraVieja.length()-1);
                    if(Arrays.asList(Operadores).contains(UltimoCaracter)){
                        Error = true;
                    }
                    else {
                        operacion.setText(MuestraVieja + buttonText);
                        signo = buttonText;
                        Calcular(Numero);
                        MuestraVieja = Numero + buttonText;
                        Numero = "";
                        Error = true;
                    }
                }
        }
        else{
            Error = false;
        }
        return  Error;
    }

    public void Calcular(String Num){
        switch (signo) {
            case "+":
                cuenta += Float.parseFloat(Num);
                break;
            case "-":
                cuenta -= Float.parseFloat(Num);
                break;
            case "*":
                cuenta = cuenta * Float.parseFloat(Num);
                break;
            case "/":
                if (Float.parseFloat(Num) > 0) {
                    cuenta = cuenta / Float.parseFloat(Num);
                }
                break;
            case "=":
                resultado.setText(cuenta+"");
                resultado.setAlpha((float) 1);
                break;
        }
    }

    public void comandoDeVoz(View view){
        ImageView micButton = (ImageView) view;
        if(voz == null){
            voz = new ComandosVoz(this, this, operacion, alertaGrabando, alertaProcesando, fondoProcesando);
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
}
