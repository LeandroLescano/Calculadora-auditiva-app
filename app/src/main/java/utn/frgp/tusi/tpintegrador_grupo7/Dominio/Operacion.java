package utn.frgp.tusi.tpintegrador_grupo7.Dominio;

import android.util.Log;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

import utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos.ConfiguracionDao;

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

    public static String verificarOperador(String[] split, int x){
        String sumaResta="";
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
        return sumaResta;
    }

    public static Float  calcularOperacionBasica(String operacion){
        String opLocal = operacion.replace("--", "+")
                .replace("+-", "-")
                .replace("-+", "-")
                .replace("++","+");
        String[] split = opLocal.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.-])(?=[\\d.-])");
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
                sumaResta = verificarOperador(split,x);
                if(sumaResta != ""){
                    break;
                }
            }
            //Log.e("sumaResta", sumaResta);
            String[] sumaRestaSplit = sumaResta.replace("--", "+")
                    .replace("+-", "-")
                    .replace("-+", "-")
                    .replace("++","+").split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.])(?=[\\d.])");

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
            //e.printStackTrace();
            resultadoParcial = null; //Operación incompleta
        }

        return resultadoParcial;
    }



    //Resolución de funciones trigonométricas, raíces y exponentes
    public static String calcularOperacionCientifica(int cant , String operacion , int formatoActual){
        String opLocal = operacion;
        int cantidad = cant;
        int formato = formatoActual;

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
        boolean checked = false;
        int vueltas = opLocal.split("(?<=[\\(])(?=[^\\)])|(?<=[^\\(])(?=[\\)])").length, cVueltas = 0;
        //Check de que no haya parentesis dentro de otras funciones
        while(!checked && cVueltas < vueltas){
            String[] splitP = opLocal.split("(?<=[\\(])(?=[^\\)])|(?<=[^\\(])(?=[\\)])");
            for(int x=1; x < splitP.length; x++){
                if(splitP[x].equals("null")){
                    return "";
                }
                //Si el anterior contiene "(", el posterior ")" y el número no es un dígito lo calcula
                if(x+1 < splitP.length && splitP[x-1].contains("(") && splitP[x+1].contains(")") && !splitP[x].matches("[-+]?[0-9]*\\.?[0-9]+")){
                    if(splitP[x-1].contains("cos") || splitP[x-1].contains("tan") || splitP[x-1].contains("sin") || splitP[x-1].contains("l")){
                        Float parcialNum = Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica(cantidad,splitP[x],formato));
                        if(parcialNum != null && parcialNum.toString().contains("E")){
                            //Si el número contiene E lo convierte en decimal
                            opLocal = opLocal.replace(splitP[x], new BigDecimal(parcialNum).toPlainString());
                        }else if(parcialNum != null){
                            opLocal = opLocal.replace(splitP[x], parcialNum.toString());
                        }
                    }else{
                    opLocal = opLocal.replace("(" + splitP[x] + ")", String.valueOf(Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica(cantidad,splitP[x],formato))));
                    }
                }else if(x+1 < splitP.length && splitP[x-1].contains("(") && splitP[x+1].contains(")") && splitP[x].matches("[-+]?[0-9]*\\.?[0-9]+")){
                    if(!splitP[x-1].contains("cos") && !splitP[x-1].contains("tan") && !splitP[x-1].contains("sin") && !splitP[x-1].contains("l")){
                        opLocal = opLocal.replace(splitP[x-2] + "(" + splitP[x] + ")", splitP[x-2] + splitP[x]);
                    }
                }
            }


            String[] splitCheck = opLocal.split("(?<=[\\(])(?=[^\\)])|(?<=[^\\(])(?=[\\)])");
            int count = 0, countChecked = 0;
            for(int x=1; x < splitCheck.length; x++) {
                if(splitCheck[x].contains("cos") || splitCheck[x].contains("tan") || splitCheck[x].contains("sin") || splitCheck[x].contains("l") || splitCheck[x].contains("^")){
                    count++;
                    boolean primeroAbierto = false, primeroCerrado = false;
                    for(int y=x+1; y < splitCheck.length; y++) {
                        if(splitCheck[y].contains("(")){
                            primeroAbierto = true;
                        }else if(splitCheck[y].contains(")")){
                            if(!primeroAbierto){
                                primeroCerrado = true;
                                if(!splitCheck[y-1].matches("[-+]?[0-9]*\\.?[0-9]+")){
                                    countChecked++;
                                }
                                break;
                            }
                        }
                    }
                    if(primeroCerrado){
                        countChecked++;
                    }
                }
            }
            if(count == countChecked){
                checked = true;
            }
            cVueltas++;
        }


        contieneOperadores = false;
        for (String op : operadores) {
            if (opLocal.contains(op)) {
                contieneOperadores = true;
                break;
            }
        }
        if(contieneOperadores) {

//        String[] split = opLocal.split("(?<=[\\d.-])(?=[^\\d.-])|(?<=[^\\d.])(?=[\\d.])|(?<=[+x/])(?=[^+x/])|(?<=[^+x/])(?=[+x/])");
            String[] split = opLocal.split("(?<=[\\d.+/x])(?=[^\\d.+/x])|(?<=[^\\d.+/x-])(?=[\\d.+/x-])|(?<=[+x/])(?=[^+x/])|(?<=[^+x/])(?=[+x/])|(?<=[\\√])(?=[^\\√])");

            try {
                for (String operador : funciones) {
                    if (split[0].contains(operador)) {
                        primerOperador = true;
                        break;
                    }
                }
                if (!primerOperador && !split[split.length - 1].contains(")")) {
                    Float f = Float.parseFloat(split[split.length - 1]);
                }
                for (int x = 0; x < split.length - 1; x++) {
                    if (split[x].contains("ln(")) {
                        double logN = Math.log(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("ln(" + split[x + 1] + ")", Double.toString(logN));
                        x++;
                    } else if (split[x].contains("^")) {

                        boolean parentesisResuelto = false;
                        if(split[x].contains("(")){
                            int pAbierto = 1, pCerrado = 0;
                            String substr = opLocal.substring(opLocal.indexOf("^")+1);
                            int largo = substr.length(), cont = 0;
                            while((pAbierto != pCerrado) && cont < largo){
                                for(int y = 1; y < substr.length(); y++){
                                    cont++;
                                    if(substr.charAt(y) == 40)
                                        pAbierto++;
                                    if(substr.charAt(y) == 41)
                                        pCerrado++;
                                    if(pAbierto == pCerrado){
                                        String parentesis="";
                                        if(cont == substr.length()-1){
                                            parentesis = substr.substring(substr.indexOf("^")+1);
                                        }else{
                                            parentesis = substr.substring(substr.indexOf("^")+1, cont+1);
                                        }
                                        opLocal = opLocal.replace(parentesis, Operacion.calcularOperacionBasica(Operacion.sacarParentesis(Operacion.calcularOperacionCientifica(cantidad,parentesis,formato))).toString());
                                        parentesisResuelto = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if(parentesisResuelto){
                            split = opLocal.split("(?<=[\\d.+/x])(?=[^\\d.+/x])|(?<=[^\\d.+/x-])(?=[\\d.+/x-])|(?<=[+x/])(?=[^+x/])|(?<=[^+x/])(?=[+x/])|(?<=[\\√])(?=[^\\√])");
                        }

                        double exp;
                        if (split[x - 1].contains("-")) {
                            exp = Math.pow(Double.parseDouble(split[x - 1]) * -1, Double.parseDouble(split[x + 1]));
                        } else {
                            exp = Math.pow(Double.parseDouble(split[x - 1]), Double.parseDouble(split[x + 1]));
                        }
                        if (String.valueOf(exp).contains("E")) {
                            String expExponencial = new BigDecimal(exp).toPlainString();
                            if (split[x - 1].contains("-")) {
                                opLocal = opLocal.replace(split[x - 1] + "^" + split[x + 1], "-" + expExponencial);
                            } else {
                                opLocal = opLocal.replace(split[x - 1] + "^" + split[x + 1], expExponencial);
                            }
                        } else {
                            if (split[x - 1].contains("-")) {
                                opLocal = opLocal.replace(split[x - 1] + "^" + split[x + 1], "-" + Double.toString(exp));
                            } else {
                                opLocal = opLocal.replace(split[x - 1] + "^" + split[x + 1], Double.toString(exp));
                            }
                        }
                        x++;
                    } else if (split[x].contains("lg(")) {
                        double log10 = Math.log10(Double.parseDouble(split[x + 1]));
                        opLocal = opLocal.replace("lg(" + split[x + 1] + ")", Double.toString(log10));
                        x++;
                    } else if (split[x].contains("arctan(")) {
                        double tanInv;
                        if(formato == 2){
                            double valorDouble = Double.parseDouble(split[x + 1]);
                            tanInv = Math.round(Math.atan(Math.toRadians(valorDouble)));
                        } else{
                            tanInv = Math.atan(Double.parseDouble(split[x + 1]));
                        }
                        opLocal = opLocal.replace("arctan(" + split[x + 1] + ")", Double.toString(tanInv));
                        x++;
                    } else if (split[x].contains("arcsin(")) {
                        double sinInv;
                        if(formato == 2){
                            double valorDouble = Double.parseDouble(split[x + 1]);
                            sinInv = Math.round(Math.asin(Math.toRadians(valorDouble)));
                        } else{
                            sinInv = Math.asin(Double.parseDouble(split[x + 1]));
                        }
                        opLocal = opLocal.replace("arcsin(" + split[x + 1] + ")", Double.toString(sinInv));
                        x++;
                    } else if (split[x].contains("arccos(")) {
                        double cosInv;
                        if(formato == 2){
                            double valorDouble = Double.parseDouble(split[x + 1]);
                            cosInv = Math.round(Math.acos(Math.toRadians(valorDouble)));
                        } else{
                            cosInv = Math.acos(Double.parseDouble(split[x + 1]));
                        }
                        opLocal = opLocal.replace("arccos(" + split[x + 1] + ")", Double.toString(cosInv));
                        x++;
                    } else if (split[x].contains("tan(")) {
                        double tan;
                        if(formato == 2){
                            double valorDouble = Double.parseDouble(split[x + 1]);
                            double tangent = Math.tan(Math.toRadians(valorDouble));
                            tan = getValueByDecimal(tangent , cantidad);
                        } else{
                            tan = Math.tan(Double.parseDouble(split[x + 1]));
                        }
                        opLocal = opLocal.replace("tan(" + split[x + 1] + ")", Double.toString(tan));
                        x++;
                    } else if (split[x].contains("sin(")) {
                        double sin;
                        if(formato == 2){
                            double valorDouble = Double.parseDouble(split[x + 1]);
                            double seno = Math.sin(Math.toRadians(valorDouble));
                            sin = getValueByDecimal(seno , cantidad);
                        } else{
                            sin = Math.sin(Double.parseDouble(split[x + 1]));
                        }
                        opLocal = opLocal.replace("sin(" + split[x + 1] + ")", Double.toString(sin));
                        x++;
                    } else if (split[x].contains("cos(")) {
                        double cos = 0;
                        double coseno = 0;
                        if(formato == 2){
                            double valorDouble = Double.parseDouble(split[x + 1]);
                            coseno = Math.cos(Math.toRadians(valorDouble));
                            cos = getValueByDecimal(coseno , cantidad);
                        } else{
                            coseno = Math.cos(Double.parseDouble(split[x + 1]));
                        }
//                        opLocal = opLocal.replace("cos(" + split[x + 1] + ")", Double.toString(cos));
                        if (x + 2 >= split.length) {
                            return "";
                        } else {
                            String valor = String.format("%." + cantidad + "f", cos);
                            opLocal = opLocal.replace(split[x] + split[x + 1] + split[x + 2], Double.toString(cos));
                        }
                        x++;
                    } else if (split[x].contains("√")) {
                        double raiz = 0;
                        if (split[x + 1].contains("cos") || split[x + 1].contains("tan") || split[x + 1].contains("sin") || split[x + 1].contains("l")) {
                            raiz = Math.sqrt(Double.parseDouble(Operacion.calcularOperacionCientifica(cantidad , (split[x + 1] + split[x + 2] + split[x + 3]), formato)));
                            opLocal = opLocal.replace("√" + split[x + 1] + split[x + 2] + split[x + 3], Double.toString(raiz));
                            x += 2;
                        } else {
                            raiz = Math.sqrt(Double.parseDouble(split[x + 1]));
                            opLocal = opLocal.replace("√" + split[x + 1], Double.toString(raiz));
                        }
                        x++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                opLocal = ""; //Operación incompleta
            }
        }
        }
        return opLocal;
    }

    public static double getValueByDecimal(double value , int cantidad){
        double returnValue = (double) Math.round(value * 100) / 100;
        switch (cantidad){
            case 2:
                returnValue = (double) Math.round(value * 100) / 100;
                break;
            case 3:
                returnValue = (double) Math.round(value * 1000) / 1000;
                break;
            case 4:
                returnValue = (double) Math.round(value * 10000) / 10000;
                break;
            case 8:
                returnValue = (double) Math.round(value * 100000000) / 100000000;
                break;
        }
        return returnValue;
    }

    public static String agregarMultiplicaciones(String operacion) {
        String[] split = operacion.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.])(?=[\\d.])");
        String[] funciones = new String[]{"arccos", "arctan", "arcsin", "cos", "tan", "sin", "lg", "ln"};
        String[] operadores = new String[]{"+","-","/","x"};
        String[] caracteres = new String[]{"+","-","/","x","^","√", "cos", "tan", "sin", "arccos", "arctan", "arcsin", "lg", "ln"};
        for (int x = 0; x < split.length; x++) {
            if(split[x].equals(")(")){
                operacion = operacion.replace(")(", ")x(");
            }else if (split[x].equals("(")) {
                if(x-1 >= 0){
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
        }

        split = operacion.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.])(?=[\\d.])");
        for (int x = 0; x < split.length; x++) {
            for(String f: funciones){
                if(split[x].contains(f)){
                    if(x-1 >= 0){
                        boolean contiene = false;
                        for(String o : operadores){
                            if(split[x-1].contains(o)){
                                contiene = true;
                                break;
                            }
                        }
                        if(!contiene){
                            operacion = operacion.replace(split[x], "x" + split[x]);
                        }
                    }
                }
            }
        }


        return operacion;
    }

    public static String sacarParentesis(String operacionACalcular) {
        String[] split = new String[]{""};
        while(operacionACalcular.contains("(") && operacionACalcular.contains(")")) {
            split = operacionACalcular.split("(?<=[\\(])(?=[^\\)])|(?<=[^\\(])(?=[\\)])");
            boolean finded = false;
            for (int x = 1; x < split.length - 1; x++) {
                if (split[x + 1].contains(")") && split[x - 1].contains("(")) {
                    finded = true;
                    Float resultadoParcial = Operacion.calcularOperacionBasica(split[x]);
                    if(resultadoParcial != null){
                        operacionACalcular = operacionACalcular.replace("(" + split[x] + ")", resultadoParcial.toString());
                    }else{
                        operacionACalcular = operacionACalcular.replace("(" + split[x] + ")", "");
                    }
                }
            }
            if(!finded){
                operacionACalcular = operacionACalcular.replace("(", "").replace(")", "");
            }
        }
        return operacionACalcular;
    }
}

