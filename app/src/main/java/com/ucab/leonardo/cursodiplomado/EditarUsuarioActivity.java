package com.ucab.leonardo.cursodiplomado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarUsuarioActivity extends AppCompatActivity {

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

            }
        });
    }
}
