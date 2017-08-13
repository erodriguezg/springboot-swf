package cl.github.erodriguezg.springbootswf.config;

import cl.github.erodriguezg.springbootswf.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String INICIO_URL = "/inicio.xhtml";
    private static final String LOGIN_URL = "/login.xhtml";
    private static final String LOGOUT_URL = "/logout";
    private static final String ACCESS_DENIED_URL = "/access.xthml";

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/ui/gestionar_usuarios/**").hasAuthority("PERFIL_ADMINISTRADOR")
                    .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl(INICIO_URL)
                    .logoutUrl(LOGOUT_URL)
                    .invalidateHttpSession(true)
                    .and()
                .exceptionHandling()
                    .accessDeniedPage(ACCESS_DENIED_URL)
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_URL))
                    .and()
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authProvider);
    }

    private Filter customUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setFilterProcessesUrl("/login-process");
        filter.setUsernameParameter("j_username");
        filter.setPasswordParameter("j_password");
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler(INICIO_URL));
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(LOGIN_URL));
        return filter;
    }

}
