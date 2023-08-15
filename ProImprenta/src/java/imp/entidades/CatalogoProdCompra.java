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
@Table(name = "catalogo_unit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CatalogoProdCompra.findAll", query = "SELECT c FROM CatalogoProdCompra c"),
    @NamedQuery(name = "CatalogoProdCompra.findByCodCatProdCompra", query = "SELECT c FROM CatalogoProdCompra c WHERE c.codCatProdCompra = :codCatProdCompra"),
    @NamedQuery(name = "CatalogoProdCompra.findByCatComNombreProveedor", query = "SELECT c FROM CatalogoProdCompra c WHERE c.catComNombreProveedor = :catComNombreProveedor"),
    @NamedQuery(name = "CatalogoProdCompra.findByCatComNombreProducto", query = "SELECT c FROM CatalogoProdCompra c WHERE c.catComNombreProducto = :catComNombreProducto"),
    @NamedQuery(name = "CatalogoProdCompra.findByCatComValorUnidad", query = "SELECT c FROM CatalogoProdCompra c WHERE c.catComValorUnidad = :catComValorUnidad"),
    @NamedQuery(name = "CatalogoProdCompra.findByCatComValorMillar", query = "SELECT c FROM CatalogoProdCompra c WHERE c.catComValorMillar = :catComValorMillar")})
public class CatalogoProdCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_cat_prod_compra")
    private Integer codCatProdCompra;
    @Size(max = 150)
    @Column(name = "cat_com_nombre_proveedor")
    private String catComNombreProveedor;
    @Size(max = 100)
    @Column(name = "cat_com_nombre_producto")
    private String catComNombreProducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cat_com_valor_unidad")
    private Float catComValorUnidad;
    @Column(name = "cat_com_valor_millar")
    private Float catComValorMillar;
    @OneToMany(mappedBy = "codCatProdCompra")
    private Collection<ProductoStock> productoStockCollection;

    public CatalogoProdCompra() {
    }

    public CatalogoProdCompra(Integer codCatProdCompra) {
        this.codCatProdCompra = codCatProdCompra;
    }

    public Integer getCodCatProdCompra() {
        return codCatProdCompra;
    }

    public void setCodCatProdCompra(Integer codCatProdCompra) {
        this.codCatProdCompra = codCatProdCompra;
    }

    public String getCatComNombreProveedor() {
        return catComNombreProveedor;
    }

    public void setCatComNombreProveedor(String catComNombreProveedor) {
        this.catComNombreProveedor = catComNombreProveedor;
    }

    public String getCatComNombreProducto() {
        return catComNombreProducto;
    }

    public void setCatComNombreProducto(String catComNombreProducto) {
        this.catComNombreProducto = catComNombreProducto;
    }

    public Float getCatComValorUnidad() {
        return catComValorUnidad;
    }

    public void setCatComValorUnidad(Float catComValorUnidad) {
        this.catComValorUnidad = catComValorUnidad;
    }

    public Float getCatComValorMillar() {
        return catComValorMillar;
    }

    public void setCatComValorMillar(Float catComValorMillar) {
        this.catComValorMillar = catComValorMillar;
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
        hash += (codCatProdCompra != null ? codCatProdCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatalogoProdCompra)) {
            return false;
        }
        CatalogoProdCompra other = (CatalogoProdCompra) object;
        if ((this.codCatProdCompra == null && other.codCatProdCompra != null) || (this.codCatProdCompra != null && !this.codCatProdCompra.equals(other.codCatProdCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.CatalogoProdCompra[ codCatProdCompra=" + codCatProdCompra + " ]";
    }
    
}
