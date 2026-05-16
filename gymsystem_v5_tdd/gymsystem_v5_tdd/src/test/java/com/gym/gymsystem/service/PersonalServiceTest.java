package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Personal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonalServiceTest {

    private PersonalService service;

    @BeforeEach
    void setUp() {
        service = new PersonalService();
    }

    // =========================================================
    // PRUEBA 1 — CREAR
    // =========================================================

    @Test
    @DisplayName("CREAR [🔴 ROJO]: personal sin guardar NO tiene ID")
    void crear_rojo() {
        Personal nuevo = new Personal(null, "Carlos Quispe", "Entrenador", "Mañana");

        System.out.println("Prueba 1 [ROJO]: personal recién creado NO debe tener ID aún");
        if (nuevo.getId() == null) {
            System.out.println("✔ Correcto");
        } else {
            System.out.println("✘ Falló");
        }

        assertNull(nuevo.getId(), "Antes de guardar, el ID debe ser null");
    }

    @Test
    @Disabled
    @DisplayName("CREAR [🟢 VERDE]: save() asigna un ID al personal")
    void crear_verde() {
        Personal nuevo = new Personal(null, "Carlos Quispe", "Entrenador", "Mañana");

        service.save(nuevo);

        System.out.println("Prueba 1 [VERDE]: ID asignado → " + nuevo.getId());
        if (nuevo.getId() != null) {
            System.out.println("✔ Pasó");
        } else {
            System.out.println("✘ Falló");
        }
    }

    @Test
    @Disabled
    @DisplayName("CREAR [🔵 REFACTOR]: guardar personal asigna ID positivo")
    void crear_refactor() {
        Personal nuevo = new Personal(null, "Carlos Quispe", "Entrenador", "Mañana");

        service.save(nuevo);

        assertNotNull(nuevo.getId(), "El ID no debe ser null después de guardar");
        assertTrue(nuevo.getId() > 0, "El ID debe ser un número positivo");

        System.out.println("✔ REFACTOR completo — ID asignado: " + nuevo.getId());
    }

    // =========================================================
    // PRUEBA 2 — LEER
    // =========================================================

    @Test
    @Disabled
    @DisplayName("LEER [🔴 ROJO]: findAll sin guardar retorna lista vacía")
    void leer_rojo() {
        System.out.println("Prueba 2 [ROJO]: findAll sin guardar nada debe estar vacío");

        List<Personal> lista = service.findAll();

        if (lista.isEmpty()) {
            System.out.println("✔ Correcto");
        } else {
            System.out.println("✘ Falló");
        }

        assertEquals(0, lista.size(), "Sin guardar nada, la lista debe estar vacía");
    }

    @Test
    @Disabled
    @DisplayName("LEER [🟢 VERDE]: tras guardar 3, findAll los retorna todos")
    void leer_verde() {
        service.save(new Personal(null, "Pedro Ramos",   "Entrenador",    "Mañana"));
        service.save(new Personal(null, "Sofía Aguilar", "Recepcionista", "Tarde"));
        service.save(new Personal(null, "Jorge Torres",  "Instructor",    "Noche"));

        List<Personal> lista = service.findAll();

        System.out.println("Prueba 2 [VERDE]: findAll retornó → " + lista.size() + " empleados");
        if (lista.size() == 3) {
            System.out.println("✔ Pasó");
        } else {
            System.out.println("✘ Falló");
        }
    }

    @Test
    @Disabled
    @DisplayName("LEER [🔵 REFACTOR]: findAll retorna exactamente los 3 empleados guardados")
    void leer_refactor() {
        service.save(new Personal(null, "Pedro Ramos",   "Entrenador",    "Mañana"));
        service.save(new Personal(null, "Sofía Aguilar", "Recepcionista", "Tarde"));
        service.save(new Personal(null, "Jorge Torres",  "Instructor",    "Noche"));

        List<Personal> lista = service.findAll();

        assertEquals(3, lista.size(), "findAll debe retornar los 3 empleados guardados");

        System.out.println("✔ REFACTOR completo — empleados encontrados: " + lista.size());
    }

    // =========================================================
    // PRUEBA 3 — ACTUALIZAR
    // =========================================================

    @Test
    @Disabled
    @DisplayName("ACTUALIZAR [🔴 ROJO]: sin update(), el cargo NO cambia")
    void actualizar_rojo() {
        Personal original = new Personal(null, "Rosa Mamani", "Limpieza", "Mañana");
        service.save(original);

        System.out.println("Prueba 3 [ROJO]: sin update(), el cargo debe seguir siendo 'Limpieza'");

        Optional<Personal> resultado = service.findById(original.getId());
        if (resultado.isPresent() && "Limpieza".equals(resultado.get().getCargo())) {
            System.out.println("✔ Correcto");
        } else {
            System.out.println("✘ Falló");
        }

        assertEquals("Limpieza", resultado.get().getCargo(), "Sin update(), el cargo no debe cambiar");
    }

    @Test
    @Disabled
    @DisplayName("ACTUALIZAR [🟢 VERDE]: update() reemplaza el personal en la lista")
    void actualizar_verde() {
        Personal original = new Personal(null, "Rosa Mamani", "Limpieza", "Mañana");
        service.save(original);

        Personal modificado = new Personal(original.getId(), "Rosa Mamani", "Supervisora", "Tarde");
        service.update(modificado);

        Optional<Personal> resultado = service.findById(original.getId());
        System.out.println("Prueba 3 [VERDE]: cargo tras update() → " + resultado.get().getCargo());
        if ("Supervisora".equals(resultado.get().getCargo())) {
            System.out.println("✔ Pasó");
        } else {
            System.out.println("✘ Falló");
        }
    }

    @Test
    @Disabled
    @DisplayName("ACTUALIZAR [🔵 REFACTOR]: update() cambia cargo y turno correctamente")
    void actualizar_refactor() {
        Personal original = new Personal(null, "Rosa Mamani", "Limpieza", "Mañana");
        service.save(original);

        Personal modificado = new Personal(original.getId(), "Rosa Mamani", "Supervisora", "Tarde");
        service.update(modificado);

        Optional<Personal> encontrado = service.findById(original.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Supervisora", encontrado.get().getCargo(), "El cargo debe haber cambiado a Supervisora");
        assertEquals("Tarde", encontrado.get().getTurno(), "El turno debe haber cambiado a Tarde");

        System.out.println("✔ REFACTOR completo — cargo: " + encontrado.get().getCargo() + ", turno: " + encontrado.get().getTurno());
    }

    // =========================================================
    // PRUEBA 4 — ELIMINAR
    // =========================================================

    @Test
    @Disabled
    @DisplayName("ELIMINAR [🔴 ROJO]: sin deleteById(), el personal sigue existiendo")
    void eliminar_rojo() {
        Personal p = new Personal(null, "Julio Perez", "Entrenador", "Mañana");
        service.save(p);

        System.out.println("Prueba 4 [ROJO]: sin deleteById(), el personal debe seguir existiendo");

        Optional<Personal> resultado = service.findById(p.getId());
        if (resultado.isPresent()) {
            System.out.println("✔ Correcto");
        } else {
            System.out.println("✘ Falló");
        }

        assertTrue(resultado.isPresent(), "Sin eliminar, el personal debe seguir existiendo");
    }

    @Test
    @Disabled
    @DisplayName("ELIMINAR [🟢 VERDE]: deleteById() quita el personal de la lista")
    void eliminar_verde() {
        Personal p = new Personal(null, "Julio Perez", "Entrenador", "Mañana");
        service.save(p);
        Long idGuardado = p.getId();

        service.deleteById(idGuardado);

        Optional<Personal> resultado = service.findById(idGuardado);
        System.out.println("Prueba 4 [VERDE]: tras deleteById(), ¿existe? → " + resultado.isPresent());
        if (resultado.isEmpty()) {
            System.out.println("✔ Pasó");
        } else {
            System.out.println("✘ Falló");
        }
    }

    @Test
    @Disabled
    @DisplayName("ELIMINAR [🔵 REFACTOR]: deleteById() deja la lista vacía")
    void eliminar_refactor() {
        Personal p = new Personal(null, "Julio Perez", "Entrenador", "Mañana");
        service.save(p);
        Long idGuardado = p.getId();

        service.deleteById(idGuardado);

        Optional<Personal> resultado = service.findById(idGuardado);
        assertFalse(resultado.isPresent(), "El empleado eliminado no debe encontrarse");

        assertEquals(0, service.findAll().size(), "La lista debe quedar vacía tras eliminar el único registro");

        System.out.println("✔ REFACTOR completo — lista vacía tras eliminar");
    }
}
