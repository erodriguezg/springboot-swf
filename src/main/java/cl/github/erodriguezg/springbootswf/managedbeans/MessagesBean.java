package cl.github.erodriguezg.springbootswf.managedbeans;

import com.github.erodriguezg.jsfutils.utils.JsfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;

@Component
@Scope("request")
public class MessagesBean {

    @Autowired
    private JsfUtils jsfUtils;

    public boolean existenMensajesGlobales() {
        FacesContext fc = jsfUtils.getFacesContextCurrentInstance();
        return (fc.getMessages(null) != null) && (fc.getMessages(null).hasNext());
    }
}
