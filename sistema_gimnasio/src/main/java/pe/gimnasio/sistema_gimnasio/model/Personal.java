package pe.gimnasio.sistema_gimnasio.model;

public class Personal {

    private Long id;
    private String nombres;
    private String cargo;
    private String turno;
    private String estado;

    public Personal() {
    }

    public Personal(Long id, String nombres, String cargo, String turno, String estado) {
        this.id = id;
        this.nombres = nombres;
        this.cargo = cargo;
        this.turno = turno;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
