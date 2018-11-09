package com.ucab.leonardo.cursodiplomado.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ucab.leonardo.cursodiplomado.peticiones.PeticionCrearUsuario;
import com.ucab.leonardo.cursodiplomado.R;
import com.ucab.leonardo.cursodiplomado.respuesta.RespuestaCrearUsuario;
import com.ucab.leonardo.cursodiplomado.api.ApiService;
import com.ucab.leonardo.cursodiplomado.api.ClienteRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CrearUsuarioActivity extends AppCompatActivity {

    private final String TAG = CrearUsuarioActivity.class.getSimpleName();

    private EditText etNombre;
    private EditText etApellido;
    private EditText etEmpresa;
    private EditText etDireccion;
    private EditText etEdad;
    private EditText etEmail;

    private Button btnCancelar;
    private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        etNombre = findViewById(R.id.et_nombre_crear);
        etApellido = findViewById(R.id.et_apellido_crear);
        etEmpresa = findViewById(R.id.et_empresa_crear);
        etDireccion = findViewById(R.id.et_direccion_crear);
        etEdad = findViewById(R.id.et_edad_crear);
        etEmail = findViewById(R.id.et_email_crear);

        Retrofit retrofit = ClienteRetrofit.obtenerClienteRetrofit();

        final ApiService apiService = retrofit.create(ApiService.class);

        btnCancelar = findViewById(R.id.btn_cancelar_crear);
        btnAceptar = findViewById(R.id.btn_aceptar_crear);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String apellido = etApellido.getText().toString();
                String empresa = etEmpresa.getText().toString();
                String direccion = etDireccion.getText().toString();
                String edad = etEdad.getText().toString();
                String email = etEmail.getText().toString();

                PeticionCrearUsuario peticion = new PeticionCrearUsuario(nombre, apellido, empresa, direccion, edad, email);
                Call<RespuestaCrearUsuario> call = apiService.crearUsuario(peticion);
                call.enqueue(new Callback<RespuestaCrearUsuario>() {
                    @Override
                    public void onResponse(Call<RespuestaCrearUsuario> call, Response<RespuestaCrearUsuario> response) {
                        if (response.isSuccessful()) {
                            Log.w(TAG, "Usuario creado con exito");
                            Toast.makeText(CrearUsuarioActivity.this,
                                    "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CrearUsuarioActivity.this,
                                    "La peticion no fue exitosa. Intente nuevamente",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaCrearUsuario> call, Throwable t) {
                        Toast.makeText(CrearUsuarioActivity.this,
                                "Error de conexion en la red. Intente nuevamente",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
