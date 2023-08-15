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
@Table(name = "estado_pro_stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoProStock.findAll", query = "SELECT e FROM EstadoProStock e"),
    @NamedQuery(name = "EstadoProStock.findByCodEstStock", query = "SELECT e FROM EstadoProStock e WHERE e.codEstStock = :codEstStock"),
    @NamedQuery(name = "EstadoProStock.findByEstStockNombre", query = "SELECT e FROM EstadoProStock e WHERE e.estStockNombre = :estStockNombre"),
    @NamedQuery(name = "EstadoProStock.findByEstStockSigla", query = "SELECT e FROM EstadoProStock e WHERE e.estStockSigla = :estStockSigla")})
public class EstadoProStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_est_stock")
    private Integer codEstStock;
    @Size(max = 100)
    @Column(name = "est_stock_nombre")
    private String estStockNombre;
    @Size(max = 20)
    @Column(name = "est_stock_sigla")
    private String estStockSigla;
    @OneToMany(mappedBy = "codEstStock")
    private Collection<ProductoStock> productoStockCollection;

    public EstadoProStock() {
    }

    public EstadoProStock(Integer codEstStock) {
        this.codEstStock = codEstStock;
    }

    public Integer getCodEstStock() {
        return codEstStock;
    }

    public void setCodEstStock(Integer codEstStock) {
        this.codEstStock = codEstStock;
    }

    public String getEstStockNombre() {
        return estStockNombre;
    }

    public void setEstStockNombre(String estStockNombre) {
        this.estStockNombre = estStockNombre;
    }

    public String getEstStockSigla() {
        return estStockSigla;
    }

    public void setEstStockSigla(String estStockSigla) {
        this.estStockSigla = estStockSigla;
    }

    @XmlTransient
    public Collection<ProductoStock> getProductoStockCollection() {
        return productoStockCollection;
    }

    public void setProductoStockCollection(Collection<ProductoStock> productoStockCollection) {
        this.productoStockCollection = productoStockCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstStock != null ? codEstStock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoProStock)) {
            return false;
        }
        EstadoProStock other = (EstadoProStock) object;
        if ((this.codEstStock == null && other.codEstStock != null) || (this.codEstStock != null && !this.codEstStock.equals(other.codEstStock))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.EstadoProStock[ codEstStock=" + codEstStock + " ]";
    }
    
}
