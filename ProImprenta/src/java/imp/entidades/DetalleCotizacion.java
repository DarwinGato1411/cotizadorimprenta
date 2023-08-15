/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "detalle_cotizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCotizacion.findAll", query = "SELECT d FROM DetalleCotizacion d"),
    @NamedQuery(name = "DetalleCotizacion.findByCodDetalle", query = "SELECT  d FROM DetalleCotizacion d WHERE d.codDetalle = :codDetalle"),
    @NamedQuery(name = "DetalleCotizacion.findPorCotizacion", query = "SELECT d FROM DetalleCotizacion d WHERE d.codCotizacion = :codCotizacion"),
    @NamedQuery(name = "DetalleCotizacion.findPorCotizacionXml", query = "SELECT d FROM DetalleCotizacion d WHERE d.codCotizacion = :codCotizacion AND d.detProductoSolicitado IS NOT NULL "),
    @NamedQuery(name = "DetalleCotizacion.findByDetProductoSolicitado", query = "SELECT d FROM DetalleCotizacion d WHERE d.detProductoSolicitado = :detProductoSolicitado"),
    @NamedQuery(name = "DetalleCotizacion.findByDetCantidadSolicitada", query = "SELECT d FROM DetalleCotizacion d WHERE d.detCantidadSolicitada = :detCantidadSolicitada"),
    @NamedQuery(name = "DetalleCotizacion.findByDetAltoSolicitado", query = "SELECT d FROM DetalleCotizacion d WHERE d.detAltoSolicitado = :detAltoSolicitado"),
    @NamedQuery(name = "DetalleCotizacion.findByDetAnchoSolicitado", query = "SELECT d FROM DetalleCotizacion d WHERE d.detAnchoSolicitado = :detAnchoSolicitado")})
public class DetalleCotizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_detalle")
    private Integer codDetalle;
    @Size(max = 150)
    @Column(name = "det_producto_solicitado")
    private String detProductoSolicitado;
    @Column(name = "det_cantidad_solicitada")
    private BigDecimal detCantidadSolicitada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "det_alto_solicitado")
    private BigDecimal detAltoSolicitado;
    @Column(name = "det_ancho_solicitado")
    private BigDecimal detAnchoSolicitado;
    @Column(name = "det_alto_solicitado_cerrado")
    private BigDecimal detAltoSolicitadoCerrado;
    @Column(name = "det_ancho_solicitado_cerrado")
    private BigDecimal detAnchoSolicitadoCerrado;
    @Column(name = "det_cantidad_placas")
    private BigDecimal detCantidadPlacas;
    @Column(name = "det_unidad_cantidad")
    private String detUnidadCantidad;
    @Column(name = "det_valor_unitario")
    private BigDecimal detValorUnitario;
    @Column(name = "det_subTotal")
    private BigDecimal detSubTotal;
    @Column(name = "det_tipo_producto")
    private BigDecimal detTipoProducto;
    @Column(name = "det_tipo_cobro")
    private String detTipoCobro;
    @Column(name = "det_motivo")
    private String detMotivo;
    @Column(name = "det_tipo_detalle")
    private String detTipoDetalle;
    @Column(name = "det_color_anverso")
    private Integer detColorAnverso;
    @Column(name = "det_color_reverso")
    private Integer detColorReverso;
    @Column(name = "det_pliegos")
    private String detPliegos;
    @Column(name = "det_cant_hora_millar")
    private String detCantHoraMillar;
    @Column(name = "det_descuento_mano")
    private BigDecimal detDescuentoMano;
    @Column(name = "det_tipo_trabajo")
    private String detTipoTrabajo;
    @Column(name = "det_descuento_material")
    private BigDecimal detDescuentoMaterial;
     @Column(name = "det_uxf")
    private BigDecimal detUxf;
    @JoinColumn(name = "cod_man_obra", referencedColumnName = "cod_man_obra")
    @ManyToOne
    private ManoDeObra codManObra;
    @JoinColumn(name = "cod_cabida", referencedColumnName = "cod_cabida")
    @ManyToOne
    private Cabida codCabida;
    @JoinColumn(name = "cod_cotizacion", referencedColumnName = "cod_cotizacion")
    @ManyToOne
    private Cotizacion codCotizacion;
    @JoinColumns({
        @JoinColumn(name = "cod_material", referencedColumnName = "cod_material"),
        @JoinColumn(name = "cod_corte", referencedColumnName = "cod_corte")})
    @ManyToOne
    private RecorteMaterial recorteMaterial;
    @JoinColumn(name = "cod_tercearizado", referencedColumnName = "cod_tercearizado")
    @ManyToOne
    private Terciarizado codTercearizado;

    public DetalleCotizacion() {
    }

    public DetalleCotizacion(Integer codDetalle) {
        this.codDetalle = codDetalle;
    }

    public Integer getCodDetalle() {
        return codDetalle;
    }

    public void setCodDetalle(Integer codDetalle) {
        this.codDetalle = codDetalle;
    }

    public String getDetProductoSolicitado() {
        return detProductoSolicitado;
    }

    public void setDetProductoSolicitado(String detProductoSolicitado) {
        this.detProductoSolicitado = detProductoSolicitado;
    }

    public BigDecimal getDetCantidadSolicitada() {
        return detCantidadSolicitada;
    }

    public void setDetCantidadSolicitada(BigDecimal detCantidadSolicitada) {
        this.detCantidadSolicitada = detCantidadSolicitada;
    }

    public BigDecimal getDetAltoSolicitado() {
        return detAltoSolicitado;
    }

    public void setDetAltoSolicitado(BigDecimal detAltoSolicitado) {
        this.detAltoSolicitado = detAltoSolicitado;
    }

    public BigDecimal getDetAnchoSolicitado() {
        return detAnchoSolicitado;
    }

    public void setDetAnchoSolicitado(BigDecimal detAnchoSolicitado) {
        this.detAnchoSolicitado = detAnchoSolicitado;
    }

    public BigDecimal getDetAltoSolicitadoCerrado() {
        return detAltoSolicitadoCerrado;
    }

    public void setDetAltoSolicitadoCerrado(BigDecimal detAltoSolicitadoCerrado) {
        this.detAltoSolicitadoCerrado = detAltoSolicitadoCerrado;
    }

    public BigDecimal getDetAnchoSolicitadoCerrado() {
        return detAnchoSolicitadoCerrado;
    }

    public void setDetAnchoSolicitadoCerrado(BigDecimal detAnchoSolicitadoCerrado) {
        this.detAnchoSolicitadoCerrado = detAnchoSolicitadoCerrado;
    }

    public BigDecimal getDetCantidadPlacas() {
        return detCantidadPlacas;
    }

    public void setDetCantidadPlacas(BigDecimal detCantidadPlacas) {
        this.detCantidadPlacas = detCantidadPlacas;
    }

    public BigDecimal getDetValorUnitario() {
        return detValorUnitario;
    }

    public void setDetValorUnitario(BigDecimal detValorUnitario) {
        this.detValorUnitario = detValorUnitario;
    }

    public BigDecimal getDetSubTotal() {
        return detSubTotal;
    }

    public void setDetSubTotal(BigDecimal detSubTotal) {
        this.detSubTotal = detSubTotal;
    }

    public BigDecimal getDetTipoProducto() {
        return detTipoProducto;
    }

    public void setDetTipoProducto(BigDecimal detTipoProducto) {
        this.detTipoProducto = detTipoProducto;
    }

    public String getDetTipoCobro() {
        return detTipoCobro;
    }

    public void setDetTipoCobro(String detTipoCobro) {
        this.detTipoCobro = detTipoCobro;
    }

    public ManoDeObra getCodManObra() {
        return codManObra;
    }

    public void setCodManObra(ManoDeObra codManObra) {
        this.codManObra = codManObra;
    }

    public Cabida getCodCabida() {
        return codCabida;
    }

    public void setCodCabida(Cabida codCabida) {
        this.codCabida = codCabida;
    }

    public Cotizacion getCodCotizacion() {
        return codCotizacion;
    }

    public void setCodCotizacion(Cotizacion codCotizacion) {
        this.codCotizacion = codCotizacion;
    }

    public RecorteMaterial getRecorteMaterial() {
        return recorteMaterial;
    }

    public void setRecorteMaterial(RecorteMaterial recorteMaterial) {
        this.recorteMaterial = recorteMaterial;
    }

    public Terciarizado getCodTercearizado() {
        return codTercearizado;
    }

    public void setCodTercearizado(Terciarizado codTercearizado) {
        this.codTercearizado = codTercearizado;
    }

    public String getDetUnidadCantidad() {
        return detUnidadCantidad;
    }

    public void setDetUnidadCantidad(String detUnidadCantidad) {
        this.detUnidadCantidad = detUnidadCantidad;
    }

    public String getDetMotivo() {
        return detMotivo;
    }

    public void setDetMotivo(String detMotivo) {
        this.detMotivo = detMotivo;
    }

    public String getDetTipoDetalle() {
        return detTipoDetalle;
    }

    public void setDetTipoDetalle(String detTipoDetalle) {
        this.detTipoDetalle = detTipoDetalle;
    }

    public Integer getDetColorAnverso() {
        return detColorAnverso;
    }

    public void setDetColorAnverso(Integer detColorAnverso) {
        this.detColorAnverso = detColorAnverso;
    }

    public Integer getDetColorReverso() {
        return detColorReverso;
    }

    public void setDetColorReverso(Integer detColorReverso) {
        this.detColorReverso = detColorReverso;
    }

    public String getDetPliegos() {
        return detPliegos;
    }

    public void setDetPliegos(String detPliegos) {
        this.detPliegos = detPliegos;
    }

    public String getDetCantHoraMillar() {
        return detCantHoraMillar;
    }

    public void setDetCantHoraMillar(String detCantHoraMillar) {
        this.detCantHoraMillar = detCantHoraMillar;
    }

    public BigDecimal getDetDescuentoMano() {
        return detDescuentoMano;
    }

    public void setDetDescuentoMano(BigDecimal detDescuentoMano) {
        this.detDescuentoMano = detDescuentoMano;
    }

    public String getDetTipoTrabajo() {
        return detTipoTrabajo;
    }

    public void setDetTipoTrabajo(String detTipoTrabajo) {
        this.detTipoTrabajo = detTipoTrabajo;
    }

    public BigDecimal getDetDescuentoMaterial() {
        return detDescuentoMaterial;
    }

    public void setDetDescuentoMaterial(BigDecimal detDescuentoMaterial) {
        this.detDescuentoMaterial = detDescuentoMaterial;
    }

    public BigDecimal getDetUxf() {
        return detUxf;
    }

    public void setDetUxf(BigDecimal detUxf) {
        this.detUxf = detUxf;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDetalle != null ? codDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCotizacion)) {
            return false;
        }
        DetalleCotizacion other = (DetalleCotizacion) object;
        if ((this.codDetalle == null && other.codDetalle != null) || (this.codDetalle != null && !this.codDetalle.equals(other.codDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DetalleCotizacion[ codDetalle=" + codDetalle + " ]";
    }
}
