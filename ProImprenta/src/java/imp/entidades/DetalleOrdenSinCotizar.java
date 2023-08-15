/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Cacheable(false)
@Table(name = "detalle_orden_sin_cotizar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleOrdenSinCotizar.findAll", query = "SELECT d FROM DetalleOrdenSinCotizar d")
    ,
    @NamedQuery(name = "DetalleOrdenSinCotizar.findAllForOrden", query = "SELECT d FROM DetalleOrdenSinCotizar d WHERE d.codOrdenSinCotizacion=:codOrdenSinCotizacion")
    ,
    @NamedQuery(name = "DetalleOrdenSinCotizar.findByCodDetSinCotizar", query = "SELECT d FROM DetalleOrdenSinCotizar d WHERE d.codDetSinCotizar = :codDetSinCotizar")
    ,
    @NamedQuery(name = "DetalleOrdenSinCotizar.findByDetsinCatidadCantida", query = "SELECT d FROM DetalleOrdenSinCotizar d WHERE d.detsinCatidadCantida = :detsinCatidadCantida")
    ,
    @NamedQuery(name = "DetalleOrdenSinCotizar.findByDetsinDescripcion", query = "SELECT d FROM DetalleOrdenSinCotizar d WHERE d.detsinDescripcion = :detsinDescripcion")
    ,
    @NamedQuery(name = "DetalleOrdenSinCotizar.findByDetsinValor", query = "SELECT d FROM DetalleOrdenSinCotizar d WHERE d.detsinValor = :detsinValor")})
public class DetalleOrdenSinCotizar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_det_sin_cotizar")
    private Integer codDetSinCotizar;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "detsin_catidad_cantida")
    private BigDecimal detsinCatidadCantida;
    @Column(name = "detsin_subtotal")
    private BigDecimal detsinSubtotal;
    @Size(max = 400)
    @Column(name = "detsin_descripcion")
    private String detsinDescripcion;
    @Column(name = "tipo_item")
    private String tipoItem;
    @Column(name = "detsin_valor")
    private BigDecimal detsinValor;
    @Column(name = "sin_tipo")
    private String sinTipo;
    @JoinColumn(name = "cod_orden_sin_cotizacion", referencedColumnName = "cod_orden_sin_cotizacion")
    @ManyToOne
    private OrdenSinCotizacion codOrdenSinCotizacion;
    @JoinColumn(name = "cod_producto", referencedColumnName = "cod_producto",nullable=true)
    @ManyToOne
    private Producto codProducto;
    @JoinColumn(name = "cod_tercearizado", referencedColumnName = "cod_tercearizado" ,nullable=true)
    @ManyToOne
    private Terciarizado codTercearizado;
    @JoinColumn(name = "id_tipo_trabajo", referencedColumnName = "id_tipo_trabajo",nullable=true)
    @ManyToOne
    private TipoTrabajo idTipoTrabajo;

    public DetalleOrdenSinCotizar() {
    }

    public DetalleOrdenSinCotizar(BigDecimal detsinCatidadCantida, BigDecimal detsinSubtotal, String detsinDescripcion, BigDecimal detsinValor) {

        this.detsinCatidadCantida = detsinCatidadCantida;
        this.detsinSubtotal = detsinSubtotal;
        this.detsinDescripcion = detsinDescripcion;
        this.detsinValor = detsinValor;
    }

    public DetalleOrdenSinCotizar(Integer codDetSinCotizar) {
        this.codDetSinCotizar = codDetSinCotizar;
    }

    public Integer getCodDetSinCotizar() {
        return codDetSinCotizar;
    }

    public void setCodDetSinCotizar(Integer codDetSinCotizar) {
        this.codDetSinCotizar = codDetSinCotizar;
    }

    public BigDecimal getDetsinCatidadCantida() {
        return detsinCatidadCantida;
    }

    public void setDetsinCatidadCantida(BigDecimal detsinCatidadCantida) {
        this.detsinCatidadCantida = detsinCatidadCantida;
    }

    public String getDetsinDescripcion() {
        return detsinDescripcion;
    }

    public void setDetsinDescripcion(String detsinDescripcion) {
        this.detsinDescripcion = detsinDescripcion;
    }

    public BigDecimal getDetsinValor() {
        return detsinValor;
    }

    public void setDetsinValor(BigDecimal detsinValor) {
        this.detsinValor = detsinValor;
    }

    public BigDecimal getDetsinSubtotal() {
        return detsinSubtotal;
    }

    public void setDetsinSubtotal(BigDecimal detsinSubtotal) {
        this.detsinSubtotal = detsinSubtotal;
    }

    public OrdenSinCotizacion getCodOrdenSinCotizacion() {
        return codOrdenSinCotizacion;
    }

    public void setCodOrdenSinCotizacion(OrdenSinCotizacion codOrdenSinCotizacion) {
        this.codOrdenSinCotizacion = codOrdenSinCotizacion;
    }

    public Producto getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Producto codProducto) {
        this.codProducto = codProducto;
    }

    public Terciarizado getCodTercearizado() {
        return codTercearizado;
    }

    public void setCodTercearizado(Terciarizado codTercearizado) {
        this.codTercearizado = codTercearizado;
    }

    public TipoTrabajo getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    public void setIdTipoTrabajo(TipoTrabajo idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }
    
      public String getSinTipo() {
        return sinTipo;
    }

    public void setSinTipo(String sinTipo) {
        this.sinTipo = sinTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDetSinCotizar != null ? codDetSinCotizar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleOrdenSinCotizar)) {
            return false;
        }
        DetalleOrdenSinCotizar other = (DetalleOrdenSinCotizar) object;
        if ((this.codDetSinCotizar == null && other.codDetSinCotizar != null) || (this.codDetSinCotizar != null && !this.codDetSinCotizar.equals(other.codDetSinCotizar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidadess.DetalleOrdenSinCotizar[ codDetSinCotizar=" + codDetSinCotizar + " ]";
    }
}
