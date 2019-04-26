package com.josec.demoh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientesActivity extends AppCompatActivity implements AgregarClienteFragment.CrearClienteCallback {
    FirebaseDatabase database;
    DatabaseReference referenciaClientes;
    FirebaseRecyclerOptions<Cliente> opciones;
    FirebaseRecyclerAdapter<Cliente, ClienteViewHolder> adapter;
    RecyclerView recyclerClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        database = FirebaseDatabase.getInstance();
        referenciaClientes = database.getReference("clientes");
        referenciaClientes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mostrarClientes();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        recyclerClientes = findViewById(R.id.recyclerViewClientes);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this));

        configurarToolbar();
        configurarBotonAbrirRegistroClientes();

        mostrarClientes();
    }

    @Override
    protected void onStop() {
        if (adapter != null) {
            adapter.stopListening();
        }
        super.onStop();
    }

    private void configurarBotonAbrirRegistroClientes() {
        Button botonAbrirRegistroClientes = findViewById(R.id.botonAbrirRegistroClientes);

        botonAbrirRegistroClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarClienteFragment fragment = new AgregarClienteFragment();
                fragment.crearClienteCallback = ClientesActivity.this;

                fragment.show(getSupportFragmentManager(), null);
            }
        });
    }

    private void configurarToolbar() {
        Toolbar clientesToolbar = findViewById(R.id.clientesToolbar);
        setSupportActionBar(clientesToolbar);
    }

    private void mostrarClientes() {
        opciones = new FirebaseRecyclerOptions.Builder<Cliente>()
                .setQuery(referenciaClientes, Cliente.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Cliente, ClienteViewHolder>(opciones) {
            @Override
            protected void onBindViewHolder(@NonNull ClienteViewHolder holder, int position, @NonNull Cliente model) {
                holder.textViewNombres.setText(model.nombre + " " + model.apellido);
                holder.textViewDatos.setText(model.edad + " años - fecha de nacimiento: " + model.fechaNacimiento);
            }

            @NonNull
            @Override
            public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_cliente, viewGroup, false);

                return new ClienteViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerClientes.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.accionSalir) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .build();

            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
            googleSignInClient.signOut();
            FirebaseAuth.getInstance().signOut();
            Intent newActivity = new Intent(this, MainActivity.class);
            startActivity(newActivity);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void alCrearCliente() {
        adapter.notifyDataSetChanged();
    }
}
