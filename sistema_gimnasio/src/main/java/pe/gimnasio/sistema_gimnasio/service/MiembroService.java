package pe.gimnasio.sistema_gimnasio.service;

import org.springframework.stereotype.Service;
import pe.gimnasio.sistema_gimnasio.model.Miembro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MiembroService {

    private final Map<Long, Miembro> miembros = new LinkedHashMap<>();
    private final AtomicLong secuencia = new AtomicLong();

    public MiembroService() {
        guardar(new Miembro(null, "Carlos Ramos", "70234561", "987654321", "Mensual", "Activo"));
        guardar(new Miembro(null, "María Quispe", "71458963", "923456781", "Trimestral", "Activo"));
        guardar(new Miembro(null, "Jorge Huamán", "72896314", "956123478", "Mensual", "Pendiente"));
    }

    public List<Miembro> listar() {
        return miembros.values().stream()
                .sorted(Comparator.comparing(Miembro::getId))
                .toList();
    }

    public Miembro buscarPorId(Long id) {
        return miembros.get(id);
    }

    public void guardar(Miembro miembro) {
        if (miembro.getId() == null) {
            miembro.setId(secuencia.incrementAndGet());
        }
        miembros.put(miembro.getId(), miembro);
    }

    public void eliminar(Long id) {
        miembros.remove(id);
    }

    public long contarActivos() {
        return miembros.values().stream()
                .filter(miembro -> "Activo".equalsIgnoreCase(miembro.getEstado()))
                .count();
    }

    public List<String> nombresMiembros() {
        List<String> nombres = new ArrayList<>();
        for (Miembro miembro : listar()) {
            nombres.add(miembro.getNombres());
        }
        return nombres;
    }
}
