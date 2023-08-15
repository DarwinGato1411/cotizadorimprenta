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
@Table(name = "estado_cotizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoCotizacion.findAll", query = "SELECT e FROM EstadoCotizacion e"),
    @NamedQuery(name = "EstadoCotizacion.findAllLike", query = "SELECT e FROM EstadoCotizacion e where e.estCotSigla LIKE :sigla"),
    @NamedQuery(name = "EstadoCotizacion.findByCodEstCotizacion", query = "SELECT e FROM EstadoCotizacion e WHERE e.codEstCotizacion = :codEstCotizacion"),
    @NamedQuery(name = "EstadoCotizacion.findByEstCotNombre", query = "SELECT e FROM EstadoCotizacion e WHERE e.estCotNombre = :estCotNombre"),
    @NamedQuery(name = "EstadoCotizacion.findByEstCotSigla", query = "SELECT e FROM EstadoCotizacion e WHERE e.estCotSigla = :estCotSigla")})
public class EstadoCotizacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_est_cotizacion")
    private Integer codEstCotizacion;
    @Size(max = 100)
    @Column(name = "est_cot_nombre")
    private String estCotNombre;
    @Size(max = 25)
    @Column(name = "est_cot_sigla")
    private String estCotSigla;
    @OneToMany(mappedBy = "codEstCotizacion")
    private Collection<Cotizacion> cotizacionCollection;

    public EstadoCotizacion() {
    }

    public EstadoCotizacion(Integer codEstCotizacion) {
        this.codEstCotizacion = codEstCotizacion;
    }

    public Integer getCodEstCotizacion() {
        return codEstCotizacion;
    }

    public void setCodEstCotizacion(Integer codEstCotizacion) {
        this.codEstCotizacion = codEstCotizacion;
    }

    public String getEstCotNombre() {
        return estCotNombre;
    }

    public void setEstCotNombre(String estCotNombre) {
        this.estCotNombre = estCotNombre;
    }

    public String getEstCotSigla() {
        return estCotSigla;
    }

    public void setEstCotSigla(String estCotSigla) {
        this.estCotSigla = estCotSigla;
    }

    @XmlTransient
    public Collection<Cotizacion> getCotizacionCollection() {
        return cotizacionCollection;
    }

    public void setCotizacionCollection(Collection<Cotizacion> cotizacionCollection) {
        this.cotizacionCollection = cotizacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstCotizacion != null ? codEstCotizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoCotizacion)) {
            return false;
        }
        EstadoCotizacion other = (EstadoCotizacion) object;
        if ((this.codEstCotizacion == null && other.codEstCotizacion != null) || (this.codEstCotizacion != null && !this.codEstCotizacion.equals(other.codEstCotizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.EstadoCotizacion[ codEstCotizacion=" + codEstCotizacion + " ]";
    }
    
}
