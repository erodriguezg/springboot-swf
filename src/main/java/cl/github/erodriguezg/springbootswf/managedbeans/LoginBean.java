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

    private String username;

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
        RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/login-process");
        dispatcher.forward(httpRequest, httpResponse);
        facesContext.responseComplete();
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
