package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Personal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonalService {

    private final List<Personal> personales = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(4);

    public PersonalService() {
        personales.add(new Personal(1L, "Rosa Salazar", "Recepcionista", "Mañana"));
        personales.add(new Personal(2L, "Victor Diaz", "Entrenador", "Tarde"));
        personales.add(new Personal(3L, "Elena Soto", "Limpieza", "Mañana"));
    }

    public List<Personal> findAll() {
        return new ArrayList<>(personales);
    }

    public Optional<Personal> findById(Long id) {
        return personales.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void save(Personal personal) {
        personal.setId(counter.getAndIncrement());
        personales.add(personal);
    }

    public void update(Personal personal) {
        for (int i = 0; i < personales.size(); i++) {
            if (personales.get(i).getId().equals(personal.getId())) {
                personales.set(i, personal);
                return;
            }
        }
    }

    public void deleteById(Long id) {
        personales.removeIf(p -> p.getId().equals(id));
    }
}
