package com.gym.gymsystem.model;

public class Membresia {
    private Long id;
    private String plan;
    private Double precio;
    private String duracion;

    public Membresia() {}

    public Membresia(Long id, String plan, Double precio, String duracion) {
        this.id = id;
        this.plan = plan;
        this.precio = precio;
        this.duracion = duracion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
}
