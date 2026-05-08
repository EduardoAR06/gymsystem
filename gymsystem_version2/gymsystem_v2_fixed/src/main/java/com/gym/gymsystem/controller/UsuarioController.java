package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Usuario;
import com.gym.gymsystem.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (logueado == null) return "redirect:/";
        if (!"ADMIN".equals(logueado.getRol())) return "redirect:/main";
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarioLogueado", logueado);
        return "usuarios/lista";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, RedirectAttributes ra) {
        usuarioService.save(usuario);
        ra.addFlashAttribute("mensaje", "Usuario creado correctamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (logueado == null || !"ADMIN".equals(logueado.getRol())) return "redirect:/main";
        model.addAttribute("usuario", usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("editando", true);
        model.addAttribute("usuarioLogueado", logueado);
        return "usuarios/lista";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Usuario usuario, RedirectAttributes ra) {
        usuarioService.update(usuario);
        ra.addFlashAttribute("mensaje", "Usuario actualizado correctamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        usuarioService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Usuario eliminado.");
        return "redirect:/usuarios";
    }
}
