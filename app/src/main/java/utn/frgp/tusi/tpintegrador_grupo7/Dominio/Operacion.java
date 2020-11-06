package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

import android.util.Log;

public class Operacion {

    private Integer id;
    private String operacion;

    public Operacion(){}

    public Operacion(Integer id, String operacion) {
        this.id = id;
        this.operacion = operacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public static Float calcularOperacionBasica(String operacion){
        String opLocal = operacion;
        String[] split = operacion.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.])(?=[\\d.])");
        String sumaResta = "";
        Float resultadoParcial;
        try{
            Float f = Float.parseFloat(split[split.length-1]);
            //Calculo las multiplicaciones y divisiones
            for(int x=0; x < split.length; x++){
                String parcial;
                switch (split[x]){
                    case "x":
                        if(x+1 < split.length) {
                            parcial = String.valueOf(Float.parseFloat(split[x - 1]) * Float.parseFloat(split[x + 1]));
                            x++;
                            int pos = x+1;
                            while(pos < split.length && (split[pos].equals("/") || split[pos].equals("x"))){
                                if(split[pos].equals("/")) {
                                    parcial = String.valueOf(Float.parseFloat(parcial) / Float.parseFloat(split[pos + 1]));
                                }else{
                                    parcial = String.valueOf(Float.parseFloat(parcial) * Float.parseFloat(split[pos + 1]));
                                }
                                x+=2;
                                pos+=2;
                            }
                            sumaResta = sumaResta.concat(parcial);
                        }
                        break;
                    case "/":
                        if(x+1 < split.length) {
                            parcial = String.valueOf(Float.parseFloat(split[x - 1]) / Float.parseFloat(split[x + 1]));
                            x++;
                            int pos = x+1;
                            while(pos < split.length && (split[pos].equals("/") || split[pos].equals("x"))){
                                if(split[pos].equals("/")) {
                                    parcial = String.valueOf(Float.parseFloat(parcial) / Float.parseFloat(split[pos + 1]));
                                }else{
                                    parcial = String.valueOf(Float.parseFloat(parcial) * Float.parseFloat(split[pos + 1]));
                                }
                                x+=2;
                                pos+=2;
                            }
                            sumaResta = sumaResta.concat(parcial);
                        }
                        break;
                    default:
                        if(x == split.length-1 || (x+1 < split.length && (!split[x+1].equals("x") && !split[x+1].equals("/")))){
                            sumaResta = sumaResta.concat(split[x]);
                        }
                        break;
                }
            }
            Log.e("sumaResta", sumaResta);
            String[] sumaRestaSplit = sumaResta.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.])(?=[\\d.])");

            //Calculo las sumas y restas
            int primerSimbolo = 1;
            if(sumaRestaSplit[0].equals("-")){
                resultadoParcial = Float.parseFloat(sumaRestaSplit[1])*-1;
                sumaRestaSplit[1] = "-" + sumaRestaSplit[1];
                primerSimbolo++;
            }else{
                resultadoParcial = Float.parseFloat(sumaRestaSplit[0]);
            }
            for(int x=primerSimbolo; x < sumaRestaSplit.length; x++){
                switch (sumaRestaSplit[x]){
                    case "+":
                        if(x+1 < sumaRestaSplit.length){
                            resultadoParcial += Float.parseFloat(sumaRestaSplit[x+1]);
                            x++;
                        }
                        break;
                    case "-":
                        if(x+1 < sumaRestaSplit.length) {
                            resultadoParcial -= Float.parseFloat(sumaRestaSplit[x + 1]);
                            x++;
                        }
                        break;
                    default:
                        break;
                }
            }
        }catch(Exception e){
            resultadoParcial = -1f; //Operación incompleta
        }

        return resultadoParcial;
    }
}

