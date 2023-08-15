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
@Table(name = "terciarizado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terciarizado.findAll", query = "SELECT t FROM Terciarizado t"),
    @NamedQuery(name = "Terciarizado.findAllNombre", query = "SELECT t FROM Terciarizado t where t.terProveedor like :nombre ORDER BY t.terNombreProducto"),
    @NamedQuery(name = "Terciarizado.findByCodTercearizado", query = "SELECT t FROM Terciarizado t WHERE t.codTercearizado = :codTercearizado"),
    @NamedQuery(name = "Terciarizado.findByTerProveedor", query = "SELECT t FROM Terciarizado t WHERE t.terProveedor = :terProveedor"),
    @NamedQuery(name = "Terciarizado.findByTerNombreProducto", query = "SELECT t FROM Terciarizado t WHERE t.terNombreProducto = :terNombreProducto"),
    @NamedQuery(name = "Terciarizado.findByTerCostoMillar", query = "SELECT t FROM Terciarizado t WHERE t.terCostoMillar = :terCostoMillar"),
    @NamedQuery(name = "Terciarizado.findByTerCostoUnitario", query = "SELECT t FROM Terciarizado t WHERE t.terCostoUnitario = :terCostoUnitario"),
    @NamedQuery(name = "Terciarizado.findByTerCostoOpcional", query = "SELECT t FROM Terciarizado t WHERE t.terCostoOpcional = :terCostoOpcional")})
public class Terciarizado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tercearizado")
    private Integer codTercearizado;
    @Size(max = 150)
    @Column(name = "ter_proveedor")
    private String terProveedor;
    @Size(max = 150)
    @Column(name = "ter_nombre_producto")
    private String terNombreProducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ter_costo_millar")
    private Float terCostoMillar;
    @Column(name = "ter_costo_unitario")
    private Float terCostoUnitario;
    @Column(name = "ter_costo_opcional")
    private Float terCostoOpcional;
    @OneToMany(mappedBy = "codTercearizado")
    private Collection<DetalleCotizacion> detalleCotizacionCollection;
     @OneToMany(mappedBy = "codTercearizado")
    private Collection<DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection;

    public Terciarizado() {
    }

    public Terciarizado(Integer codTercearizado) {
        this.codTercearizado = codTercearizado;
    }

    public Integer getCodTercearizado() {
        return codTercearizado;
    }

    public void setCodTercearizado(Integer codTercearizado) {
        this.codTercearizado = codTercearizado;
    }

    public String getTerProveedor() {
        return terProveedor;
    }

    public void setTerProveedor(String terProveedor) {
        this.terProveedor = terProveedor;
    }

    public String getTerNombreProducto() {
        return terNombreProducto;
    }

    public void setTerNombreProducto(String terNombreProducto) {
        this.terNombreProducto = terNombreProducto;
    }

    public Float getTerCostoMillar() {
        return terCostoMillar;
    }

    public void setTerCostoMillar(Float terCostoMillar) {
        this.terCostoMillar = terCostoMillar;
    }

    public Float getTerCostoUnitario() {
        return terCostoUnitario;
    }

    public void setTerCostoUnitario(Float terCostoUnitario) {
        this.terCostoUnitario = terCostoUnitario;
    }

    public Float getTerCostoOpcional() {
        return terCostoOpcional;
    }

    public void setTerCostoOpcional(Float terCostoOpcional) {
        this.terCostoOpcional = terCostoOpcional;
    }

    @XmlTransient
    public Collection<DetalleCotizacion> getDetalleCotizacionCollection() {
        return detalleCotizacionCollection;
    }

    public void setDetalleCotizacionCollection(Collection<DetalleCotizacion> detalleCotizacionCollection) {
        this.detalleCotizacionCollection = detalleCotizacionCollection;
    }
    
    @XmlTransient
    public Collection<DetalleOrdenSinCotizar> getDetalleOrdenSinCotizarCollection() {
        return detalleOrdenSinCotizarCollection;
    }

    public void setDetalleOrdenSinCotizarCollection(Collection<DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection) {
        this.detalleOrdenSinCotizarCollection = detalleOrdenSinCotizarCollection;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTercearizado != null ? codTercearizado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Terciarizado)) {
            return false;
        }
        Terciarizado other = (Terciarizado) object;
        if ((this.codTercearizado == null && other.codTercearizado != null) || (this.codTercearizado != null && !this.codTercearizado.equals(other.codTercearizado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.Terciarizado[ codTercearizado=" + codTercearizado + " ]";
    }
    
}
