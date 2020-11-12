package utn.frgp.tusi.tpintegrador_grupo7.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import utn.frgp.tusi.tpintegrador_grupo7.AccesoDatos.ConfiguracionDao;
import utn.frgp.tusi.tpintegrador_grupo7.R;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Operacion;


public class OperacionAdapter extends BaseAdapter {

    private ArrayList<Operacion> operaciones;
    private Context context;
    private Button botonVer;
    private ConfiguracionDao config;

    public OperacionAdapter(Context context, ArrayList<Operacion> operaciones){
        this.context = context;
        this.operaciones = operaciones;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = convertView;
        if(convertView == null){
            view = inflater.inflate(R.layout.card_template, null);
        }
        config = new ConfiguracionDao();
        TextView operacion = (TextView) view.findViewById(R.id.txt_operacion);
        operacion.setText(getItem(position).getOperacion());
        botonVer = (Button) view.findViewById(R.id.btn_Ver);
        botonVer.setBackgroundColor(config.setearColorBoton(context));
        botonVer.setTextColor(config.setearColorTexto(context));
        botonVer.setTypeface(config.setearTipografia(context));

        return view;
    }
}
