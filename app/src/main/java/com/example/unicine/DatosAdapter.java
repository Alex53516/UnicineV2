package com.example.unicine;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

 public class DatosAdapter extends RecyclerView.Adapter<DatosAdapter.ViewHolder> {

    private List<String> dates;
    private List<String> times;
    private List<String> names;
     private List<String> idCines;
     private List<String> idSesion;




     public DatosAdapter(List<String> dates, List<String> times, List<String> names, List<String> idcin , List<String> idsesi) {
        this.dates = dates;
        this.times = times;
        this.names = names;
        this.idCines = idcin;
        this.idSesion = idsesi;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date = dates.get(position);
        String time = times.get(position);
        String name = names.get(position);
        String idC = idCines.get(0);
        String idS = idSesion.get(position);





        holder.dateText.setText("Fecha: " + date);
        holder.timeText.setText("Hora: " + time);
        holder.nameText.setText("Sala: " + name);

        // Agrega un listener de clics
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), SeleccionAsiento.class);
                intent.putExtra("idCines", idC);
                intent.putExtra("idSesiones", idS);
                intent.putExtra("nombreSala", name);




                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;
        TextView timeText;
        TextView nameText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.date_text);
            timeText = itemView.findViewById(R.id.time_text);
            nameText = itemView.findViewById(R.id.name_text);
        }
    }
}
