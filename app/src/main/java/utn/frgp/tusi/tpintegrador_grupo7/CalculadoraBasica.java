package utn.frgp.tusi.tpintegrador_grupo7;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class CalculadoraBasica extends AppCompatActivity {

    TextView operacion, resultado;
    float cuenta = 0;
    private String Numero = "";
    String MuestraVieja = "";
    String buttonText = "";
    String signo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basica);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        operacion = findViewById(R.id.txtOperacion);
        resultado = findViewById(R.id.txtResultado);
        resultado.setText("0");
        resultado.setAlpha((float) 0.5);
    }

    public void ingresarDigito(View view){
        Button digit = (Button)view;
        buttonText = digit.getText().toString();

        MuestraVieja = (String) operacion.getText();
        if(!ObtenerOperador()){
            operacion.setText(MuestraVieja + buttonText);
            Numero = Numero + buttonText;
        }
    }

    public void borrarDigito(View view){
        if(operacion.length()>0) {
            operacion.setText(operacion.getText().toString().substring(0, operacion.length() - 1));
        }
    }

    public void eliminarOperacion(View view){
        operacion.setText("");
        resultado.setText("0");
        resultado.setAlpha((float) 0.5);
        cuenta = 0;
        Numero = "";
    }

    public void calcularOperacion(View view){
        Calcular(Numero);
        signo = "=";
        Calcular(Numero);
        Numero = "0";
    }

    public boolean ObtenerOperador(){
        String[] Operadores = { "+", "-", "x", "/", "="};

        if (Arrays.asList(Operadores).contains(buttonText)) {
                operacion.setText(MuestraVieja + buttonText);
                signo = buttonText;
                Calcular(Numero);
                MuestraVieja = Numero + buttonText;
                Numero = "";
                return true;
        }
        return false;
    }

    public void Calcular(String Num){
        switch (signo) {
            case "+":
                cuenta += Float.parseFloat(Num);
                break;
            case "-":
                cuenta -= Float.parseFloat(Num);
                break;
            case "*":
                cuenta = cuenta * Float.parseFloat(Num);
                break;
            case "/":
                if (Float.parseFloat(Num) > 0) {
                    cuenta = cuenta / Float.parseFloat(Num);
                }
                break;
            case "=":
                resultado.setText(cuenta+"");
                resultado.setAlpha((float) 1);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item= menu.findItem(R.id.action_basica);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
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