package cl.github.erodriguezg.springbootswf.managedbeans;

import cl.github.erodriguezg.springbootswf.security.Identidad;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("identidad")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class IdentidadBean implements Identidad {

    @Autowired
    private HttpServletRequest httpRequest;

    public boolean tieneRol(String rol) {
        return httpRequest.isUserInRole(rol);
    }

    private UsuarioDto obtenerUsuarioDesdeSesion() {
        Object principal = getPrincipal();
        if (principal != null && (principal instanceof UsuarioDto)) {
            return (UsuarioDto) principal;
        }
        return null;
    }

    private Object getPrincipal() {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) httpRequest.getUserPrincipal();
        return token != null ? token.getPrincipal() : null;
    }

    @Override
    public Long getIdPersona() {
        UsuarioDto usuario = obtenerUsuarioDesdeSesion();
        return usuario != null ? usuario.getId() : null;
    }

    @Override
    public String getNombres() {
        return null;
    }

    @Override
    public String getApellidos() {
        return null;
    }

    @Override
    public Integer getIdPerfil() {
        return null;
    }

    @Override
    public String getNombrePerfil() {
        return null;
    }

    @Override
    public void setNombrePerfil(String nombrePerfil) {

    }
}
