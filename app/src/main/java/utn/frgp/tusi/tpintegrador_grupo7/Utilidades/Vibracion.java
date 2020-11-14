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

    public void VibracionGrabar()
    {
        getVibrator();
        long tiempo = 100; //en milisegundos
        v.vibrate(tiempo);
    }

    public void VibracionError()
    {
        getVibrator();
        long[] tiempo = {200,
                        300,200,300};
        v.vibrate(tiempo, -1);
    }

    public void VibracionBoton()
    {
        getVibrator();
        long tiempo = 50; //en milisegundos
        v.vibrate(tiempo);
    }
}
