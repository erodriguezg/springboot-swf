package cl.github.erodriguezg.springbootswf.config;

import com.github.erodriguezg.javautils.CodecUtils;
import com.github.erodriguezg.javautils.DateUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by eduar on 15/05/2017.
 */
@EnableWebMvc
@Configuration
public class WebConfig {

    /*
    Listeners
     */

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    /*
    Utilitarios
     */

    @Bean
    public CodecUtils codecUtils() {
        return new CodecUtils();
    }

    @Bean
    public DateUtils dateUtils() {
        return new DateUtils();
    }

}
