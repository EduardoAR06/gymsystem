package pe.gimnasio.sistema_gimnasio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pe.gimnasio.sistema_gimnasio.model.Miembro;
import pe.gimnasio.sistema_gimnasio.service.MembresiaService;
import pe.gimnasio.sistema_gimnasio.service.MiembroService;

@Controller
public class MiembroController {

    private final MiembroService miembroService;
    private final MembresiaService membresiaService;

    public MiembroController(MiembroService miembroService, MembresiaService membresiaService) {
        this.miembroService = miembroService;
        this.membresiaService = membresiaService;
    }

    @GetMapping("/miembros")
    public String listar(Model model) {
        cargarVista(model, new Miembro());
        return "miembros";
    }

    @GetMapping("/miembros/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Miembro miembro = miembroService.buscarPorId(id);
        cargarVista(model, miembro == null ? new Miembro() : miembro);
        return "miembros";
    }

    @PostMapping("/miembros/guardar")
    public String guardar(@ModelAttribute Miembro miembro) {
        miembroService.guardar(miembro);
        return "redirect:/miembros";
    }

    @GetMapping("/miembros/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        miembroService.eliminar(id);
        return "redirect:/miembros";
    }

    private void cargarVista(Model model, Miembro formulario) {
        model.addAttribute("formulario", formulario);
        model.addAttribute("miembros", miembroService.listar());
        model.addAttribute("membresiasDisponibles", membresiaService.nombresMembresias());
    }
}
