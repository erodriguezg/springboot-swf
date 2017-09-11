package com.github.erodriguezg.springbootswf.managedbeans;

import com.github.erodriguezg.beanvalidationutils.annotations.Password;
import com.github.erodriguezg.jsfutils.utils.JsfUtils;
import com.github.erodriguezg.springbootswf.exceptions.LogicaNegocioException;
import com.github.erodriguezg.springbootswf.services.UsuarioService;
import com.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import com.github.erodriguezg.springbootswf.utils.ConstantesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;

/**
 * @author takeda
 */
public class EditarUsuarioBean implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(EditarUsuarioBean.class);

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient JsfUtils jsfUtils;

    private UsuarioDto usuarioDto;

    @Password
    private String password;
    private String passwordConfirmacion;
    private boolean modoEditar;
    private Integer rutInicial;

    @PostConstruct
    public void init() {
        LOG.info("post-construct - EditarUsuarioBean");
    }

    @PreDestroy
    public void destroy() {
        LOG.info("pre-destroy - EditarUsuarioBean");
    }

    public void iniciar(UsuarioDto usuarioDto) {
        if (usuarioDto == null) {
            modoEditar = false;
            this.usuarioDto = new UsuarioDto();
            this.rutInicial = null;
        } else {
            modoEditar = true;
            this.usuarioDto = usuarioDto;
            this.rutInicial = usuarioDto.getRut();
        }
    }

    public String guardar() {
        try {
            if (!confirmacionPasswordValida()) {
                jsfUtils.addErrorMessage("formUsuario:password", "La contraseña no corresponde a la confirmación");
                return null;
            }
            usuarioDto.setPassword(password);
            usuarioService.guardarUsuario(this.usuarioDto);
            jsfUtils.addInfoMessage(ConstantesUtil.MSJ_EXITO);
            return "exito";
        } catch (LogicaNegocioException ex) {
            LOG.debug(ex.getMessage(), ex);
            jsfUtils.addErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            LOG.error("error guardar", ex);
            jsfUtils.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
        }
        usuarioDto.setPassword(null);
        return null;
    }

    public void validarRut(FacesContext facesContext, UIComponent component, Object value) {
        if (facesContext == null || component == null || value == null) {
            return;
        }
        Integer rutAValidar = (Integer) value;
        UsuarioDto usuarioDtoExistente = usuarioService.traerPorRun(rutAValidar);
        if (usuarioDtoExistente != null && usuarioDtoExistente.getRut().equals(rutAValidar) && !rutAValidar.equals(this.rutInicial)) {
            FacesMessage msg = jsfUtils.createErrorMessage("El RUN se encuentra ocupado");
            throw new ValidatorException(msg);
        }
    }

    public boolean confirmacionPasswordValida() {
        if (password == null) {
            return true;
        }
        return password.equals(passwordConfirmacion);
    }

    //getters and Setters

    public UsuarioDto getUsuarioDto() {
        return usuarioDto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmacion() {
        return passwordConfirmacion;
    }

    public void setPasswordConfirmacion(String passwordConfirmacion) {
        this.passwordConfirmacion = passwordConfirmacion;
    }

    public boolean isModoEditar() {
        return modoEditar;
    }

    public Integer getRutInicial() {
        return rutInicial;
    }

}
