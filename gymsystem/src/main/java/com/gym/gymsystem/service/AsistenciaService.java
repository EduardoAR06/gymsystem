package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Asistencia;
import com.gym.gymsystem.model.Personal;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    private final List<Asistencia> asistencias = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);
    private final PersonalService personalService; // Necesario para buscar el nombre del trabajador

    public AsistenciaService(PersonalService personalService) {
        this.personalService = personalService;
    }

    // --- MÉTODOS CRUD BÁSICOS (Mantenidos para el Admin) ---

    public List<Asistencia> findAll() {
        return new ArrayList<>(asistencias);
    }

    public Optional<Asistencia> findById(Long id) {
        return asistencias.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public void deleteById(Long id) {
        asistencias.removeIf(a -> a.getId().equals(id));
    }

    // --- LÓGICA DE MARCACIÓN BIOMÉTRICA (La Magia) ---

    /**
     * Registra la entrada usando solo el ID del personal.
     * Automatiza Fecha, Hora y Seguridad.
     */
    public Asistencia registrarEntrada(Long personalId) {
        Personal p = personalService.findById(personalId)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        Asistencia nueva = new Asistencia();
        nueva.setId(counter.getAndIncrement());
        nueva.setPersonalId(personalId);
        nueva.setTrabajador(p.getNombres()); // Traemos el nombre del otro service
        nueva.setFecha(LocalDate.now());
        nueva.setHoraIngreso(LocalTime.now());
        nueva.setTipoRegistro("Biometrico");

        // Simulación de Hash SHA-256 (sin librerías externas para evitar errores)
        nueva.setHuellaSimulada("SHA-" + p.getDni().hashCode() + "-" + LocalDate.now());

        asistencias.add(nueva);
        return nueva;
    }

    /**
     * Busca la entrada de hoy que no tenga salida y le pone la hora actual.
     */
    public Asistencia registrarSalida(Long personalId) {
        Asistencia asistencia = asistencias.stream()
                .filter(a -> a.getPersonalId().equals(personalId)
                        && a.getFecha().equals(LocalDate.now())
                        && a.getHoraSalida() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró una entrada pendiente para hoy"));

        asistencia.setHoraSalida(LocalTime.now());
        // El cálculo de minutos se hace automáticamente por el método que pusimos en el Modelo
        return asistencia;
    }

    /**
     * Método inteligente para el Controller: Decide si marca entrada o salida.
     */
    public String registrarMarcacionAutomatica(Long personalId) {
        // ¿Ya tiene una entrada hoy sin salida?
        boolean tieneEntradaAbierta = asistencias.stream()
                .anyMatch(a -> a.getPersonalId().equals(personalId)
                        && a.getFecha().equals(LocalDate.now())
                        && a.getHoraSalida() == null);

        if (tieneEntradaAbierta) {
            registrarSalida(personalId);
            return "Salida registrada con éxito.";
        } else {
            registrarEntrada(personalId);
            return "Entrada registrada con éxito.";
        }
    }

    // --- FILTROS ---

    public List<Asistencia> filtrarPorPersonal(Long personalId) {
        return asistencias.stream()
                .filter(a -> a.getPersonalId().equals(personalId))
                .collect(Collectors.toList());
    }
}