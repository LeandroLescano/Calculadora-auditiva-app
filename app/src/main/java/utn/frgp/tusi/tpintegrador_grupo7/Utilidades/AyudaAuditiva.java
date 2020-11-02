package utn.frgp.tusi.tpintegrador_grupo7.Utilidades;

import android.content.Context;
import android.content.res.Resources;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import org.w3c.dom.Text;

import java.util.Locale;

import utn.frgp.tusi.tpintegrador_grupo7.R;

public class AyudaAuditiva {

    private TextToSpeech mTTS;
    private String textoAudio;
    private Locale locale;
    private Context context;
    private String POTENCIA;
    private String TAN;
    private String SIN;
    private String COS;
    private String ARCTAN;
    private String ARCSIN;
    private String ARCCOS;
    private String RAIZ;
    private String PI;

    public AyudaAuditiva(Context context){
        this.context = context;
        locale = new Locale("es", "US");
        mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int estado) {
                if(estado == TextToSpeech.SUCCESS){
                    int resultado = mTTS.setLanguage(locale);
                    if(resultado == TextToSpeech.LANG_MISSING_DATA || resultado == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Lenguaje no compatible");
                    }
                }else{
                    Log.e("TTS", "Error en inicialización");
                }
            }
        });
        POTENCIA = context.getString(R.string.potencia);
        TAN= context.getString(R.string.tan);
        SIN = context.getString(R.string.sin);
        COS = context.getString(R.string.cos);
        ARCTAN = context.getString(R.string.taninverso);
        ARCSIN = context.getString(R.string.sininverso);
        ARCCOS = context.getString(R.string.cosinverso);
        RAIZ = context.getString(R.string.raiz);
        PI = context.getString(R.string.pi);
    }

    public void emitirAudio(String texto){
        textoAudio = texto;
        if(texto.length() > 0 && mTTS != null){
            switch (texto){
                case "x":
                    textoAudio = "por";
                    break;
                case "/":
                    textoAudio = "dividir";
                    break;
                case "-":
                    textoAudio = "menos";
                    break;
                case "(":
                    textoAudio = "abrir paréntesis";
                    break;
                case ")":
                    textoAudio = "cerrar paréntesis";
                    break;
                case "Lg":
                    textoAudio = "logaritmo decimal";
                    break;
                case "Ln":
                    textoAudio = "logaritmo natural";
                    break;
                default:
                    if(texto.equals(POTENCIA)){
                        textoAudio = "potencia";
                    }else if(texto.equals(TAN)){
                        textoAudio = "tangente";
                    }else if(texto.equals(SIN)){
                        textoAudio = "seno";
                    }else if(texto.equals(COS)){
                        textoAudio = "coseno";
                    }else if(texto.equals(ARCTAN)){
                        textoAudio = "tangente inversa";
                    }else if(texto.equals(ARCSIN)){
                        textoAudio = "seno inverso";
                    }else if(texto.equals(ARCCOS)){
                        textoAudio = "coseno inverso";
                    }else if(texto.equals(RAIZ)){
                        textoAudio = "raiz cuadrada";
                    }else if(texto.equals(PI)){
                        textoAudio = "pi";
                    }
            }
            mTTS.speak(textoAudio, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}
