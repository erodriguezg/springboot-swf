package cl.github.erodriguezg.springbootswf.services.impl;

import cl.github.erodriguezg.springbootswf.dao.UsuarioDao;
import cl.github.erodriguezg.springbootswf.entities.Usuario;
import cl.github.erodriguezg.springbootswf.exceptions.LogicaNegocioException;
import cl.github.erodriguezg.springbootswf.services.UsuarioService;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioFiltroDto;
import cl.github.erodriguezg.springbootswf.services.mappers.UsuarioMapper;
import cl.github.erodriguezg.springbootswf.utils.ConstantesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Transactional(readOnly = true)
    @Override
    public List<UsuarioDto> traerTodos() {
        return this.usuarioDao.taerTodos().stream()
                .map(this.usuarioMapper::toUsuarioDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    @Override
    public UsuarioDto guardarUsuario(UsuarioDto usuarioDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UsuarioDto traerPorUsername(String username) {
        Usuario usuario = usuarioDao.traerPorUsername(username);
        return usuarioMapper.toUsuarioDto(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UsuarioDto> buscar(UsuarioFiltroDto usuarioFiltroDto) {
        return  this.traerTodos();
    }

    @Transactional(readOnly = false)
    @Override
    public void eliminar(UsuarioDto usuarioDto, Long idUsuarioActual) {
        if (idUsuarioActual.equals(usuarioDto.getId())) {
            throw new LogicaNegocioException(ConstantesUtil.MSJ_ELIMINAR_A_SI_MISMO);
        }
        Usuario usuario = this.usuarioDao.traerPorId(usuarioDto.getId());
        em.remove(usuario);
    }

    @Transactional(readOnly = false)
    @Override
    public void habilitar(UsuarioDto usuarioDto) {
        Usuario usuario = this.usuarioDao.traerPorId(usuarioDto.getId());
        usuario.setHabilitado(true);
    }

    @Transactional(readOnly = false)
    @Override
    public void deshabilitar(UsuarioDto usuarioDto, Long idUsuarioActual) {
        if (idUsuarioActual.equals(usuarioDto.getId())) {
            throw new LogicaNegocioException(ConstantesUtil.MSJ_DESHABILITAR_A_SI_MISMO);
        }
        Usuario usuario = this.usuarioDao.traerPorId(usuarioDto.getId());
        usuario.setHabilitado(false);
    }

}