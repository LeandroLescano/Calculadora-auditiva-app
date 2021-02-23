package utn.frgp.tusi.tpintegrador_grupo7.Utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos.ConfiguracionDao;
import utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos.HistorialDao;
import utn.frgp.tusi.tpintegrador_grupo7.CalculadoraBasica;
import utn.frgp.tusi.tpintegrador_grupo7.CalculadoraCientifica;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Configuracion;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Decimales;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Operacion;
import utn.frgp.tusi.tpintegrador_grupo7.R;
import utn.frgp.tusi.tpintegrador_grupo7.Interface.ChangeCalculator;

import static android.Manifest.permission.RECORD_AUDIO;

public class ComandosVoz implements RecognitionListener  {

    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;
    private boolean isListening = false;
    private EditText operacion;
    private TextView resultado;
    private AyudaAuditiva audio;
    private Button grabando, procesando;
    private ConstraintLayout fondoProcesando;
    private ArrayList<View> botones;
    private Vibracion vibrar;
    private Vibrator vibrator;
    private HistorialDao hist;
    private Context context;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean cambiarCalculadora = false;
    private DecimalFormat formatter;
    private ConfiguracionDao config;
    private Decimales d;
    private int cantidad;
    private String valorActual;
    private int formatoActual;
    private ChangeCalculator changeCalc;


    public ComandosVoz(Context context, Activity activity, EditText operacion, TextView resultado, Button grabando, Button procesando, ConstraintLayout fondoProcesando, LinearLayout layout, int cant , int format , ChangeCalculator cc){
        this.operacion = operacion;
        this.resultado = resultado;
        this.grabando = grabando;
        this.procesando = procesando;
        this.fondoProcesando = fondoProcesando;
        this.context = context;
        botones = layout.getTouchables();
        audio = new AyudaAuditiva(context);
        ActivityCompat.requestPermissions(activity, new String[]{RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(this);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrar = new Vibracion();
        vibrar = vibrar.getManager(context);
        hist = new HistorialDao();
        formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMAN);
        cantidad = cant;
        formatoActual = format;
        this.changeCalc = cc;
    }

   /*public boolean getActivity(){
        boolean changeActivity = false;
        boolean textLetras = true;
        ArrayList<String> matchesFound = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matchesFound != null) {
            for(String match : matchesFound){
        return  changeActivity;
    }*/

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i("tag", "Ready For Speech");
    }

    @Override
    public void onBeginningOfSpeech() {
        grabando.setVisibility(Button.VISIBLE);
        vibrar.VibracionGrabar();
        if(!isListening)
            isListening = true;
    }

    @Override
    public void onRmsChanged(float v) {
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        Log.e("buffer", "onBufferReceived()");
    }

    @Override
    public void onEndOfSpeech() {
        Log.i("Msg", "Procesando...");
    }

    @Override
    public void onError(int i) {
        if(!isListening){
            if (i == SpeechRecognizer.ERROR_AUDIO) {
                Log.i("error", "error AUDIO");
            }
            if (i == SpeechRecognizer.ERROR_NO_MATCH) {
                Log.i("error match", "error NO MATCH");
                audio.emitirAudio("Ingreso inentendible");
            }
        }else{
            isListening = false;
        }
        vibrar.VibracionError();
        grabando.setVisibility(Button.INVISIBLE);
        fondoProcesando.setVisibility(ConstraintLayout.INVISIBLE);
        procesando.setVisibility(Button.INVISIBLE);
        cambiarEstadoBotones(true);
    }

    @Override
    public void onResults(Bundle bundle) {
        if(grabando.getVisibility() == Button.VISIBLE){
            grabando.setVisibility(Button.INVISIBLE);
        }
        fondoProcesando.setVisibility(ConstraintLayout.INVISIBLE);
        procesando.setVisibility(Button.INVISIBLE);
        cambiarEstadoBotones(true);
        if(isListening){
            isListening = false;
        }
        ArrayList<String> matchesFound = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        boolean textLetras = true;
        if (matchesFound != null) {
            for(String match : matchesFound){
                textLetras = true;
                if(!soloLetras(match)){
                    textLetras = false;
                }
                if(!textLetras){
                    String opTraducida = match.toLowerCase();
                    opTraducida = traducirOperacion(opTraducida);
                    if(opTraducida != null){
                        operacion.setText(opTraducida.replace(" ", ""));
                        operacion.setSelection(operacion.length());
                        break;
                    }
                }
            }
            if(textLetras){
                        if(matchesFound.contains("calculadora básica") ) {
                            cambiarCalculadora = true;
                            this.changeCalc.cambiarPantalla("calculadoraBasica");
                        }
                if(matchesFound.contains("calculadora científica") ) {
                    cambiarCalculadora = true;
                    this.changeCalc.cambiarPantalla("calculadoraCientifica");
                }
                if(matchesFound.contains("Configuración") ) {
                    cambiarCalculadora = true;
                    this.changeCalc.cambiarPantalla("configuracion");
                }
                if(matchesFound.contains("Historial de operaciones") ) {
                    cambiarCalculadora = true;
                    this.changeCalc.cambiarPantalla("historial");
                }
                    if(cambiarCalculadora == false){
                        audio.emitirAudio("Ingreso incorrecto");
                    }
            }else{
                Float resultadoOperacion = Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica(cantidad,operacion.getText().toString(),formatoActual));
                formatter.setMaximumFractionDigits(cantidad);
                if(resultadoOperacion != null && Float.isNaN(resultadoOperacion)){
                    resultado.setText("Error matemático");
                    audio.emitirAudio("Error matemático");
                    vibrar.VibracionError();
                }else if(resultadoOperacion != null && resultadoOperacion%1 == 0 && resultadoOperacion != -1){
                    valorActual = formatter.format(resultadoOperacion);
                    resultado.setText(String.valueOf(Math.round(resultadoOperacion)));
                    audio.emitirAudio("El resultado de " + operacion.getText() + " es " + resultado.getText());
                    hist.cargarOperacion(operacion.getText() + "=" + resultado.getText(), context);
                    vibrar.VibracionGrabar();
                }else if (resultadoOperacion != null && resultadoOperacion != -1){
                    valorActual = formatter.format(resultadoOperacion);
                    resultado.setText(valorActual);
                    audio.emitirAudio("El resultado de " + operacion.getText() + " es " + resultado.getText());
                    hist.cargarOperacion(operacion.getText() + "=" + resultado.getText(), context);
                    vibrar.VibracionGrabar();
                }else{
                    operacion.setText("");
                    resultado.setText(R.string.ing_incorrecto);
                    audio.emitirAudio("Ingreso incorrecto");
                    vibrar.VibracionError();
                }
            }
            Log.e("onResults", matchesFound.get(0));
        }
        Log.e("onResults", String.valueOf(bundle));
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        Log.e("onPartialResults", String.valueOf(bundle));
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.e("event", bundle.toString());
    }

    private void cambiarEstadoBotones(boolean estado){
        for(View v : botones){
            if(v.getId() != R.id.btnMic && v.getId() != R.id.txtOperacion){
                v.setClickable(estado);
            }
        }
    }

    public boolean startStop() {
        if (isListening) {
            isListening = false;
            grabando.setVisibility(Button.INVISIBLE);
            fondoProcesando.setVisibility(ConstraintLayout.VISIBLE);
            procesando.setVisibility(Button.VISIBLE);
            speechRecognizer.stopListening();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(fondoProcesando.getVisibility() == ConstraintLayout.VISIBLE){
                        fondoProcesando.setVisibility(ConstraintLayout.INVISIBLE);
                        procesando.setVisibility(Button.INVISIBLE);
                        cambiarEstadoBotones(true);
                        audio.emitirAudio("Ingreso inentendible");
                    }
                }
            }, 10000);
            Log.i("onClick", "stopListening");

        } else {
            isListening = true;
            cambiarEstadoBotones(false);
            speechRecognizer.startListening(speechIntent);
            Log.i("onClick", "startListening");
        }
        return cambiarCalculadora;
    }

    public static String traducirOperacion(String opTraducida){
        if(opTraducida.contains("menos")){
            opTraducida = opTraducida.replace("menos","-");
        }
        if(opTraducida.contains("más") || opTraducida.contains("mas")){
            opTraducida = opTraducida.replace("más","+");
            opTraducida = opTraducida.replace("mas","+");
        }
        opTraducida = opTraducida.replace("--", "+")
                .replace("+-", "-")
                .replace("-+", "-")
                .replace("++","+")
                .replace(",",".")
                .replace("- ", "-")
                .replace("+ ", "+");
        if(opTraducida.contains("*")){
            opTraducida = opTraducida.replace("*","x");
        }
        if(opTraducida.contains("por")){
            opTraducida = opTraducida.replace("por","x");
        }
        if(opTraducida.contains("dividido")){
            opTraducida = opTraducida.replace("dividido","/");
        }
        if(opTraducida.contains("elevado a la")){
            opTraducida = opTraducida.replace("elevado a la","^");
        }
        if(opTraducida.contains("raíz cuadrada de")){
            opTraducida = opTraducida.replace("raíz cuadrada de","√");
        }
        if(opTraducida.contains("a el cuadrado") || opTraducida.contains("al cuadrado")){
            opTraducida = opTraducida.replace("a el cuadrado","^2");
            opTraducida = opTraducida.replace("al cuadrado","^2");
        }
        if(opTraducida.contains("a el cubo") || opTraducida.contains("al cubo")){
            opTraducida = opTraducida.replace("a el cubo","^3");
            opTraducida = opTraducida.replace("al cubo","^3");
        }
        if(opTraducida.contains("raíz de")){
            opTraducida = opTraducida.replace("raíz de","√");
        }
        if(opTraducida.contains("pi")){
            opTraducida = opTraducida.replace("pi",String.valueOf(Math.PI).substring(0,10));
        }
        try {
            opTraducida = deteccionFuncion("logaritmo natural", "ln", opTraducida);
            opTraducida = deteccionFuncion("logaritmo decimal", "lg", opTraducida);
            opTraducida = deteccionFuncion("logaritmo", "lg", opTraducida);
            opTraducida = deteccionFuncion("coseno inverso", "arccos", opTraducida);
            opTraducida = deteccionFuncion("coseno", "cos", opTraducida);
            opTraducida = deteccionFuncion("tangente inversa", "arctan", opTraducida);
            opTraducida = deteccionFuncion("tangente", "tan", opTraducida);
            opTraducida = deteccionFuncion("seno inverso", "arcsin", opTraducida);
            opTraducida = deteccionFuncion("seno", "sin", opTraducida);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return opTraducida;
    }

    private static String deteccionFuncion(String palabra, String operador, String opTraducida) throws Exception{
        while(opTraducida.contains(palabra)){
        String[] split = opTraducida.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.])(?=[\\d.])|(?<=[-+x/])(?=[^-+x/])|(?<=[^-+x/])(?=[-+x/])");
            String number = "0";
            int posLog = -1;
            for(int x=0; x< split.length ;x++){
                if(split[x].contains(palabra)){
                    posLog = x;
                    if(split[x+1].contains("-")){
                        number = "-" + split[x+2];
                        opTraducida = opTraducida.replace(split[posLog] + split[posLog+1] + split[posLog+2], operador + "(" + number + ")");
                    }else{
                        number = split[x+1];
                        opTraducida = opTraducida.replace(split[posLog] + split[posLog+1], operador + "(" + number + ")");
                    }
                }
            }

        }
        return opTraducida;
    }

    public boolean soloLetras(String text){
        char[] chars = text.trim().toCharArray();
        for (char c : chars) {
            if(c >= 48 && c <= 57) {
                return false;
            }
        }
        return true;
    }

}
