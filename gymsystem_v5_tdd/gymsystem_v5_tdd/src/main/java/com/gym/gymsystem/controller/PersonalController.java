package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Personal;
import com.gym.gymsystem.model.Usuario;
import com.gym.gymsystem.service.PersonalService;
import jakarta.servlet.http.HttpSession;
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
    public String listar(Model model, HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) return "redirect:/";
        if (!u.puedeVerPersonal()) return "redirect:/main";
        model.addAttribute("personales", personalService.findAll());
        model.addAttribute("personal", new Personal());
        model.addAttribute("usuario", u);
        return "personal/lista";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Personal personal, HttpSession session, RedirectAttributes ra) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null || !u.puedeEditarPersonal()) return "redirect:/personal";
        personalService.save(personal);
        ra.addFlashAttribute("mensaje", "Personal registrado correctamente.");
        return "redirect:/personal";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) return "redirect:/";
        if (!u.puedeEditarPersonal()) return "redirect:/personal";
        model.addAttribute("personal", personalService.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado: " + id)));
        model.addAttribute("personales", personalService.findAll());
        model.addAttribute("editando", true);
        model.addAttribute("usuario", u);
        return "personal/lista";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Personal personal, RedirectAttributes ra) {
        personalService.update(personal);
        ra.addFlashAttribute("mensaje", "Personal actualizado.");
        return "redirect:/personal";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null || !u.puedeEditarPersonal()) return "redirect:/personal";
        personalService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Personal eliminado.");
        return "redirect:/personal";
    }
}
