package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Personal;
import com.gym.gymsystem.service.PersonalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/personal")
public class PersonalController {

    private final PersonalService personalService;

    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("personales", personalService.findAll());
        model.addAttribute("personal", new Personal());
        return "personal/lista";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Personal personal, RedirectAttributes ra) {
        personalService.save(personal);
        ra.addFlashAttribute("mensaje", "Personal guardado correctamente.");
        return "redirect:/personal";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Personal personal = personalService.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado: " + id));
        model.addAttribute("personal", personal);
        model.addAttribute("personales", personalService.findAll());
        model.addAttribute("editando", true);
        return "personal/lista";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Personal personal, RedirectAttributes ra) {
        personalService.update(personal);
        ra.addFlashAttribute("mensaje", "Personal actualizado correctamente.");
        return "redirect:/personal";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        personalService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Personal eliminado correctamente.");
        return "redirect:/personal";
    }
}
