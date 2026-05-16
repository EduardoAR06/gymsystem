package com.gym.gymsystem.model;

import java.time.LocalDate;

public class Cobro {
    private Long id;
    private Long miembroId;
    private String miembroNombre; // para mostrar
    private String planPagado;    // Mensual, Trimestral, Anual
    private Double monto;
    private String metodoPago;
    private String estado;
    private LocalDate fecha;

    public Cobro() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMiembroId() { return miembroId; }
    public void setMiembroId(Long miembroId) { this.miembroId = miembroId; }
    public String getMiembroNombre() { return miembroNombre; }
    public void setMiembroNombre(String miembroNombre) { this.miembroNombre = miembroNombre; }
    public String getPlanPagado() { return planPagado; }
    public void setPlanPagado(String planPagado) { this.planPagado = planPagado; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
