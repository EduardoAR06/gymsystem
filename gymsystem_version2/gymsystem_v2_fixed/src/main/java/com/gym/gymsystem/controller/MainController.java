package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Usuario;
import com.gym.gymsystem.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final MiembroService miembroService;
    private final CobroService cobroService;
    private final PersonalService personalService;
    private final AsistenciaService asistenciaService;

    public MainController(MiembroService miembroService, CobroService cobroService,
                          PersonalService personalService, AsistenciaService asistenciaService) {
        this.miembroService = miembroService;
        this.cobroService = cobroService;
        this.personalService = personalService;
        this.asistenciaService = asistenciaService;
    }

    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        model.addAttribute("usuario", u);
        model.addAttribute("totalMiembros", miembroService.findAll().size());
        model.addAttribute("miembrosActivos", miembroService.findAll().stream()
                .filter(m -> "Activo".equals(m.getEstado())).count());
        model.addAttribute("totalCobros", cobroService.totalCobrado());
        model.addAttribute("totalAsistencias", asistenciaService.findAll().size());
        return "main";
    }

    @GetMapping("/gestion")
    public String gestion(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (!"ADMIN".equals(u.getRol())) return "redirect:/main";
        model.addAttribute("usuario", u);
        model.addAttribute("totalMiembros", miembroService.findAll().size());
        model.addAttribute("miembrosActivos", miembroService.findAll().stream()
                .filter(m -> "Activo".equals(m.getEstado())).count());
        model.addAttribute("totalCobros", cobroService.totalCobrado());
        model.addAttribute("totalPersonal", personalService.findAll().size());
        model.addAttribute("totalAsistencias", asistenciaService.findAll().size());
        return "gestion";
    }

    @GetMapping("/contacto")
    public String contacto(Model model, HttpSession session) {
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "contacto";
    }

    @GetMapping("/publicidad")
    public String publicidad(Model model, HttpSession session) {
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "publicidad";
    }
}
