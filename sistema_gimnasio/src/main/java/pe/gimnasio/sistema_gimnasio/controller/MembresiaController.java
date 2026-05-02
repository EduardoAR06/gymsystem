package pe.gimnasio.sistema_gimnasio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pe.gimnasio.sistema_gimnasio.model.Membresia;
import pe.gimnasio.sistema_gimnasio.service.MembresiaService;

@Controller
public class MembresiaController {

    private final MembresiaService membresiaService;

    public MembresiaController(MembresiaService membresiaService) {
        this.membresiaService = membresiaService;
    }

    @GetMapping("/membresias")
    public String listar(Model model) {
        cargarVista(model, new Membresia());
        return "membresias";
    }

    @GetMapping("/membresias/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Membresia membresia = membresiaService.buscarPorId(id);
        cargarVista(model, membresia == null ? new Membresia() : membresia);
        return "membresias";
    }

    @PostMapping("/membresias/guardar")
    public String guardar(@ModelAttribute Membresia membresia) {
        membresiaService.guardar(membresia);
        return "redirect:/membresias";
    }

    @GetMapping("/membresias/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        membresiaService.eliminar(id);
        return "redirect:/membresias";
    }

    private void cargarVista(Model model, Membresia formulario) {
        model.addAttribute("formulario", formulario);
        model.addAttribute("membresias", membresiaService.listar());
    }
}
