package com.gym.gymsystem.model;

import java.time.LocalDate;

public class Miembro {
    private Long id;
    private String nombres;
    private String dni;
    private String telefono;
    private String membresia; // Mensual, Trimestral, Anual
    private String estado;    // Activo, Inactivo (calculado automáticamente)
    private int diasRestantes;
    private LocalDate fechaVencimiento;

    public Miembro() {}

    public Miembro(Long id, String nombres, String dni, String telefono,
                   String membresia, String estado, int diasRestantes, LocalDate fechaVencimiento) {
        this.id = id;
        this.nombres = nombres;
        this.dni = dni;
        this.telefono = telefono;
        this.membresia = membresia;
        this.estado = estado;
        this.diasRestantes = diasRestantes;
        this.fechaVencimiento = fechaVencimiento;
    }

    /** Recalcula estado según días restantes. Llamar antes de mostrar datos. */
    public void actualizarEstado() {
        if (fechaVencimiento != null) {
            long dias = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
            diasRestantes = (int) Math.max(0, dias);
        }
        this.estado = (diasRestantes > 0) ? "Activo" : "Inactivo";
    }

    /** Días correspondientes a cada plan */
    public static int diasDePlan(String tipoPlan) {
        if (tipoPlan == null) return 30;
        return switch (tipoPlan) {
            case "Trimestral" -> 90;
            case "Anual"      -> 365;
            default           -> 30;
        };
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getMembresia() { return membresia; }
    public void setMembresia(String membresia) { this.membresia = membresia; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getDiasRestantes() { return diasRestantes; }
    public void setDiasRestantes(int diasRestantes) { this.diasRestantes = diasRestantes; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}
