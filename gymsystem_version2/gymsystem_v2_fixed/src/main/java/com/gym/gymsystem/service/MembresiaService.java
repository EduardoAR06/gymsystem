package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Membresia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MembresiaService {

    private final List<Membresia> membresias = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Membresia> findAll() {
        return new ArrayList<>(membresias);
    }

    public Optional<Membresia> findById(Long id) {
        return membresias.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public void save(Membresia membresia) {
        membresia.setId(counter.getAndIncrement());
        membresias.add(membresia);
    }

    public void update(Membresia membresia) {
        for (int i = 0; i < membresias.size(); i++) {
            if (membresias.get(i).getId().equals(membresia.getId())) {
                membresias.set(i, membresia);
                return;
            }
        }
    }

    public void deleteById(Long id) {
        membresias.removeIf(m -> m.getId().equals(id));
    }
}
