package com.gym.gymsystem.model;

/**
 * Representa un usuario del sistema con rol definido.
 * Roles: ADMIN, MIEMBROS, ASISTENCIAS
 */
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String rol;      // ADMIN, MIEMBROS, ASISTENCIAS
    private String nombres;

    public Usuario() {}

    public Usuario(Long id, String username, String password, String rol, String nombres) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.nombres = nombres;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
}
