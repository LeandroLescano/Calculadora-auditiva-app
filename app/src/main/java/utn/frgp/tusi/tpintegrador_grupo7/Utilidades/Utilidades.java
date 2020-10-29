package utn.frgp.tusi.tpintegrador_grupo7.Utilidades;

public class Utilidades {

    //Constantes campos tabla Colores
    public static final String TABLA_COLOR="colores";
    public static final String CAMPO_ID_COLOR="id";
    public static final String CAMPO_NOMBRE_COLOR="color";

    public static final String CREAR_TABLA_COLOR="CREATE TABLE " + TABLA_COLOR + "(" + CAMPO_ID_COLOR + " integer primary key autoincrement, " + CAMPO_NOMBRE_COLOR + " text)";

    //Constantes campos tabla Tipografías
    public static final String TABLA_TIPOGRAFIA="tipografias";
    public static final String CAMPO_ID_TIPOGRAFIA="id";
    public static final String CAMPO_NOMBRE_TIPOGRAFIA="tipografia";

    public static final String CREAR_TABLA_TIPOGRAFIA="CREATE TABLE " + TABLA_TIPOGRAFIA + "(" + CAMPO_ID_TIPOGRAFIA + " integer primary key autoincrement, " + CAMPO_NOMBRE_TIPOGRAFIA + " text)";

    //Constantes campos tabla Tamaños
    public static final String TABLA_TAMANO="tamanos";
    public static final String CAMPO_ID_TAMANO="id";
    public static final String CAMPO_VALOR_TAMANO="tamano";

    public static final String CREAR_TABLA_TAMANO="CREATE TABLE " + TABLA_TAMANO + "(" + CAMPO_ID_TAMANO + " integer primary key autoincrement, " + CAMPO_VALOR_TAMANO + " integer)";

    //Constantes campos tabla Estados
    public static final String TABLA_ESTADO="estados";
    public static final String CAMPO_ID_ESTADO="id";
    public static final String CAMPO_DESCRIPCION_ESTADO="descripcion";

    public static final String CREAR_TABLA_ESTADO="CREATE TABLE " + TABLA_ESTADO + "(" + CAMPO_ID_ESTADO + " integer primary key autoincrement, " + CAMPO_DESCRIPCION_ESTADO + " text)";

    //Constantes campos tabla Configuracion
    public static final String TABLA_CONFIG="configuracion";
    public static final String CAMPO_COLOR_CONFIG="id_color";
    public static final String CAMPO_TIPOGRAFIA_CONFIG="id_tipografia";
    public static final String CAMPO_TAMANO_CONFIG="id_tamano";
    public static final String CAMPO_ESTADO_VIBRACION="estado_vibracion";
    public static final String CAMPO_ESTADO_SONIDO="estado_sonido";

    public static final String CREAR_TABLA_CONFIG="CREATE TABLE " + TABLA_CONFIG + "(" + CAMPO_COLOR_CONFIG + " integer, " + CAMPO_TIPOGRAFIA_CONFIG + " integer, "+ CAMPO_TAMANO_CONFIG + " integer, " + CAMPO_ESTADO_VIBRACION + " integer, " + CAMPO_ESTADO_SONIDO + "integer, " +
            "FOREIGN KEY ("+CAMPO_COLOR_CONFIG+") REFERENCES "+ TABLA_COLOR +"("+CAMPO_ID_COLOR+"), " +
            "FOREIGN KEY ("+CAMPO_TIPOGRAFIA_CONFIG+") REFERENCES "+ TABLA_TIPOGRAFIA +"("+CAMPO_ID_TIPOGRAFIA+"), " +
            "FOREIGN KEY ("+CAMPO_TAMANO_CONFIG+") REFERENCES "+ TABLA_TAMANO +"("+CAMPO_ID_TAMANO+"), " +
            "FOREIGN KEY ("+CAMPO_ESTADO_VIBRACION+") REFERENCES "+ TABLA_ESTADO +"("+CAMPO_ID_ESTADO+"), " +
            "FOREIGN KEY ("+CAMPO_ESTADO_SONIDO+") REFERENCES "+ TABLA_ESTADO +"("+CAMPO_ID_ESTADO+"))";

    //Constantes campos tabla Historial
    public static final String TABLA_HISTORIAL="historial";
    public static final String CAMPO_ID_OPERACION="id"; //Ver
    public static final String CAMPO_OPERACION="operacion";

    public static final String CREAR_TABLA_HISTORIAL="CREATE TABLE " + TABLA_HISTORIAL + "(" + CAMPO_ID_OPERACION + " integer primary key autoincrement, " + CAMPO_OPERACION + " text)";

}
