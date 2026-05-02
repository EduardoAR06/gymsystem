package pe.gimnasio.sistema_gimnasio.model;

public class Membresia {

    private Long id;
    private String nombre;
    private double precioMensual;
    private int duracionDias;
    private String descripcion;

    public Membresia() {
    }

    public Membresia(Long id, String nombre, double precioMensual, int duracionDias, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precioMensual = precioMensual;
        this.duracionDias = duracionDias;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(double precioMensual) {
        this.precioMensual = precioMensual;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
