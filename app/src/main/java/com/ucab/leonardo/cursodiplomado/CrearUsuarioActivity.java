package com.ucab.leonardo.cursodiplomado;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CrearUsuarioActivity extends AppCompatActivity {

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

            }
        });
    }
}
