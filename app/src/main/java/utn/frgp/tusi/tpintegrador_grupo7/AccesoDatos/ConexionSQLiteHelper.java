package utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import utn.frgp.tusi.tpintegrador_grupo7.Utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db_calculadora) {
        db_calculadora.execSQL(Utilidades.CREAR_TABLA_COLOR);
        db_calculadora.execSQL(Utilidades.CREAR_TABLA_ESTADO);
        db_calculadora.execSQL(Utilidades.CREAR_TABLA_TAMANO);
        db_calculadora.execSQL(Utilidades.CREAR_TABLA_TIPOGRAFIA);
        db_calculadora.execSQL(Utilidades.CREAR_TABLA_HISTORIAL);
        db_calculadora.execSQL(Utilidades.CREAR_TABLA_CONFIG);
        db_calculadora.execSQL(Utilidades.INSERTAR_TABLA_COLOR);
        db_calculadora.execSQL(Utilidades.INSERTAR_TABLA_TIPOGRAFIA);
        db_calculadora.execSQL(Utilidades.INSERTAR_TABLA_TAMANO);
        db_calculadora.execSQL(Utilidades.INSERTAR_TABLA_ESTADO);
        db_calculadora.execSQL(Utilidades.INSERTAR_TABLA_CONFIGURACION);
        db_calculadora.execSQL(Utilidades.INSERTAR_TABLA_HISTORIAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
