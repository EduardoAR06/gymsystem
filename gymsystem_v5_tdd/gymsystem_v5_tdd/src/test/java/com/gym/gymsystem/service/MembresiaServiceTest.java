package com.gym.gymsystem.service;

import com.gym.gymsystem.model.Membresia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MembresiaServiceTest {

    private MembresiaService service;

    @BeforeEach
    void setUp() {
        service = new MembresiaService();
    }

    // =========================================================
    // PRUEBA 1 — CREAR
    // =========================================================

    @Test
    @DisplayName("CREAR [🔴 ROJO]: membresía sin guardar NO tiene ID")
    void crear_rojo() {
        Membresia nueva = new Membresia(null, "Plan Mensual", 80.0, "30 días");

        System.out.println("Prueba 1 [ROJO]: membresía recién creada NO debe tener ID aún");
        if (nueva.getId() == null) {
            System.out.println("✔ Correcto");
        } else {
            System.out.println("✘ Falló");
        }

        assertNull(nueva.getId(), "Antes de guardar, el ID debe ser null");
    }

    @Test
    @Disabled
    @DisplayName("CREAR [🟢 VERDE]: save() asigna un ID a la membresía")
    void crear_verde() {
        Membresia nueva = new Membresia(null, "Plan Mensual", 80.0, "30 días");

        service.save(nueva);

        System.out.println("Prueba 1 [VERDE]: ID asignado → " + nueva.getId());
        if (nueva.getId() != null) {
            System.out.println("✔ Pasó");
        } else {
            System.out.println("✘ Falló");
        }
    }

    @Test
    //@Disabled
    @DisplayName("CREAR [🔵 REFACTOR]: guardar membresía asigna ID positivo")
    void crear_refactor() {
        Membresia nueva = new Membresia(null, "Plan Mensual", 80.0, "30 días");

        service.save(nueva);

        assertNotNull(nueva.getId(), "El ID no debe ser null después de guardar");
        assertTrue(nueva.getId() > 0, "El ID debe ser un número positivo");

        System.out.println("✔ REFACTOR completo — ID asignado: " + nueva.getId());
    }

    // =========================================================
    // PRUEBA 2 — LEER
    // =========================================================

    @Test
    @Disabled
    @DisplayName("LEER [🔴 ROJO]: buscar ID inexistente retorna vacío")
    void leer_rojo() {
        System.out.println("Prueba 2 [ROJO]: buscar ID 1 sin haber guardado nada");

        Optional<Membresia> resultado = service.findById(1L);

        if (resultado.isEmpty()) {
            System.out.println("✔ Correcto");
        } else {
            System.out.println("✘ Falló");
        }

        assertFalse(resultado.isPresent(), "Sin guardar nada, findById debe retornar vacío");
    }

    @Test
    @Disabled
    @DisplayName("LEER [🟢 VERDE]: tras save(), findById encuentra la membresía")
    void leer_verde() {
        Membresia m = new Membresia(null, "Plan Trimestral", 200.0, "90 días");

        service.save(m);
        Optional<Membresia> resultado = service.findById(m.getId());

        System.out.println("Prueba 2 [VERDE]: buscando ID " + m.getId());
        if (resultado.isPresent()) {
            System.out.println("✔ Pasó");
        } else {
            System.out.println("✘ Falló");
        }
    }

    @Test
    @Disabled
    @DisplayName("LEER [🔵 REFACTOR]: findById retorna membresía con datos correctos")
    void leer_refactor() {
        Membresia m = new Membresia(null, "Plan Trimestral", 200.0, "90 días");

        service.save(m);
        Optional<Membresia> resultado = service.findById(m.getId());

        assertTrue(resultado.isPresent(), "Debe encontrarse la membresía guardada");
        assertEquals("Plan Trimestral", resultado.get().getPlan(), "El plan debe ser 'Plan Trimestral'");

        System.out.println("✔ REFACTOR completo — plan: " + resultado.get().getPlan());
    }

    // =========================================================
    // PRUEBA 3 — ACTUALIZAR
    // =========================================================

    @Test
    @Disabled
    @DisplayName("ACTUALIZAR [🔴 ROJO]: sin update(), el plan NO cambia")
    void actualizar_rojo() {
        Membresia original = new Membresia(null, "Plan Básico", 50.0, "30 días");
        service.save(original);

        System.out.println("Prueba 3 [ROJO]: sin update(), el plan debe seguir siendo 'Plan Básico'");

        Optional<Membresia> resultado = service.findById(original.getId());
        if (resultado.isPresent() && "Plan Básico".equals(resultado.get().getPlan())) {
            System.out.println("✔ Correcto");
        } else {
            System.out.println("✘ Falló");
        }

        assertEquals("Plan Básico", resultado.get().getPlan(), "Sin update(), el plan no debe cambiar");
    }

    @Test
    @Disabled
    @DisplayName("ACTUALIZAR [🟢 VERDE]: update() reemplaza la membresía en la lista")
    void actualizar_verde() {
        Membresia original = new Membresia(null, "Plan Básico", 50.0, "30 días");
        service.save(original);

        Membresia actualizada = new Membresia(original.getId(), "Plan Premium", 150.0, "30 días");
        service.update(actualizada);

        Optional<Membresia> resultado = service.findById(original.getId());
        System.out.println("Prueba 3 [VERDE]: plan tras update() → " + resultado.get().getPlan());
        if ("Plan Premium".equals(resultado.get().getPlan())) {
            System.out.println("✔ Pasó");
        } else {
            System.out.println("✘ Falló");
        }
    }

    @Test
    @Disabled
    @DisplayName("ACTUALIZAR [🔵 REFACTOR]: update() cambia plan y precio correctamente")
    void actualizar_refactor() {
        Membresia original = new Membresia(null, "Plan Básico", 50.0, "30 días");
        service.save(original);

        Membresia actualizada = new Membresia(original.getId(), "Plan Premium", 150.0, "30 días");
        service.update(actualizada);

        Optional<Membresia> encontrada = service.findById(original.getId());
        assertTrue(encontrada.isPresent());
        assertEquals("Plan Premium", encontrada.get().getPlan(), "El plan debe haber cambiado a 'Plan Premium'");
        assertEquals(150.0, encontrada.get().getPrecio(), "El precio debe haber actualizado a 150.0");

        System.out.println("✔ REFACTOR completo — plan: " + encontrada.get().getPlan() + ", precio: " + encontrada.get().getPrecio());
    }

    // =========================================================
    // PRUEBA 4 — ELIMINAR
    // =========================================================

    @Test
    @Disabled
    @DisplayName("ELIMINAR [🔴 ROJO]: sin deleteById(), la membresía sigue existiendo")
    void eliminar_rojo() {
        Membresia m = new Membresia(null, "Plan Semestral", 350.0, "180 días");
        service.save(m);

        System.out.println("Prueba 4 [ROJO]: sin deleteById(), la membresía debe seguir existiendo");

        Optional<Membresia> resultado = service.findById(m.getId());
        if (resultado.isPresent()) {
            System.out.println("✔ Correcto");
        } else {
            System.out.println("✘ Falló");
        }

        assertTrue(resultado.isPresent(), "Sin eliminar, la membresía debe seguir existiendo");
    }

    @Test
    @Disabled
    @DisplayName("ELIMINAR [🟢 VERDE]: deleteById() quita la membresía de la lista")
    void eliminar_verde() {
        Membresia m = new Membresia(null, "Plan Semestral", 350.0, "180 días");
        service.save(m);
        Long idGuardado = m.getId();

        service.deleteById(idGuardado);

        Optional<Membresia> resultado = service.findById(idGuardado);
        System.out.println("Prueba 4 [VERDE]: tras deleteById(), ¿existe? → " + resultado.isPresent());
        if (resultado.isEmpty()) {
            System.out.println("✔ Pasó");
        } else {
            System.out.println("✘ Falló");
        }
    }

    @Test
    //@Disabled
    @DisplayName("ELIMINAR [🔵 REFACTOR]: deleteById() deja la lista vacía")
    void eliminar_refactor() {
        Membresia m = new Membresia(null, "Plan Semestral", 350.0, "180 días");
        service.save(m);
        Long idGuardado = m.getId();

        service.deleteById(idGuardado);

        Optional<Membresia> resultado = service.findById(idGuardado);
        assertFalse(resultado.isPresent(), "La membresía eliminada no debe encontrarse");

        List<Membresia> lista = service.findAll();
        assertEquals(0, lista.size(), "La lista debe estar vacía tras eliminar el único elemento");

        System.out.println("✔ REFACTOR completo — lista vacía tras eliminar");
    }
}
