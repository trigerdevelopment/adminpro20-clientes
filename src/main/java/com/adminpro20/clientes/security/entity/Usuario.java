package com.adminpro20.clientes.security.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    @Column(unique = true)
    private String nombreUsuario;
    @NotNull
    private String email;
    private String photoName;
    @NotNull
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotNull
    private boolean enabled;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    public Usuario() {
    }

    public Usuario(Long id, @NotNull String nombre, @NotNull String apellido, @NotNull String nombreUsuario, @NotNull String email, String photoName, @NotNull String password, @NotNull boolean enabled) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.photoName = photoName;
        this.password = password;
        this.enabled = enabled;
    }
//    public Usuario(Long id,@NotNull String nombre, @NotNull String apellido, @NotNull String nombreUsuario, @NotNull String email, @NotNull String password, @NotNull Boolean enabled) {
//        this.id=id;
//        this.nombre = nombre;
//        this.apellido = apellido;
//        this.nombreUsuario = nombreUsuario;
//        this.email = email;
//        this.password = password;
//        this.enabled = enabled;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}