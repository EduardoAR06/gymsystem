package pe.gimnasio.sistema_gimnasio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pe.gimnasio.sistema_gimnasio.model.Personal;
import pe.gimnasio.sistema_gimnasio.service.PersonalService;

@Controller
public class PersonalController {

    private final PersonalService personalService;

    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }

    @GetMapping("/personal")
    public String listar(Model model) {
        cargarVista(model, new Personal());
        return "personal";
    }

    @GetMapping("/personal/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Personal personal = personalService.buscarPorId(id);
        cargarVista(model, personal == null ? new Personal() : personal);
        return "personal";
    }

    @PostMapping("/personal/guardar")
    public String guardar(@ModelAttribute Personal personal) {
        personalService.guardar(personal);
        return "redirect:/personal";
    }

    @GetMapping("/personal/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        personalService.eliminar(id);
        return "redirect:/personal";
    }

    private void cargarVista(Model model, Personal formulario) {
        model.addAttribute("formulario", formulario);
        model.addAttribute("personalLista", personalService.listar());
    }
}
