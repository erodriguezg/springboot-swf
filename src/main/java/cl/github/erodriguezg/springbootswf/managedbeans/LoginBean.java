package cl.github.erodriguezg.springbootswf.managedbeans;

import com.github.erodriguezg.jsfutils.utils.JsfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Component
@Scope("view")
public class LoginBean implements Serializable {

    @Autowired
    private JsfUtils jsfUtils;

    private Integer rut;

    private String password;

    public void verificarErroresLogin() {
        if (jsfUtils.getFacesContextCurrentInstance().isPostback()) {
            return;
        }
        Exception e = (Exception) jsfUtils.getSessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (e instanceof AuthenticationException) {
            jsfUtils.setSessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            jsfUtils.addErrorMessage(e.getMessage());
        }
    }

    public String login() throws Exception {
        FacesContext facesContext = jsfUtils.getFacesContextCurrentInstance();
        HttpServletRequest httpRequest = jsfUtils.obtenerHttpServletRequest();
        HttpServletResponse httpResponse = jsfUtils.obtenerHttpServletResponse();
        RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward(httpRequest, httpResponse);
        facesContext.responseComplete();
        return null;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
