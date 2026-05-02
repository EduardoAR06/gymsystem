package pe.gimnasio.sistema_gimnasio.model;

public class UsuarioSistema {

    private final String usuario;
    private final String password;
    private final String nombreCompleto;
    private final String rol;

    public UsuarioSistema(String usuario, String password, String nombreCompleto, String rol) {
        this.usuario = usuario;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getRol() {
        return rol;
    }
}
