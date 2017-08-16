package com.github.erodriguezg.springbootswf.services.impl;

import com.github.erodriguezg.springbootswf.dao.UsuarioDao;
import com.github.erodriguezg.springbootswf.entities.Usuario;
import com.github.erodriguezg.springbootswf.exceptions.LogicaNegocioException;
import com.github.erodriguezg.springbootswf.services.UsuarioService;
import com.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import com.github.erodriguezg.springbootswf.services.dto.UsuarioFiltroDto;
import com.github.erodriguezg.springbootswf.services.mappers.UsuarioMapper;
import com.github.erodriguezg.springbootswf.utils.ConstantesUtil;
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
        List<Usuario> usuarioList = this.usuarioDao.traerPorFiltro(usuarioFiltroDto);
        return usuarioList.stream()
                .map(this.usuarioMapper::toUsuarioDto)
                .collect(Collectors.toList());
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