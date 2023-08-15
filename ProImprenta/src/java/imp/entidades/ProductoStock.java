/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "producto_stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoStock.findAll", query = "SELECT p FROM ProductoStock p"),
    @NamedQuery(name = "ProductoStock.findByCodProdStock", query = "SELECT p FROM ProductoStock p WHERE p.codProdStock = :codProdStock"),
    @NamedQuery(name = "ProductoStock.findByProdstNombre", query = "SELECT p FROM ProductoStock p WHERE p.prodstNombre = :prodstNombre"),
    @NamedQuery(name = "ProductoStock.findByProdstCantidad", query = "SELECT p FROM ProductoStock p WHERE p.prodstCantidad = :prodstCantidad"),
    @NamedQuery(name = "ProductoStock.findByProdstValorCompra", query = "SELECT p FROM ProductoStock p WHERE p.prodstValorCompra = :prodstValorCompra"),
    @NamedQuery(name = "ProductoStock.findByProdstFechaCompra", query = "SELECT p FROM ProductoStock p WHERE p.prodstFechaCompra = :prodstFechaCompra"),
    @NamedQuery(name = "ProductoStock.findByProdstNumeroFactura", query = "SELECT p FROM ProductoStock p WHERE p.prodstNumeroFactura = :prodstNumeroFactura"),
    @NamedQuery(name = "ProductoStock.findByProdstFechaPagoFactura", query = "SELECT p FROM ProductoStock p WHERE p.prodstFechaPagoFactura = :prodstFechaPagoFactura")})
public class ProductoStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_prod_stock")
    private Integer codProdStock;
    @Size(max = 100)
    @Column(name = "prodst_nombre")
    private String prodstNombre;
    @Column(name = "prodst_cantidad")
    private Integer prodstCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prodst_valor_compra")
    private Float prodstValorCompra;
    @Column(name = "prodst_fecha_compra")
    @Temporal(TemporalType.DATE)
    private Date prodstFechaCompra;
    @Size(max = 50)
    @Column(name = "prodst_numero_factura")
    private String prodstNumeroFactura;
    @Column(name = "prodst_fecha_pago_factura")
    @Temporal(TemporalType.DATE)
    private Date prodstFechaPagoFactura;
    @JoinColumn(name = "cod_cat_prod_compra", referencedColumnName = "cod_cat_prod_compra")
    @ManyToOne
    private CatalogoProdCompra codCatProdCompra;
    @JoinColumn(name = "cod_est_stock", referencedColumnName = "cod_est_stock")
    @ManyToOne
    private EstadoProStock codEstStock;

    public ProductoStock() {
    }

    public ProductoStock(Integer codProdStock) {
        this.codProdStock = codProdStock;
    }

    public Integer getCodProdStock() {
        return codProdStock;
    }

    public void setCodProdStock(Integer codProdStock) {
        this.codProdStock = codProdStock;
    }

    public String getProdstNombre() {
        return prodstNombre;
    }

    public void setProdstNombre(String prodstNombre) {
        this.prodstNombre = prodstNombre;
    }

    public Integer getProdstCantidad() {
        return prodstCantidad;
    }

    public void setProdstCantidad(Integer prodstCantidad) {
        this.prodstCantidad = prodstCantidad;
    }

    public Float getProdstValorCompra() {
        return prodstValorCompra;
    }

    public void setProdstValorCompra(Float prodstValorCompra) {
        this.prodstValorCompra = prodstValorCompra;
    }

    public Date getProdstFechaCompra() {
        return prodstFechaCompra;
    }

    public void setProdstFechaCompra(Date prodstFechaCompra) {
        this.prodstFechaCompra = prodstFechaCompra;
    }

    public String getProdstNumeroFactura() {
        return prodstNumeroFactura;
    }

    public void setProdstNumeroFactura(String prodstNumeroFactura) {
        this.prodstNumeroFactura = prodstNumeroFactura;
    }

    public Date getProdstFechaPagoFactura() {
        return prodstFechaPagoFactura;
    }

    public void setProdstFechaPagoFactura(Date prodstFechaPagoFactura) {
        this.prodstFechaPagoFactura = prodstFechaPagoFactura;
    }

    public CatalogoProdCompra getCodCatProdCompra() {
        return codCatProdCompra;
    }

    public void setCodCatProdCompra(CatalogoProdCompra codCatProdCompra) {
        this.codCatProdCompra = codCatProdCompra;
    }

    public EstadoProStock getCodEstStock() {
        return codEstStock;
    }

    public void setCodEstStock(EstadoProStock codEstStock) {
        this.codEstStock = codEstStock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codProdStock != null ? codProdStock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoStock)) {
            return false;
        }
        ProductoStock other = (ProductoStock) object;
        if ((this.codProdStock == null && other.codProdStock != null) || (this.codProdStock != null && !this.codProdStock.equals(other.codProdStock))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.ProductoStock[ codProdStock=" + codProdStock + " ]";
    }
    
}
