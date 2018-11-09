package com.ucab.leonardo.cursodiplomado;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    public RecyclerView recyclerView;
    private List<Usuario> usuarios = new ArrayList<>();

    public UsuarioAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*usuarios.add(new Usuario("Leonardo", "Guedez", "CVG Venalum",
                "Urb. Las Garzas, manzana 23, casa 9","21", "leo@gmail.com", R.drawable.ic1));

        usuarios.add(new Usuario("Pedro", "Perez", "CVG Bauxilum",
                "Residencia Los Saltos, casa 14","21", "pedroperez@gmail.com", R.drawable.ic2));

        usuarios.add(new Usuario("Jesus", "Rodriguez", "CVG Alcasa",
                "Urb. Loma Linda, Manzana 14, calle 16, casa 4","21", "jesus.rodriguez@gmail.com", R.drawable.ic3));

        usuarios.add(new Usuario("Carlos", "Gonzalez", "SIDOR",
                "Urb. Loma Linda, Manzana 14, calle 16, casa 4","21", "carlosg@gmail.com", R.drawable.ic4));

        usuarios.add(new Usuario("Luis", "Perez", "Momentum Lab",
                "Urb. Villa Africana, calle 14, casa 2","21", "perezluis@gmail.com", R.drawable.ic5));

        usuarios.add(new Usuario("Maria", "Villalba", "CVG Venalum",
                "Urb. Las Garzas","21", "villalbamaria@gmail.com", R.drawable.ic6));

        usuarios.add(new Usuario("Jose", "Rodriguez", "Empresas Polar",
                "Urb. Las Garzas","21", "jrodriguez@gmail.com", R.drawable.ic7));

        usuarios.add(new Usuario("Manuel", "Perez","SIDOR",
                "Urb. Las Garzas", "21", "mperez@gmail.com", R.drawable.ic8));*/
        adapter = new UsuarioAdapter(this, usuarios);

        recyclerView = findViewById(R.id.recyvler_view);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.250.7:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
        ApiService apiService = retrofit.create(ApiService.class);
        Call<RespuestaObtenerUsuarios> call = apiService.obtenerUsuarios();
        call.enqueue(new Callback<RespuestaObtenerUsuarios>() {
            @Override
            public void onResponse(Call<RespuestaObtenerUsuarios> call, Response<RespuestaObtenerUsuarios> response) {
                Log.w(TAG, "RESPUESTA: " + response.code());

                if (response.isSuccessful()) {
                    Log.w(TAG, "Respuesta exitosa");
                    if (response.body() != null) {
                        List<Usuario> usuariosRespuesta = response.body().getUsuarios();

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

            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrearUsuarioActivity.class);
                startActivity(intent);
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.w(TAG, token);
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("micanal123", "canal3", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Un buen canal");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(MyFirebaseMessagingService.RESULT));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}