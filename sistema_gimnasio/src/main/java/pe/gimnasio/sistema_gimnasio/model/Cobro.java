package pe.gimnasio.sistema_gimnasio.model;

import java.time.LocalDate;

public class Cobro {

    private Long id;
    private String miembro;
    private double monto;
    private LocalDate fechaCobro;
    private String metodoPago;
    private String estado;

    public Cobro() {
    }

    public Cobro(Long id, String miembro, double monto, LocalDate fechaCobro, String metodoPago, String estado) {
        this.id = id;
        this.miembro = miembro;
        this.monto = monto;
        this.fechaCobro = fechaCobro;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMiembro() {
        return miembro;
    }

    public void setMiembro(String miembro) {
        this.miembro = miembro;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(LocalDate fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
