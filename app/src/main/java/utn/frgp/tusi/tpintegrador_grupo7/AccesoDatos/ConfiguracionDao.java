package utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Color;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Configuracion;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Decimales;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Estado;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Formato;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Tamano;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Tipografia;
import utn.frgp.tusi.tpintegrador_grupo7.R;

public class ConfiguracionDao {

    private Configuracion config;
    private Color color, colorBoton;
    private Tipografia tipografia;
    private Tamano tamano;
    private Decimales decimal;
    private Estado estado, vibracion, sonido;
    private Decimales decimales;
    private ArrayList<Color> listCol;
    private ArrayList<Tamano> listTam;
    private ArrayList<Tipografia> listTip;
    private ArrayList<Estado> listEst;
    private ArrayList<Decimales> listdecimal;
    private ArrayList<Formato> listarFormato;
    private int Rojo, Azul, Amarillo, Blanco, Negro;
    private Typeface Helvetica, Verdana, ComicSans, Arial, Roboto;
    private String dos,tres,cuatro,todos;
    private Formato formato;

    public Configuracion traerConfiguracion(Context context){

        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

        color = new Color();
        colorBoton = new Color();
        tipografia = new Tipografia();
        tamano = new Tamano();
        vibracion = new Estado();
        sonido = new Estado();
        decimales = new Decimales();
        formato = new Formato();

//        Cursor configs = BasedeDatos.rawQuery("select id_color, id_tipografia, id_tamano, estado_vibracion, estado_sonido, id_colorBoton from configuracion", null);
        Cursor configs = BasedeDatos.rawQuery("select c.id, c.color, tipografias.id, tipografias.tipografia, tamanos.id, tamanos.tamano, v.id, v.descripcion, s.id, s.descripcion, cb.id, cb.color, dc.id, dc.cantidad, format.id, format.tipo from configuracion " +
                "inner join colores as c on c.id = configuracion.id_color " +
                "inner join tipografias on tipografias.id = configuracion.id_tipografia " +
                "inner join tamanos on tamanos.id = configuracion.id_tamano " +
                "inner join estados as v on v.id = configuracion.estado_vibracion " +
                "inner join estados as s on s.id = configuracion.estado_sonido " +
                "inner join colores as cb on cb.id = configuracion.id_colorBoton " +
                "inner join decimales as dc on dc.id = configuracion.id_decimal " +
                "inner join formato as format on format.id = configuracion.id_formato", null);
        Log.e("configbd", configs.toString());
        if(configs.moveToFirst()){
            config = new Configuracion();

            color = new Color(configs.getInt(0), configs.getString(1));
            config.setColor(color);

            tipografia = new Tipografia(configs.getInt(2), configs.getString(3));
            config.setTipografia(tipografia);

            tamano = new Tamano(configs.getInt(4), configs.getInt(5));
            config.setTamano(tamano);

            vibracion = new Estado(configs.getInt(6), configs.getString(7));
            config.setVibracion(vibracion);

            sonido = new Estado(configs.getInt(8), configs.getString(9));
            config.setSonido(sonido);

            colorBoton = new Color(configs.getInt(10), configs.getString(11));
            config.setColorBoton(colorBoton);

            decimales = new Decimales(configs.getInt(12), configs.getInt(13));
            config.setDecimales(decimales);

            formato = new Formato(configs.getInt(14), configs.getString(15));
            config.setFormato(formato);

        }
        BasedeDatos.close();
        return config;
    }

    public boolean cargarConfiguracion(int idColor, int idColorBoton, int idTipografia, int idTamano, int idEstadoVibracion, int idEstadoSonido, int IdDecimal , int idFormato, Context context) {
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
                registro.put("id_decimal", IdDecimal);
                registro.put("id_formato" , idFormato);
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
        BasedeDatos.close();
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

        BasedeDatos.close();
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

        BasedeDatos.close();
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

        BasedeDatos.close();
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

        BasedeDatos.close();
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

        BasedeDatos.close();
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

        BasedeDatos.close();
        return listTam;
    }

    public ArrayList<Decimales> listarDecimales(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

        listdecimal = new ArrayList<>();


        Cursor cfg = BasedeDatos.rawQuery("select id, cantidad from decimales", null);
        if(cfg.moveToFirst()){

            decimal = new Decimales();
            decimal.setId(Integer.parseInt(cfg.getString(0)));
            decimal.setCantidad(Integer.parseInt(cfg.getString(1)));
            listdecimal.add(decimal);


            while(cfg.moveToNext()){

                decimal = new Decimales();
                decimal.setId(Integer.parseInt(cfg.getString(0)));
                decimal.setCantidad(Integer.parseInt(cfg.getString(1)));
                listdecimal.add(decimal);

            }
        }

        BasedeDatos.close();
        return listdecimal;
    }

    public ArrayList<Formato> listarFormato(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

        listarFormato = new ArrayList<Formato>();


        Cursor cfg = BasedeDatos.rawQuery("select id, tipo from formato", null);
        if(cfg.moveToFirst()){

            formato = new Formato();
            formato.setId(Integer.parseInt(cfg.getString(0)));
            formato.setFormato(cfg.getString(1));
            listarFormato.add(formato);


            while(cfg.moveToNext()){

                formato = new Formato();
                formato.setId(Integer.parseInt(cfg.getString(0)));
                formato.setFormato(cfg.getString(1));
                listarFormato.add(formato);

            }
        }

        BasedeDatos.close();
        return listarFormato;
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

        BasedeDatos.close();
        return listEst;
    }

    public int setearColorTexto (Context context)
    {
        Rojo = 0xFFFF0000;
        Azul = 0xFF0000FF;
        Amarillo = 0xFFFFFF00;
        Blanco = 0xFFFFFFFF;
        Negro = 0xFF000000;

        if (config.getColor().getColor().equals("Rojo"))
        {
            return Rojo;
        }
        if (config.getColor().getColor().equals("Azul"))
        {
            return Azul;
        }
        if (config.getColor().getColor().equals("Amarillo"))
        {
            return Amarillo;
        }
        if (config.getColor().getColor().equals("Blanco"))
        {
            return Blanco;
        }
        if (config.getColor().getColor().equals("Negro"))
        {
            return Negro;
        }

        return 0;
    }

    public int setearColorBoton (Context context)
    {
        Rojo = 0xFFFF0000;
        Azul = 0xFF0000FF;
        Amarillo = 0xFFFFFF00;
        Blanco = 0xFFFFFFFF;
        Negro = 0xFF000000;

        if (config.getColorBoton().getColor().equals("Rojo"))
        {
            return Rojo;
        }
        if (config.getColorBoton().getColor().equals("Azul"))
        {
            return Azul;
        }
        if (config.getColorBoton().getColor().equals("Amarillo"))
        {
            return Amarillo;
        }
        if (config.getColorBoton().getColor().equals("Blanco"))
        {
            return Blanco;
        }
        if (config.getColorBoton().getColor().equals("Negro"))
        {
            return Negro;
        }

        return 0;
    }

    public Typeface setearTipografia (Context context)
    {
        Arial = ResourcesCompat.getFont(context, R.font.arial);
        Helvetica = ResourcesCompat.getFont(context, R.font.helvetica);
        Verdana = ResourcesCompat.getFont(context, R.font.verdana);
        Roboto = ResourcesCompat.getFont(context, R.font.roboto);
        ComicSans = ResourcesCompat.getFont(context, R.font.comicsans);


        if (config.getTipografia().getTipografia().equals("Arial"))
        {
            return Arial;
        }
        if (config.getTipografia().getTipografia().equals("Helvetica"))
        {
            return Helvetica;
        }
        if (config.getTipografia().getTipografia().equals("Verdana"))
        {
            return Verdana;
        }
        if (config.getTipografia().getTipografia().equals("Roboto"))
        {
            return Roboto;
        }
        if (config.getTipografia().getTipografia().equals("Comic Sans"))
        {
            return ComicSans;
        }

        return Arial;
    }

    public int setearTamano (Context context)
    {
        int tamano;
        tamano = config.getTamano().getTamano() +2;

        return tamano;
    }

    public ArrayList<String> setearDecimal (ArrayList<Decimales> Decimals)
    {
        dos = "2 decimales";
        tres = "3 decimales";
        cuatro = "4 decimales";
        todos = "Todos";
        ArrayList<String>ListaTexto = new ArrayList<String>();
        for(int i = 0; i < Decimals.size(); i++) {
            if(Decimals.get(i).getCantidad().equals(2)){
                ListaTexto.add(dos);
            }
            if(Decimals.get(i).getCantidad().equals(3)){
                ListaTexto.add(tres);
            }
            if(Decimals.get(i).getCantidad().equals(4)){
                ListaTexto.add(cuatro);
            }
            if(Decimals.get(i).getCantidad().equals(8)){
                ListaTexto.add(todos);
            }
        }

        return ListaTexto;
    }

    public int getDecimalValue(Object selectedItem){
        if(selectedItem.equals("2 decimales")){
            return 1;
        }
        if(selectedItem.equals("3 decimales")){
            return 2;
        }
        if(selectedItem.equals("4 decimales")){
            return 3;
        }
        if(selectedItem.equals("Todos")){
            return 4;
        }
        return 2;
    }


}
