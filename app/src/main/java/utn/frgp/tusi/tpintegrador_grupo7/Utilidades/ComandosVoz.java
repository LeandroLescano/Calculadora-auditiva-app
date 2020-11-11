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
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Operacion;
import utn.frgp.tusi.tpintegrador_grupo7.R;

import static android.Manifest.permission.RECORD_AUDIO;

public class ComandosVoz implements RecognitionListener {

    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;
    private boolean isListening = false;
    private EditText operacion;
    private TextView resultado;
    private AyudaAuditiva audio;
    private Button grabando, procesando;
    private ConstraintLayout fondoProcesando;
    private ArrayList<View> botones;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public ComandosVoz(Context context, Activity activity, EditText operacion, TextView resultado, Button grabando, Button procesando, ConstraintLayout fondoProcesando, LinearLayout layout){
        this.operacion = operacion;
        this.resultado = resultado;
        this.grabando = grabando;
        this.procesando = procesando;
        this.fondoProcesando = fondoProcesando;
        botones = layout.getTouchables();
        audio = new AyudaAuditiva(context);
        ActivityCompat.requestPermissions(activity, new String[]{RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(this);
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i("tag", "Ready For Speech");
    }

    @Override
    public void onBeginningOfSpeech() {
        grabando.setVisibility(Button.VISIBLE);
        Log.i("Msg", "Grabando...");
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
                if(textLetras && !soloLetras(match)){
                    textLetras = false;
                }
                String opTraducida = match.toLowerCase();

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
                if(opTraducida.contains("elevado a la")){
                    opTraducida = opTraducida.replace("elevado a la","^");
                }
                if(opTraducida.contains("raíz cuadrada de")){
                    opTraducida = opTraducida.replace("raíz cuadrada de","√");
                }
                if(opTraducida.contains("raíz de")){
                    opTraducida = opTraducida.replace("raíz","√");
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
                operacion.setText(opTraducida.replace(" ", ""));
                operacion.setSelection(operacion.length());
            }
            if(textLetras){
                audio.emitirAudio("Ingreso incorrecto");
            }

            Float resultadoOperacion = Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica(operacion.getText().toString()));

            if(Float.isNaN(resultadoOperacion)){
                resultado.setText("Error matemático");
                audio.emitirAudio("Error matemático");
            }else if(resultadoOperacion%1 == 0 && resultadoOperacion != -1){
                resultado.setText(String.valueOf(Math.round(resultadoOperacion)));
                audio.emitirAudio("El resultado de " + operacion.getText() + " es " + resultado.getText());
            }else if (resultadoOperacion != -1){
                resultado.setText(resultadoOperacion.toString());
                audio.emitirAudio("El resultado de " + operacion.getText() + " es " + resultado.getText());
            }else{
                operacion.setText("");
                resultado.setText(R.string.ing_incorrecto);
                audio.emitirAudio("Ingreso incorrecto");
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

    public void startStop() {
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
    }

    private String deteccionFuncion(String palabra, String operador, String opTraducida) throws Exception{
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
