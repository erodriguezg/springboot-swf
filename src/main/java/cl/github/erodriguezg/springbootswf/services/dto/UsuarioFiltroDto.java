package cl.github.erodriguezg.springbootswf.services.dto;

import java.io.Serializable;
import java.util.Date;

public class UsuarioFiltroDto implements Serializable {

    private static final long serialVersionUID = -7369622710383099279L;

    private String nombres;
    private String apellidos;
    private Integer rut;
    private String email;
    private Date fechaNacimientoInferior;
    private Date fechaNacimientoSuperior;
    private Boolean habilitado;
    private PerfilDto perfilDto;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNacimientoInferior() {
        return fechaNacimientoInferior;
    }

    public void setFechaNacimientoInferior(Date fechaNacimientoInferior) {
        this.fechaNacimientoInferior = fechaNacimientoInferior;
    }

    public Date getFechaNacimientoSuperior() {
        return fechaNacimientoSuperior;
    }

    public void setFechaNacimientoSuperior(Date fechaNacimientoSuperior) {
        this.fechaNacimientoSuperior = fechaNacimientoSuperior;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public PerfilDto getPerfilDto() {
        return perfilDto;
    }

    public void setPerfilDto(PerfilDto perfilDto) {
        this.perfilDto = perfilDto;
    }
}