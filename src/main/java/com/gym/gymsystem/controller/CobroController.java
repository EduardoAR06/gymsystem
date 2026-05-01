package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Cobro;
import com.gym.gymsystem.service.CobroService;
import com.gym.gymsystem.service.MiembroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cobros")
public class CobroController {

    private final CobroService cobroService;
    private final MiembroService miembroService;

    public CobroController(CobroService cobroService, MiembroService miembroService) {
        this.cobroService = cobroService;
        this.miembroService = miembroService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cobros", cobroService.findAll());
        model.addAttribute("cobro", new Cobro());
        model.addAttribute("miembros", miembroService.findAll());
        return "cobros/lista";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cobro cobro, RedirectAttributes ra) {
        cobro.setEstado("Pagado");
        cobroService.save(cobro);
        ra.addFlashAttribute("mensaje", "Cobro registrado correctamente.");
        return "redirect:/cobros";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Cobro cobro = cobroService.findById(id)
                .orElseThrow(() -> new RuntimeException("Cobro no encontrado: " + id));
        model.addAttribute("cobro", cobro);
        model.addAttribute("cobros", cobroService.findAll());
        model.addAttribute("miembros", miembroService.findAll());
        model.addAttribute("editando", true);
        return "cobros/lista";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Cobro cobro, RedirectAttributes ra) {
        cobroService.update(cobro);
        ra.addFlashAttribute("mensaje", "Cobro actualizado correctamente.");
        return "redirect:/cobros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        cobroService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Cobro eliminado correctamente.");
        return "redirect:/cobros";
    }
}
