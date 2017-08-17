package com.github.erodriguezg.springbootswf.services.mappers;

import com.github.erodriguezg.springbootswf.entities.PerfilUsuario;
import com.github.erodriguezg.springbootswf.services.dto.PerfilDto;
import org.springframework.stereotype.Component;

@Component
public class PerfilUsuarioMapper {

    public PerfilDto toPerfilDto(PerfilUsuario entidad) {
        PerfilDto dto = new PerfilDto();
        if(entidad != null) {
            dto.setId(entidad.getIdPerfilUsuario());
            dto.setNombre(entidad.getNombre());
        }
        return dto;
    }

    public PerfilUsuario toPerfilUsuario(PerfilDto dto) {
        PerfilUsuario entidad = new PerfilUsuario();
        if(dto == null) {
            return null;
        }
        entidad.setIdPerfilUsuario(dto.getId());
        entidad.setNombre(dto.getNombre());
        return entidad;
    }
}
