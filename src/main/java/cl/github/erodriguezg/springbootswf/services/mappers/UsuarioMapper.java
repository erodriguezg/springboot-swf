package cl.github.erodriguezg.springbootswf.services.mappers;

import cl.github.erodriguezg.springbootswf.entities.Usuario;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
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
        return dto;
    }
}
