/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "ubicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubicacion.findAll", query = "SELECT u FROM Ubicacion u"),
    @NamedQuery(name = "Ubicacion.findByCodUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.codUbicacion = :codUbicacion"),
    @NamedQuery(name = "Ubicacion.findByUbiNombre", query = "SELECT u FROM Ubicacion u WHERE u.ubiNombre = :ubiNombre")})
public class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_ubicacion")
    private Integer codUbicacion;
    @Size(max = 50)
    @Column(name = "ubi_nombre")
    private String ubiNombre;
    @OneToMany(mappedBy = "codUbicacion")
    private Collection<Producto> productoCollection;
    @OneToMany(mappedBy = "codUbicacion")
    private Collection<Operacion> operacionCollection;

    public Ubicacion() {
    }

    public Ubicacion(Integer codUbicacion) {
        this.codUbicacion = codUbicacion;
    }

    public Integer getCodUbicacion() {
        return codUbicacion;
    }

    public void setCodUbicacion(Integer codUbicacion) {
        this.codUbicacion = codUbicacion;
    }

    public String getUbiNombre() {
        return ubiNombre;
    }

    public void setUbiNombre(String ubiNombre) {
        this.ubiNombre = ubiNombre;
    }

    @XmlTransient
    public Collection<Operacion> getOperacionCollection() {
        return operacionCollection;
    }

    public void setOperacionCollection(Collection<Operacion> operacionCollection) {
        this.operacionCollection = operacionCollection;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUbicacion != null ? codUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ubicacion)) {
            return false;
        }
        Ubicacion other = (Ubicacion) object;
        if ((this.codUbicacion == null && other.codUbicacion != null) || (this.codUbicacion != null && !this.codUbicacion.equals(other.codUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidades.Ubicacion[ codUbicacion=" + codUbicacion + " ]";
    }
}
