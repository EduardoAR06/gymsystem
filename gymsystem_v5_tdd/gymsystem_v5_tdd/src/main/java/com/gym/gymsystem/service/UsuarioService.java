package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public UsuarioService() {
        usuarios.add(new Usuario(counter.getAndIncrement(), "Admin",      "1234", "ADMIN",      "Administrador General"));
        usuarios.add(new Usuario(counter.getAndIncrement(), "RecepMem",   "1234", "RECEP_MEM",  "Recepcionista de Membresías"));
        usuarios.add(new Usuario(counter.getAndIncrement(), "RecepAsis",  "1234", "RECEP_ASIS", "Recepcionista de Asistencias"));
        usuarios.add(new Usuario(counter.getAndIncrement(), "Entrenador", "1234", "ENTRENADOR", "Entrenador"));
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

    public void save(Usuario u) { u.setId(counter.getAndIncrement()); usuarios.add(u); }

    public void update(Usuario u) {
        for (int i = 0; i < usuarios.size(); i++)
            if (usuarios.get(i).getId().equals(u.getId())) { usuarios.set(i, u); return; }
    }

    public void deleteById(Long id) { usuarios.removeIf(u -> u.getId().equals(id)); }
}
