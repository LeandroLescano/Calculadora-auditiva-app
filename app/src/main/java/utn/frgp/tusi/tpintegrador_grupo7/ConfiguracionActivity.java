package utn.frgp.tusi.tpintegrador_grupo7;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos.ConfiguracionDao;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Color;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Configuracion;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Estado;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Tamano;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Tipografia;

public class ConfiguracionActivity extends AppCompatActivity {

    private Spinner tamano, tipografia, color, botones, vibracion, sonido;
    private Button botonGuardar;
    private Toast toast;
    private ConfiguracionDao config;
    private ArrayList<Tamano> listTam;
    private ArrayList<Tipografia> listTip;
    private ArrayList<Color> listCol;
    private ArrayList<Estado> listEst;
    private Configuracion cfgActual;
    private Color colSelec;
    private Color colBotSelec;
    private Tamano tamSelec;
    private Tipografia tipSelec;
    private Estado estVibSelec;
    private Estado estSonSelec;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        config = new ConfiguracionDao();
        cfgActual = new Configuracion();
        listTam = new ArrayList<Tamano>();
        listTip = new ArrayList<Tipografia>();
        listCol = new ArrayList<Color>();
        listEst = new ArrayList<Estado>();

        listTam = config.listarTamanos(this);
        listTip = config.listarTipografias(this);
        listCol = config.listarColores(this);
        listEst = config.listarEstados(this);

        cfgActual = config.traerConfiguracion(this);


        ArrayAdapter<Tamano> adapterTamano = new ArrayAdapter<Tamano>(this, R.layout.spinner_item, listTam);
        ArrayAdapter<Tipografia> adapterTipo = new ArrayAdapter<Tipografia>(this, R.layout.spinner_item, listTip);
        ArrayAdapter<Color> adapterColor = new ArrayAdapter<Color>(this, R.layout.spinner_item, listCol);
        ArrayAdapter<Color> adapterBotones = new ArrayAdapter<Color>(this, R.layout.spinner_item, listCol);
        ArrayAdapter<Estado> adapterVibracion = new ArrayAdapter<Estado>(this, R.layout.spinner_item, listEst);
        ArrayAdapter<Estado> adapterSonido = new ArrayAdapter<Estado>(this, R.layout.spinner_item, listEst);

        botonGuardar = findViewById(R.id.btnGuardar);
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

        tamano.setSelection(cfgActual.getTamano().getId()-1);
        tipografia.setSelection(cfgActual.getTipografia().getId()-1);
        color.setSelection(cfgActual.getColor().getId()-1);
        botones.setSelection(cfgActual.getColorBoton().getId()-1);
        vibracion.setSelection(cfgActual.getVibracion().getId()-1);
        sonido.setSelection(cfgActual.getSonido().getId()-1);


        botonGuardar.setBackgroundColor(config.setearColorBoton(this));
        botonGuardar.setTextColor(config.setearColorTexto(this));

        //Typeface typeface = getResources().getFont(R.font.myfont);
        botonGuardar.setTypeface(R.font.Helvetica);

        //tamano.setSelection(adapterTamano.getPosition(cfgActual.getTamano()));
        //tipografia.setSelection(adapterTipo.getPosition(cfgActual.getTipografia()));
        //color.setSelection(adapterColor.getPosition(cfgActual.getColor()));
        //botones.setSelection(adapterBotones.getPosition(cfgActual.getColorBoton()));
        //vibracion.setSelection(adapterVibracion.getPosition(cfgActual.getVibracion()));
        //sonido.setSelection(adapterSonido.getPosition(cfgActual.getSonido()));

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

    public void guardarConfig (View view)
    {
        config = new ConfiguracionDao();
        tipSelec = new Tipografia();
        tamSelec = new Tamano();
        colBotSelec = new Color();
        colSelec = new Color();
        estVibSelec = new Estado();
        estSonSelec = new Estado();


        //    private Spinner vibracion, sonido;

        tipSelec = (Tipografia) tipografia.getSelectedItem();
        tamSelec = (Tamano) tamano.getSelectedItem();
        colBotSelec = (Color) botones.getSelectedItem();
        colSelec = (Color) color.getSelectedItem();
        estVibSelec = (Estado) vibracion.getSelectedItem();
        estSonSelec = (Estado) sonido.getSelectedItem();

        if (colSelec.getId() == colBotSelec.getId())
        {
            toast = Toast.makeText(this, "El color del texto y del botón deben ser distintos", Toast.LENGTH_SHORT);
         }

        else

        {
            if (config.cargarConfiguracion(colSelec.getId(), colBotSelec.getId(), tipSelec.getId(), tamSelec.getId(), estVibSelec.getId(), estSonSelec.getId(), this))
            {

                toast = Toast.makeText(this, "Configuración modificada exitosamente", Toast.LENGTH_SHORT);

            }
            else
            {

                toast = Toast.makeText(this, "Error en el cambio de configuración", Toast.LENGTH_SHORT);

            }
        }


         toast.show();
    }
}
