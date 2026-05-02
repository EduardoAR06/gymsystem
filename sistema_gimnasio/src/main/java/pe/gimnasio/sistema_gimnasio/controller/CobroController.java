package pe.gimnasio.sistema_gimnasio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pe.gimnasio.sistema_gimnasio.model.Cobro;
import pe.gimnasio.sistema_gimnasio.service.CobroService;
import pe.gimnasio.sistema_gimnasio.service.MiembroService;

@Controller
public class CobroController {

    private final CobroService cobroService;
    private final MiembroService miembroService;

    public CobroController(CobroService cobroService, MiembroService miembroService) {
        this.cobroService = cobroService;
        this.miembroService = miembroService;
    }

    @GetMapping("/cobros")
    public String listar(Model model) {
        cargarVista(model, new Cobro());
        return "cobros";
    }

    @GetMapping("/cobros/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cobro cobro = cobroService.buscarPorId(id);
        cargarVista(model, cobro == null ? new Cobro() : cobro);
        return "cobros";
    }

    @PostMapping("/cobros/guardar")
    public String guardar(@ModelAttribute Cobro cobro) {
        cobroService.guardar(cobro);
        return "redirect:/cobros";
    }

    @GetMapping("/cobros/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        cobroService.eliminar(id);
        return "redirect:/cobros";
    }

    private void cargarVista(Model model, Cobro formulario) {
        model.addAttribute("formulario", formulario);
        model.addAttribute("cobros", cobroService.listar());
        model.addAttribute("miembrosDisponibles", miembroService.nombresMiembros());
    }
}
