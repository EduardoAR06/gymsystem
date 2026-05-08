package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Usuario;
import com.gym.gymsystem.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        return usuarioService.login(username, password).map(u -> {
            session.setAttribute("usuarioLogueado", u);
            return "redirect:/main";
        }).orElseGet(() -> {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            return "login";
        });
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
