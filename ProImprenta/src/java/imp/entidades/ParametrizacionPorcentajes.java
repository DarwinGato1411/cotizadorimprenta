/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "parametrizacion_porcentajes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametrizacionPorcentajes.findAll", query = "SELECT p FROM ParametrizacionPorcentajes p"),
    @NamedQuery(name = "ParametrizacionPorcentajes.findByCodParametrizacion", query = "SELECT p FROM ParametrizacionPorcentajes p WHERE p.codParametrizacion = :codParametrizacion"),
    @NamedQuery(name = "ParametrizacionPorcentajes.findByPorDescripcion", query = "SELECT p FROM ParametrizacionPorcentajes p WHERE p.porDescripcion = :porDescripcion"),
    @NamedQuery(name = "ParametrizacionPorcentajes.findByPorGanancia", query = "SELECT p FROM ParametrizacionPorcentajes p WHERE p.porGanancia = :porGanancia"),
    @NamedQuery(name = "ParametrizacionPorcentajes.findByPorPerdida", query = "SELECT p FROM ParametrizacionPorcentajes p WHERE p.porPerdida = :porPerdida"),
    @NamedQuery(name = "ParametrizacionPorcentajes.findByPorExtra", query = "SELECT p FROM ParametrizacionPorcentajes p WHERE p.porExtra = :porExtra")})
public class ParametrizacionPorcentajes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_parametrizacion")
    private Integer codParametrizacion;
    @Size(max = 150)
    @Column(name = "por_descripcion")
    private String porDescripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "por_ganancia")
    private BigDecimal porGanancia;
    @Column(name = "por_perdida")
    private BigDecimal porPerdida;
    @Column(name = "por_extra")
    private BigDecimal porExtra;
    @OneToMany(mappedBy = "codParametrizacion")
    private Collection<Cotizacion> cotizacionCollection;

    public ParametrizacionPorcentajes() {
    }

    public ParametrizacionPorcentajes(Integer codParametrizacion) {
        this.codParametrizacion = codParametrizacion;
    }

    public Integer getCodParametrizacion() {
        return codParametrizacion;
    }

    public void setCodParametrizacion(Integer codParametrizacion) {
        this.codParametrizacion = codParametrizacion;
    }

    public String getPorDescripcion() {
        return porDescripcion;
    }

    public void setPorDescripcion(String porDescripcion) {
        this.porDescripcion = porDescripcion;
    }

    public BigDecimal getPorGanancia() {
        return porGanancia;
    }

    public void setPorGanancia(BigDecimal porGanancia) {
        this.porGanancia = porGanancia;
    }

    public BigDecimal getPorPerdida() {
        return porPerdida;
    }

    public void setPorPerdida(BigDecimal porPerdida) {
        this.porPerdida = porPerdida;
    }

    public BigDecimal getPorExtra() {
        return porExtra;
    }

    public void setPorExtra(BigDecimal porExtra) {
        this.porExtra = porExtra;
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
        hash += (codParametrizacion != null ? codParametrizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametrizacionPorcentajes)) {
            return false;
        }
        ParametrizacionPorcentajes other = (ParametrizacionPorcentajes) object;
        if ((this.codParametrizacion == null && other.codParametrizacion != null) || (this.codParametrizacion != null && !this.codParametrizacion.equals(other.codParametrizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.ParametrizacionPorcentajes[ codParametrizacion=" + codParametrizacion + " ]";
    }
    
}
