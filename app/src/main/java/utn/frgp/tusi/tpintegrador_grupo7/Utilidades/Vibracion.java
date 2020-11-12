package utn.frgp.tusi.tpintegrador_grupo7.Utilidades;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;



public class Vibracion {

    private static Vibracion me;
    private Context context;

    Vibrator v = null;

    private Vibrator getVibrator(){
        if(v == null){
            v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        return v;
    }

    public static Vibracion getManager(Context context) {
        if(me == null){
            me = new Vibracion();
        }
        me.setContext(context);
        return me;
    }

    private void setContext(Context context){
        this.context = context;
    }

    public void VibracionResultado()
    {
        getVibrator();
        long tiempo = 500; //en milisegundos
        v.vibrate(tiempo);
    }

    public void VibracionError()
    {
        getVibrator();
        long[] tiempo = {400,
                        500,400,500};
        v.vibrate(tiempo, -1);
    }
}
