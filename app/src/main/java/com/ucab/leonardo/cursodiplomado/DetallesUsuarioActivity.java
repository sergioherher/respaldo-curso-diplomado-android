package com.ucab.leonardo.cursodiplomado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetallesUsuarioActivity extends AppCompatActivity {

    private ImageView ivPerfil;
    private TextView tvNombres;
    private TextView tvEmpresa;
    private TextView tvDireccion;
    private TextView tvEdad;
    private TextView tvEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_usuario);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String apellido = intent.getStringExtra("apellido");
        String empresa = intent.getStringExtra("empresa");
        String direccion = intent.getStringExtra("direccion");
        String edad = intent.getStringExtra("edad");
        String email = intent.getStringExtra("email");
        int imagen = intent.getIntExtra("imagen", -1);

//        ivPerfil = findViewById(R.id.iv_detalle_perfil);
        tvNombres = findViewById(R.id.tv_detalle_nombres);
        tvEmpresa = findViewById(R.id.tv_detalle_empresa);
        tvDireccion = findViewById(R.id.tv_detalle_direccion);
        tvEdad = findViewById(R.id.tv_detalle_edad);
        tvEmail = findViewById(R.id.tv_detalle_email);

        tvNombres.setText(String.format("%s %s", nombre, apellido));
        tvEmpresa.setText(empresa);
        tvDireccion.setText(direccion);
        tvEdad.setText(String.format("%s años", edad));
        tvEmail.setText(email);

//        Glide.with(this)
//                .load(imagen)
//                .apply(RequestOptions.overrideOf(600, 600))
//                .into(ivPerfil);
    }
}
