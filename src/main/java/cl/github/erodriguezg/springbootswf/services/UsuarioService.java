package cl.github.erodriguezg.springbootswf.services;


import cl.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioFiltroDto;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> traerTodos();

    UsuarioDto guardarUsuario(UsuarioDto usuario);

    UsuarioDto traerPorUsername(String username);

    UsuarioDto traerPorRut(String rutString);

    UsuarioDto traerPorRut(Integer rut);

    List<UsuarioDto> buscar(UsuarioFiltroDto filterDto);

    void eliminar(UsuarioDto usuario);

    void habilitar(UsuarioDto usuario);

    void deshabilitar(UsuarioDto usuario);

}