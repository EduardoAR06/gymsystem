package com.gym.gymsystem.model;

public class Miembro {
    private Long id;
    private String nombres;
    private String dni;
    private String telefono;
    private String membresia;
    private String estado;

    public Miembro() {}

    public Miembro(Long id, String nombres, String dni, String telefono, String membresia, String estado) {
        this.id = id;
        this.nombres = nombres;
        this.dni = dni;
        this.telefono = telefono;
        this.membresia = membresia;
        this.estado = estado;
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
}
