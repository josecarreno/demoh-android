package com.josec.demoh;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AgregarClienteFragment extends DialogFragment {

    CrearClienteCallback crearClienteCallback;
    private TextInputLayout inputLayoutNombre;
    private TextInputLayout inputLayoutApellido;
    private TextInputLayout inputLayoutEdad;
    private TextInputLayout inputLayoutFechaNacimiento;

    public AgregarClienteFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return inflater.inflate(R.layout.fragment_agregar_cliente, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fitFullScreen();
    }

    private void fitFullScreen() {
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        referenciarComponentes();
        configurarBotonCerrar();
        configurarBotonCrearCliente();
    }

    private void referenciarComponentes() {
        inputLayoutNombre = getView().findViewById(R.id.inputLayoutNombre);
        inputLayoutApellido = getView().findViewById(R.id.inputLayoutApellido);
        inputLayoutEdad = getView().findViewById(R.id.inputLayoutEdad);
        inputLayoutFechaNacimiento = getView().findViewById(R.id.inputLayoutFechaNacimiento);
    }

    private void configurarBotonCerrar() {
        ImageButton botonCerrar = getView().findViewById(R.id.botonCerrar);

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void configurarBotonCrearCliente() {
        Button botonCrearCliente = getView().findViewById(R.id.botonCrearCliente);

        botonCrearCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference referenciaClientes = database.getReference("clientes");
                Cliente nuevoCliente = new Cliente(inputLayoutNombre.getEditText().getText().toString(),
                        inputLayoutApellido.getEditText().getText().toString(),
                        inputLayoutEdad.getEditText().getText().toString(),
                        inputLayoutFechaNacimiento.getEditText().getText().toString());

                referenciaClientes
                        .push()
                        .setValue(nuevoCliente)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        crearClienteCallback.alCrearCliente();
                        dismiss();
                    }
                });

            }
        });
    }

    interface CrearClienteCallback {
        void alCrearCliente();
    }
}
