package utn.frgp.tusi.tpintegrador_grupo7;

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

        resultado.setText(resultado.getText() + buttonText);
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
            return true;
        } else if(id == R.id.action_cientifica){
            return true;
        } else if(id == R.id.action_historial){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
