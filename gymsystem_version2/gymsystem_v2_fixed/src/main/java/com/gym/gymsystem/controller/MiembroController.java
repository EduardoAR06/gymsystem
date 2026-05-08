package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Miembro;
import com.gym.gymsystem.model.Usuario;
import com.gym.gymsystem.service.MiembroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/miembros")
public class MiembroController {

    private final MiembroService miembroService;

    public MiembroController(MiembroService miembroService) {
        this.miembroService = miembroService;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if ("ASISTENCIAS".equals(u.getRol())) return "redirect:/main";
        model.addAttribute("miembros", miembroService.findAll());
        model.addAttribute("miembro", new Miembro());
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "miembros/lista";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Miembro miembro, RedirectAttributes ra) {
        miembroService.save(miembro);
        ra.addFlashAttribute("mensaje", "Miembro registrado. Membresía activa por " +
                Miembro.diasDePlan(miembro.getMembresia()) + " días.");
        return "redirect:/miembros";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        Miembro miembro = miembroService.findById(id)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado: " + id));
        model.addAttribute("miembro", miembro);
        model.addAttribute("miembros", miembroService.findAll());
        model.addAttribute("editando", true);
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "miembros/lista";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Miembro miembro, RedirectAttributes ra) {
        miembroService.update(miembro);
        ra.addFlashAttribute("mensaje", "Miembro actualizado correctamente.");
        return "redirect:/miembros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        miembroService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Miembro eliminado correctamente.");
        return "redirect:/miembros";
    }
}
