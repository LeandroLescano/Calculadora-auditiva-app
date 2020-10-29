package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

public class Configuracion {

    private Color color;
    private Tipografia tipografia;
    private Tamano tamano;
    private Estado vibracion;
    private Estado sonido;

    public Configuracion() {
    }

    public Configuracion(Color color, Tipografia tipografia, Tamano tamano, Estado vibracion, Estado sonido) {
        this.color = color;
        this.tipografia = tipografia;
        this.tamano = tamano;
        this.vibracion = vibracion;
        this.sonido = sonido;
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
}
