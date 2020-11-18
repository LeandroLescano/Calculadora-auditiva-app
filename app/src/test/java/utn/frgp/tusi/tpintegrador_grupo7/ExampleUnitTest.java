package utn.frgp.tusi.tpintegrador_grupo7;

import android.util.Log;

import org.junit.Test;

import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Operacion;
import utn.frgp.tusi.tpintegrador_grupo7.Utilidades.ComandosVoz;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void calculoCientifico() {
        assertEquals("-258.27866",Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica("lg(10)/ln(4)-9^2+322-500")).toString());
    }

    @Test
    public void calculoCientifico2() {
        assertEquals("7.0106416",Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica("8-sin(8)")).toString());
    }

    @Test
    public void calculoBasicoMenosMas() {
        assertEquals("-1.0",Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica("-6+5")).toString());
    }

    @Test
    public void calculoBasicoMasMas() {
        assertEquals("11.0",Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica("6+5")).toString());
    }

    @Test
    public void calculoBasicoParentesis() {
        assertEquals("-1.0",Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica(Operacion.sacarParentesis("6+6-(7+4)-(4/2)"))).toString());
    }

    @Test
    public void calculoFuncionParentesis(){
        assertEquals("-0.577874",Operacion.calcularOperacionBasica(Operacion.sacarParentesis(Operacion.calcularOperacionCientifica("cos(9-6)+sin((9-6)x3)"))).toString());
    }

    @Test
    public void calculoFuncionParentesis2(){
        assertEquals(null,Operacion.calcularOperacionBasica(Operacion.sacarParentesis(Operacion.calcularOperacionCientifica("cos(9-6)-sin(()"))));
    }

    @Test
    public void calculoIncompleto(){
        assertEquals(null,Operacion.calcularOperacionBasica(Operacion.sacarParentesis(Operacion.calcularOperacionCientifica("cos(())"))));
    }

    @Test
    public void calculoBasicoMultiplicacionParentesis() {
        assertEquals("2x11.0",Operacion.sacarParentesis("2x(5+6)"));
    }

    @Test
    public void calculoBasicoMenosMenos() {
        assertEquals("-12.0",Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica("-6-6")).toString());
    }

    @Test
    public void comandoDeVoz(){
        assertEquals("lg(-8)+ln(322)x6/4^-5",ComandosVoz.traducirOperacion("logaritmo de menos 8 más logaritmo natural de 322 por 6 dividido 4 elevado a la -5").replace(" ", ""));
    }
}