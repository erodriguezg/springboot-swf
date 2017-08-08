package cl.github.erodriguezg.springbootswf.security;

import cl.github.erodriguezg.springbootswf.services.UsuarioService;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import cl.github.erodriguezg.springbootswf.utils.ConstantesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationManager.class);

    @Autowired
    private UsuarioService usuarioService;

    private final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    @Override
    public Authentication authenticate(Authentication auth) {
        UsuarioDto usuario;

        usuario = usuarioService.traerPorUsername(auth.getName());
        if (usuario == null) {
            LOG.debug("Usuario no existe!");
            throw new BadCredentialsException(ConstantesUtil.MSJ_ERROR_CREDENCIALES_INVALIDAS);
        }

        if (!usuario.getHabilitado()) {
            LOG.debug("Usuario no habilitado");
            throw new DisabledException(ConstantesUtil.MSJ_ERROR_USUARIO_NO_HABILITADO);
        }

        if (!passwordEncoder.isPasswordValid(usuario.getPassword(), (String) auth.getCredentials(), null)) {
            LOG.debug("Password incorrecto!");
            throw new BadCredentialsException(ConstantesUtil.MSJ_ERROR_CREDENCIALES_INVALIDAS);
        }

        return new UsernamePasswordAuthenticationToken(
                usuario,
                auth.getCredentials(),
                getAuthorities(usuario));
    }

    private List<? extends GrantedAuthority> getAuthorities(UsuarioDto usuarioDto) {

        List<GrantedAuthority> rolesList = new ArrayList<>();

        if (usuarioDto.getPerfil() == null) {
            throw new DisabledException(ConstantesUtil.MSJ_ERROR_CREDENCIALES_INVALIDAS);
        }

        switch (usuarioDto.getPerfil().getId()) {
            case ConstantesUtil.ID_PERFIL_SUPER_ADMINISTRADOR:
                rolesList.add(new SimpleGrantedAuthority("PERFIL_SUPER_ADMINISTRADOR"));
                break;
            case ConstantesUtil.ID_PERFIL_ADMINISTRADOR:
                rolesList.add(new SimpleGrantedAuthority("PERFIL_ADMINISTRADOR"));
                break;
            case ConstantesUtil.ID_PERFIL_USUARIO:
                rolesList.add(new SimpleGrantedAuthority("PERFIL_USUARIO"));
                break;
            default:
                throw new DisabledException(ConstantesUtil.MSJ_ERROR_CREDENCIALES_INVALIDAS);
        }

        return rolesList;
    }

}
