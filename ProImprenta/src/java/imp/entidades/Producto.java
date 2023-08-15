/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findByCodProducto", query = "SELECT p FROM Producto p WHERE p.codProducto = :codProducto")
    ,
    @NamedQuery(name = "Producto.findByProdSerie", query = "SELECT p FROM Producto p WHERE p.prodSerie = :prodSerie")
    ,
    @NamedQuery(name = "Producto.findByProdNombre", query = "SELECT p FROM Producto p WHERE p.prodNombre = :prodNombre")
    ,
    @NamedQuery(name = "Producto.findLikeNombre", query = "SELECT p FROM Producto p WHERE p.prodNombre LIKE :prodNombre")
    ,
    @NamedQuery(name = "Producto.findByProdCostoCompra", query = "SELECT p FROM Producto p WHERE p.prodCostoCompra = :prodCostoCompra")
    ,
    @NamedQuery(name = "Producto.findByProdCostoVenta", query = "SELECT p FROM Producto p WHERE p.prodCostoVenta = :prodCostoVenta")
    ,
    @NamedQuery(name = "Producto.findByProdValorIncial", query = "SELECT p FROM Producto p WHERE p.prodValorIncial = :prodValorIncial")
    ,
    @NamedQuery(name = "Producto.findByProdUnidadMedida", query = "SELECT p FROM Producto p WHERE p.prodUnidadMedida = :prodUnidadMedida")
    ,
    @NamedQuery(name = "Producto.findByCodCategoria", query = "SELECT p FROM Producto p WHERE p.codCategoria = :codCategoria")
    ,
    @NamedQuery(name = "Producto.findByCodUbicacion", query = "SELECT p FROM Producto p WHERE p.codUbicacion = :codUbicacion")
    ,
    @NamedQuery(name = "Producto.findByCodCategoriaCodUbicacion", query = "SELECT p FROM Producto p WHERE p.codUbicacion = :codUbicacion AND p.codCategoria = :codCategoria")
    ,
    @NamedQuery(name = "Producto.findByProdFechaRegistro", query = "SELECT p FROM Producto p WHERE p.prodFechaRegistro = :prodFechaRegistro")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_producto")
    private Integer codProducto;
    @Size(max = 50)
    @Column(name = "prod_serie")
    private String prodSerie;
    @Size(max = 100)
    @Column(name = "prod_nombre")
    private String prodNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prod_costo_compra")
    private BigDecimal prodCostoCompra;
    @Column(name = "prod_costo_venta")
    private BigDecimal prodCostoVenta;
    @Column(name = "prod_valor_incial")
    private BigDecimal prodValorIncial;
    @Size(max = 50)
    @Column(name = "prod_unidad_medida")
    private String prodUnidadMedida;
    @Size(max = 90)
    @Column(name = "prod_marca")
    private String prodMarca;
    @Column(name = "prod_fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date prodFechaRegistro;
    @Column(name = "prod_cantidad_minima")
    private BigDecimal prodCantidadMinima;
    @OneToMany(mappedBy = "codProducto")
    private Collection<Operacion> operacionCollection;
    @JoinColumn(name = "cod_categoria", referencedColumnName = "cod_categoria")
    @ManyToOne
    private Categoria codCategoria;
    @JoinColumn(name = "cod_ubicacion", referencedColumnName = "cod_ubicacion")
    @ManyToOne
    private Ubicacion codUbicacion;

    @OneToMany(mappedBy = "codProducto")
    private Collection<DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection;

    public Producto() {
    }

    public Producto(String prodSerie, String prodNombre,
            BigDecimal prodCostoCompra,
            BigDecimal prodCostoVenta,
            BigDecimal prodValorIncial,
            String prodUnidadMedida,
            String prodMarca,
            BigDecimal prodCantidadMinima) {
        this.prodSerie = prodSerie;
        this.prodNombre = prodNombre;
        this.prodCostoCompra = prodCostoCompra;
        this.prodCostoVenta = prodCostoVenta;
        this.prodValorIncial = prodValorIncial;
        this.prodUnidadMedida = prodUnidadMedida;
        this.prodMarca = prodMarca;
        this.prodCantidadMinima = prodCantidadMinima;
    }

    public Producto(Integer codProducto) {
        this.codProducto = codProducto;
    }

    public Integer getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Integer codProducto) {
        this.codProducto = codProducto;
    }

    public String getProdSerie() {
        return prodSerie;
    }

    public void setProdSerie(String prodSerie) {
        this.prodSerie = prodSerie;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public BigDecimal getProdCostoCompra() {
        return prodCostoCompra;
    }

    public void setProdCostoCompra(BigDecimal prodCostoCompra) {
        this.prodCostoCompra = prodCostoCompra;
    }

    public BigDecimal getProdCostoVenta() {
        return prodCostoVenta;
    }

    public void setProdCostoVenta(BigDecimal prodCostoVenta) {
        this.prodCostoVenta = prodCostoVenta;
    }

    public BigDecimal getProdValorIncial() {
        return prodValorIncial;
    }

    public void setProdValorIncial(BigDecimal prodValorIncial) {
        this.prodValorIncial = prodValorIncial;
    }

    public String getProdUnidadMedida() {
        return prodUnidadMedida;
    }

    public void setProdUnidadMedida(String prodUnidadMedida) {
        this.prodUnidadMedida = prodUnidadMedida;
    }

    public Date getProdFechaRegistro() {
        return prodFechaRegistro;
    }

    public void setProdFechaRegistro(Date prodFechaRegistro) {
        this.prodFechaRegistro = prodFechaRegistro;
    }

    public BigDecimal getProdCantidadMinima() {
        return prodCantidadMinima;
    }

    public void setProdCantidadMinima(BigDecimal prodCantidadMinima) {
        this.prodCantidadMinima = prodCantidadMinima;
    }

    @XmlTransient
    public Collection<Operacion> getOperacionCollection() {
        return operacionCollection;
    }

    public void setOperacionCollection(Collection<Operacion> operacionCollection) {
        this.operacionCollection = operacionCollection;
    }

    public Categoria getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Categoria codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Ubicacion getCodUbicacion() {
        return codUbicacion;
    }

    public void setCodUbicacion(Ubicacion codUbicacion) {
        this.codUbicacion = codUbicacion;
    }

    public String getProdMarca() {
        return prodMarca;
    }

    public void setProdMarca(String prodMarca) {
        this.prodMarca = prodMarca;
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
        hash += (codProducto != null ? codProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.codProducto == null && other.codProducto != null) || (this.codProducto != null && !this.codProducto.equals(other.codProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidades.Producto[ codProducto=" + codProducto + " ]";
    }
}
