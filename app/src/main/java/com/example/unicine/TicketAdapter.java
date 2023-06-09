package com.example.unicine;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private List<String> cinemaNames;
    private List<String> roomNames;
    private List<String> showDates;
    private List<String> showTimes;
    private List<String> reservedSeats;
    private List<String> userNames;
    private List<String> moovieNames;
    private List<String> idTickets;
    private List<String> idSesiones;
    private List<String> precioTickets;


    public TicketAdapter(List<String> cinemaNames, List<String> roomNames, List<String> showDates, List<String> showTimes, List<String> reservedSeats, List<String> userNames, List<String> moovieNames, List<String> idTickets, List<String> idSesiones,  List<String> precioTickets) {
        this.cinemaNames = cinemaNames;
        this.roomNames = roomNames;
        this.showDates = showDates;
        this.showTimes = showTimes;
        this.reservedSeats = reservedSeats;
        this.userNames = userNames;
        this.moovieNames = moovieNames;
        this.idTickets = idTickets;
        this.idSesiones = idSesiones;
        this.precioTickets = precioTickets;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String cinemaName = cinemaNames.get(position);
        String roomName = roomNames.get(position);
        String showDate = showDates.get(position);
        String showTime = showTimes.get(position);
        String reservedSeat = reservedSeats.get(position);
        String userName = userNames.get(0);
        String mooviName = moovieNames.get(position);
        String idTicket = idTickets.get(position);
        String idSesion = idSesiones.get(position);
        String precio = precioTickets.get(0);

        holder.tvCinemaName.setText("Cine: " + cinemaName);
        holder.tvShowDate.setText("Fecha y Hora: " + showDate + " " + showTime);
        holder.tvReservedSeats.setText("Asientos: " + reservedSeat);
        holder.tvMoovieName.setText("Película: " + mooviName);


        // Agrega un listener de clics
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), VisualizarTicket.class);
                intent.putExtra("cine", cinemaName);
                intent.putExtra("sala", roomName);
                intent.putExtra("fecha", showDate);
                intent.putExtra("hora", showTime);
                intent.putExtra("asientos", reservedSeat);
                intent.putExtra("usuario", userName);
                intent.putExtra("pelicula", mooviName);
                intent.putExtra("idTicket", idTicket);
                intent.putExtra("idSesiones", idSesion);
                intent.putExtra("precio", precio);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return cinemaNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCinemaName;
        TextView tvShowDate;
        TextView tvReservedSeats;
        TextView tvMoovieName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCinemaName = itemView.findViewById(R.id.tv_cinema_name);
            tvShowDate = itemView.findViewById(R.id.tv_show_date);
            tvReservedSeats = itemView.findViewById(R.id.tv_reserved_seats);
            tvMoovieName = itemView.findViewById(R.id.tv_movie_name);
        }
    }
}
