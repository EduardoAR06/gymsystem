package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Asistencia;
import com.gym.gymsystem.model.Personal;
import com.gym.gymsystem.service.AsistenciaService;
import com.gym.gymsystem.service.PersonalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;
    private final PersonalService personalService;

    public AsistenciaController(AsistenciaService asistenciaService, PersonalService personalService) {
        this.asistenciaService = asistenciaService;
        this.personalService = personalService;
    }

    // Vista principal: Lista de asistencias y panel de marcación
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("asistencias", asistenciaService.findAll());
        model.addAttribute("personales", personalService.findAll());
        return "asistencias/lista";
    }

    /**
     * API para el botón de marcación rápida del Kiosko.
     * Responde con JSON para que el JavaScript funcione.
     */
    @PostMapping("/marcar")
    @ResponseBody
    public Map<String, Object> marcar(@RequestParam Long personalId, @RequestParam String tipo) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            Asistencia resultado;
            if ("entrada".equalsIgnoreCase(tipo)) {
                resultado = asistenciaService.registrarEntrada(personalId);
                respuesta.put("message", "Entrada registrada: " + resultado.getHoraIngreso());
            } else {
                resultado = asistenciaService.registrarSalida(personalId);
                respuesta.put("message", "Salida registrada: " + resultado.getHoraSalida());
            }
            respuesta.put("success", true);
        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("message", "Error: " + e.getMessage());
        }
        return respuesta;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        asistenciaService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Registro eliminado.");
        return "redirect:/asistencias";
    }
}