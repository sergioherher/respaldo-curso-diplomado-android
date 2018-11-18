package com.ucab.leonardo.cursodiplomado.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ucab.leonardo.cursodiplomado.R;
import com.ucab.leonardo.cursodiplomado.RecyclerItemTouchHelper;
import com.ucab.leonardo.cursodiplomado.UsuarioAdapter;
import com.ucab.leonardo.cursodiplomado.api.ApiService;
import com.ucab.leonardo.cursodiplomado.api.ClienteRetrofit;
import com.ucab.leonardo.cursodiplomado.modelos.Usuario;
import com.ucab.leonardo.cursodiplomado.respuesta.RespuestaBorraUsuario;
import com.ucab.leonardo.cursodiplomado.respuesta.RespuestaObtenerUsuarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private final String TAG = MainActivity.class.getSimpleName();

    private CoordinatorLayout coordinatorLayout;

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

        coordinatorLayout = findViewById(R.id.coordinator_layout);

        adapter = new UsuarioAdapter(this, usuarios);
        recyclerView = findViewById(R.id.recyvler_view);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

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

    // Metodo que se llama cuando se elimina una entrada de la lista con el gesto swipe
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        // Guardamos la posicion de la orden que se va a borrar, por si se quiere "deshacer"
        final int deletedUserPosition = viewHolder.getAdapterPosition();
        Log.e(TAG, "-----ANTES-----");
        Log.w(TAG, "deletedUserPosition " + deletedUserPosition);
        Log.w(TAG, "adapter.getItemCount() " + adapter.getItemCount());

        // Obtiene el nombre del usuaro que se va a borrar
        final Usuario usuario = usuarios.get(deletedUserPosition);
        String nombre = usuario.getNombre();

        // Obtener el email del usuario que se va a borrar
        final String email = usuario.getEmail();

        adapter.removeItem(deletedUserPosition);
        Log.e(TAG, "-----DESPUES-----");
        Log.w(TAG, "adapter.getItemCount() " + adapter.getItemCount());

        Snackbar snackbar = Snackbar.make(coordinatorLayout,
                nombre + " eliminado", 5000);
        snackbar.setAction("Deshacer", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.restoreItem(usuario, deletedUserPosition);
                Log.w(TAG, "Restaura, adapter.getItemCount() " + adapter.getItemCount());

                // Si era el primero o el ultimo de la lista, hace scroll hacia esa posicion
                if (eraElPrimero(deletedUserPosition) || eraElUltimo(deletedUserPosition)) {
                    layoutManager.scrollToPosition(deletedUserPosition);
                }
            }
        });

        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                //see Snackbar.Callback docs for event details

                final ApiService apiService = retrofit.create(ApiService.class);
                //Retrofit retrofit = ClienteRetrofit.obtenerClienteRetrofit();

                Call<RespuestaBorraUsuario> call = apiService.borrarUsuario(email);
                call.enqueue(new Callback<RespuestaBorraUsuario>() {
                    @Override
                    public void onResponse(Call<RespuestaBorraUsuario> call, Response<RespuestaBorraUsuario> response) {
                        if (response.isSuccessful()) {
                            Log.w(TAG, "Usuario borrado");
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "La peticion no fue exitosa. Intente nuevamente",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaBorraUsuario> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                "Error de conexion en la red. Intente nuevamente",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onShown(Snackbar snackbar) {

            }
        });


        snackbar.show();
    }

    // Indica si el usuario eliminado era el primero de la lista. Retorna true si es asi, false sino
    private boolean eraElPrimero(int position) {
        return position == 0;
    }

    // Indica si el usuario eliminado era el ultimo de la lista. Retorna true si es asi, false sino
    private boolean eraElUltimo(int positon) {
        // Restamos uno porque para el momento en que se llama a este metodo, ya el elemento fue
        // restaurado, por lo que volvemos a tener la misma cantidad de elementos de antes de borrar
        return positon == adapter.getItemCount()-1;
    }
}