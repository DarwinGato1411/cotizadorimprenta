/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "operacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operacion.findAll", query = "SELECT o FROM Operacion o")
    ,
    @NamedQuery(name = "Operacion.findUltimaSalida", query = "SELECT o FROM Operacion o WHERE o.codTipoOperacion.codTipoOperacion=2 ORDER BY o.codOperacion DESC")
    ,
    @NamedQuery(name = "Operacion.findSalidaForFecha", query = "SELECT o FROM Operacion o WHERE o.codTipoOperacion.codTipoOperacion=2 AND o.opeFecha BETWEEN :inicio AND :fin  ORDER BY o.codOperacion DESC")
    ,
    @NamedQuery(name = "Operacion.findByCodOperacion", query = "SELECT o FROM Operacion o WHERE o.codOperacion = :codOperacion")
    ,
    @NamedQuery(name = "Operacion.findByCodCategoria", query = "SELECT o FROM Operacion o WHERE o.codCategoria = :codCategoria AND o.codProducto.prodNombre like :prodNombre")
    ,
    @NamedQuery(name = "Operacion.findByCodUbicacion", query = "SELECT o FROM Operacion o WHERE o.codUbicacion = :codUbicacion AND o.codProducto.prodNombre like :prodNombre")
    ,
    @NamedQuery(name = "Operacion.findByCategoriaUbicacion", query = "SELECT o FROM Operacion o WHERE o.codUbicacion = :codUbicacion AND o.codCategoria=:codCategoria")
    ,
    @NamedQuery(name = "Operacion.findForProducto", query = "SELECT o FROM Operacion o WHERE o.codProducto = :codProducto")
    ,
    @NamedQuery(name = "Operacion.findForProductoFechas", query = "SELECT o FROM Operacion o WHERE o.codProducto = :codProducto AND o.opeFecha BETWEEN :fechaInicio AND :fechaFin ")
    ,
    @NamedQuery(name = "Operacion.findForNombreProducto", query = "SELECT o FROM Operacion o WHERE o.codProducto.prodNombre LIKE :prodNombre ORDER BY o.opeFecha DESC")
    ,
    @NamedQuery(name = "Operacion.findByOpeCatidad", query = "SELECT o FROM Operacion o WHERE o.opeCatidad = :opeCatidad")
    ,
    @NamedQuery(name = "Operacion.findByOpeFecha", query = "SELECT o FROM Operacion o WHERE o.opeFecha = :opeFecha")
    ,
    @NamedQuery(name = "Operacion.findByOpeRangoFecha", query = "SELECT o FROM Operacion o WHERE o.opeFecha BETWEEN :fechaInicio AND :fechaFin")
    ,
    @NamedQuery(name = "Operacion.findByTipoOperacion", query = "SELECT o FROM Operacion o WHERE o.codTipoOperacion = :codTipoOperacion")
    ,
    @NamedQuery(name = "Operacion.findByOpeEstadoProducto", query = "SELECT o FROM Operacion o WHERE o.opeEstadoProducto = :opeEstadoProducto")})
public class Operacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_operacion")
    private Integer codOperacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ope_catidad")
    private BigDecimal opeCatidad;
    @Column(name = "ope_fecha")
    @Temporal(TemporalType.DATE)
    private Date opeFecha;
    @Size(max = 50)
    @Column(name = "ope_estado_producto")
    private String opeEstadoProducto;
    @Size(max = 100)
    @Column(name = "ope_nombre_despacho")
    private String opeNombreDespacho;
    @Size(max = 100)
    @Column(name = "ope_area_despacho")
    private String opeAreaDespacho;
    @Size(max = 100)
    @Column(name = "ope_concepo")
    private String opeConcepo;
    @Size(max = 100)
    @Column(name = "ope_referencia")
    private String opeReferencia;
    @Size(max = 45)
    @Column(name = "ope_factura")
    private String opeFactura;
    @Column(name = "ope_costo_ultima_compra")
    private BigDecimal opeCostoUltimaCompra;
    @JoinColumn(name = "cod_categoria", referencedColumnName = "cod_categoria")
    @ManyToOne
    private Categoria codCategoria;
    @JoinColumn(name = "cod_ubicacion", referencedColumnName = "cod_ubicacion")
    @ManyToOne
    private Ubicacion codUbicacion;
    @JoinColumn(name = "id_usuario", referencedColumnName = "cod_usuario_sistema")
    @ManyToOne
    private UsuarioSistema idUsuario;
    @JoinColumn(name = "cod_tipo_operacion", referencedColumnName = "cod_tipo_operacion")
    @ManyToOne
    private TipoOperacion codTipoOperacion;
    @JoinColumn(name = "cod_producto", referencedColumnName = "cod_producto")
    @ManyToOne
    private Producto codProducto;
    @Transient
    private BigDecimal valorTotal;

    public Operacion() {
    }

    public Operacion(BigDecimal opeCatidad, Date opeFecha, String opeEstadoProducto, String opeNombreDespacho, String opeAreaDespacho, String opeConcepo, String opeReferencia, String opeFactura, BigDecimal opeCostoUltimaCompra, Categoria codCategoria, Ubicacion codUbicacion, UsuarioSistema idUsuario, TipoOperacion codTipoOperacion, Producto codProducto, BigDecimal valorTotal) {
        this.opeCatidad = opeCatidad;
        this.opeFecha = opeFecha;
        this.opeEstadoProducto = opeEstadoProducto;
        this.opeNombreDespacho = opeNombreDespacho;
        this.opeAreaDespacho = opeAreaDespacho;
        this.opeConcepo = opeConcepo;
        this.opeReferencia = opeReferencia;
        this.opeFactura = opeFactura;
        this.opeCostoUltimaCompra = opeCostoUltimaCompra;
        this.codCategoria = codCategoria;
        this.codUbicacion = codUbicacion;
        this.idUsuario = idUsuario;
        this.codTipoOperacion = codTipoOperacion;
        this.codProducto = codProducto;
        this.valorTotal = valorTotal;
    }

    public Operacion(BigDecimal opeCatidad, String opeNombreDespacho, String opeEstadoProducto, String opeAreaDespacho, String opeConcepo, String opeReferencia, String opeFactura, BigDecimal opeCostoUltimaCompra) {
        this.opeEstadoProducto = opeEstadoProducto;
        this.opeCatidad = opeCatidad;
        this.opeNombreDespacho = opeNombreDespacho;
        this.opeAreaDespacho = opeAreaDespacho;
        this.opeConcepo = opeConcepo;
        this.opeReferencia = opeReferencia;
        this.opeFactura = opeFactura;
        this.opeCostoUltimaCompra = opeCostoUltimaCompra;

    }

    public Operacion(Integer codOperacion) {
        this.codOperacion = codOperacion;
    }

    public Integer getCodOperacion() {
        return codOperacion;
    }

    public void setCodOperacion(Integer codOperacion) {
        this.codOperacion = codOperacion;
    }

    public BigDecimal getOpeCatidad() {
        return opeCatidad;
    }

    public void setOpeCatidad(BigDecimal opeCatidad) {
        this.opeCatidad = opeCatidad;
    }

    public Date getOpeFecha() {
        return opeFecha;
    }

    public void setOpeFecha(Date opeFecha) {
        this.opeFecha = opeFecha;
    }

    public String getOpeEstadoProducto() {
        return opeEstadoProducto;
    }

    public void setOpeEstadoProducto(String opeEstadoProducto) {
        this.opeEstadoProducto = opeEstadoProducto;
    }

    public UsuarioSistema getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuarioSistema idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TipoOperacion getCodTipoOperacion() {
        return codTipoOperacion;
    }

    public void setCodTipoOperacion(TipoOperacion codTipoOperacion) {
        this.codTipoOperacion = codTipoOperacion;
    }

    public Producto getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Producto codProducto) {
        this.codProducto = codProducto;
    }

    public String getOpeNombreDespacho() {
        return opeNombreDespacho;
    }

    public void setOpeNombreDespacho(String opeNombreDespacho) {
        this.opeNombreDespacho = opeNombreDespacho;
    }

    public String getOpeAreaDespacho() {
        return opeAreaDespacho;
    }

    public void setOpeAreaDespacho(String opeAreaDespacho) {
        this.opeAreaDespacho = opeAreaDespacho;
    }

    public String getOpeConcepo() {
        return opeConcepo;
    }

    public void setOpeConcepo(String opeConcepo) {
        this.opeConcepo = opeConcepo;
    }

    public String getOpeReferencia() {
        return opeReferencia;
    }

    public void setOpeReferencia(String opeReferencia) {
        this.opeReferencia = opeReferencia;
    }

    public String getOpeFactura() {
        return opeFactura;
    }

    public void setOpeFactura(String opeFactura) {
        this.opeFactura = opeFactura;
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

    public BigDecimal getValorTotal() {
        if (codProducto.getProdCostoCompra() != null && opeCatidad != null) {
            valorTotal = codProducto.getProdCostoCompra().multiply(opeCatidad);
        }
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getOpeCostoUltimaCompra() {
        return opeCostoUltimaCompra;
    }

    public void setOpeCostoUltimaCompra(BigDecimal opeCostoUltimaCompra) {
        this.opeCostoUltimaCompra = opeCostoUltimaCompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codOperacion != null ? codOperacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operacion)) {
            return false;
        }
        Operacion other = (Operacion) object;
        if ((this.codOperacion == null && other.codOperacion != null) || (this.codOperacion != null && !this.codOperacion.equals(other.codOperacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidades.Operacion[ codOperacion=" + codOperacion + " ]";
    }
}
