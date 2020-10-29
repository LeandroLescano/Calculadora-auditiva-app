package utn.frgp.tusi.tpintegrador_grupo7;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ConfiguracionActivity extends AppCompatActivity {

    private Spinner tamano, tipografia, color, botones, vibracion, sonido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        ArrayAdapter adapterTamano = ArrayAdapter.createFromResource(this, R.array.Tamano, R.layout.spinner_item);
        ArrayAdapter adapterTipo = ArrayAdapter.createFromResource(this, R.array.Tipografia, R.layout.spinner_item);
        ArrayAdapter adapterColor = ArrayAdapter.createFromResource(this, R.array.Color, R.layout.spinner_item);
        ArrayAdapter adapterBotones = ArrayAdapter.createFromResource(this, R.array.Botones, R.layout.spinner_item);
        ArrayAdapter adapterVibracion = ArrayAdapter.createFromResource(this, R.array.Vibracion, R.layout.spinner_item);
        ArrayAdapter adapterSonido = ArrayAdapter.createFromResource(this, R.array.Sonido, R.layout.spinner_item);
        tamano = findViewById(R.id.cbTamano);
        tipografia = findViewById(R.id.cbTipografia);
        color = findViewById(R.id.cbColor);
        botones = findViewById(R.id.cbBotones);
        vibracion = findViewById(R.id.cbVibracion);
        sonido = findViewById(R.id.cbSonido);
        adapterTamano.setDropDownViewResource(R.layout.spinner_item);
        adapterTipo.setDropDownViewResource(R.layout.spinner_item);
        adapterColor.setDropDownViewResource(R.layout.spinner_item);
        adapterBotones.setDropDownViewResource(R.layout.spinner_item);
        adapterVibracion.setDropDownViewResource(R.layout.spinner_item);
        adapterSonido.setDropDownViewResource(R.layout.spinner_item);
        tamano.setAdapter(adapterTamano);
        tipografia.setAdapter(adapterTipo);
        color.setAdapter(adapterColor);
        botones.setAdapter(adapterBotones);
        vibracion.setAdapter(adapterVibracion);
        sonido.setAdapter(adapterSonido);

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
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
