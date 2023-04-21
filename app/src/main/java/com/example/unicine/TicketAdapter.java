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

    public TicketAdapter(List<String> cinemaNames, List<String> roomNames, List<String> showDates, List<String> showTimes, List<String> reservedSeats, List<String> userNames, List<String> moovieNames) {
        this.cinemaNames = cinemaNames;
        this.roomNames = roomNames;
        this.showDates = showDates;
        this.showTimes = showTimes;
        this.reservedSeats = reservedSeats;
        this.userNames = userNames;
        this.moovieNames = moovieNames;

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

        holder.tvCinemaName.setText("Cine: " + cinemaName);
        holder.tvRoomName.setText("Sala: " + roomName);
        holder.tvShowDate.setText("Fecha y Hora: " + showDate + " " + showTime);
        holder.tvReservedSeats.setText("Asientos: " + reservedSeat);
        holder.tvUserName.setText("Cliente: " + userName);
        holder.tvMoovieName.setText("Película: " + mooviName);


        // Agrega un listener de clics
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), VisualizarTicket.class);
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
        TextView tvRoomName;
        TextView tvShowDate;
        TextView tvReservedSeats;
        TextView tvUserName;
        TextView tvMoovieName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCinemaName = itemView.findViewById(R.id.tv_cinema_name);
            tvRoomName = itemView.findViewById(R.id.tv_room_name);
            tvShowDate = itemView.findViewById(R.id.tv_show_date);
            tvReservedSeats = itemView.findViewById(R.id.tv_reserved_seats);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvMoovieName = itemView.findViewById(R.id.tv_movie_name);
        }
    }
}
