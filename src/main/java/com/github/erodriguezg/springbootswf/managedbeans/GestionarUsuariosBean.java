package com.github.erodriguezg.springbootswf.managedbeans;

import com.github.erodriguezg.springbootswf.entities.Usuario;
import com.github.erodriguezg.springbootswf.exceptions.LogicaNegocioException;
import com.github.erodriguezg.springbootswf.security.Identidad;
import com.github.erodriguezg.springbootswf.services.UsuarioService;
import com.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import com.github.erodriguezg.springbootswf.services.dto.UsuarioFiltroDto;
import com.github.erodriguezg.springbootswf.utils.ConstantesUtil;
import com.github.erodriguezg.jsfutils.utils.JsfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.List;

public class GestionarUsuariosBean implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(GestionarUsuariosBean.class);

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient Identidad identidad;

    @Autowired
    private transient JsfUtils jsfUtils;

    private UsuarioDto usuarioSelected;
    private List<UsuarioDto> usuariosDTO;
    private UsuarioFiltroDto usuarioFilterDto;

    @PostConstruct
    public void init() {
        LOG.info("post-construct - GestionarUsuariosBean");
    }

    @PreDestroy
    public void destroy() {
        LOG.info("pre-destroy - GestionarUsuariosBean");
    }

    public void iniciar() {
        LOG.debug("iniciando flow: gestionar_usuarios!");
        this.usuarioFilterDto = new UsuarioFiltroDto();
    }

    public void buscar() {
        LOG.debug("buscando usuariosDTO");
        this.usuariosDTO = usuarioService.buscar(usuarioFilterDto);
    }

    public String irEditar(UsuarioDto usuario) {
        LOG.debug("ir a editar");
        if (usuario == null) {
            this.usuarioSelected = null;
            return "ir_crear";
        } else {
            this.usuarioSelected = usuario;
            return "ir_editar";
        }
    }

    public void eliminar(UsuarioDto usuarioDto) {
        try {
            usuarioService.eliminar(usuarioDto, identidad.getIdPersona());
            buscar();
            jsfUtils.addInfoMessage(ConstantesUtil.MSJ_ELIMINAR_USUARIO);
        } catch (LogicaNegocioException ex) {
            LOG.debug("negocio exception eliminar", ex);
            jsfUtils.addErrorMessage(ex.getMessage());
        } catch (RuntimeException ex) {
            LOG.error("error eliminar", ex);
            jsfUtils.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
        }
    }

    public void habilitar(UsuarioDto usuarioDto) {
        try {
            usuarioService.habilitar(usuarioDto);
            usuarioDto.setHabilitado(true);
            jsfUtils.addInfoMessage(ConstantesUtil.MSJ_HABILITAR_USUARIO);
        } catch (LogicaNegocioException ex) {
            LOG.debug("negocio exception habilitar", ex);
            jsfUtils.addErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            LOG.error("error habilitar", ex);
            jsfUtils.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
        }
    }

    public void deshabilitar(UsuarioDto usuarioDto) {
        try {
            usuarioService.deshabilitar(usuarioDto, identidad.getIdPersona());
            usuarioDto.setHabilitado(false);
            jsfUtils.addInfoMessage(ConstantesUtil.MSJ_DESHABILITAR_USUARIO);
        } catch (LogicaNegocioException ex) {
            LOG.debug("negocio exception deshabilitar", ex);
            jsfUtils.addErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            LOG.error("error deshabilitar", ex);
            jsfUtils.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
        }
    }

    /* getters and setters */
    public UsuarioFiltroDto getUsuarioFilterDto() {
        return usuarioFilterDto;
    }

    public void setUsuarioFilterDto(UsuarioFiltroDto usuarioFilterDto) {
        this.usuarioFilterDto = usuarioFilterDto;
    }

    public List<UsuarioDto> getUsuariosDTO() {
        return usuariosDTO;
    }

    public void setUsuariosDTO(List<UsuarioDto> usuariosDTO) {
        this.usuariosDTO = usuariosDTO;
    }

    public UsuarioDto getUsuarioSelected() {
        return usuarioSelected;
    }
}
