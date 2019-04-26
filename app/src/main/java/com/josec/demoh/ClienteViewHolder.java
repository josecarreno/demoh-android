package com.josec.demoh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class ClienteViewHolder extends RecyclerView.ViewHolder {
    TextView textViewNombres;
    TextView textViewDatos;

    public ClienteViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewNombres = itemView.findViewById(R.id.textViewNombres);
        textViewDatos = itemView.findViewById(R.id.textViewDatos);
    }
}
