package com.ucab.leonardo.cursodiplomado;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private final String TAG = UsuarioAdapter.class.getSimpleName();

    private Context context;
    private List<Usuario> usuarios;

    public UsuarioAdapter(Context context, List<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    class UsuarioViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombres;
        private TextView tvEmail;
        private TextView tvEdad;

        UsuarioViewHolder(View itemView) {
            super(itemView);
            tvNombres = itemView.findViewById(R.id.tv_nombres);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvEdad = itemView.findViewById(R.id.tv_edad);
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
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(holder.getAdapterPosition());
        holder.tvNombres.setText(usuario.getNombre() + " " + usuario.getApellido());
        holder.tvEmail.setText(usuario.getEmail());
        holder.tvEdad.setText(usuario.getEdad());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }


}
