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

public class CalculadoraBasica extends AppCompatActivity {

    TextView operacion, resultado;
    private TextView Resultado;
    private String Signo = "";
    private String Numero= "";

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
        String buttonText = digit.getText().toString();

        String MuestraVieja = (String) operacion.getText();
        String NumeroViejo = Numero;
        switch (view.getId())
        {
            case R.id.btn0:
                operacion.setText(MuestraVieja + buttonText);
                Numero = NumeroViejo + "0";
                break;
            case R.id.btn1:
                operacion.setText(MuestraVieja + buttonText);
                Numero = NumeroViejo + "1";
                break;
            case R.id.btn2:
                operacion.setText(MuestraVieja + "2");
                Numero = NumeroViejo + "2";
                break;
            case R.id.btn3:
                operacion.setText(MuestraVieja + "3");
                Numero = NumeroViejo + "3";
                break;
            case R.id.btn4:
                operacion.setText(MuestraVieja + "4");
                Numero = NumeroViejo + "4";
                break;
            case R.id.btn5:
                operacion.setText(MuestraVieja + "5");
                Numero = NumeroViejo + "5";
                break;
            case R.id.btn6:
                operacion.setText(MuestraVieja + "6");
                Numero = NumeroViejo + "6";
                break;
            case R.id.btn7:
                operacion.setText(MuestraVieja + "7");
                Numero = NumeroViejo + "7";
                break;
            case R.id.btn8:
                operacion.setText(MuestraVieja + "8");
                Numero = NumeroViejo + "8";
                break;
            case R.id.btn9:
                operacion.setText(MuestraVieja + "9");
                Numero = NumeroViejo + "9";
                break;
            case R.id.btnPunto:
                operacion.setText(MuestraVieja + ".");
                Numero = NumeroViejo + ".";
                break;
            case R.id.btnCerrarParent:
                operacion.setText(MuestraVieja + ")");
                Numero = NumeroViejo + ")";
                break;
            case R.id.btnAbrirParent:
                operacion.setText(MuestraVieja + "(");
                Numero = NumeroViejo + "(";
                break;

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
            return true;
        } else if(id == R.id.action_cientifica){
            Intent intent = new Intent(this, utn.frgp.tusi.tpintegrador_grupo7.CalculadoraCientifica.class);
            startActivity(intent);
        } else if(id == R.id.action_historial){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}