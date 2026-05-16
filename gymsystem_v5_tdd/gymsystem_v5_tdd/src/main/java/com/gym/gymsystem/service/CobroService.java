package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Cobro;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CobroService {

    private final List<Cobro> cobros = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Cobro> findAll() { return new ArrayList<>(cobros); }

    public Optional<Cobro> findById(Long id) {
        return cobros.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    /** Historial de cobros de un miembro específico (ordenado por fecha desc) */
    public List<Cobro> findByMiembroId(Long miembroId) {
        return cobros.stream()
                .filter(c -> miembroId.equals(c.getMiembroId()))
                .sorted((a, b) -> b.getFecha().compareTo(a.getFecha()))
                .collect(Collectors.toList());
    }

    public void save(Cobro cobro) {
        cobro.setId(counter.getAndIncrement());
        cobro.setEstado("Pagado");
        cobro.setFecha(LocalDate.now());
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

    public double totalCobrado() {
        return cobros.stream()
                .filter(c -> "Pagado".equals(c.getEstado()))
                .mapToDouble(Cobro::getMonto)
                .sum();
    }
}
