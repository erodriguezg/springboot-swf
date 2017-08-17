package com.github.erodriguezg.springbootswf.services.mappers;

import com.github.erodriguezg.springbootswf.entities.PerfilUsuario;
import com.github.erodriguezg.springbootswf.entities.Persona;
import com.github.erodriguezg.springbootswf.entities.Usuario;
import com.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by eduar on 05/07/2017.
 */
@Component
public class UsuarioMapper {

    @Autowired
    private PerfilUsuarioMapper perfilUsuarioMapper;

    public UsuarioDto toUsuarioDto(Usuario entidad) {
        if(entidad == null) {
            return null;
        }
        UsuarioDto dto = new UsuarioDto();
        dto.setApMaterno(entidad.getPersona().getApellidoMaterno());
        dto.setApPaterno(entidad.getPersona().getApellidoPaterno());
        dto.setEmail(entidad.getPersona().getEmail());
        dto.setFechaNacimiento(entidad.getPersona().getFechanacimiento());
        dto.setHabilitado(entidad.getHabilitado());
        dto.setId(entidad.getIdPersona());
        dto.setNombres(entidad.getPersona().getNombres());
        dto.setPassword(entidad.getPassword());
        dto.setPerfil(perfilUsuarioMapper.toPerfilDto(entidad.getIdPerfilUsuario()));
        dto.setRut(entidad.getPersona().getRun());
        dto.setUsername(entidad.getUsername());
        return dto;
    }

    public Usuario toUsuario(UsuarioDto dto) {
        if(dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        Persona persona = new Persona();
        PerfilUsuario perfilUsuario = new PerfilUsuario();

        usuario.setPersona(persona);
        usuario.setIdPerfilUsuario(perfilUsuario);

        persona.setApellidoMaterno(dto.getApMaterno());
        persona.setApellidoPaterno(dto.getApPaterno());
        persona.setEmail(dto.getEmail());
        persona.setFechanacimiento(dto.getFechaNacimiento());
        persona.setIdPersona(dto.getId());
        persona.setNombres(dto.getNombres());
        persona.setRun(dto.getRut());

        usuario.setUsername(dto.getUsername());
        usuario.setHabilitado(dto.getHabilitado());
        usuario.setIdPersona(dto.getId());
        usuario.setPassword(dto.getPassword());

        usuario.setIdPerfilUsuario(perfilUsuarioMapper.toPerfilUsuario(dto.getPerfil()));

        return usuario;
    }
}
