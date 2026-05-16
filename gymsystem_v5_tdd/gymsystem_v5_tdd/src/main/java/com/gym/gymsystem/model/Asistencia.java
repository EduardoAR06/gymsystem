package com.gym.gymsystem.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Asistencia {
    private Long id;
    private String trabajador;
    private LocalDate fecha;
    private LocalTime horaIngreso;
    private LocalTime horaSalida;

    public Asistencia() {}

    public Asistencia(Long id, String trabajador, LocalDate fecha, LocalTime horaIngreso, LocalTime horaSalida) {
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
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(LocalTime horaIngreso) { this.horaIngreso = horaIngreso; }
    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }
}
