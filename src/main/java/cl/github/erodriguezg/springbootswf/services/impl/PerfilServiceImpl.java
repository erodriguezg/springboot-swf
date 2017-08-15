package cl.github.erodriguezg.springbootswf.services.impl;

import cl.github.erodriguezg.springbootswf.dao.PerfilUsuarioDao;
import cl.github.erodriguezg.springbootswf.services.PerfilService;
import cl.github.erodriguezg.springbootswf.services.dto.PerfilDto;
import cl.github.erodriguezg.springbootswf.services.mappers.PerfilUsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    private PerfilUsuarioDao perfilUsuarioDao;

    @Autowired
    private PerfilUsuarioMapper perfilUsuarioMapper;

    @Transactional(readOnly = true)
    @Override
    public List<PerfilDto> traerTodos() {
        return perfilUsuarioDao.traerTodos().stream()
                .map(perfilUsuarioMapper::toPerfilDto)
                .collect(Collectors.toList());
    }

}
