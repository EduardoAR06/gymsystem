package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Miembro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MiembroService {

    private final List<Miembro> miembros = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    // --- MÉTODOS CRUD BÁSICOS ---

    public List<Miembro> findAll() {
        return new ArrayList<>(miembros);
    }

    public Optional<Miembro> findById(Long id) {
        return miembros.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public void save(Miembro miembro) {
        if (miembro.getId() == null) {
            miembro.setId(counter.getAndIncrement());
        }
        if (miembro.getEstado() == null) miembro.setEstado("ACTIVO");
        miembros.add(miembro);
    }

    public void update(Miembro miembro) {
        for (int i = 0; i < miembros.size(); i++) {
            if (miembros.get(i).getId().equals(miembro.getId())) {
                miembros.set(i, miembro);
                return;
            }
        }
    }

    public void deleteById(Long id) {
        miembros.removeIf(m -> m.getId().equals(id));
    }

    // --- BUSCADOR CORREGIDO (Usando getNombres) ---

    public List<Miembro> buscarPor(String criterio, String valor) {
        if (valor == null || valor.isEmpty()) return findAll();

        return miembros.stream()
                .filter(m -> {
                    switch(criterio.toUpperCase()) {
                        case "DNI":
                            return m.getDni() != null && m.getDni().contains(valor);
                        case "NOMBRES":
                            // CAMBIO: Ahora usa getNombres()
                            return m.getNombres() != null && m.getNombres().toLowerCase().contains(valor.toLowerCase());
                        case "ESTADO":
                            return m.getEstado() != null && m.getEstado().equalsIgnoreCase(valor);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }

    // --- AUTOCOMPLETADO CORREGIDO (Usando getNombres) ---

    public List<Miembro> buscarPorTermino(String query) {
        if (query == null || query.isEmpty()) return new ArrayList<>();

        String term = query.toLowerCase();
        return miembros.stream()
                .filter(m -> (m.getNombres() != null && m.getNombres().toLowerCase().contains(term)) ||
                        (m.getDni() != null && m.getDni().contains(term)))
                .limit(10)
                .collect(Collectors.toList());
    }

    public void actualizarEstadoSocio(Long id, String nuevoEstado) {
        findById(id).ifPresent(m -> {
            m.setEstado(nuevoEstado.toUpperCase());
            update(m);
        });
    }
}