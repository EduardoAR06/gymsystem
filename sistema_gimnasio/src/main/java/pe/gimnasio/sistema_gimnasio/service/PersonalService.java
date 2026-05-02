package pe.gimnasio.sistema_gimnasio.service;

import org.springframework.stereotype.Service;
import pe.gimnasio.sistema_gimnasio.model.Personal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonalService {

    private final Map<Long, Personal> personal = new LinkedHashMap<>();
    private final AtomicLong secuencia = new AtomicLong();

    public PersonalService() {
        guardar(new Personal(null, "Rosa Salazar", "Recepcionista", "Mañana", "Activo"));
        guardar(new Personal(null, "Víctor Díaz", "Entrenador", "Tarde", "Activo"));
        guardar(new Personal(null, "Elena Soto", "Limpieza", "Mañana", "Activo"));
    }

    public List<Personal> listar() {
        return personal.values().stream()
                .sorted(Comparator.comparing(Personal::getId))
                .toList();
    }

    public Personal buscarPorId(Long id) {
        return personal.get(id);
    }

    public void guardar(Personal item) {
        if (item.getId() == null) {
            item.setId(secuencia.incrementAndGet());
        }
        personal.put(item.getId(), item);
    }

    public void eliminar(Long id) {
        personal.remove(id);
    }

    public long contarActivos() {
        return personal.values().stream()
                .filter(item -> "Activo".equalsIgnoreCase(item.getEstado()))
                .count();
    }

    public List<String> nombresPersonal() {
        List<String> nombres = new ArrayList<>();
        for (Personal item : listar()) {
            nombres.add(item.getNombres());
        }
        return nombres;
    }
}
