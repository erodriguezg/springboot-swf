package cl.github.erodriguezg.springbootswf.dao;

import cl.github.erodriguezg.springbootswf.entities.Usuario;
import cl.github.erodriguezg.springbootswf.utils.JpaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by eduar on 05/07/2017.
 */
@Repository
public class UsuarioDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JpaUtils jpaUtils;

    public Usuario traerPorId(Long id) {
        return em.find(Usuario.class, id);
    }

    public Usuario traerPorUsername(String username) {
        return jpaUtils.resultForOneObject(
                em.createNamedQuery("Usuario.findByUsername", Usuario.class)
                        .setParameter("username", username)
        );
    }

    public List<Usuario> taerTodos() {
        return em.createNamedQuery("Usuario.findAll").getResultList();
    }
}
