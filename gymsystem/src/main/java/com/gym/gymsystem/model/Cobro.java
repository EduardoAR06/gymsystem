package com.gym.gymsystem.model;

public class Cobro {
    private Long id;
    private Long miembroId;      // ID numérico para lógica de base de datos
    private Long suscripcionId;  // ID de la suscripción que se está pagando
    private String miembro;      // Nombre del miembro (para mostrar en tablas)
    private Double monto;
    private String metodoPago;
    private String estado;
    private String fecha;       // ¡Este es el que faltaba!

    public Cobro() {}

    public Cobro(Long id, Long miembroId, Long suscripcionId, String miembro, Double monto, String metodoPago, String estado, String fecha) {
        this.id = id;
        this.miembroId = miembroId;
        this.suscripcionId = suscripcionId;
        this.miembro = miembro;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMiembroId() { return miembroId; }
    public void setMiembroId(Long miembroId) { this.miembroId = miembroId; }

    public Long getSuscripcionId() { return suscripcionId; }
    public void setSuscripcionId(Long suscripcionId) { this.suscripcionId = suscripcionId; }

    public String getMiembro() { return miembro; }
    public void setMiembro(String miembro) { this.miembro = miembro; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}