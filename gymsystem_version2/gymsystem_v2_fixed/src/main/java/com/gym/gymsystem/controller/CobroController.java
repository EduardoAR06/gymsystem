package com.gym.gymsystem.controller;

import com.gym.gymsystem.model.Cobro;
import com.gym.gymsystem.model.Miembro;
import com.gym.gymsystem.model.Usuario;
import com.gym.gymsystem.service.CobroService;
import com.gym.gymsystem.service.MiembroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cobros")
public class CobroController {

    private final CobroService cobroService;
    private final MiembroService miembroService;

    public CobroController(CobroService cobroService, MiembroService miembroService) {
        this.cobroService = cobroService;
        this.miembroService = miembroService;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if ("ASISTENCIAS".equals(u.getRol())) return "redirect:/main";
        model.addAttribute("cobros", cobroService.findAll());
        model.addAttribute("cobro", new Cobro());
        model.addAttribute("miembros", miembroService.findAll());
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "cobros/lista";
    }

    @GetMapping("/buscar")
    @ResponseBody
    public List<Miembro> buscarMiembros(@RequestParam String q) {
        return miembroService.findAll().stream()
                .filter(m -> m.getNombres().toLowerCase().contains(q.toLowerCase())
                          || m.getDni().contains(q))
                .limit(10)
                .toList();
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cobro cobro,
                          @RequestParam(required = false) Long miembroIdForm,
                          @RequestParam(required = false) String miembroNombreForm,
                          RedirectAttributes ra) {
        // Resolver miembro
        Long mid = (cobro.getMiembroId() != null) ? cobro.getMiembroId() : miembroIdForm;
        if (mid != null) {
            miembroService.findById(mid).ifPresent(m -> {
                cobro.setMiembroId(m.getId());
                cobro.setMiembroNombre(m.getNombres());
            });
        } else if (miembroNombreForm != null) {
            cobro.setMiembroNombre(miembroNombreForm);
        }

        cobroService.save(cobro);

        // Renovar membresía automáticamente
        if (mid != null && cobro.getPlanPagado() != null) {
            miembroService.renovarMembresia(mid, cobro.getPlanPagado());
        }

        ra.addFlashAttribute("mensaje", "Cobro registrado. Membresía renovada automáticamente.");
        return "redirect:/cobros";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        Cobro cobro = cobroService.findById(id)
                .orElseThrow(() -> new RuntimeException("Cobro no encontrado: " + id));
        model.addAttribute("cobro", cobro);
        model.addAttribute("cobros", cobroService.findAll());
        model.addAttribute("miembros", miembroService.findAll());
        model.addAttribute("editando", true);
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "cobros/lista";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Cobro cobro, RedirectAttributes ra) {
        cobroService.update(cobro);
        ra.addFlashAttribute("mensaje", "Cobro actualizado correctamente.");
        return "redirect:/cobros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        cobroService.deleteById(id);
        ra.addFlashAttribute("mensaje", "Cobro eliminado correctamente.");
        return "redirect:/cobros";
    }
}
