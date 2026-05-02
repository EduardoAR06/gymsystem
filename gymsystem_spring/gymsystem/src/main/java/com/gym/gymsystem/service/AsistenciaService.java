package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Asistencia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AsistenciaService {

    private final List<Asistencia> asistencias = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(4);

    public AsistenciaService() {
        asistencias.add(new Asistencia(1L, "Rosa Salazar", "06/04/2026", "07:55", "14:00"));
        asistencias.add(new Asistencia(2L, "Victor Diaz", "06/04/2026", "14:05", "21:00"));
        asistencias.add(new Asistencia(3L, "Elena Soto", "06/04/2026", "08:00", "15:00"));
    }

    public List<Asistencia> findAll() {
        return new ArrayList<>(asistencias);
    }

    public Optional<Asistencia> findById(Long id) {
        return asistencias.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public void save(Asistencia asistencia) {
        asistencia.setId(counter.getAndIncrement());
        asistencias.add(asistencia);
    }

    public void update(Asistencia asistencia) {
        for (int i = 0; i < asistencias.size(); i++) {
            if (asistencias.get(i).getId().equals(asistencia.getId())) {
                asistencias.set(i, asistencia);
                return;
            }
        }
    }

    public void deleteById(Long id) {
        asistencias.removeIf(a -> a.getId().equals(id));
    }
}
