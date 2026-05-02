package pe.gimnasio.sistema_gimnasio.service;

import org.springframework.stereotype.Service;
import pe.gimnasio.sistema_gimnasio.model.Cobro;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CobroService {

    private final Map<Long, Cobro> cobros = new LinkedHashMap<>();
    private final AtomicLong secuencia = new AtomicLong();

    public CobroService() {
        guardar(new Cobro(null, "Carlos Ramos", 120.0, LocalDate.now().minusDays(2), "Efectivo", "Pagado"));
        guardar(new Cobro(null, "María Quispe", 300.0, LocalDate.now().minusDays(1), "Yape", "Pagado"));
        guardar(new Cobro(null, "Jorge Huamán", 120.0, LocalDate.now(), "Tarjeta", "Pendiente"));
    }

    public List<Cobro> listar() {
        return cobros.values().stream()
                .sorted(Comparator.comparing(Cobro::getId))
                .toList();
    }

    public Cobro buscarPorId(Long id) {
        return cobros.get(id);
    }

    public void guardar(Cobro cobro) {
        if (cobro.getId() == null) {
            cobro.setId(secuencia.incrementAndGet());
        }
        cobros.put(cobro.getId(), cobro);
    }

    public void eliminar(Long id) {
        cobros.remove(id);
    }

    public double totalCobrado() {
        return cobros.values().stream()
                .filter(cobro -> "Pagado".equalsIgnoreCase(cobro.getEstado()))
                .mapToDouble(Cobro::getMonto)
                .sum();
    }
}
