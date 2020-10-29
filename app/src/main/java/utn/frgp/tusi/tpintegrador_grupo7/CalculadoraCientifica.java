package utn.frgp.tusi.tpintegrador_grupo7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;

public class CalculadoraCientifica extends AppCompatActivity {
    private TextView resultado;
    private EditText operacion;
    private String Signo = "";
    private String Numero= "";
    private Integer posActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cientifica);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        operacion = findViewById(R.id.txtOperacion);
        resultado = findViewById(R.id.txtResultado);
        resultado.setAlpha((float) 0.5);
        resultado.setText("0");
    }

    public void ingresarDigito(View view){
        Button digit = (Button)view;
        String buttonText = digit.getText().toString();
        posActual = operacion.getSelectionEnd();
        //resultado.setText(resultado.getText() + buttonText);
        String MuestraVieja = operacion.getText().toString();
        String NumeroViejo = Numero;
        switch (view.getId())
        {
            case R.id.btn0:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("0".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "0";
                break;
            case R.id.btn1:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("1".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "1";
                break;
            case R.id.btn2:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("2".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "2";
                break;
            case R.id.btn3:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("3".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "3";
                break;
            case R.id.btn4:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("4".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "4";
                break;
            case R.id.btn5:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("5".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "5";
                break;
            case R.id.btn6:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("6".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "6";
                break;
            case R.id.btn7:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("7".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "7";
                break;
            case R.id.btn8:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("8".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "8";
                break;
            case R.id.btn9:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("9".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "9";
                break;
            case R.id.btnPunto:
                operacion.setText(MuestraVieja.substring(0,posActual).concat(".".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + ".";
                break;
            case R.id.btnSumar:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("+".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "+";
                break;
            case R.id.btnResta:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("-".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "-";
                break;
            case R.id.btnMultiplicar:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("x".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "x";
                break;
            case R.id.btnDividir:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("/".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "/";
                break;
            case R.id.btnCerrarParent:
                operacion.setText(MuestraVieja.substring(0,posActual).concat(")".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + ")";
                break;
            case R.id.btnAbrirParent:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("(".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "(";
                break;
            case R.id.btntan:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("tan()".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "tan()";
                posActual = posActual+2;
                break;
            case R.id.btnsin:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("sin()".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "sin()";
                posActual = posActual+2;
                break;
            case R.id.btncos:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("cos()".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "cos()";
                posActual = posActual+2;
                break;
            case R.id.btnLogDecimal:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("Lg()".concat(MuestraVieja.substring(posActual))));
                Numero= NumeroViejo + "lg()";
                posActual = posActual+2;
                break;
            case R.id.btnLogNatural:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("Ln()".concat(MuestraVieja.substring(posActual))));
                posActual = posActual+2;
                Numero = NumeroViejo + "ln()";
                break;
            case R.id.btnPi:
                operacion.setText(MuestraVieja.concat(MuestraVieja.substring(0,posActual).concat(String.valueOf(Math.PI).substring(0,10).concat(MuestraVieja.substring(posActual)))));
                Numero = NumeroViejo + String.valueOf(Math.PI);
                break;
            case R.id.btnRaiz:
                operacion.setText(MuestraVieja.substring(0,posActual).concat("√".concat(MuestraVieja.substring(posActual))));
                Numero = NumeroViejo + "√";
                break;
        }
        if(operacion.getSelectionEnd() < operacion.getText().length()){
            operacion.setSelection(posActual+1);
        }
    }

    public void moverCursor(View view){
        AppCompatImageButton flecha = (AppCompatImageButton) view;
        posActual = operacion.getSelectionEnd();
        switch(flecha.getId()){
            case R.id.btnDerecha:
                if(posActual < operacion.getText().length()){
                    operacion.setSelection(posActual+1);
                    posActual++;
                }
                break;
            case R.id.btnIzquierda:
                if(posActual-1 > 0){
                    operacion.setSelection(posActual-1);
                    posActual--;
                }
                break;
        }
    }

    public void borrarDigito(View view){
        if(operacion.getText().length() > 0){
            operacion.setText(operacion.getText().toString().substring(0, operacion.length()-1));
        }
    }

    public void eliminarOperacion(View view){
        operacion.setText("");
        resultado.setText("0");
        resultado.setAlpha((float) 0.5);
    }

    public void calcularOperacion(View view){

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item= menu.findItem(R.id.action_cientifica);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_configuracion) {
            Intent intent = new Intent(this, ConfiguracionActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_basica){
            Intent intent = new Intent(this, utn.frgp.tusi.tpintegrador_grupo7.CalculadoraBasica.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_cientifica){
            Intent intent = new Intent(this, utn.frgp.tusi.tpintegrador_grupo7.CalculadoraCientifica.class);
            startActivity(intent);
        } else if(id == R.id.action_historial){
            Intent intent = new Intent(this, utn.frgp.tusi.tpintegrador_grupo7.HistorialOperaciones.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
