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
@Table(name = "estado_orden_produccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoOrdenProduccion.findAll", query = "SELECT e FROM EstadoOrdenProduccion e"),
    @NamedQuery(name = "EstadoOrdenProduccion.findAllLikeSigla", query = "SELECT e FROM EstadoOrdenProduccion e where e.estOrdSigla LIKE :sigla"),
    @NamedQuery(name = "EstadoOrdenProduccion.findByCodEstadoOrden", query = "SELECT e FROM EstadoOrdenProduccion e WHERE e.codEstadoOrden = :codEstadoOrden"),
    @NamedQuery(name = "EstadoOrdenProduccion.findByEstOrdNombre", query = "SELECT e FROM EstadoOrdenProduccion e WHERE e.estOrdNombre = :estOrdNombre"),
    @NamedQuery(name = "EstadoOrdenProduccion.findByEstOrdSigla", query = "SELECT e FROM EstadoOrdenProduccion e WHERE e.estOrdSigla = :estOrdSigla")})
public class EstadoOrdenProduccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_estado_orden")
    private Integer codEstadoOrden;
    @Size(max = 50)
    @Column(name = "est_ord_nombre")
    private String estOrdNombre;
    @Size(max = 25)
    @Column(name = "est_ord_sigla")
    private String estOrdSigla;
    @OneToMany(mappedBy = "codEstadoOrden")
    private Collection<OrdenDeProduccion> ordenDeProduccionCollection;

    public EstadoOrdenProduccion() {
    }

    public EstadoOrdenProduccion(Integer codEstadoOrden) {
        this.codEstadoOrden = codEstadoOrden;
    }

    public Integer getCodEstadoOrden() {
        return codEstadoOrden;
    }

    public void setCodEstadoOrden(Integer codEstadoOrden) {
        this.codEstadoOrden = codEstadoOrden;
    }

    public String getEstOrdNombre() {
        return estOrdNombre;
    }

    public void setEstOrdNombre(String estOrdNombre) {
        this.estOrdNombre = estOrdNombre;
    }

    public String getEstOrdSigla() {
        return estOrdSigla;
    }

    public void setEstOrdSigla(String estOrdSigla) {
        this.estOrdSigla = estOrdSigla;
    }

    @XmlTransient
    public Collection<OrdenDeProduccion> getOrdenDeProduccionCollection() {
        return ordenDeProduccionCollection;
    }

    public void setOrdenDeProduccionCollection(Collection<OrdenDeProduccion> ordenDeProduccionCollection) {
        this.ordenDeProduccionCollection = ordenDeProduccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstadoOrden != null ? codEstadoOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoOrdenProduccion)) {
            return false;
        }
        EstadoOrdenProduccion other = (EstadoOrdenProduccion) object;
        if ((this.codEstadoOrden == null && other.codEstadoOrden != null) || (this.codEstadoOrden != null && !this.codEstadoOrden.equals(other.codEstadoOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.EstadoOrdenProduccion[ codEstadoOrden=" + codEstadoOrden + " ]";
    }
    
}
