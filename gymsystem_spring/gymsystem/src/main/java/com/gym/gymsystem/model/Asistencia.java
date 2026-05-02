package com.gym.gymsystem.model;

public class Asistencia {
    private Long id;
    private String trabajador;
    private String fecha;
    private String horaIngreso;
    private String horaSalida;

    public Asistencia() {}

    public Asistencia(Long id, String trabajador, String fecha, String horaIngreso, String horaSalida) {
        this.id = id;
        this.trabajador = trabajador;
        this.fecha = fecha;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTrabajador() { return trabajador; }
    public void setTrabajador(String trabajador) { this.trabajador = trabajador; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(String horaIngreso) { this.horaIngreso = horaIngreso; }
    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }
}
