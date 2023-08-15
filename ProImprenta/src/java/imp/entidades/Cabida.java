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
import javax.persistence.Lob;
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
@Table(name = "cabida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cabida.findAll", query = "SELECT c FROM Cabida c"),
    @NamedQuery(name = "Cabida.findByCodCabida", query = "SELECT c FROM Cabida c WHERE c.codCabida = :codCabida"),
    @NamedQuery(name = "Cabida.findCabidaOptima", query = "SELECT c FROM Cabida c WHERE c.cabCategoria = :cabCategoria"),
    @NamedQuery(name = "Cabida.findByCodCabidaLike", query = "SELECT c FROM Cabida c WHERE c.cabDescripcion like :descripcion"),
    @NamedQuery(name = "Cabida.findByUxf", query = "SELECT c FROM Cabida c WHERE c.cabUxf = :cabUxf"),
    @NamedQuery(name = "Cabida.findByCabDescripcion", query = "SELECT c FROM Cabida c WHERE c.cabDescripcion = :cabDescripcion")})
public class Cabida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_cabida")
    private Integer codCabida;
    @Size(max = 100)
    @Column(name = "cab_descripcion")
    private String cabDescripcion;
    @Lob
    @Column(name = "cab_grafico")
    private byte[] cabGrafico;
    @Column(name = "cab_largo")
    private BigDecimal cabLargo;
    @Column(name = "cab_ancho")
    private BigDecimal cabAncho;
    @Column(name = "cab_uxf")
    private BigDecimal cabUxf;
    @Column(name = "cab_categoria")
    private Integer cabCategoria;
    @OneToMany(mappedBy = "codCabida")
    private Collection<DetalleCotizacion> detalleCotizacionCollection;

    public Cabida() {
    }

    public Cabida(Integer codCabida) {
        this.codCabida = codCabida;
    }

    public Integer getCodCabida() {
        return codCabida;
    }

    public void setCodCabida(Integer codCabida) {
        this.codCabida = codCabida;
    }

    public String getCabDescripcion() {
        return cabDescripcion;
    }

    public void setCabDescripcion(String cabDescripcion) {
        this.cabDescripcion = cabDescripcion;
    }

    public byte[] getCabGrafico() {
        return cabGrafico;
    }

    public void setCabGrafico(byte[] cabGrafico) {
        this.cabGrafico = cabGrafico;
    }

    public BigDecimal getCabLargo() {
        return cabLargo;
    }

    public void setCabLargo(BigDecimal cabLargo) {
        this.cabLargo = cabLargo;
    }

    public BigDecimal getCabAncho() {
        return cabAncho;
    }

    public void setCabAncho(BigDecimal cabAncho) {
        this.cabAncho = cabAncho;
    }

    public Integer getCabCategoria() {
        return cabCategoria;
    }

    public void setCabCategoria(Integer cabCategoria) {
        this.cabCategoria = cabCategoria;
    }

    public BigDecimal getCabUxf() {
        return cabUxf;
    }

    public void setCabUxf(BigDecimal cabUxf) {
        this.cabUxf = cabUxf;
    }

    @XmlTransient
    public Collection<DetalleCotizacion> getDetalleCotizacionCollection() {
        return detalleCotizacionCollection;
    }

    public void setDetalleCotizacionCollection(Collection<DetalleCotizacion> detalleCotizacionCollection) {
        this.detalleCotizacionCollection = detalleCotizacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCabida != null ? codCabida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cabida)) {
            return false;
        }
        Cabida other = (Cabida) object;
        if ((this.codCabida == null && other.codCabida != null) || (this.codCabida != null && !this.codCabida.equals(other.codCabida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Cabida[ codCabida=" + codCabida + " ]";
    }
}
