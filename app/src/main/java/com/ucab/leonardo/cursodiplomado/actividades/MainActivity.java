package com.ucab.leonardo.cursodiplomado.actividades;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ucab.leonardo.cursodiplomado.R;
import com.ucab.leonardo.cursodiplomado.UsuarioAdapter;
import com.ucab.leonardo.cursodiplomado.api.ApiService;
import com.ucab.leonardo.cursodiplomado.api.ClienteRetrofit;
import com.ucab.leonardo.cursodiplomado.modelos.Usuario;
import com.ucab.leonardo.cursodiplomado.respuesta.RespuestaObtenerUsuarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    public RecyclerView recyclerView;

    // Lista donde se guardaran los usuarios
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public UsuarioAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Retrofit retrofit;

    final int imagenes[] = {
            R.drawable.ic1,
            R.drawable.ic2,
            R.drawable.ic3,
            R.drawable.ic4,
            R.drawable.ic5,
            R.drawable.ic6,
            R.drawable.ic7,
            R.drawable.ic8,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new UsuarioAdapter(this, usuarios);
        recyclerView = findViewById(R.id.recyvler_view);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Obtiene el cliente de retrofit
        retrofit = ClienteRetrofit.obtenerClienteRetrofit();
        refrescarUsuarios();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrearUsuarioActivity.class);
                startActivity(intent);
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Log.w(TAG, "onSuccess: " + instanceIdResult.getToken());
            }
        });

        // A partir de Android Oreo, las notificaciones deben pertenecer a un canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String idCanal = "1";
            String nombreCanal = "Mi canal";
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(idCanal,
                    nombreCanal, NotificationManager.IMPORTANCE_HIGH));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Actualiza
        if (id == R.id.action_refrescar) {
            refrescarUsuarios();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Hace la peticion al servidor para obtener todos los usuarios
    private void refrescarUsuarios() {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<RespuestaObtenerUsuarios> call = apiService.obtenerUsuarios();
        call.enqueue(new Callback<RespuestaObtenerUsuarios>() {
            @Override
            public void onResponse(Call<RespuestaObtenerUsuarios> call, Response<RespuestaObtenerUsuarios> response) {
                Log.w(TAG, "Codigo respuesta: " + response.code());

                if (response.isSuccessful()) {
                    Log.w(TAG, "Respuesta exitosa");
                    if (response.body() != null) {
                        List<Usuario> usuariosRespuesta = response.body().getUsuarios();
                        usuarios.clear();
                        for (int i = 0; i < usuariosRespuesta.size(); i++) {
                            Usuario u = usuariosRespuesta.get(i);
                            String nombre = u.getNombre();
                            String apellido = u.getApellido();
                            String empresa = u.getEmpresa();
                            String direccion = u.getDireccion();
                            String edad = u.getEdad();
                            String email = u.getEmail();

                            Usuario nuevoUsuario = new Usuario(nombre, apellido, empresa,
                                    direccion, edad, email, imagenes[i % 8]);
                            usuarios.add(nuevoUsuario);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaObtenerUsuarios> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Error de conexion en la red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}