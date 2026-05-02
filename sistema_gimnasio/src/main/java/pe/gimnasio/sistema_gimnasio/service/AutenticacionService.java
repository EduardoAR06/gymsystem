package pe.gimnasio.sistema_gimnasio.service;

import org.springframework.stereotype.Service;
import pe.gimnasio.sistema_gimnasio.model.UsuarioSistema;

import java.util.List;

@Service
public class AutenticacionService {

    private final List<UsuarioSistema> usuarios = List.of(
            new UsuarioSistema("admin", "1234", "Ana Torres", "Administrador"),
            new UsuarioSistema("recepcion", "1234", "Luis Pérez", "Recepción")
    );

    public boolean esValido(String usuario, String password) {
        return usuarios.stream()
                .anyMatch(item -> item.getUsuario().equalsIgnoreCase(usuario)
                        && item.getPassword().equals(password));
    }
}
