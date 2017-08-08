package cl.github.erodriguezg.springbootswf.services.impl;

import cl.github.erodriguezg.springbootswf.services.UsuarioService;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioFiltroDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @PersistenceContext
    private EntityManager em;


    @Transactional(readOnly = true)
    @Override
    public List<UsuarioDto> traerTodos() {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = false)
    @Override
    public UsuarioDto guardarUsuario(UsuarioDto usuarioDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UsuarioDto traerPorUsername(String username) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public UsuarioDto traerPorRut(String rutString) {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = true)
    @Override
    public UsuarioDto traerPorRut(Integer rut) {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = true)
    @Override
    public List<UsuarioDto> buscar(UsuarioFiltroDto usuarioFiltroDto) {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = false)
    @Override
    public void eliminar(UsuarioDto usuarioDto) {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = false)
    @Override
    public void habilitar(UsuarioDto usuarioDto) {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = false)
    @Override
    public void deshabilitar(UsuarioDto usuarioDto) {
        throw new UnsupportedOperationException();
    }

}