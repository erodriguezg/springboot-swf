package com.github.erodriguezg.springbootswf.managedbeans;

import com.github.erodriguezg.springbootswf.security.Identidad;
import com.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("identidad")
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IdentidadBean implements Identidad, Serializable {

    public boolean tieneRol(String rol) {
        UsernamePasswordAuthenticationToken token = getSecurityToken();
        return token != null && token.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(rol));
    }

    private UsuarioDto obtenerUsuarioDesdeSesion() {
        UsernamePasswordAuthenticationToken token = getSecurityToken();
        if (token != null && token.getPrincipal() != null && (token.getPrincipal() instanceof UsuarioDto)) {
            return (UsuarioDto) token.getPrincipal();
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken getSecurityToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return null;
        }
        return (UsernamePasswordAuthenticationToken) authentication;
    }

    @Override
    public Long getIdPersona() {
        UsuarioDto usuario = obtenerUsuarioDesdeSesion();
        return usuario != null ? usuario.getId() : null;
    }

    @Override
    public boolean isLoggedIn() {
        return obtenerUsuarioDesdeSesion() != null;
    }

    @Override
    public String getNombrePersona() {
        UsuarioDto usuario = obtenerUsuarioDesdeSesion();
        return usuario != null ? usuario.getNombres() : null;
    }
}
