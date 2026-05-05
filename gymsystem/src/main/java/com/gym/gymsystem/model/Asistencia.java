package com.gym.gymsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {
    private Long id;
    private Long personalId;
    private String trabajador;
    private LocalDate fecha;
    private LocalTime horaIngreso;
    private LocalTime horaSalida;

    // --- MEJORAS DE LÓGICA ---
    private String tipoRegistro;
    private String huellaSimulada;

    /**
     * Calcula los minutos trabajados entre ingreso y salida.
     */
    public Long getDuracionMinutos() {
        if (horaIngreso != null && horaSalida != null) {
            return Duration.between(horaIngreso, horaSalida).toMinutes();
        }
        return 0L;
    }

    /**
     * Genera un hash simulado de seguridad.
     */
    public void generarHuellaDigital() {
        this.huellaSimulada = "SHA256-" + (this.trabajador != null ? this.trabajador.hashCode() : "0") + "-" + this.fecha;
    }
}
