package com.github.erodriguezg.springbootswf.config;

import com.github.erodriguezg.jsfutils.converters.RutConverter;
import com.github.erodriguezg.jsfutils.converters.jsonconverter.JpaConverterExclusionStrategy;
import com.github.erodriguezg.jsfutils.converters.jsonconverter.JsonConverter;
import com.github.erodriguezg.jsfutils.utils.JsfUtils;
import com.github.erodriguezg.jsfutils.utils.PrimefacesUtils;
import com.github.erodriguezg.jsfutils.utils.impl.JsfUtilsImpl;
import com.github.erodriguezg.jsfutils.utils.impl.PrimefacesUtilsImpl;
import com.github.erodriguezg.springbootswf.utils.ConstantesUtil;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.faces.webapp.FacesServlet;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class JsfConfig {

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.xhtml");
        servletRegistrationBean.setMultipartConfig(new MultipartConfigElement(null, 500000000, 500000000, 1048576));
        return servletRegistrationBean;
    }

    @Configuration
    static class CommonsConfigureJSFContextParameters implements ServletContextInitializer {
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", "true");
            servletContext.setInitParameter("org.jboss.jbossfaces.WAR_BUNDLES_JSF_IMPL", "true");
            servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
            servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
            servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
            servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING", "true");
            servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
            servletContext.setInitParameter("primefaces.THEME", "omega");
            servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/primefaces-omega.taglib.xml");
            servletContext.setInitParameter("primefaces.UPLOADER", "commons");
            servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
        }
    }

    @Configuration
    @Profile(ConstantesUtil.SPRING_BOOT_PROFILE_NAME_DEVELOPMENT)
    static class DevelopmentConfigureJSFContextParameters implements ServletContextInitializer {
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
            servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
            servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
        }
    }

    @Configuration
    @Profile(ConstantesUtil.SPRING_BOOT_PROFILE_NAME_PRODUCTION)
    static class ProductionConfigureJSFContextParameters implements ServletContextInitializer {
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Production");
            servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "-1");
            servletContext.setInitParameter("facelets.DEVELOPMENT", "false");
        }
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public JsfUtils jsfUtils() {
        return new JsfUtilsImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public PrimefacesUtils primefacesUtils() {
        return new PrimefacesUtilsImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RutConverter rutConverter() {
        return new RutConverter();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public JsonConverter jsonConverter() {
        JsonConverter jsonConverter = new JsonConverter();
        jsonConverter.setExclusionStrategy(new JpaConverterExclusionStrategy());
        return jsonConverter;
    }

}