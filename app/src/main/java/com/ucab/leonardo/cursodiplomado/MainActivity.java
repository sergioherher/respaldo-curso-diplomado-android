package com.ucab.leonardo.cursodiplomado;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    public RecyclerView recyclerView;
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    public UsuarioAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuarios.add(new Usuario("Leonardo", "Guedez", "CVG Venalum",
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
                "Urb. Las Garzas", "21", "mperez@gmail.com", R.drawable.ic8));
        adapter = new UsuarioAdapter(this, usuarios);

        recyclerView = findViewById(R.id.recyvler_view);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));


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
            notificationManager.createNotificationChannel(channel);
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

    public static void actualizarUsuario(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equalsIgnoreCase(usuario.getNombre())) {
                usuarios.set(i, usuario);
                return;
            }
        }
    }
}