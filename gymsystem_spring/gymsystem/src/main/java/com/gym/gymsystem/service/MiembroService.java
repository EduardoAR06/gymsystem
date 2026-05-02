package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Miembro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MiembroService {

    private final List<Miembro> miembros = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(4);

    public MiembroService() {
        miembros.add(new Miembro(1L, "Carlos Ramos", "70234561", "987654321", "Mensual", "Activo"));
        miembros.add(new Miembro(2L, "Maria Quispe", "72345612", "976543210", "Trimestral", "Activo"));
        miembros.add(new Miembro(3L, "Jorge Huaman", "69876543", "965432109", "Mensual", "Pendiente"));
    }

    public List<Miembro> findAll() {
        return new ArrayList<>(miembros);
    }

    public Optional<Miembro> findById(Long id) {
        return miembros.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public void save(Miembro miembro) {
        miembro.setId(counter.getAndIncrement());
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
}
