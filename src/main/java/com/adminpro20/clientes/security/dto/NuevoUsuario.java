package com.adminpro20.clientes.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class NuevoUsuario {

    private Long id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String nombreUsuario;
    @Email
    private String email;
    private String photoName;
    @NotBlank
    private Boolean enabled;
    @NotBlank
    private String password;
    private Set<String> roles = new HashSet<>();

//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public String getNombreUsuario() {
//        return nombreUsuario;
//    }
//
//    public void setNombreUsuario(String nombreUsuario) {
//        this.nombreUsuario = nombreUsuario;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Set<String> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<String> roles) {
//        this.roles = roles;
//    }
//
//    public String getApellido() {
//        return apellido;
//    }
//
//    public void setApellido(String apellido) {
//        this.apellido = apellido;
//    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Date getHistoryDate() {
//        return historyDate;
//    }
//
//    public void setHistoryDate(Date historyDate) {
//        this.historyDate = historyDate;
//    }
//
//    public Boolean getEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(Boolean enabled) {
//        this.enabled = enabled;
//    }
}
