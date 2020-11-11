package utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Color;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Configuracion;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Estado;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Operacion;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Tamano;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Tipografia;

public class ConfiguracionDao {

    Configuracion config;
    Color color, colorBoton;
    Tipografia tipografia;
    Tamano tamano;
    Estado estado, vibracion, sonido;

    private ArrayList<Color> listCol;
    private ArrayList<Tamano> listTam;
    private ArrayList<Tipografia> listTip;
    private ArrayList<Estado> listEst;



    public Configuracion traerConfiguracion(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

        color = new Color();
        colorBoton = new Color();
        tipografia = new Tipografia();
        tamano = new Tamano();
        vibracion = new Estado();
        sonido = new Estado();

        Cursor configs = BasedeDatos.rawQuery("select id_color, id_tipografia, id_tamano, estado_vibracion, estado_sonido, id_colorBoton from configuracion", null);
        if(configs.moveToFirst()){

            config = new Configuracion();

            color = traerColor(Integer.parseInt(configs.getString(0)), context);
            config.setColor(color);

            tipografia = traerTipografia(Integer.parseInt(configs.getString(1)), context);
            config.setTipografia(tipografia);

            tamano = traerTamano(Integer.parseInt(configs.getString(2)), context);
            config.setTamano(tamano);

            vibracion = traerEstado(Integer.parseInt(configs.getString(3)), context);
            config.setVibracion(vibracion);

            sonido = traerEstado(Integer.parseInt(configs.getString(4)), context);
            config.setSonido(sonido);

            colorBoton = traerColor(Integer.parseInt(configs.getString(5)), context);
            config.setColorBoton(colorBoton);

            /*
            while(configs.moveToNext()){

                config = new Configuracion();
                color = traerColor(Integer.parseInt(configs.getString(0)), context);
                tipografia = traerTipografia(Integer.parseInt(configs.getString(1)), context);
                tamano = traerTamano(Integer.parseInt(configs.getString(2)), context);
                vibracion = traerEstado(Integer.parseInt(configs.getString(3)), context);
                sonido = traerEstado(Integer.parseInt(configs.getString(4)), context);
                colorBoton = traerColor(Integer.parseInt(configs.getString(5)), context);

                config.setColor(color);
                config.setColorBoton(colorBoton);
                config.setTipografia(tipografia);
                config.setTamano(tamano);
                config.setVibracion(vibracion);
                config.setSonido(sonido);

            }
            */

        }

        return config;
    }


    public boolean cargarConfiguracion(int idColor, int idColorBoton, int idTipografia, int idTamano, int idEstadoVibracion, int idEstadoSonido, Context context) {


            ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
            SQLiteDatabase BaseDatos = admin.getWritableDatabase();
            try {
                ContentValues registro = new ContentValues();

                registro.put("id_color", idColor);
                registro.put("id_colorBoton", idColorBoton);
                registro.put("id_tipografia", idTipografia);
                registro.put("id_tamano", idTamano);
                registro.put("estado_vibracion", idEstadoVibracion);
                registro.put("estado_sonido", idEstadoSonido);
                //Ver si funciona - tiene que haber config inicial ya en la BD
                BaseDatos.update("configuracion", registro, null, null);
                //BaseDatos.insert("configuracion", null, registro);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                BaseDatos.close();
            }

        return true;
    }

    public Color traerColor (int idColor, Context context) {

        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        Cursor colores = BasedeDatos.rawQuery("select id, color from colores where id=" + idColor, null);
        if (colores.moveToFirst()) {

            color = new Color();
            color.setId(idColor);
            color.setColor(colores.getString(1));

            while (colores.moveToNext()) {

                color = new Color();
                color.setId(idColor);
                color.setColor(colores.getString(1));

            }

        }
        return color;
    }

    public Tipografia traerTipografia (int idTipografia, Context context) {

        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        Cursor tipogs = BasedeDatos.rawQuery("select id, tipografia from tipografias where id=" + idTipografia, null);
        if (tipogs.moveToFirst()) {

            tipografia = new Tipografia();
            tipografia.setId(idTipografia);
            tipografia.setTipografia(tipogs.getString(1));

            while (tipogs.moveToNext()) {

                tipografia = new Tipografia();
                tipografia.setId(idTipografia);
                tipografia.setTipografia(tipogs.getString(1));

            }

        }
        return tipografia;
    }

    public Tamano traerTamano (int idTamano, Context context) {

        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        Cursor tams = BasedeDatos.rawQuery("select id, tamano from tamanos where id=" + idTamano, null);
        if (tams.moveToFirst()) {

            tamano = new Tamano();
            tamano.setId(idTamano);
            tamano.setTamano(Integer.parseInt(tams.getString(1)));

            while (tams.moveToNext()) {

                tamano = new Tamano();
                tamano.setId(idTamano);
                tamano.setTamano(Integer.parseInt(tams.getString(1)));

            }

        }
        return tamano;
    }

    public Estado traerEstado (int idEstado, Context context) {

        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        Cursor ests = BasedeDatos.rawQuery("select id, descripcion from estados where id=" + idEstado, null);
        if (ests.moveToFirst()) {

            estado = new Estado();
            estado.setId(idEstado);
            estado.setDescripcion(ests.getString(1));

            while (ests.moveToNext()) {

                estado = new Estado();
                estado.setId(idEstado);
                estado.setDescripcion(ests.getString(1));

            }

        }
        return estado;
    }

    public ArrayList<Color> listarColores(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        listCol = new ArrayList<>();


        Cursor cfg = BasedeDatos.rawQuery("select id, color from colores", null);
        if(cfg.moveToFirst()){

            color = new Color();
            color.setId(Integer.parseInt(cfg.getString(0)));
            color.setColor(cfg.getString(1));
            listCol.add(color);


            while(cfg.moveToNext()){

                color = new Color();
                color.setId(Integer.parseInt(cfg.getString(0)));
                color.setColor(cfg.getString(1));
                listCol.add(color);

            }
        }
        return listCol;
    }

    public ArrayList<Tipografia> listarTipografias(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

        listTip = new ArrayList<>();


        Cursor cfg = BasedeDatos.rawQuery("select id, tipografia from tipografias", null);
        if(cfg.moveToFirst()){

            tipografia = new Tipografia();
            tipografia.setId(Integer.parseInt(cfg.getString(0)));
            tipografia.setTipografia(cfg.getString(1));
            listTip.add(tipografia);


            while(cfg.moveToNext()){

                tipografia = new Tipografia();
                tipografia.setId(Integer.parseInt(cfg.getString(0)));
                tipografia.setTipografia(cfg.getString(1));
                listTip.add(tipografia);

            }
        }
        return listTip;
    }

    public ArrayList<Tamano> listarTamanos(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

        listTam = new ArrayList<>();


        Cursor cfg = BasedeDatos.rawQuery("select id, tamano from tamanos", null);
        if(cfg.moveToFirst()){

            tamano = new Tamano();
            tamano.setId(Integer.parseInt(cfg.getString(0)));
            tamano.setTamano(Integer.parseInt(cfg.getString(1)));
            listTam.add(tamano);


            while(cfg.moveToNext()){

                tamano = new Tamano();
                tamano.setId(Integer.parseInt(cfg.getString(0)));
                tamano.setTamano(Integer.parseInt(cfg.getString(1)));
                listTam.add(tamano);

            }
        }
        return listTam;
    }

    public ArrayList<Estado> listarEstados(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

        listEst = new ArrayList<>();


        Cursor cfg = BasedeDatos.rawQuery("select id, descripcion from estados", null);
        if(cfg.moveToFirst()){

            estado = new Estado();
            estado.setId(Integer.parseInt(cfg.getString(0)));
            estado.setDescripcion(cfg.getString(1));
            listEst.add(estado);


            while(cfg.moveToNext()){

                estado = new Estado();
                estado.setId(Integer.parseInt(cfg.getString(0)));
                estado.setDescripcion(cfg.getString(1));
                listEst.add(estado);

            }
        }
        return listEst;
    }

}
