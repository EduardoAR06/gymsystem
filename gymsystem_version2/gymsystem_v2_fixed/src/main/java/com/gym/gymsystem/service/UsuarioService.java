package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public UsuarioService() {
        // Usuarios precargados
        usuarios.add(new Usuario(counter.getAndIncrement(), "Admin",        "1234", "ADMIN",       "Administrador General"));
        usuarios.add(new Usuario(counter.getAndIncrement(), "Miembros",     "1234", "MIEMBROS",    "Gestión de Miembros"));
        usuarios.add(new Usuario(counter.getAndIncrement(), "Asistencias",  "1234", "ASISTENCIAS", "Control de Asistencias"));
    }

    public Optional<Usuario> login(String username, String password) {
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
    }

    public List<Usuario> findAll() { return new ArrayList<>(usuarios); }

    public Optional<Usuario> findById(Long id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public void save(Usuario u) {
        u.setId(counter.getAndIncrement());
        usuarios.add(u);
    }

    public void update(Usuario u) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(u.getId())) {
                usuarios.set(i, u);
                return;
            }
        }
    }

    public void deleteById(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
    }
}
