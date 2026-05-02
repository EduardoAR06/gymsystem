package com.gym.gymsystem.model;

public class Cobro {
    private Long id;
    private String miembro;
    private Double monto;
    private String metodoPago;
    private String estado;

    public Cobro() {}

    public Cobro(Long id, String miembro, Double monto, String metodoPago, String estado) {
        this.id = id;
        this.miembro = miembro;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMiembro() { return miembro; }
    public void setMiembro(String miembro) { this.miembro = miembro; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
