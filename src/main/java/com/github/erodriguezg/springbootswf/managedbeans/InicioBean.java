package com.github.erodriguezg.springbootswf.managedbeans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("view")
public class InicioBean {

    private String saludo;

    @PostConstruct
    public void iniciar() {
        saludo = "Hola Mundo !!!";
    }
    
    /*getters and setters*/
    public String getSaludo() {
        return saludo;
    }
    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }
}
