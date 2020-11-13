package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

import android.util.Log;

import java.math.BigDecimal;
import java.util.Arrays;

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
        String opLocal = operacion.replace("--", "+")
                .replace("+-", "-")
                .replace("-+", "-")
                .replace("++","+");
        String[] split = opLocal.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.])(?=[\\d.])");
        String sumaResta = "";
        Float resultadoParcial;
        try{
            Float f = Float.parseFloat(split[split.length-1]);
            //Calculo las multiplicaciones y divisiones
            for(int x=0; x < split.length; x++){
                try{
                    if(Float.parseFloat(split[x])%1 == 0){
                        split[x] = String.valueOf(Math.round(Float.parseFloat(split[x])));
                    }
                }catch(Exception e){

                }
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
                            if(parcial.contains("E")){
                                String parcialExponencial = new BigDecimal(parcial).toPlainString();
                                sumaResta = sumaResta.concat(parcialExponencial);
                            }else{
                                sumaResta = sumaResta.concat(parcial);
                            }
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
                            if(parcial.contains("E")){
                                String parcialExponencial = new BigDecimal(parcial).toPlainString();
                                sumaResta = sumaResta.concat(parcialExponencial);
                            }else{
                                sumaResta = sumaResta.concat(parcial);
                            }
                        }
                        break;
                    default:
                        if(x == split.length-1 || (x+1 < split.length && (!split[x+1].equals("x") && !split[x+1].equals("/")))){
                            sumaResta = sumaResta.concat(split[x]);
                        }
                        break;
                }
            }
            //Log.e("sumaResta", sumaResta);
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
            e.printStackTrace();
            resultadoParcial = null; //Operación incompleta
        }

        return resultadoParcial;
    }

    //Resolución de funciones trigonométricas, raíces y exponentes
    public static String calcularOperacionCientifica(String operacion){
        String opLocal = operacion;
        String[] split = operacion.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.-])(?=[\\d.-])|(?<=[+x/])(?=[^+x/])|(?<=[^+x/])(?=[+x/])");
        String[] funciones = new String[]{"arctan(", "arcsin(", "arccos(", "tan(", "sin(", "cos(", "lg(", "ln("};
        String[] operadores = new String[]{"arctan(", "arcsin(", "arccos(", "tan(", "sin(", "cos(", "lg(", "ln(", "^", "√"};
        boolean primerOperador = false, contieneOperadores = false;
        //Si no tiene operadores, llama directamente a calcularOperacionBasica
        for (String op : operadores) {
            if (opLocal.contains(op)) {
                contieneOperadores = true;
                break;
            }
        }
        if(contieneOperadores){
            try {
                for (String operador : funciones) {
                    if (split[0].contains(operador)) {
                        primerOperador = true;
                        break;
                    }
                }
                if(!primerOperador && !split[split.length-1].equals(")")){
                    Float f = Float.parseFloat(split[split.length - 1]);
                }
                for (int x = 0; x < split.length; x++) {
                    if (split[x].contains("ln(")) {
                        double logN = Math.log(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("ln(" + split[x + 1] + ")", Double.toString(logN));
                        x++;
                    } else if (split[x].contains("^")) {
                        double exp;
                        if(split[x-1].contains("-")){
                            exp = Math.pow(Double.parseDouble(split[x - 1])*-1, Double.parseDouble(split[x + 1]));
                        }else{
                            exp = Math.pow(Double.parseDouble(split[x - 1]), Double.parseDouble(split[x + 1]));
                        }
                        if (String.valueOf(exp).contains("E")) {
                            String expExponencial = new BigDecimal(exp).toPlainString();
                            if(split[x-1].contains("-")){
                                opLocal = opLocal.replace(split[x - 1] + "^" + split[x + 1], "-" + expExponencial);
                            }else{
                                opLocal = opLocal.replace(split[x - 1] + "^" + split[x + 1], expExponencial);
                            }
                        } else {
                            if(split[x-1].contains("-")) {
                                opLocal = opLocal.replace(split[x - 1] + "^" + split[x + 1], "-" + Double.toString(exp));
                            }else{
                                opLocal = opLocal.replace(split[x - 1] + "^" + split[x + 1], Double.toString(exp));
                            }
                        }
                        x++;
                    } else if (split[x].contains("√")) {
                        double raiz = Math.sqrt(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("√" + split[x + 1], Double.toString(raiz));
                        x++;
                    } else if (split[x].contains("lg(")) {
                        double log10 = Math.log10(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("lg(" + split[x + 1] + ")", Double.toString(log10));
                        x++;
                    } else if (split[x].contains("arctan(")) {
                        double tanInv = Math.atan(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("arctan(" + split[x + 1] + ")", Double.toString(tanInv));
                        x++;
                    } else if (split[x].contains("arcsin(")) {
                        double sinInv = Math.asin(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("arcsin(" + split[x + 1] + ")", Double.toString(sinInv));
                        x++;
                    } else if (split[x].contains("arccos(")) {
                        double cosInv = Math.acos(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("arccos(" + split[x + 1] + ")", Double.toString(cosInv));
                        x++;
                    } else if (split[x].contains("tan(")) {
                        double tan= Math.tan(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("tan(" + split[x + 1]+ ")", Double.toString(tan));
                        x++;
                    } else if (split[x].contains("sin(")) {
                        double sin = Math.sin(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("sin(" + split[x + 1] + ")", Double.toString(sin));
                        x++;
                    } else if (split[x].contains("cos(")) {
                        double cos = Math.cos(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("cos(" + split[x + 1] + ")", Double.toString(cos));
                        x++;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                opLocal = ""; //Operación incompleta
            }

        }
        return opLocal;
    }

    public static String agregarMultiplicaciones(String operacion) {
        String[] split = operacion.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.])(?=[\\d.])");
        String[] caracteres = new String[]{"+","-","/","x","^","√", "cos", "tan", "sin", "arccos", "arctan", "arcsin", "lg", "ln"};
        for (int x = 0; x < split.length; x++) {
            if (split[x].equals("(")) {
                boolean contiene = false;
                for(String c : caracteres){
                    if(split[x-1].contains(c)){
                        contiene = true;
                        break;
                    }
                }
                if(!contiene){
                    operacion = operacion.replace(split[x], "x" + split[x]);
                }
            }

        }
        return operacion;
    }

}

