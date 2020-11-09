package utn.frgp.tusi.tpintegrador_grupo7;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos.HistorialDao;
import utn.frgp.tusi.tpintegrador_grupo7.Adapter.OperacionAdapter;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Operacion;

public class HistorialOperaciones extends AppCompatActivity {

    private ArrayList<Operacion> operaciones;
    private OperacionAdapter adapter;
    private GridView grid;
    private HistorialDao hist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        operaciones = new ArrayList<Operacion>();
        hist = new HistorialDao();
        operaciones = hist.listarOperaciones(this);
        //operaciones.add(new Operacion(1, "5+5=10"));
        adapter = new OperacionAdapter(this, operaciones);
        grid = (GridView) findViewById(R.id.gv_operaciones);
        grid.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_flecha_izq_menu);
        invalidateOptionsMenu();
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
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

        finish();
        return super.onOptionsItemSelected(item);
    }
}
