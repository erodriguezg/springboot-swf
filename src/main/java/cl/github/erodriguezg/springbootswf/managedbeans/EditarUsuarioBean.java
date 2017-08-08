package cl.github.erodriguezg.springbootswf.managedbeans;

import cl.github.erodriguezg.springbootswf.entities.Usuario;
import cl.github.erodriguezg.springbootswf.exceptions.LogicaNegocioException;
import cl.github.erodriguezg.springbootswf.services.UsuarioService;
import cl.github.erodriguezg.springbootswf.utils.ConstantesUtil;
import com.github.erodriguezg.beanvalidationutils.annotations.Password;
import com.github.erodriguezg.jsfutils.utils.JsfUtils;
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
    private UsuarioService usuarioService;

    @Autowired
    private JsfUtils jsfUtils;

    private Usuario usuario;

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

    public void iniciar(Usuario usuario) {
        if (usuario == null) {
            modoEditar = false;
            this.usuario = new Usuario();
            this.rutInicial = null;
        } else {
            modoEditar = true;
            this.usuario = usuario;
            //this.rutInicial = usuario.getRut();
        }
    }

    public String guardar() {
        try {
            if (!confirmacionPasswordValida()) {
                jsfUtils.addErrorMessage("formUsuario:password", "La contraseña no corresponde a la confirmación");
                return null;
            }
            usuario.setPassword(password);
            //usuarioService.guardarUsuario(this.usuario);
            jsfUtils.addInfoMessage(ConstantesUtil.MSJ_EXITO);
            return "exito";
        } catch (LogicaNegocioException ex) {
            LOG.debug(ex.getMessage(), ex);
            jsfUtils.addErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            LOG.error("error guardar", ex);
            jsfUtils.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
        }
        usuario.setPassword(null);
        return null;
    }

    public void validarRut(FacesContext facesContext, UIComponent component, Object value) {
   /*     if (value == null) {
            return;
        }
        Integer rutAValidar = (Integer) value;
        Usuario usuarioExistente = usuarioService.traerPorRutConPerfil(rutAValidar);
        if (usuarioExistente != null && usuarioExistente.getRut().equals(rutAValidar) && !rutAValidar.equals(this.rutInicial)) {
            FacesMessage msg = jsfUtils.createErrorMessage("El RUT se encuentra ocupado");
            throw new ValidatorException(msg);
        }*/
    }

    public boolean confirmacionPasswordValida() {
        if (password == null) {
            return true;
        }
        if (password.equals(passwordConfirmacion)) {
            return true;
        }
        return false;
    }

    //getters and Setters
    public Usuario getUsuario() {
        return usuario;
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
