package com.ucab.leonardo.cursodiplomado;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ucab.leonardo.cursodiplomado.actividades.DetallesUsuarioActivity;
import com.ucab.leonardo.cursodiplomado.actividades.EditarUsuarioActivity;
import com.ucab.leonardo.cursodiplomado.modelos.Usuario;

import java.util.ArrayList;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private final String TAG = UsuarioAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Usuario> usuarios;

    public UsuarioAdapter(Context context, ArrayList<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    class UsuarioViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPerfil;
        private TextView tvNombres;
        private TextView tvEmpresa;
        private ImageView ivEditar;
        private ConstraintLayout clFilaUsuario;

        UsuarioViewHolder(View itemView) {
            super(itemView);
            ivPerfil = itemView.findViewById(R.id.iv_perfil);
            tvNombres = itemView.findViewById(R.id.tv_nombres);
            tvEmpresa = itemView.findViewById(R.id.tv_empresa);
            ivEditar = itemView.findViewById(R.id.iv_editar);
            clFilaUsuario = itemView.findViewById(R.id.constraint_layout_fila_usuario);
        }
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_row, parent, false);

        return new UsuarioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsuarioViewHolder holder, int position) {
        final Usuario usuario = usuarios.get(holder.getAdapterPosition());
        holder.tvNombres.setText(String.format("%s %s", usuario.getNombre(), usuario.getApellido()));
        holder.tvEmpresa.setText(usuario.getEmpresa());

        Glide.with(context)
                .load(usuario.getImagen())
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.overrideOf(200, 200))
                .into(holder.ivPerfil);


        holder.clFilaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetallesUsuarioActivity.class);
                prepararIntent(intent, usuario);
                context.startActivity(intent);
            }
        });

        holder.ivEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarUsuarioActivity.class);
                prepararIntent(intent, usuario);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    private void prepararIntent(Intent intent, Usuario usuario) {
        intent.putExtra("nombre", usuario.getNombre());
        intent.putExtra("apellido", usuario.getApellido());
        intent.putExtra("empresa", usuario.getEmpresa());
        intent.putExtra("direccion", usuario.getDireccion());
        intent.putExtra("edad", usuario.getEdad());
        intent.putExtra("email", usuario.getEmail());
        intent.putExtra("imagen", usuario.getImagen());
    }

}
