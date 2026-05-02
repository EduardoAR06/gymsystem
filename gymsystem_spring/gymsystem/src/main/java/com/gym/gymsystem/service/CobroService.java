package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Cobro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CobroService {

    private final List<Cobro> cobros = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(4);

    public CobroService() {
        cobros.add(new Cobro(1L, "Carlos Ramos", 120.0, "Efectivo", "Pagado"));
        cobros.add(new Cobro(2L, "Maria Quispe", 300.0, "Yape", "Pagado"));
        cobros.add(new Cobro(3L, "Jorge Huaman", 120.0, "Efectivo", "Pendiente"));
    }

    public List<Cobro> findAll() {
        return new ArrayList<>(cobros);
    }

    public Optional<Cobro> findById(Long id) {
        return cobros.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void save(Cobro cobro) {
        cobro.setId(counter.getAndIncrement());
        cobros.add(cobro);
    }

    public void update(Cobro cobro) {
        for (int i = 0; i < cobros.size(); i++) {
            if (cobros.get(i).getId().equals(cobro.getId())) {
                cobros.set(i, cobro);
                return;
            }
        }
    }

    public void deleteById(Long id) {
        cobros.removeIf(c -> c.getId().equals(id));
    }
}
