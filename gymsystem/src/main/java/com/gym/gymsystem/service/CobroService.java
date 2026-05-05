package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Cobro;
import com.gym.gymsystem.model.Miembro;
import com.gym.gymsystem.model.Suscripcion;
import com.gym.gymsystem.model.Membresia;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CobroService {

    private final List<Cobro> cobros = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    private final MiembroService miembroService;
    private final SuscripcionService suscripcionService;
    private final MembresiaService membresiaService;

    public CobroService(MiembroService miembroService,
                        SuscripcionService suscripcionService,
                        MembresiaService membresiaService) {
        this.miembroService = miembroService;
        this.suscripcionService = suscripcionService;
        this.membresiaService = membresiaService;
    }

    public List<Cobro> findAll() {
        return new ArrayList<>(cobros);
    }

    public Optional<Cobro> findById(Long id) {
        return cobros.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void deleteById(Long id) {
        cobros.removeIf(c -> c.getId().equals(id));
    }

    public void update(Cobro cobro) {
        for (int i = 0; i < cobros.size(); i++) {
            if (cobros.get(i).getId().equals(cobro.getId())) {
                cobros.set(i, cobro);
                return;
            }
        }
    }

    public Cobro registrarCobro(Cobro cobro) {
        cobro.setId(counter.getAndIncrement());
        cobro.setFecha(LocalDate.now().toString());
        cobros.add(cobro);

        // Lógica de reactivación automática
        if (cobro.getMiembroId() != null && cobro.getSuscripcionId() != null) {
            reactivarServicio(cobro.getMiembroId(), cobro.getSuscripcionId());
        }

        return cobro;
    }

    private void reactivarServicio(Long miembroId, Long suscripcionId) {
        suscripcionService.findById(suscripcionId).ifPresent(suscrip -> {
            membresiaService.findById(suscrip.getMembresiaId()).ifPresent(memb -> {
                suscrip.renovar(1);
                suscripcionService.update(suscrip);
            });
        });

        miembroService.findById(miembroId).ifPresent(miembro -> {
            miembro.setEstado("ACTIVO");
            miembroService.update(miembro);
        });
    }
}