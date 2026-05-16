package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Asistencia;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AsistenciaService {

    private final List<Asistencia> asistencias = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Asistencia> findAll() {
        return new ArrayList<>(asistencias);
    }

    public Optional<Asistencia> findById(Long id) {
        return asistencias.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    /**
     * Busca si el trabajador ya tiene un registro de ingreso HOY sin salida.
     */
    public Optional<Asistencia> findIngresoAbierto(String trabajador) {
        LocalDate hoy = LocalDate.now();
        return asistencias.stream()
                .filter(a -> a.getTrabajador().equals(trabajador)
                        && a.getFecha().equals(hoy)
                        && a.getHoraSalida() == null)
                .findFirst();
    }

    /**
     * Verifica si el trabajador ya registró ingreso Y salida hoy (jornada completa).
     */
    public boolean tieneJornadaCompletaHoy(String trabajador) {
        LocalDate hoy = LocalDate.now();
        return asistencias.stream()
                .anyMatch(a -> a.getTrabajador().equals(trabajador)
                        && a.getFecha().equals(hoy)
                        && a.getHoraSalida() != null);
    }

    /**
     * Registra INGRESO: asigna fecha y hora automáticamente desde el servidor.
     * Retorna mensaje de error si ya existe ingreso abierto o jornada completa.
     */
    public String registrarIngreso(String trabajador) {
        if (tieneJornadaCompletaHoy(trabajador)) {
            return "ERROR_JORNADA_COMPLETA";
        }
        if (findIngresoAbierto(trabajador).isPresent()) {
            return "ERROR_YA_INGRESO";
        }
        Asistencia a = new Asistencia();
        a.setId(counter.getAndIncrement());
        a.setTrabajador(trabajador);
        a.setFecha(LocalDate.now());
        a.setHoraIngreso(LocalTime.now().withSecond(0).withNano(0));
        a.setHoraSalida(null);
        asistencias.add(a);
        return "OK";
    }

    /**
     * Registra SALIDA: asigna hora automáticamente.
     * Retorna mensaje de error si no existe ingreso abierto.
     */
    public String registrarSalida(String trabajador) {
        Optional<Asistencia> abierto = findIngresoAbierto(trabajador);
        if (abierto.isEmpty()) {
            return "ERROR_SIN_INGRESO";
        }
        Asistencia a = abierto.get();
        a.setHoraSalida(LocalTime.now().withSecond(0).withNano(0));
        return "OK";
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
