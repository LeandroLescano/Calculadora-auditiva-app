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
        boolean encontrado = false, textLetras = true;
        if (matchesFound != null) {
            for(String match : matchesFound){
                if(textLetras && !soloLetras(match)){
                    textLetras = false;
                }
                String opTraducida = match.toLowerCase();
                if(opTraducida.contains("√") || opTraducida.contains("/") || opTraducida.contains("-") || opTraducida.contains("+") || opTraducida.contains("menos")){
                    if(opTraducida.contains("menos")){
                        opTraducida = opTraducida.replace("menos","-");
                    }
                    opTraducida = opTraducida.replace("--", "+")
                            .replace("+-", "-")
                            .replace("-+", "-")
                            .replace("++","+");
                    encontrado = true;
                }
                if(opTraducida.contains("*")){
                    opTraducida = opTraducida.replace("*","x");
                    encontrado = true;
                }
                if(opTraducida.contains("por")){
                    opTraducida = opTraducida.replace("por","x");
                    encontrado = true;
                }
                if(opTraducida.contains("elevado a la")){
                    opTraducida = opTraducida.replace("elevado a la","^");
                    encontrado = true;
                }
                if(encontrado){
                    operacion.setText(opTraducida.replace(" ", ""));
                    operacion.setSelection(operacion.length());
                    break;
                }
            }
            if(textLetras){
                audio.emitirAudio("Ingreso incorrecto");
            }else if(!encontrado){
                operacion.setText(matchesFound.get(0).replace(" ", ""));
                operacion.setSelection(operacion.length());
            }

            Float resultadoOperacion = Operacion.calcularOperacionBasica(operacion.getText().toString());
            if(resultadoOperacion%1 == 0 && resultadoOperacion != -1){
                resultado.setText(String.valueOf(Math.round(resultadoOperacion)));
            }else if (resultadoOperacion != -1){
                resultado.setText(resultadoOperacion.toString());
            }
            audio.emitirAudio("El resultado de " + operacion.getText() + " es " + resultado.getText());
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
