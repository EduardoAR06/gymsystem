package com.gym.gymsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {
    private Long id;
    private Long miembroId;
    private Long membresiaId;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private String estado;        // ACTIVA, SUSPENDIDA, VENCIDA, CANCELADA
    private int renovaciones;
    private int diasGracia;       // Días de tolerancia antes de suspender (Ej: 3 días)

    // ==========================================
    // LÓGICA DE ESTADOS (MODELO CLARO/MOVISTAR)
    // ==========================================

    /**
     * Calcula cuántos días le quedan de membresía.
     * Si el número es negativo, ya venció.
     */
    public int diasRestantes() {
        if (fechaVencimiento == null) return 0;
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
    }

    /**
     * Determina si el servicio debe cortarse.
     * Se suspende solo si pasaron los días de gracia tras el vencimiento.
     */
    public boolean estaSuspendida() {
        LocalDate limiteGracia = fechaVencimiento.plusDays(diasGracia);
        return LocalDate.now().isAfter(limiteGracia);
    }

    /**
     * Verifica si está en ese periodo donde ya venció pero aún puede entrar (tolerancia).
     */
    public boolean estaEnPeriodoGracia() {
        LocalDate hoy = LocalDate.now();
        return hoy.isAfter(fechaVencimiento) && !estaSuspendida();
    }

    /**
     * Para mostrar alertas en el Dashboard si vence en menos de 3 días.
     */
    public boolean estaProximaAVencer() {
        int restantes = diasRestantes();
        return restantes <= 3 && restantes > 0;
    }

    /**
     * Método para activar/renovar la suscripción automáticamente al pagar.
     */
    public void renovar(int meses) {
        this.fechaInicio = LocalDate.now();
        // Si ya estaba activa, sumamos a la fecha vieja, si no, desde hoy
        if (this.fechaVencimiento != null && this.fechaVencimiento.isAfter(LocalDate.now())) {
            this.fechaVencimiento = this.fechaVencimiento.plusMonths(meses);
        } else {
            this.fechaVencimiento = this.fechaInicio.plusMonths(meses);
        }
        this.estado = "ACTIVA";
        this.renovaciones++;
    }

    /**
     * Actualiza el string del estado basado en la fecha actual.
     */
    public void actualizarEstadoAutomatico() {
        if (estaSuspendida()) {
            this.estado = "SUSPENDIDA";
        } else if (estaEnPeriodoGracia()) {
            this.estado = "VENCIDA"; // O "POR RENOVAR"
        } else {
            this.estado = "ACTIVA";
        }
    }
}