package cl.github.erodriguezg.springbootswf.dao;

import cl.github.erodriguezg.springbootswf.entities.PerfilUsuario;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by eduar on 05/07/2017.
 */
@Repository
public class PerfilUsuarioDao {

    @PersistenceContext
    private EntityManager em;

    public List<PerfilUsuario> traerTodos() {
        return em.createNamedQuery("PerfilUsuario.findAll", PerfilUsuario.class).getResultList();
    }

}
