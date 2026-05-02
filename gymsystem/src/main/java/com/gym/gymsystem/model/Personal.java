package com.gym.gymsystem.model;

public class Personal {
    private Long id;
    private String nombres;
    private String cargo;
    private String turno;

    public Personal() {}

    public Personal(Long id, String nombres, String cargo, String turno) {
        this.id = id;
        this.nombres = nombres;
        this.cargo = cargo;
        this.turno = turno;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
}
