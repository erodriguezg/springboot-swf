package cl.github.erodriguezg.springbootswf.config;

import cl.github.erodriguezg.springbootswf.security.CustomAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

/**
 * Created by eduar on 14/05/2017.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String INICIO_URL = "/inicio.xhtml";
    private static final String LOGIN_URL = "/login.xhtml";
    private static final String LOGOUT_URL = "/logout";
    private static final String ACCESS_DENIED_URL = "/access.xthml";


    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf ().disable ()
                .formLogin ()
                    .loginPage ( LOGIN_URL ).permitAll ()
                    .usernameParameter ( "username" )
                    .passwordParameter ( "password" )
                    .loginProcessingUrl ( "/j_spring_security_check" )
                    .defaultSuccessUrl ( INICIO_URL, true )
                    .failureUrl ( LOGIN_URL + "?error=true" )
                .and()
                    .logout()
                        .logoutSuccessUrl(INICIO_URL)
                        .logoutUrl(LOGOUT_URL)
                        .invalidateHttpSession(true)
                .and()
                    .authorizeRequests()
                        .antMatchers("/ui/gestionar_usuarios/**").hasAuthority("PERFIL_SUPER_ADMINISTRADOR")
                    .anyRequest().permitAll()
                .and()
                    .exceptionHandling()
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_URL))
                        .accessDeniedPage(ACCESS_DENIED_URL)
                .and()
                    .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    private Filter customUsernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(customAuthenticationManager);
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler(INICIO_URL));
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(LOGIN_URL));
        return filter;
    }

}
