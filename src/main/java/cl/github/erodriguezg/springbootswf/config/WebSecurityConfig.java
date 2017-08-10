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
                .csrf ().disable ()
                .formLogin ()
                    .loginPage ( "/ui/login" ).permitAll ()
                    .usernameParameter ( "username" )
                    .passwordParameter ( "password" )
                    .loginProcessingUrl ( "/j_spring_security_check" )
                    .defaultSuccessUrl ( "/ui/inicio", true )
                    .failureUrl ( "/ui/login?error=true" )
                .and()
                    .logout()
                        .logoutSuccessUrl("/ui/index")
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                .and()
                    .authorizeRequests()
                        .antMatchers("/ui/gestionar_usuarios/**").hasAuthority("PERFIL_SUPER_ADMINISTRADOR")
                    .anyRequest().permitAll()
                .and()
                    .exceptionHandling()
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/ui/login"))
                        .accessDeniedPage("/ui/index")
                .and()
                    .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    private Filter customUsernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(customAuthenticationManager);
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/ui/inicio"));
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/ui/login"));
        return filter;
    }

}
