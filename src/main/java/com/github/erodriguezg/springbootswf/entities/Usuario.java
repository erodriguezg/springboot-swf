package com.github.erodriguezg.springbootswf.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author eduar
 */
@Entity
@Table(name = "usuario")
@NamedQueries({
        @NamedQuery(name = "Usuario.findByRun", query = "select u from Usuario u inner join u.persona p where p.run = :run "),
        @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
        , @NamedQuery(name = "Usuario.findByIdPersona", query = "SELECT u FROM Usuario u WHERE u.idPersona = :idPersona")
        , @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username")
        , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 7439126925283605347L;

    @Id
    @Column(name = "id_persona")
    private Long idPersona;

    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @JoinColumn(name = "id_perfil_usuario", referencedColumnName = "id_perfil_usuario")
    @ManyToOne(optional = false)
    private PerfilUsuario idPerfilUsuario;

    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    @Column(name = "habilitado")
    private Boolean habilitado;

    public Usuario() {
        //empty
    }

    public Usuario(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Usuario(Long idPersona, String username) {
        this.idPersona = idPersona;
        this.username = username;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PerfilUsuario getIdPerfilUsuario() {
        return idPerfilUsuario;
    }

    public void setIdPerfilUsuario(PerfilUsuario idPerfilUsuario) {
        this.idPerfilUsuario = idPerfilUsuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(idPersona, usuario.idPersona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersona);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idPersona=" + idPersona +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", idPerfilUsuario=" + idPerfilUsuario +
                ", persona=" + persona +
                '}';
    }
}
