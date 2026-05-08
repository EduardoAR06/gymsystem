package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Miembro;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MiembroService {

    private final List<Miembro> miembros = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    /** Devuelve todos los miembros con estado recalculado */
    public List<Miembro> findAll() {
        miembros.forEach(Miembro::actualizarEstado);
        return new ArrayList<>(miembros);
    }

    public Optional<Miembro> findById(Long id) {
        return miembros.stream().filter(m -> m.getId().equals(id)).findFirst()
                .map(m -> { m.actualizarEstado(); return m; });
    }

    /** Registra miembro nuevo. Asigna días según plan elegido desde hoy. */
    public void save(Miembro miembro) {
        miembro.setId(counter.getAndIncrement());
        int dias = Miembro.diasDePlan(miembro.getMembresia());
        miembro.setFechaVencimiento(LocalDate.now().plusDays(dias));
        miembro.actualizarEstado();
        miembros.add(miembro);
    }

    public void update(Miembro miembro) {
        for (int i = 0; i < miembros.size(); i++) {
            if (miembros.get(i).getId().equals(miembro.getId())) {
                miembro.actualizarEstado();
                miembros.set(i, miembro);
                return;
            }
        }
    }

    /**
     * Registra un pago: acumula días restantes + días del nuevo plan.
     * Si el miembro está inactivo, cuenta desde hoy.
     */
    public void renovarMembresia(Long miembroId, String nuevoPlan) {
        findById(miembroId).ifPresent(m -> {
            int diasNuevos = Miembro.diasDePlan(nuevoPlan);
            LocalDate base = (m.getFechaVencimiento() != null && m.getFechaVencimiento().isAfter(LocalDate.now()))
                    ? m.getFechaVencimiento()
                    : LocalDate.now();
            m.setFechaVencimiento(base.plusDays(diasNuevos));
            m.setMembresia(nuevoPlan);
            m.actualizarEstado();
            update(m);
        });
    }

    public void deleteById(Long id) {
        miembros.removeIf(m -> m.getId().equals(id));
    }
}
