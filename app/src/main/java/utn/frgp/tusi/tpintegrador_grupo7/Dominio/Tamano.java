package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

public class Tamano {

    private Integer id;
    private Integer tamano;

    public Tamano() {
    }

    public Tamano(Integer id, Integer tamano) {
        this.id = id;
        this.tamano = tamano;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTamano() {
        return tamano;
    }

    public void setTamano(Integer tamano) {
        this.tamano = tamano;
    }
}
