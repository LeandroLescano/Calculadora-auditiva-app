package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

public class Decimales {

    private Integer id;
    private Integer cantidad;

    public Decimales() {
    }

    public Decimales(Integer id, Integer cant) {
        this.id = id;
        this.cantidad = cant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cant) {
        this.cantidad = cant;
    }

    @Override
    public String toString() {
        return getCantidad().toString();
    }
}
