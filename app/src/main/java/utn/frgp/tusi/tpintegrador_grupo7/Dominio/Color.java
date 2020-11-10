package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

public class Color {

    private Integer id;
    private String color;

    public Color() {
    }

    public Color(Integer id, String color) {
        this.id = id;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return getColor();
    }
}
