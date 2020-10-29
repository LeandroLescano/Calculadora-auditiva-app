package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

public class Tipografia {

    private Integer id;
    private String tipografia;

    public Tipografia() {
    }

    public Tipografia(Integer id, String tipografia) {
        this.id = id;
        this.tipografia = tipografia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipografia() {
        return tipografia;
    }

    public void setTipografia(String tipografia) {
        this.tipografia = tipografia;
    }
}
