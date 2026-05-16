package com.gym.gymsystem.model;

/**
 * Roles del sistema:
 *
 *  ADMIN        — Acceso total a todo el sistema
 *  RECEP_MEM    — Recepcionista de Membresías: Inicio, Publicidad, Contacto,
 *                 Miembros, Membresías (Categorías), Cobros
 *  RECEP_ASIS   — Recepcionista de Asistencias: Inicio, Publicidad, Contacto,
 *                 Personal, Asistencias
 *  ENTRENADOR   — Solo acceso de lectura: Inicio, Publicidad, Contacto, Personal
 */
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String rol;
    private String nombres;

    public Usuario() {}

    public Usuario(Long id, String username, String password, String rol, String nombres) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.nombres = nombres;
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }
    public String getRol() { return rol; }
    public void setRol(String r) { this.rol = r; }
    public String getNombres() { return nombres; }
    public void setNombres(String n) { this.nombres = n; }

    // ── Helpers de permisos ───────────────────────────────────────────────────
    public boolean isAdmin()      { return "ADMIN".equals(rol); }
    public boolean isRecepMem()   { return "RECEP_MEM".equals(rol); }
    public boolean isRecepAsis()  { return "RECEP_ASIS".equals(rol); }
    public boolean isEntrenador() { return "ENTRENADOR".equals(rol); }

    /** Puede gestionar Miembros, Membresías y Cobros */
    public boolean puedeMembresias() { return isAdmin() || isRecepMem(); }

    /** Puede gestionar Personal y Asistencias */
    public boolean puedeAsistencias() { return isAdmin() || isRecepAsis(); }

    /** Puede ver Personal (lectura) */
    public boolean puedeVerPersonal() { return isAdmin() || isRecepAsis() || isEntrenador(); }

    /** Puede editar/eliminar Personal */
    public boolean puedeEditarPersonal() { return isAdmin() || isRecepAsis(); }

    /** Nombre legible del rol */
    public String getRolNombre() {
        return switch (rol != null ? rol : "") {
            case "ADMIN"      -> "Administrador";
            case "RECEP_MEM"  -> "Recep. Membresías";
            case "RECEP_ASIS" -> "Recep. Asistencias";
            case "ENTRENADOR" -> "Entrenador";
            default           -> rol;
        };
    }
}
