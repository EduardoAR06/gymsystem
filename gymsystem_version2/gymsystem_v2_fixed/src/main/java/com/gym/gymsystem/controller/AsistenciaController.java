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
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if ("MIEMBROS".equals(u.getRol())) return "redirect:/main";
        model.addAttribute("asistencias", asistenciaService.findAll());
        model.addAttribute("asistencia", new Asistencia());
        model.addAttribute("personales", personalService.findAll());
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "asistencias/lista";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Asistencia asistencia, RedirectAttributes ra) {
        asistenciaService.save(asistencia);
        ra.addFlashAttribute("mensaje", "Asistencia registrada correctamente.");
        return "redirect:/asistencias";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        model.addAttribute("asistencia", asistenciaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada: " + id)));
        model.addAttribute("asistencias", asistenciaService.findAll());
        model.addAttribute("personales", personalService.findAll());
        model.addAttribute("editando", true);
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "asistencias/lista";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Asistencia asistencia, RedirectAttributes ra) {
        asistenciaService.update(asistencia);
        ra.addFlashAttribute("mensaje", "Asistencia actualizada correctamente.");
        return "redirect:/asistencias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        asistenciaService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Asistencia eliminada correctamente.");
        return "redirect:/asistencias";
    }
}
