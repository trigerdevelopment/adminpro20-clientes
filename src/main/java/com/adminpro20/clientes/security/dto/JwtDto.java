package com.adminpro20.clientes.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtDto {
    private String token;
    private String bearer = "Bearer";
    private String nombreUsuario;
    private String usuarioDetails;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String token, String nombreUsuario, String usuarioDetails, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.usuarioDetails = usuarioDetails;
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getUsuarioDetails() {
        return usuarioDetails;
    }

    public void setUsuarioDetails(String usuarioDetails) {
        this.usuarioDetails = usuarioDetails;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}