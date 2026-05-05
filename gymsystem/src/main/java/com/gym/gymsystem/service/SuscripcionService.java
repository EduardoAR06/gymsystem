package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Suscripcion;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SuscripcionService {
    private final List<Suscripcion> suscripciones = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Suscripcion> findAll() {
        return new ArrayList<>(suscripciones);
    }

    public Optional<Suscripcion> findById(Long id) {
        return suscripciones.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public void save(Suscripcion suscripcion) {
        suscripcion.setId(counter.getAndIncrement());
        suscripciones.add(suscripcion);
    }

    public void update(Suscripcion suscripcion) {
        for (int i = 0; i < suscripciones.size(); i++) {
            if (suscripciones.get(i).getId().equals(suscripcion.getId())) {
                suscripciones.set(i, suscripcion);
                return;
            }
        }
    }
}