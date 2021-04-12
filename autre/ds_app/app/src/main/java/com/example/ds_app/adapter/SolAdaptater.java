package com.example.ds_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ds_app.R;
import com.example.ds_app.modele.Sol;

import java.util.List;

public class SolAdaptater extends ArrayAdapter<Sol> {
    private Context context;
    private List<Sol> values;
    private LayoutInflater inflater;

    public SolAdaptater(Context context, List<Sol> sol_list) {
        super(context, -1, sol_list);
        this.context = context;
        this.values = sol_list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View solView = inflater.inflate(R.layout.sol_list, parent, false);
        Sol modele = values.get(position);
        TextView solName = solView.findViewById(R.id.name);
        TextView solTemperature = solView.findViewById(R.id.temperature);
        TextView solPression = solView.findViewById(R.id.pression);
        solName.setText("Sol n°" + modele.getKey());
        solTemperature.setText(context.getResources().getText((R.string.temperature)) + " : " + modele.getAvg_temperature());
        solPression.setText(context.getResources().getText((R.string.pressure)) + " : " + modele.getAvg_pre());
        return solView;
    }

}
