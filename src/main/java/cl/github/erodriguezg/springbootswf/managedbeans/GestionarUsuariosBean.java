package cl.github.erodriguezg.springbootswf.managedbeans;

import cl.github.erodriguezg.springbootswf.entities.Usuario;
import cl.github.erodriguezg.springbootswf.security.Identidad;
import cl.github.erodriguezg.springbootswf.services.UsuarioService;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioDto;
import cl.github.erodriguezg.springbootswf.services.dto.UsuarioFiltroDto;
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
    private UsuarioService usuarioService;

    @Autowired
    private Identidad identidad;

    @Autowired
    private JsfUtils jsfUtils;

    private Usuario usuarioSelected;
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

    public String irEditar(Usuario usuario) {
        LOG.debug("ir a editar");
        if (usuario == null) {
            this.usuarioSelected = null;
            return "ir_crear";
        } else {
            this.usuarioSelected = usuario;
            return "ir_editar";
        }
    }

    public void eliminar(Usuario usuario) {
       /* try {
            if (identidad.getRutPersona().equals(usuario.getRut())) {
                jsfUtils.addErrorMessage(ConstantesUtil.MSJ_ELIMINAR_A_SI_MISMO);
                return;
            }
            usuarioService.eliminar(usuario);
            buscar();
            jsfUtils.addInfoMessage(ConstantesUtil.MSJ_ELIMINAR_USUARIO);
        } catch (LogicaNegocioException ex) {
            LOG.debug("negocio exception eliminar", ex);
            jsfUtils.addErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            LOG.error("error eliminar", ex);
            jsfUtils.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
        }*/
    }

    public void habilitar(Usuario usuario) {
      /*  try {
            usuarioService.habilitar(usuario);
            usuario.setHabilitado(true);
            jsfUtils.addInfoMessage(ConstantesUtil.MSJ_HABILITAR_USUARIO);
        } catch (LogicaNegocioException ex) {
            LOG.debug("negocio exception habilitar", ex);
            jsfUtils.addErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            LOG.error("error habilitar", ex);
            jsfUtils.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
        }*/
    }

    public void deshabilitar(Usuario usuario) {
       /* try {
            if (identidad.getRutPersona().equals(usuario.getRut())) {
                jsfUtils.addErrorMessage(ConstantesUtil.MSJ_DESHABILITAR_A_SI_MISMO);
                return;
            }
            usuarioService.deshabilitar(usuario);
            usuario.setHabilitado(false);
            jsfUtils.addInfoMessage(ConstantesUtil.MSJ_DESHABILITAR_USUARIO);
        } catch (LogicaNegocioException ex) {
            LOG.debug("negocio exception deshabilitar", ex);
            jsfUtils.addErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            LOG.error("error deshabilitar", ex);
            jsfUtils.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
        }*/
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

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

}