package com.github.erodriguezg.springbootswf.config;

import com.github.erodriguezg.springbootswf.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
    private static final String ACCESS_DENIED_URL = "/access.xhtml";

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                .authorizeRequests()
                    .antMatchers("/ui/gestionar_usuarios/**").hasAuthority("PERFIL_ADMINISTRADOR")
                    .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .and()
                .logout()
                    .logoutSuccessUrl(INICIO_URL)
                    .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
                    .invalidateHttpSession(true)
                    .and()
                .exceptionHandling()
                    .accessDeniedPage(ACCESS_DENIED_URL)
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_URL))
                    .and()
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authProvider);
    }

    private Filter customUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setFilterProcessesUrl("/login-process");
        filter.setUsernameParameter("j_username");
        filter.setPasswordParameter("j_password");
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(LOGIN_URL));
        return filter;
    }

}
