package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Asistencia;
import com.gym.gymsystem.model.Usuario;
import com.gym.gymsystem.service.AsistenciaService;
import com.gym.gymsystem.service.PersonalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;
    private final PersonalService personalService;

    public AsistenciaController(AsistenciaService asistenciaService, PersonalService personalService) {
        this.asistenciaService = asistenciaService;
        this.personalService = personalService;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) return "redirect:/";
        if (!u.puedeAsistencias()) return "redirect:/main";
        model.addAttribute("asistencias", asistenciaService.findAll());
        model.addAttribute("personales", personalService.findAll());
        model.addAttribute("usuario", u);
        return "asistencias/lista";
    }

    @PostMapping("/ingreso")
    public String registrarIngreso(@RequestParam String trabajador, RedirectAttributes ra) {
        String r = asistenciaService.registrarIngreso(trabajador);
        switch (r) {
            case "OK"                    -> ra.addFlashAttribute("mensaje", "Ingreso registrado para " + trabajador + ".");
            case "ERROR_YA_INGRESO"      -> ra.addFlashAttribute("error", trabajador + " ya tiene ingreso hoy. Registre la salida primero.");
            case "ERROR_JORNADA_COMPLETA"-> ra.addFlashAttribute("error", trabajador + " ya completó su jornada de hoy.");
        }
        return "redirect:/asistencias";
    }

    @PostMapping("/salida")
    public String registrarSalida(@RequestParam String trabajador, RedirectAttributes ra) {
        String r = asistenciaService.registrarSalida(trabajador);
        switch (r) {
            case "OK"               -> ra.addFlashAttribute("mensaje", "Salida registrada para " + trabajador + ".");
            case "ERROR_SIN_INGRESO"-> ra.addFlashAttribute("error", trabajador + " no tiene ingreso pendiente hoy.");
        }
        return "redirect:/asistencias";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) return "redirect:/";
        if (!u.puedeAsistencias()) return "redirect:/main";
        model.addAttribute("asistencia", asistenciaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada: " + id)));
        model.addAttribute("asistencias", asistenciaService.findAll());
        model.addAttribute("personales", personalService.findAll());
        model.addAttribute("editando", true);
        model.addAttribute("usuario", u);
        return "asistencias/lista";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Asistencia asistencia, RedirectAttributes ra) {
        asistenciaService.update(asistencia);
        ra.addFlashAttribute("mensaje", "Asistencia actualizada.");
        return "redirect:/asistencias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        asistenciaService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Registro eliminado.");
        return "redirect:/asistencias";
    }
}
