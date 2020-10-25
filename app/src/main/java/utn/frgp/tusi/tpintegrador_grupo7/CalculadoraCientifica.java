package utn.frgp.tusi.tpintegrador_grupo7;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CalculadoraCientifica extends AppCompatActivity {
    TextView operacion, resultado;
    private TextView Resultado;
    private TextView Muestra;
    private String Signo = "";
    private String Numero= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cientifica);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        operacion = findViewById(R.id.txtOperacion);
        resultado = findViewById(R.id.txtResultado);
    }

    public void ingresarDigito(View view){
        Button digit = (Button)view;
        String buttonText = digit.getText().toString();

        resultado.setText(resultado.getText() + buttonText);

        String MuestraVieja = buttonText;
        String NumeroViejo = Numero;
        switch (view.getId())
        {
            case R.id.btn0:
                Muestra.setText(MuestraVieja + "0");
                Numero = NumeroViejo + "0";
                break;
            case R.id.btn1:
                Muestra.setText(MuestraVieja + "1");
                Numero = NumeroViejo + "1";
                break;
            case R.id.btn2:
                Muestra.setText(MuestraVieja + "2");
                Numero = NumeroViejo + "2";
                break;
            case R.id.btn3:
                Muestra.setText(MuestraVieja + "3");
                Numero = NumeroViejo + "3";
                break;
            case R.id.btn4:
                Muestra.setText(MuestraVieja + "4");
                Numero = NumeroViejo + "4";
                break;
            case R.id.btn5:
                Muestra.setText(MuestraVieja + "5");
                Numero = NumeroViejo + "5";
                break;
            case R.id.btn6:
                Muestra.setText(MuestraVieja + "6");
                Numero = NumeroViejo + "6";
                break;
            case R.id.btn7:
                Muestra.setText(MuestraVieja + "7");
                Numero = NumeroViejo + "7";
                break;
            case R.id.btn8:
                Muestra.setText(MuestraVieja + "8");
                Numero = NumeroViejo + "8";
                break;
            case R.id.btn9:
                Muestra.setText(MuestraVieja + "9");
                Numero = NumeroViejo + "9";
                break;
            case R.id.btnPunto:
                Muestra.setText(MuestraVieja + ".");
                Numero = NumeroViejo + ".";
                break;
            case R.id.btnCerrarParent:
                Muestra.setText(MuestraVieja + ")");
                Numero = NumeroViejo + ")";
                break;
            case R.id.btnAbrirParent:
                Muestra.setText(MuestraVieja + "(");
                Numero = NumeroViejo + "(";
                break;
            case R.id.btntan:
                Muestra.setText(MuestraVieja + "tan()");
                Numero = NumeroViejo + "tan()";
                break;
            case R.id.btnsin:
                Muestra.setText(MuestraVieja + "sin()");
                Numero = NumeroViejo + "sin()";
                break;
            case R.id.btncos:
                Muestra.setText(MuestraVieja + "cos()");
                Numero = NumeroViejo + "cos()";
                break;
            case R.id.btnLogNatural:
                Muestra.setText(MuestraVieja + "log()");
                Numero = NumeroViejo + "log()";
                break;
            case R.id.btnRaiz:
                Muestra.setText(MuestraVieja + "√");
                Numero = NumeroViejo + "√";
                break;

        }
    }

    public void borrarDigito(View view){
        resultado.setText(resultado.getText().toString().substring(0, resultado.length()-1));
    }

    public void eliminarOperacion(View view){
        resultado.setText("");
    }

    public void calcularOperacion(View view){

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
            return true;
        } else if(id == R.id.action_basica){
            Intent intent = new Intent(this, utn.frgp.tusi.tpintegrador_grupo7.CalculadoraBasica.class);
            startActivity(intent);
        } else if(id == R.id.action_cientifica){
            return true;
        } else if(id == R.id.action_historial){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
