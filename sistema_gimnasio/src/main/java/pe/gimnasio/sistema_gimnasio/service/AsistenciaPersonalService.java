package pe.gimnasio.sistema_gimnasio.service;

import org.springframework.stereotype.Service;
import pe.gimnasio.sistema_gimnasio.model.AsistenciaPersonal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AsistenciaPersonalService {

    private final Map<Long, AsistenciaPersonal> asistencias = new LinkedHashMap<>();
    private final AtomicLong secuencia = new AtomicLong();

    public AsistenciaPersonalService() {
        guardar(new AsistenciaPersonal(null, "Rosa Salazar", LocalDate.now(), LocalTime.of(7, 55),
                LocalTime.of(14, 0), "Turno completo"));
        guardar(new AsistenciaPersonal(null, "Víctor Díaz", LocalDate.now(), LocalTime.of(14, 5),
                LocalTime.of(21, 0), "Ingreso con 5 minutos de retraso"));
    }

    public List<AsistenciaPersonal> listar() {
        return asistencias.values().stream()
                .sorted(Comparator.comparing(AsistenciaPersonal::getId))
                .toList();
    }

    public AsistenciaPersonal buscarPorId(Long id) {
        return asistencias.get(id);
    }

    public void guardar(AsistenciaPersonal asistencia) {
        if (asistencia.getId() == null) {
            asistencia.setId(secuencia.incrementAndGet());
        }
        asistencias.put(asistencia.getId(), asistencia);
    }

    public void eliminar(Long id) {
        asistencias.remove(id);
    }

    public long contarHoy() {
        return asistencias.values().stream()
                .filter(asistencia -> LocalDate.now().equals(asistencia.getFecha()))
                .count();
    }
}
