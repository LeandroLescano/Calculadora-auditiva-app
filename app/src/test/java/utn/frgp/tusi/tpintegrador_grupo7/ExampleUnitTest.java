package utn.frgp.tusi.tpintegrador_grupo7;

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
    public void calculoBasico() {
        assertEquals("-1.0",Operacion.calcularOperacionBasica(Operacion.calcularOperacionCientifica("-6+5")).toString());
    }

    @Test
    public void comandoDeVoz(){
        assertEquals("lg(-8)+ln(322)x6/4^-5",ComandosVoz.traducirOperacion("logaritmo de menos 8 más logaritmo natural de 322 por 6 dividido 4 elevado a la -5").replace(" ", ""));
    }
}