package cl.github.erodriguezg.springbootswf.config;

import com.sun.faces.config.FacesInitializer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class JsfConfig {

    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        ServletRegistrationBean servletRegistrationBean = new JsfServletRegistrationBean();
        return servletRegistrationBean;
    }

    public class JsfServletRegistrationBean extends ServletRegistrationBean {
        public JsfServletRegistrationBean() {
            super();
        }

        @Override
        public void onStartup(ServletContext servletContext)
                throws ServletException {
            FacesInitializer facesInitializer = new FacesInitializer();
            Set<Class<?>> clazz = new HashSet<Class<?>>();
            clazz.add(JsfConfig.class);
            facesInitializer.onStartup(clazz, servletContext);
        }
    }

    @Configuration
    static class ConfigureJSFContextParameters implements ServletContextInitializer {
        @Override
        public void onStartup(ServletContext servletContext)
                throws ServletException {
            servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX",
                    ".xhtml");
            servletContext.setInitParameter(
                    "javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
            servletContext.setInitParameter(
                    "javax.faces.STATE_SAVING_METHOD", "server");
            servletContext.setInitParameter(
                    "com.sun.faces.forceLoadConfiguration", "true");
            servletContext.setInitParameter("javax.faces.PROJECT_STAGE",
                    "Development");
            servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
            servletContext.setInitParameter(
                    "javax.faces.FACELETS_REFRESH_PERIOD", "1");
            servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
        }
    }
}