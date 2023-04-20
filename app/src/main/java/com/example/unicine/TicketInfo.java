package com.example.unicine;

import java.util.List;

public class TicketInfo {
    private String nombreCine;
    private String nombreSala;
    private String fecha;
    private String hora;
    private List<String> asientos;
    private String nombreUsuario;
    private String nombrePelicula;

    private int completedQueries;

    // Constructor
    public TicketInfo(String nombreCine, String nombreSala, String fecha, String hora, List<String> asientos, String nombreUsuario, String nombrePelicula) {
        this.nombreCine = nombreCine;
        this.nombreSala = nombreSala;
        this.fecha = fecha;
        this.hora = hora;
        this.asientos = asientos;
        this.nombreUsuario = nombreUsuario;
        this.nombrePelicula = nombrePelicula;

        this.completedQueries = 0;
    }

    // Constructor vacío requerido para Firestore
    public TicketInfo() {}

    // Getters y setters
    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<String> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<String> asientos) {
        this.asientos = asientos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public int getCompletedQueries() {
        return completedQueries;
    }

    public void incrementCompletedQueries() {
        completedQueries++;
    }
}
