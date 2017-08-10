package cl.github.erodriguezg.springbootswf.config;

import com.github.erodriguezg.jsfutils.utils.JsfUtils;
import com.github.erodriguezg.jsfutils.utils.impl.JsfUtilsImpl;
import com.sun.faces.config.FacesInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class JsfConfig {

    @Bean
    public JsfContextInitializer jsfContextInitializer() {
        return new JsfContextInitializer();
    }

    public static class JsfContextInitializer extends ServletRegistrationBean {

        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {

            servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", "true");
            servletContext.setInitParameter("org.jboss.jbossfaces.WAR_BUNDLES_JSF_IMPL", "true");
            servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
            servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
            servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
            servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
            servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
            servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING", "true");
            servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
            servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
            servletContext.setInitParameter("primefaces.THEME", "omega");
            servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/primefaces-omega.taglib.xml");
            servletContext.setInitParameter("primefaces.UPLOADER", "commons");
            servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
            servletContext.setInitParameter("facelets.DEVELOPMENT", "true");

            FacesInitializer facesInitializer = new FacesInitializer();

            Set<Class<?>> clazz = new HashSet<>();
            clazz.add(JsfConfig.class);
            facesInitializer.onStartup(clazz, servletContext);

        }
    }

    @Bean
    @Scope("request")
    public JsfUtils jsfUtils() {
        return new JsfUtilsImpl();
    }

}