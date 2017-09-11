package com.github.erodriguezg.springbootswf.managedbeans;

import com.github.erodriguezg.jsfutils.utils.JsfUtils;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author eduardo
 */
@Component
@Scope("view")
public class ShowcaseBean implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(ShowcaseBean.class);

    @Autowired
    private transient JsfUtils jsfUtils;

    private List<String> lista1;
    private DualListModel<String> cities;
    private DualListModel<String> cities2;
    private Date fechaUno;
    private Date fechaDos;

    @PostConstruct
    public void iniciar() {

        String[] arrayAux = new String[500];

        for (int i = 0; i < arrayAux.length; i++) {
            arrayAux[i] = "posicion i " + i;
        }

        lista1 = Arrays.asList(arrayAux);

        //Cities  
        List<String> citiesSource = new ArrayList<>();
        List<String> citiesTarget = new ArrayList<>();

        citiesSource.add("Istanbul");
        citiesSource.add("Ankara");
        citiesSource.add("Izmir");
        citiesSource.add("Antalya");
        citiesSource.add("Bursa");

        cities = new DualListModel<>(citiesSource, citiesTarget);

        List<String> citiesSource2 = new ArrayList<>();
        List<String> citiesTarget2 = new ArrayList<>();

        citiesSource2.add("Istanbul");
        citiesSource2.add("Ankara");
        citiesSource2.add("Izmir");
        citiesSource2.add("Antalya");
        citiesSource2.add("Bursa");

        cities2 = new DualListModel<>(citiesSource2, citiesTarget2);
    }

    public void lanzarMensajeErrorGlobal() {
        jsfUtils.addErrorMessage("Mensaje de Prueba!");
    }

    public void lanzarMensajeInfoGlobal() {
        jsfUtils.addInfoMessage("Mensaje de Prueba!");
    }

    public void procesando() {
        try {
            Thread.sleep(5000);
        } catch (Exception ex) {
            log.error("error procesando: ", ex);
        }
    }

    /* getters and setters */
    public List<String> getLista1() {
        return lista1;
    }

    public void setLista1(List<String> lista1) {
        this.lista1 = lista1;
    }

    public DualListModel<String> getCities() {
        return cities;
    }

    public void setCities(DualListModel<String> cities) {
        this.cities = cities;
    }

    public DualListModel<String> getCities2() {
        return cities2;
    }

    public void setCities2(DualListModel<String> cities2) {
        this.cities2 = cities2;
    }

    public Date getFechaUno() {
        return fechaUno;
    }

    public void setFechaUno(Date fechaUno) {
        this.fechaUno = fechaUno;
    }

    public Date getFechaDos() {
        return fechaDos;
    }

    public void setFechaDos(Date fechaDos) {
        this.fechaDos = fechaDos;
    }

}
