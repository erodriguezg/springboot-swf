package com.github.erodriguezg.springbootswf.services.impl;

import com.github.erodriguezg.javautils.CodecUtils;
import com.github.erodriguezg.springbootswf.dao.UsuarioDao;
import com.github.erodriguezg.springbootswf.entities.Persona;
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

    @Autowired
    private CodecUtils codecUtils;

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
        LOG.debug("Guardar Usuario RUT: " + usuarioDto.getRut());
        Usuario usuarioAux = usuarioDao.traerPorEmail(usuarioDto.getEmail());
        if (usuarioAux != null && usuarioAux.getPersona()!= null  && !usuarioAux.getPersona().getRun().equals(usuarioDto.getRut())) {
            throw new LogicaNegocioException("Ya existe correo para el usuario");
        }
        usuarioAux = usuarioDao.traerPorRun(usuarioDto.getRut());
        if (usuarioAux == null) {
            usuarioDto.setHabilitado(true);
        }
        if (usuarioAux == null || !usuarioAux.getPassword().equals(usuarioDto.getPassword())) {
            usuarioDto.setPassword(codecUtils.generarHash(CodecUtils.TypeHash.MD5, usuarioDto.getPassword()));
        }
        Usuario usuario = usuarioMapper.toUsuario(usuarioDto);
        Persona persona = em.merge(usuario.getPersona());
        persona.setUsuario(usuario);
        usuario.setPersona(persona);
        if(usuarioAux == null) {
            em.persist(usuario);
        }else {
            usuario = em.merge(usuario);
        }
        return usuarioMapper.toUsuarioDto(usuario);
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
        Persona persona = usuario.getPersona();
        em.remove(usuario);
        em.remove(persona);

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

    @Transactional(readOnly = true)
    @Override
    public UsuarioDto traerPorRun(Integer run) {
        Usuario usuario = this.usuarioDao.traerPorRun(run);
        return usuarioMapper.toUsuarioDto(usuario);
    }

}