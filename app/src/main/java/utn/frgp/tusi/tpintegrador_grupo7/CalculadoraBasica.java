package utn.frgp.tusi.tpintegrador_grupo7;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class CalculadoraBasica extends AppCompatActivity {

    TextView operacion, resultado;
    int cuenta = 0;
    private String Numero= "";
    String MuestraVieja = "";
    String NumeroViejo = "";
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
    }

    public void ingresarDigito(View view){
        Button digit = (Button)view;
        buttonText = digit.getText().toString();

        MuestraVieja = (String) operacion.getText();
        NumeroViejo = Numero;
        if(!ObtenerOperador()){
            operacion.setText(MuestraVieja + buttonText);
            Numero = NumeroViejo + buttonText;
        }
    }

    public void borrarDigito(View view){
        if(operacion.length()>0) {
            operacion.setText(operacion.getText().toString().substring(0, operacion.length() - 1));
        }
    }

    public void eliminarOperacion(View view){
        operacion.setText("");
    }

    public void calcularOperacion(View view){
        signo = "=";
        Calcular(MuestraVieja);
    }

    public boolean ObtenerOperador(){
        String[] Operadores = { "+", "-", "x", "/", "="};

        if (Arrays.asList(Operadores).contains(buttonText)) {
                operacion.setText(MuestraVieja + buttonText);
                signo = buttonText;
                Calcular(Numero);
                Numero = NumeroViejo + buttonText;
                MuestraVieja = "";
                return true;
        }
        return false;
    }

    public void Calcular(String Num){
        switch (signo) {
            case "+":
                cuenta += Integer.parseInt(Num);
                break;
            case "-":
                cuenta -= Integer.parseInt(Num);
                break;
            case "*":
                cuenta = cuenta * Integer.parseInt(Num);
                break;
            case "/":
                if (Integer.parseInt(Num) > 0) {
                    cuenta = cuenta / Integer.parseInt(Num);
                }
                break;
            case "=":
                String valor = cuenta+Num+"";
                resultado.setText(valor);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_configuracion) {
            Intent intent = new Intent(this, utn.frgp.tusi.tpintegrador_grupo7.Configuracion.class);
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