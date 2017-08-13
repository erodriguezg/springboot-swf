package cl.github.erodriguezg.springbootswf.services.mappers;

import cl.github.erodriguezg.springbootswf.entities.PerfilUsuario;
import cl.github.erodriguezg.springbootswf.services.dto.PerfilDto;
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

}
