/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.github.erodriguezg.springbootswf.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author eduar
 */
@Entity
@Table(name = "persona")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")
    , @NamedQuery(name = "Persona.findByIdPersona", query = "SELECT p FROM Persona p WHERE p.idPersona = :idPersona")
    , @NamedQuery(name = "Persona.findByRun", query = "SELECT p FROM Persona p WHERE p.run = :run")
    , @NamedQuery(name = "Persona.findByNombres", query = "SELECT p FROM Persona p WHERE p.nombres = :nombres")
    , @NamedQuery(name = "Persona.findByApellidopaterno", query = "SELECT p FROM Persona p WHERE p.apellidopaterno = :apellidopaterno")
    , @NamedQuery(name = "Persona.findByApellidomaterno", query = "SELECT p FROM Persona p WHERE p.apellidomaterno = :apellidomaterno")
    , @NamedQuery(name = "Persona.findByFechanacimiento", query = "SELECT p FROM Persona p WHERE p.fechanacimiento = :fechanacimiento")
    , @NamedQuery(name = "Persona.findByTelefono", query = "SELECT p FROM Persona p WHERE p.telefono = :telefono")
    , @NamedQuery(name = "Persona.findByEmail", query = "SELECT p FROM Persona p WHERE p.email = :email")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona")
    private Long idPersona;
    @Column(name = "run")
    private Integer run;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidopaterno")
    private String apellidopaterno;
    @Column(name = "apellidomaterno")
    private String apellidomaterno;
    @Column(name = "fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;

    public Persona() {
    }

    public Persona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Persona(Long idPersona, String nombres, String apellidopaterno, String email) {
        this.idPersona = idPersona;
        this.nombres = nombres;
        this.apellidopaterno = apellidopaterno;
        this.email = email;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getRun() {
        return run;
    }

    public void setRun(Integer run) {
        this.run = run;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    public String getNombreCompleto() {
        return nombres + " " + apellidopaterno + " " + apellidomaterno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(idPersona, persona.idPersona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersona);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", run=" + run +
                ", nombres='" + nombres + '\'' +
                ", apellidopaterno='" + apellidopaterno + '\'' +
                ", apellidomaterno='" + apellidomaterno + '\'' +
                ", fechanacimiento=" + fechanacimiento +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
