package utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Color;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Configuracion;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Estado;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Tamano;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Tipografia;

public class ConfiguracionDao {

    Configuracion config;
    Color color;
    Tipografia tipografia;
    Tamano tamano;
    Estado estado, vibracion, sonido;


    public Configuracion traerConfiguracion(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        config = new Configuracion();
        color = new Color();
        tipografia = new Tipografia();
        tamano = new Tamano();
        vibracion = new Estado();
        sonido = new Estado();

        Cursor configs = BasedeDatos.rawQuery("select id_color, id_tipografia, id_tamano, estado_vibracion, estado_sonido from configuracion", null);
        if(configs.moveToFirst()){
            while(configs.moveToNext()){

                color = traerColor(Integer.parseInt(configs.getString(0)), context);
                tipografia = traerTipografia(Integer.parseInt(configs.getString(1)), context);
                tamano = traerTamano(Integer.parseInt(configs.getString(2)), context);
                vibracion = traerEstado(Integer.parseInt(configs.getString(3)), context);
                sonido = traerEstado(Integer.parseInt(configs.getString(4)), context);

                config.setColor(color);
                config.setTipografia(tipografia);
                config.setTamano(tamano);
                config.setVibracion(vibracion);
                config.setSonido(sonido);

            }
        }
        return config;
    }


    public boolean cargarConfiguracion(int idColor, int idTipografia, int idTamano, int idEstadoVibracion, int idEstadoSonido, Context context) {

        config = new Configuracion();
        color = new Color();
        tipografia = new Tipografia();
        tamano = new Tamano();
        vibracion = new Estado();
        sonido = new Estado();

            ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
            SQLiteDatabase BaseDatos = admin.getWritableDatabase();
            try {
                ContentValues registro = new ContentValues();

                registro.put("id_color", idColor);
                registro.put("id_tipografia", idTipografia);
                registro.put("id_tamano", idTamano);
                registro.put("estado_vibracion", idEstadoVibracion);
                registro.put("estado_sonido", idEstadoSonido);
                BaseDatos.insert("configuracion", null, registro);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                BaseDatos.close();
            }

        return true;
    }

    public Color traerColor (int idColor, Context context) {
        color = new Color();
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        Cursor colores = BasedeDatos.rawQuery("select id, color from colores where id=" + idColor, null);
        if (colores.moveToFirst()) {
            while (colores.moveToNext()) {

                color.setId(idColor);
                color.setColor(colores.getString(1));

            }

        }
        return color;
    }

    public Tipografia traerTipografia (int idTipografia, Context context) {
        tipografia = new Tipografia();
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        Cursor tipogs = BasedeDatos.rawQuery("select id, tipografia from tipografias where id=" + idTipografia, null);
        if (tipogs.moveToFirst()) {
            while (tipogs.moveToNext()) {

                tipografia.setId(idTipografia);
                tipografia.setTipografia(tipogs.getString(1));

            }

        }
        return tipografia;
    }

    public Tamano traerTamano (int idTamano, Context context) {
        tamano = new Tamano();
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        Cursor tams = BasedeDatos.rawQuery("select id, tamano from tamanos where id=" + idTamano, null);
        if (tams.moveToFirst()) {
            while (tams.moveToNext()) {

                tamano.setId(idTamano);
                tamano.setTamano(Integer.parseInt(tams.getString(1)));

            }

        }
        return tamano;
    }

    public Estado traerEstado (int idEstado, Context context) {
        estado = new Estado();
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        Cursor ests = BasedeDatos.rawQuery("select id, descripcion from estados where id=" + idEstado, null);
        if (ests.moveToFirst()) {
            while (ests.moveToNext()) {

                estado.setId(idEstado);
                estado.setDescripcion(ests.getString(1));

            }

        }
        return estado;
    }

}
