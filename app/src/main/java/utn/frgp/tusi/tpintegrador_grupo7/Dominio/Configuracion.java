package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

public class Configuracion {

    private Color color;
    private Color colorBoton;
    private Tipografia tipografia;
    private Tamano tamano;
    private Estado vibracion;
    private Estado sonido;
    private Decimales decimales;

    public Configuracion() {
    }

    public Configuracion(Color color, Color colorBoton, Tipografia tipografia, Tamano tamano, Estado vibracion, Estado sonido, Decimales decimales) {
        this.color = color;
        this.colorBoton = colorBoton;
        this.tipografia = tipografia;
        this.tamano = tamano;
        this.vibracion = vibracion;
        this.sonido = sonido;
        this.decimales = decimales;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Tipografia getTipografia() {
        return tipografia;
    }

    public void setTipografia(Tipografia tipografia) {
        this.tipografia = tipografia;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public void setTamano(Tamano tamano) {
        this.tamano = tamano;
    }

    public Estado getVibracion() {
        return vibracion;
    }

    public void setVibracion(Estado vibracion) {
        this.vibracion = vibracion;
    }

    public Estado getSonido() {
        return sonido;
    }

    public void setSonido(Estado sonido) {
        this.sonido = sonido;
    }

    public Color getColorBoton() {
        return colorBoton;
    }

    public void setColorBoton(Color colorBoton) {
        this.colorBoton = colorBoton;
    }

    public Decimales getDecimales() {
        return decimales;
    }

    public void setDecimales(Decimales decimales) {
        this.decimales = decimales;
    }
}
