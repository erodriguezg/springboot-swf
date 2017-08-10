package cl.github.erodriguezg.springbootswf.security;

/**
 * Created by eduar on 16/07/2017.
 */
public interface Identidad {

    Long getIdPersona();

    boolean isLoggedIn();

    String getNombrePersona();

}
