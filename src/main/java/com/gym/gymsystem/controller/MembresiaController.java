package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Membresia;
import com.gym.gymsystem.service.MembresiaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/membresias")
public class MembresiaController {

    private final MembresiaService membresiaService;

    public MembresiaController(MembresiaService membresiaService) {
        this.membresiaService = membresiaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("membresias", membresiaService.findAll());
        model.addAttribute("membresia", new Membresia());
        return "membresias/lista";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Membresia membresia, RedirectAttributes ra) {
        membresiaService.save(membresia);
        ra.addFlashAttribute("mensaje", "Membresía guardada correctamente.");
        return "redirect:/membresias";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Membresia membresia = membresiaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada: " + id));
        model.addAttribute("membresia", membresia);
        model.addAttribute("membresias", membresiaService.findAll());
        model.addAttribute("editando", true);
        return "membresias/lista";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Membresia membresia, RedirectAttributes ra) {
        membresiaService.update(membresia);
        ra.addFlashAttribute("mensaje", "Membresía actualizada correctamente.");
        return "redirect:/membresias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        membresiaService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Membresía eliminada correctamente.");
        return "redirect:/membresias";
    }
}
