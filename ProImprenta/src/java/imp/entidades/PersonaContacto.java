/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "persona_contacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonaContacto.findAll", query = "SELECT p FROM PersonaContacto p"),
    @NamedQuery(name = "PersonaContacto.findAllCliente", query = "SELECT p FROM PersonaContacto p where p.codCliente=:codCliente"),
    @NamedQuery(name = "PersonaContacto.findByCodPerContacto", query = "SELECT p FROM PersonaContacto p WHERE p.codPerContacto = :codPerContacto"),
    @NamedQuery(name = "PersonaContacto.findByPerConNombre", query = "SELECT p FROM PersonaContacto p WHERE p.perConNombre = :perConNombre"),
    @NamedQuery(name = "PersonaContacto.findByPerConTelefono", query = "SELECT p FROM PersonaContacto p WHERE p.perConTelefono = :perConTelefono"),
    @NamedQuery(name = "PersonaContacto.findByPerConCelular", query = "SELECT p FROM PersonaContacto p WHERE p.perConCelular = :perConCelular"),
    @NamedQuery(name = "PersonaContacto.findByPerConDireccion", query = "SELECT p FROM PersonaContacto p WHERE p.perConDireccion = :perConDireccion"),
    @NamedQuery(name = "PersonaContacto.findByPerConCorreo", query = "SELECT p FROM PersonaContacto p WHERE p.perConCorreo = :perConCorreo")})
public class PersonaContacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_per_contacto")
    private Integer codPerContacto;
    @Size(max = 100)
    @Column(name = "per_con_nombre")
    private String perConNombre;
    @Size(max = 20)
    @Column(name = "per_con_telefono")
    private String perConTelefono;
    @Size(max = 20)
    @Column(name = "per_con_celular")
    private String perConCelular;
    @Size(max = 150)
    @Column(name = "per_con_direccion")
    private String perConDireccion;
    @Size(max = 150)
    @Column(name = "per_con_correo")
    private String perConCorreo;
    @JoinColumn(name = "cod_cliente", referencedColumnName = "cod_cliente")
    @ManyToOne
    private Cliente codCliente;

    public PersonaContacto() {
    }

    public PersonaContacto(Integer codPerContacto) {
        this.codPerContacto = codPerContacto;
    }

    public Integer getCodPerContacto() {
        return codPerContacto;
    }

    public void setCodPerContacto(Integer codPerContacto) {
        this.codPerContacto = codPerContacto;
    }

    public String getPerConNombre() {
        return perConNombre;
    }

    public void setPerConNombre(String perConNombre) {
        this.perConNombre = perConNombre;
    }

    public String getPerConTelefono() {
        return perConTelefono;
    }

    public void setPerConTelefono(String perConTelefono) {
        this.perConTelefono = perConTelefono;
    }

    public String getPerConCelular() {
        return perConCelular;
    }

    public void setPerConCelular(String perConCelular) {
        this.perConCelular = perConCelular;
    }

    public String getPerConDireccion() {
        return perConDireccion;
    }

    public void setPerConDireccion(String perConDireccion) {
        this.perConDireccion = perConDireccion;
    }

    public String getPerConCorreo() {
        return perConCorreo;
    }

    public void setPerConCorreo(String perConCorreo) {
        this.perConCorreo = perConCorreo;
    }

    public Cliente getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Cliente codCliente) {
        this.codCliente = codCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPerContacto != null ? codPerContacto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaContacto)) {
            return false;
        }
        PersonaContacto other = (PersonaContacto) object;
        if ((this.codPerContacto == null && other.codPerContacto != null) || (this.codPerContacto != null && !this.codPerContacto.equals(other.codPerContacto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.PersonaContacto[ codPerContacto=" + codPerContacto + " ]";
    }
    
}
