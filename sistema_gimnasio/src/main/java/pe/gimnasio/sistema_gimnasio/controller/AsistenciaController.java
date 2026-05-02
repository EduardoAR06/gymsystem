package pe.gimnasio.sistema_gimnasio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pe.gimnasio.sistema_gimnasio.model.AsistenciaPersonal;
import pe.gimnasio.sistema_gimnasio.service.AsistenciaPersonalService;
import pe.gimnasio.sistema_gimnasio.service.PersonalService;

@Controller
public class AsistenciaController {

    private final AsistenciaPersonalService asistenciaPersonalService;
    private final PersonalService personalService;

    public AsistenciaController(AsistenciaPersonalService asistenciaPersonalService, PersonalService personalService) {
        this.asistenciaPersonalService = asistenciaPersonalService;
        this.personalService = personalService;
    }

    @GetMapping("/asistencias")
    public String listar(Model model) {
        cargarVista(model, new AsistenciaPersonal());
        return "asistencias";
    }

    @GetMapping("/asistencias/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        AsistenciaPersonal asistencia = asistenciaPersonalService.buscarPorId(id);
        cargarVista(model, asistencia == null ? new AsistenciaPersonal() : asistencia);
        return "asistencias";
    }

    @PostMapping("/asistencias/guardar")
    public String guardar(@ModelAttribute AsistenciaPersonal asistencia) {
        asistenciaPersonalService.guardar(asistencia);
        return "redirect:/asistencias";
    }

    @GetMapping("/asistencias/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        asistenciaPersonalService.eliminar(id);
        return "redirect:/asistencias";
    }

    private void cargarVista(Model model, AsistenciaPersonal formulario) {
        model.addAttribute("formulario", formulario);
        model.addAttribute("asistencias", asistenciaPersonalService.listar());
        model.addAttribute("personalDisponible", personalService.nombresPersonal());
    }
}
