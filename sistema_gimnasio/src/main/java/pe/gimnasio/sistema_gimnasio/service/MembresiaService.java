package pe.gimnasio.sistema_gimnasio.service;

import org.springframework.stereotype.Service;
import pe.gimnasio.sistema_gimnasio.model.Membresia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MembresiaService {

    private final Map<Long, Membresia> membresias = new LinkedHashMap<>();
    private final AtomicLong secuencia = new AtomicLong();

    public MembresiaService() {
        guardar(new Membresia(null, "Mensual", 120.0, 30, "Acceso general al gimnasio por 30 días."));
        guardar(new Membresia(null, "Trimestral", 300.0, 90, "Plan preferido por alumnos con descuento."));
        guardar(new Membresia(null, "Personalizado", 180.0, 30, "Incluye asesoría del entrenador."));
    }

    public List<Membresia> listar() {
        return membresias.values().stream()
                .sorted(Comparator.comparing(Membresia::getId))
                .toList();
    }

    public Membresia buscarPorId(Long id) {
        return membresias.get(id);
    }

    public void guardar(Membresia membresia) {
        if (membresia.getId() == null) {
            membresia.setId(secuencia.incrementAndGet());
        }
        membresias.put(membresia.getId(), membresia);
    }

    public void eliminar(Long id) {
        membresias.remove(id);
    }

    public long contar() {
        return membresias.size();
    }

    public List<String> nombresMembresias() {
        List<String> nombres = new ArrayList<>();
        for (Membresia membresia : listar()) {
            nombres.add(membresia.getNombre());
        }
        return nombres;
    }
}
