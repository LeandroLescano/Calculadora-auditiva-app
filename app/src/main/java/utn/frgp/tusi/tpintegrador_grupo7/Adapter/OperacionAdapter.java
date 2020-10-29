package utn.frgp.tusi.tpintegrador_grupo7.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import utn.frgp.tusi.tpintegrador_grupo7.R;
import utn.frgp.tusi.tpintegrador_grupo7.Dominio.Operacion;


public class OperacionAdapter extends BaseAdapter {

    private ArrayList<Operacion> operaciones;
    private Context context;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = convertView;
        if(convertView == null){
            view = inflater.inflate(R.layout.card_template, null);
        }

        TextView operacion = (TextView) view.findViewById(R.id.txt_operacion);
        operacion.setText(getItem(position).getOperacion());

        return view;
    }
}
