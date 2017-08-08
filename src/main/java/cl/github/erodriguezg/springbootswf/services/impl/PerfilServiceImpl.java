package cl.github.erodriguezg.springbootswf.services.impl;

import cl.github.erodriguezg.springbootswf.services.PerfilService;
import cl.github.erodriguezg.springbootswf.services.dto.PerfilDto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class PerfilServiceImpl implements PerfilService {

    @Transactional(readOnly = true)
    @Override
    public List<PerfilDto> traerTodos() {
        return null;
    }

}
