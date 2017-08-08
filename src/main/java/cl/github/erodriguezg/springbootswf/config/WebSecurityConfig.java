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

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/app/gestionar_usuarios/**").hasAuthority("PERFIL_SUPER_ADMINISTRADOR")
                .anyRequest().permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/inicio.xhtml")
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login.xhtml"))
                .accessDeniedPage("/inicio.xhtml")
                .and()
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    private Filter customUsernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(customAuthenticationManager);
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/inicio.xhtml"));
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login.xhtml"));
        return filter;
    }

}
