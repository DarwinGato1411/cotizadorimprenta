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
@Table(name = "tipo_operacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoOperacion.findAll", query = "SELECT t FROM TipoOperacion t"),
    @NamedQuery(name = "TipoOperacion.findByCodTipoOperacion", query = "SELECT t FROM TipoOperacion t WHERE t.codTipoOperacion = :codTipoOperacion"),
    @NamedQuery(name = "TipoOperacion.findByTipOperacion", query = "SELECT t FROM TipoOperacion t WHERE t.tipOperacion = :tipOperacion")})
public class TipoOperacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tipo_operacion")
    private Integer codTipoOperacion;
    @Size(max = 50)
    @Column(name = "tip_operacion")
    private String tipOperacion;
    @OneToMany(mappedBy = "codTipoOperacion")
    private Collection<Operacion> operacionCollection;

    public TipoOperacion() {
    }

    public TipoOperacion(Integer codTipoOperacion) {
        this.codTipoOperacion = codTipoOperacion;
    }

    public Integer getCodTipoOperacion() {
        return codTipoOperacion;
    }

    public void setCodTipoOperacion(Integer codTipoOperacion) {
        this.codTipoOperacion = codTipoOperacion;
    }

    public String getTipOperacion() {
        return tipOperacion;
    }

    public void setTipOperacion(String tipOperacion) {
        this.tipOperacion = tipOperacion;
    }

    @XmlTransient
    public Collection<Operacion> getOperacionCollection() {
        return operacionCollection;
    }

    public void setOperacionCollection(Collection<Operacion> operacionCollection) {
        this.operacionCollection = operacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTipoOperacion != null ? codTipoOperacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoOperacion)) {
            return false;
        }
        TipoOperacion other = (TipoOperacion) object;
        if ((this.codTipoOperacion == null && other.codTipoOperacion != null) || (this.codTipoOperacion != null && !this.codTipoOperacion.equals(other.codTipoOperacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidades.TipoOperacion[ codTipoOperacion=" + codTipoOperacion + " ]";
    }
}
