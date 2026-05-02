package com.gym.gymsystem.controller;

import com.gym.gymsystem.service.*;
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

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("totalMiembros", miembroService.findAll().size());
        model.addAttribute("totalMembresias", 3);
        model.addAttribute("totalCobros", cobroService.findAll().stream()
                .filter(c -> "Pagado".equals(c.getEstado()))
                .mapToDouble(c -> c.getMonto()).sum());
        model.addAttribute("totalAsistencias", asistenciaService.findAll().size());
        return "main";
    }

    @GetMapping("/gestion")
    public String gestion(Model model) {
        model.addAttribute("totalMiembros", miembroService.findAll().size());
        double totalCobros = cobroService.findAll().stream()
                .filter(c -> "Pagado".equals(c.getEstado()))
                .mapToDouble(c -> c.getMonto()).sum();
        model.addAttribute("totalCobros", totalCobros);
        model.addAttribute("totalPersonal", personalService.findAll().size());
        model.addAttribute("totalAsistencias", asistenciaService.findAll().size());
        return "gestion";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/publicidad")
    public String publicidad() {
        return "publicidad";
    }
}
