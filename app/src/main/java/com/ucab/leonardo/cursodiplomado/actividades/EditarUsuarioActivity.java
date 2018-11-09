package com.ucab.leonardo.cursodiplomado.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ucab.leonardo.cursodiplomado.peticiones.PeticionActualizarUsuario;
import com.ucab.leonardo.cursodiplomado.R;
import com.ucab.leonardo.cursodiplomado.respuesta.RespuestaActualizarUsuario;
import com.ucab.leonardo.cursodiplomado.api.ApiService;
import com.ucab.leonardo.cursodiplomado.api.ClienteRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditarUsuarioActivity extends AppCompatActivity {

    private final String TAG = EditarUsuarioActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_editar_usuario);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String apellido = intent.getStringExtra("apellido");
        String empresa = intent.getStringExtra("empresa");
        String direccion = intent.getStringExtra("direccion");
        String edad = intent.getStringExtra("edad");
        String email = intent.getStringExtra("email");

        etNombre = findViewById(R.id.et_nombre);
        etApellido = findViewById(R.id.et_apellido);
        etEmpresa = findViewById(R.id.et_empresa);
        etDireccion = findViewById(R.id.et_direccion);
        etEdad = findViewById(R.id.et_edad);
        etEmail = findViewById(R.id.et_email);

        Retrofit retrofit = ClienteRetrofit.obtenerClienteRetrofit();

        final ApiService apiService = retrofit.create(ApiService.class);

        btnCancelar = findViewById(R.id.btn_cancelar);
        btnAceptar = findViewById(R.id.btn_aceptar);


        etNombre.setText(nombre);
        etApellido.setText(apellido);
        etEmpresa.setText(empresa);
        etDireccion.setText(direccion);
        etEdad.setText(edad);
        etEmail.setText(email);

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

                PeticionActualizarUsuario peticion = new PeticionActualizarUsuario(nombre, apellido,
                        empresa, direccion, edad, email);
                Call<RespuestaActualizarUsuario> call = apiService.actualizarUsuario(email, peticion);
                call.enqueue(new Callback<RespuestaActualizarUsuario>() {
                    @Override
                    public void onResponse(Call<RespuestaActualizarUsuario> call, Response<RespuestaActualizarUsuario> response) {
                        if (response.isSuccessful()) {
                            Log.w(TAG, "Usuario actualizado con exito");
                            Toast.makeText(EditarUsuarioActivity.this,
                                    "Usuario actualizado con exito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditarUsuarioActivity.this,
                                    "La peticion no fue exitosa", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaActualizarUsuario> call, Throwable t) {
                        Toast.makeText(EditarUsuarioActivity.this,
                                "Error de conexion en la red", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
