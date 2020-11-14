package utn.frgp.tusi.tpintegrador_grupo7.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;

import utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos.ConfiguracionDao;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Configuracion;
import utn.frgp.tusi.tpintegrador_grupo7.R;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Operacion;


public class OperacionAdapter extends BaseAdapter {

    private ArrayList<Operacion> operaciones;
    private Context context;
    private Button botonVer;
    private ConfiguracionDao config;
    private Configuracion cfgActual;
    private SharedPreferences preferences;
    private final String[] operadores = new String[]{"arctan(", "arcsin(", "arccos(", "tan(", "sin(", "cos(", "lg(", "ln(", "^", "√"};

    public OperacionAdapter(Context context, ArrayList<Operacion> operaciones){
        this.context = context;
        this.operaciones = operaciones;
        preferences = context.getSharedPreferences("calculadora", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return operaciones.size();
    }

    @Override
    public Operacion getItem(int i) {
        return operaciones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = convertView;
        if(convertView == null){
            view = inflater.inflate(R.layout.card_template, null);
        }

        final TextView operacion = (TextView) view.findViewById(R.id.txt_operacion);
        operacion.setText(getItem(position).getOperacion());
        botonVer = (Button) view.findViewById(R.id.btn_Ver);
        botonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String opActual = getItem(position).getOperacion();
                Intent intent;
                boolean contieneOperadores = false;
                for(String op : operadores){
                    if(opActual.contains(op)){
                        contieneOperadores = true;
                        break;
                    }
                }
                if(contieneOperadores){
                    intent = new Intent(context, utn.frgp.tusi.tpintegrador_grupo7.CalculadoraCientifica.class);
                }else{
                    String ultimaC = preferences.getString("ultima", "");
                    if(ultimaC != null && ultimaC.equals("cientifica")){
                        intent = new Intent(context, utn.frgp.tusi.tpintegrador_grupo7.CalculadoraCientifica.class);

                    }else{
                        intent = new Intent(context, utn.frgp.tusi.tpintegrador_grupo7.CalculadoraBasica.class);
                    }
                }
                intent.putExtra("operacion", opActual);
                context.startActivity(intent);
            }
        });

        cargarConfiguracion(view);
        return view;
    }

    public void cargarConfiguracion(View view)
    {
        config = new ConfiguracionDao();
        cfgActual = config.traerConfiguracion(context);
        botonVer = (Button) view.findViewById(R.id.btn_Ver);
        botonVer.setBackgroundColor(config.setearColorBoton(context));
        botonVer.setTextColor(config.setearColorTexto(context));
        botonVer.setTypeface(config.setearTipografia(context));
    }
}
