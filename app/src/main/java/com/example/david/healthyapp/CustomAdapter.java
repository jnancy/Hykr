package com.example.david.healthyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Trail> {

    public CustomAdapter(Context context, ArrayList<Trail> trails) {
        super(context, R.layout.list_adapter,trails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Trail trail = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_adapter, parent, false);
        }
        TextView firstLine = convertView.findViewById(R.id.firstLine);
        TextView secondLine = convertView.findViewById(R.id.secondLine);
        firstLine.setText(trail.name);
        secondLine.setText(trail.length + " km");

        return convertView;
    }
}
