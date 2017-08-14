package cl.github.erodriguezg.springbootswf.dao;

import cl.github.erodriguezg.springbootswf.entities.Usuario;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioFiltroDto;
import cl.github.erodriguezg.springbootswf.utils.JpaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * Created by eduar on 05/07/2017.
 */
@Repository
public class UsuarioDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JpaUtils jpaUtils;

    public Usuario traerPorId(Long id) {
        return em.find(Usuario.class, id);
    }

    public Usuario traerPorUsername(String username) {
        return jpaUtils.resultForOneObject(
                em.createNamedQuery("Usuario.findByUsername", Usuario.class)
                        .setParameter("username", username)
        );
    }

    public List<Usuario> taerTodos() {
        return em.createNamedQuery("Usuario.findAll").getResultList();
    }

    public List<Usuario> traerPorFiltro(UsuarioFiltroDto filtroDto) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> parametros = this.jpaUtils.createEmptyParams();

        query.append("select distinct u from Usuario u ");
        query.append("inner join u.persona p ");
        query.append("inner join u.idPerfilUsuario perfil ");
        query.append("where 1 = 1 ");

        queryBuscarCondicionNombres(query, parametros, filtroDto);
        queryBuscarCondicionApellidoPaterno(query, parametros, filtroDto);
        queryBuscarCondicionApellidoMaterno(query, parametros, filtroDto);
        queryBuscarCondicionRut(query, parametros, filtroDto);
        queryBuscarCondicionPerfil(query, parametros, filtroDto);
        queryBuscarCondicionEmail(query, parametros, filtroDto);
        queryBuscarCondicionFechaNacimientoInferior(query, parametros, filtroDto);
        queryBuscarCondicionFechaNacimientoSuperior(query, parametros, filtroDto);
        queryBuscarCondicionHabilitado(query, parametros, filtroDto);

        return jpaUtils.queryForList(query, parametros, Usuario.class);
    }


    private void queryBuscarCondicionNombres(StringBuilder query, Map<String, Object> parametros, UsuarioFiltroDto usuarioFilterDto) {
        if (usuarioFilterDto.getNombres() != null && !usuarioFilterDto.getNombres().isEmpty()) {
            query.append("and upper(p.nombres) like upper(:nombres) ");
            parametros.put("nombres", "%" + usuarioFilterDto.getNombres() + "%");
        }
    }

    private void queryBuscarCondicionApellidoPaterno(StringBuilder query, Map<String, Object> parametros, UsuarioFiltroDto usuarioFilterDto) {
        if (usuarioFilterDto.getApPaterno() != null && !usuarioFilterDto.getApPaterno().isEmpty()) {
            query.append("and upper(p.apellidoPaterno) like upper(:apPaterno) ");
            parametros.put("apPaterno", "%" + usuarioFilterDto.getApPaterno() + "%");
        }
    }

    private void queryBuscarCondicionApellidoMaterno(StringBuilder query, Map<String, Object> parametros, UsuarioFiltroDto usuarioFilterDto) {
        if (usuarioFilterDto.getApMaterno() != null && !usuarioFilterDto.getApMaterno().isEmpty()) {
            query.append("and upper(p.apellidoMaterno) like upper(:apMaterno) ");
            parametros.put("apMaterno", "%" + usuarioFilterDto.getApMaterno() + "%");
        }
    }

    private void queryBuscarCondicionRut(StringBuilder query, Map<String, Object> parametros, UsuarioFiltroDto usuarioFilterDto) {
        if (usuarioFilterDto.getRun() != null) {
            query.append("and p.run = :run ");
            parametros.put("run", usuarioFilterDto.getRun());
        }
    }

    private void queryBuscarCondicionPerfil(StringBuilder query, Map<String, Object> parametros, UsuarioFiltroDto usuarioFilterDto) {
        if (usuarioFilterDto.getPerfilDto() != null && usuarioFilterDto.getPerfilDto().getId() != null) {
            query.append("and perfil.idPerfilUsuario = :idPerfil ");
            parametros.put("idPerfil", usuarioFilterDto.getPerfilDto().getId());
        }
    }

    private void queryBuscarCondicionEmail(StringBuilder query, Map<String, Object> parametros, UsuarioFiltroDto usuarioFilterDto) {
        if (usuarioFilterDto.getEmail() != null && !usuarioFilterDto.getEmail().isEmpty()) {
            query.append("and upper(p.email) like upper(:email) ");
            parametros.put("email", "%" + usuarioFilterDto.getEmail() + "%");
        }
    }

    private void queryBuscarCondicionFechaNacimientoInferior(StringBuilder query, Map<String, Object> parametros, UsuarioFiltroDto usuarioFilterDto) {
        if (usuarioFilterDto.getFechaNacimientoInferior() != null) {
            query.append("and p.fechanacimiento >= :fechaNacimientoInf ");
            parametros.put("fechaNacimientoInf", usuarioFilterDto.getFechaNacimientoInferior());
        }
    }

    private void queryBuscarCondicionFechaNacimientoSuperior(StringBuilder query, Map<String, Object> parametros, UsuarioFiltroDto usuarioFilterDto) {
        if (usuarioFilterDto.getFechaNacimientoSuperior() != null) {
            query.append("and p.fechanacimiento <= :fechaNacimientoSup ");
            parametros.put("fechaNacimientoSup", usuarioFilterDto.getFechaNacimientoSuperior());
        }
    }

    private void queryBuscarCondicionHabilitado(StringBuilder query, Map<String, Object> parametros, UsuarioFiltroDto usuarioFilterDto) {
        if (usuarioFilterDto.getHabilitado() != null) {
            query.append("and u.habilitado = :habilitado ");
            parametros.put("habilitado", usuarioFilterDto.getHabilitado());
        }
    }

}
