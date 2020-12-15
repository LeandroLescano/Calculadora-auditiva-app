package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

public class Formato {
    private Integer id;
    private String formato;

    public Formato() {
    }

    public Formato(Integer id, String format) {
        this.id = id;
        this.formato = format;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String format) {
        this.formato = format;
    }

    @Override
    public String toString() { return getFormato();
    }

}
