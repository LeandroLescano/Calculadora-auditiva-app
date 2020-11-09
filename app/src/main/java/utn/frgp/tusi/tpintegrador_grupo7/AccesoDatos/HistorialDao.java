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

public class HistorialDao {

    Operacion operacion;
    private ArrayList<Operacion> lista;
    int cantidadOps;



    public ArrayList<Operacion> listarOperaciones(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        lista = new ArrayList<>();
        operacion = new Operacion();

        Cursor ops = BasedeDatos.rawQuery("select id, operacion from historial", null);
        if(ops.moveToFirst()){
            while(ops.moveToNext()){

                operacion.setId(Integer.parseInt(ops.getString(0)));
                operacion.setOperacion(ops.getString(1));
                lista.add(operacion);

            }
        }
        return lista;
    }

    public int contarOperaciones(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        cantidadOps = 0;

        Cursor ops = BasedeDatos.rawQuery("select count (id) from historial", null);
        if(ops.moveToFirst()){
            while(ops.moveToNext()){

                cantidadOps = Integer.parseInt(ops.getString(0));

            }
        }
        return cantidadOps;
    }

    public boolean cargarOperacion(String op, Context context) {


        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        cantidadOps = contarOperaciones(context);

        try {
            ContentValues registro = new ContentValues();

            if (cantidadOps>9)
            {
                borrarUltima(context);
            }

            registro.put("operacion", op);
            BaseDatos.insert("historial", null, registro);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDatos.close();
        }

        return true;
    }

    public void borrarUltima(Context context){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "db_calculadora", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        //Chequear
        BasedeDatos.delete("historial", "id = (select MIN (id) from historial)", null);

    }


}
