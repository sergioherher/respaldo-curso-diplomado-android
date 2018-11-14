package com.ucab.leonardo.cursodiplomado.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    private TextView tvResultadoFiltroVacio;

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

        tvResultadoFiltroVacio = findViewById(R.id.tv_resultado_filtro_vacio);

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
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Obtiene el elemento del menu referente a la accion de buscar
        final MenuItem item = menu.findItem(R.id.action_search);

        // Obtiene el view de la barra de busqueda
        SearchView searchView = (SearchView) item.getActionView();

        // Establece el texto que funciona como pista en la barra
        searchView.setQueryHint(getResources().getString(R.string.action_search));

        // Establece el listener para las acciones de la barra de busqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Escucha cuando el usuario presiona el boton de buscar
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // Escucha a medida que el usuario escribe texto en la barra
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filtrar(newText);

                // Si no hubo resultados, muestra el TextView que lo indica
                if (adapter.getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    tvResultadoFiltroVacio.setVisibility(View.VISIBLE);
                } else if (recyclerView.getVisibility() == View.GONE) {
                    // Si el RecyclerView estaba oculto, lo hacemos visible otra vez
                    recyclerView.setVisibility(View.VISIBLE);
                    tvResultadoFiltroVacio.setVisibility(View.GONE);
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Manejamos los clicks que se hagan a los botones del menu, dependiendo de cual sea
        switch (id) {
            case R.id.action_refrescar:
                refrescarUsuarios();
                return true;
            case R.id.action_search:
                TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.toolbar));
                item.expandActionView();
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
                        adapter.setCopiaUsuarios(usuarios);
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