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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "cotizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cotizacion.findAllDescendente", query = "SELECT c FROM Cotizacion c ORDER BY c.cotNumero  DESC"),
    @NamedQuery(name = "Cotizacion.findAllCerradas", query = "SELECT NEW imp.utilitario.GraficarCotizacion( c.codCliente.vendedorAsignado,COUNT(c.cotNumero)) FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='A' AND c.cotFechaEmision BETWEEN :fechaInicio AND :fechaFin GROUP BY c.codCliente.vendedorAsignado"),
    @NamedQuery(name = "Cotizacion.findAllFacturadaFecha", query = "SELECT c FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='F' AND c.cotFechaFacturacion BETWEEN :fechaInicio AND :fechaFin ORDER BY  c.cotFechaFacturacion"),
    @NamedQuery(name = "Cotizacion.findPorFechas", query = "SELECT c FROM Cotizacion c WHERE c.cotFechaEmision BETWEEN :fecha1 AND :fecha2 AND c.cotTipoCotizacion <> 'CIGI' ORDER BY c.cotFechaEmision ASC"),
    @NamedQuery(name = "Cotizacion.findPorFechasCerradas", query = "SELECT c FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='T' AND c.cotFechaEmision BETWEEN :fecha1 AND :fecha2 AND c.cotTipoCotizacion <> 'CIGI' ORDER BY c.cotFechaEmision ASC"),
    @NamedQuery(name = "Cotizacion.findPorFechasGeneradas", query = "SELECT c FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='G' AND c.cotFechaEmision BETWEEN :fecha1 AND :fecha2 AND c.cotTipoCotizacion <> 'CIGI' ORDER BY c.cotFechaEmision ASC"),
    @NamedQuery(name = "Cotizacion.findPorFechasGiganto", query = "SELECT c FROM Cotizacion c WHERE c.cotFechaEmision BETWEEN :fecha1 AND :fecha2 AND c.cotTipoCotizacion = 'CIGI' ORDER BY c.cotFechaEmision ASC"),
    @NamedQuery(name = "Cotizacion.findAllLikeNumero", query = "SELECT c FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='G' AND c.cotNumero like :numeroCotizacion ORDER BY c.cotNumero DESC"),
    @NamedQuery(name = "Cotizacion.findAllLikeNumeroFacturadas", query = "SELECT c FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='F' AND c.cotNumero like :numeroCotizacion ORDER BY c.cotNumero  DESC"),
    @NamedQuery(name = "Cotizacion.findAllLikeNumeroCerradas", query = "SELECT c FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='T' AND c.cotNumero like :numeroCotizacion ORDER BY c.cotNumero  DESC"),
    @NamedQuery(name = "Cotizacion.findAllLikeCliente", query = "SELECT c FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='G'  AND c.codCliente.nombreComercial like :cliente ORDER BY c.cotNumero  DESC"),
    @NamedQuery(name = "Cotizacion.findAllLikeClienteFacturadas", query = "SELECT c FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='F' AND c.codCliente.nombreComercial like :cliente ORDER BY c.cotNumero  DESC"),
    @NamedQuery(name = "Cotizacion.findAllLikeClienteCerrada", query = "SELECT c FROM Cotizacion c WHERE  c.codEstCotizacion.estCotSigla='T' AND c.codCliente.nombreComercial like :cliente ORDER BY c.cotNumero  DESC"),
    @NamedQuery(name = "Cotizacion.findByCodCotizacion", query = "SELECT c FROM Cotizacion c WHERE c.codCotizacion = :codCotizacion"),
    @NamedQuery(name = "Cotizacion.findByCotNumero", query = "SELECT c FROM Cotizacion c WHERE c.cotNumero = :cotNumero"),
    @NamedQuery(name = "Cotizacion.findByCotFechaEmision", query = "SELECT c FROM Cotizacion c WHERE c.cotFechaEmision = :cotFechaEmision"),
    @NamedQuery(name = "Cotizacion.findByCotSubtotal", query = "SELECT c FROM Cotizacion c WHERE c.cotSubtotal = :cotSubtotal"),
    @NamedQuery(name = "Cotizacion.findByCotValorAdicional", query = "SELECT c FROM Cotizacion c WHERE c.cotValorAdicional = :cotValorAdicional"),
    @NamedQuery(name = "Cotizacion.findByCotDescuento", query = "SELECT c FROM Cotizacion c WHERE c.cotDescuento = :cotDescuento"),
    @NamedQuery(name = "Cotizacion.findByCotTotal", query = "SELECT c FROM Cotizacion c WHERE c.cotTotal = :cotTotal"),
    @NamedQuery(name = "Cotizacion.findByCotTotalLetras", query = "SELECT c FROM Cotizacion c WHERE c.cotTotalLetras = :cotTotalLetras"),
    @NamedQuery(name = "Cotizacion.findByCotPorcentajeGanancia", query = "SELECT c FROM Cotizacion c WHERE c.cotPorcentajeGanancia = :cotPorcentajeGanancia")})
public class Cotizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_cotizacion")
    private Integer codCotizacion;
    @Size(max = 100)
    @Column(name = "cot_numero")
    private String cotNumero;
    @Column(name = "cot_fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date cotFechaEmision;
    @Column(name = "cot_fecha_facturacion")
    @Temporal(TemporalType.DATE)
    private Date cotFechaFacturacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cot_subtotal")
    private BigDecimal cotSubtotal;
    @Column(name = "cot_valor_adicional")
    private BigDecimal cotValorAdicional;
    @Column(name = "cot_descuento")
    private BigDecimal cotDescuento;
    @Column(name = "cot_total")
    private BigDecimal cotTotal;
    @Size(max = 250)
    @Column(name = "cot_total_letras")
    private String cotTotalLetras;
    @Column(name = "cot_porcentaje_ganancia")
    private BigDecimal cotPorcentajeGanancia;
    @Column(name = "cot_tipo_cotizacion")
    private String cotTipoCotizacion;
    @Column(name = "cot_unificar")
    private String cotUnificar;
    @Column(name = "cot_observacion")
    private String cotObservacion;
    @Size(max = 40)
    @Column(name = "cot_numero_facturado")
    private String cotNumeroFacturado;
    @OneToMany(mappedBy = "codCotizacion")
    private Collection<DetalleCotizacion> detalleCotizacionCollection;
    @JoinColumn(name = "cod_est_cotizacion", referencedColumnName = "cod_est_cotizacion")
    @ManyToOne
    private EstadoCotizacion codEstCotizacion;
    @JoinColumn(name = "cod_parametrizacion", referencedColumnName = "cod_parametrizacion")
    @ManyToOne
    private ParametrizacionPorcentajes codParametrizacion;
    @JoinColumn(name = "cod_cliente", referencedColumnName = "cod_cliente")
    @ManyToOne
    private Cliente codCliente;
    @OneToMany(mappedBy = "codCotizacion")
    private Collection<DocumentoGenerado> documentoGeneradoCollection;
    @OneToMany(mappedBy = "codCotizacion")
    private Collection<OrdenDeProduccion> ordenDeProduccionCollection;
    @Transient
    private String tipoCotizacion = "";

    public Cotizacion() {
    }

    public Cotizacion(String cotNumero, Date cotFechaEmision, BigDecimal cotSubtotal, BigDecimal cotTotal, BigDecimal cotPorcentajeGanancia, Cliente codCliente, String cotObservacion) {
        this.cotNumero = cotNumero;
        this.cotFechaEmision = cotFechaEmision;
        this.cotSubtotal = cotSubtotal;
        this.cotTotal = cotTotal;
        this.cotPorcentajeGanancia = cotPorcentajeGanancia;
        this.codCliente = codCliente;
        this.cotObservacion = cotObservacion;
    }

    public Cotizacion(String cotNumero, Date cotFechaEmision, BigDecimal cotSubtotal, BigDecimal cotTotal, BigDecimal cotPorcentajeGanancia, Cliente codCliente) {
        this.cotNumero = cotNumero;
        this.cotFechaEmision = cotFechaEmision;
        this.cotSubtotal = cotSubtotal;
        this.cotTotal = cotTotal;
        this.cotPorcentajeGanancia = cotPorcentajeGanancia;
        this.codCliente = codCliente;

    }

    public Cotizacion(Integer codCotizacion) {
        this.codCotizacion = codCotizacion;
    }

    public Integer getCodCotizacion() {
        return codCotizacion;
    }

    public void setCodCotizacion(Integer codCotizacion) {
        this.codCotizacion = codCotizacion;
    }

    public String getCotNumero() {
        return cotNumero;
    }

    public void setCotNumero(String cotNumero) {
        this.cotNumero = cotNumero;
    }

    public Date getCotFechaEmision() {
        return cotFechaEmision;
    }

    public void setCotFechaEmision(Date cotFechaEmision) {
        this.cotFechaEmision = cotFechaEmision;
    }

    public BigDecimal getCotSubtotal() {
        return cotSubtotal;
    }

    public void setCotSubtotal(BigDecimal cotSubtotal) {
        this.cotSubtotal = cotSubtotal;
    }

    public BigDecimal getCotValorAdicional() {
        return cotValorAdicional;
    }

    public void setCotValorAdicional(BigDecimal cotValorAdicional) {
        this.cotValorAdicional = cotValorAdicional;
    }

    public BigDecimal getCotDescuento() {
        return cotDescuento;
    }

    public void setCotDescuento(BigDecimal cotDescuento) {
        this.cotDescuento = cotDescuento;
    }

    public BigDecimal getCotTotal() {
        return cotTotal;
    }

    public void setCotTotal(BigDecimal cotTotal) {
        this.cotTotal = cotTotal;
    }

    public String getCotTotalLetras() {
        return cotTotalLetras;
    }

    public void setCotTotalLetras(String cotTotalLetras) {
        this.cotTotalLetras = cotTotalLetras;
    }

    public BigDecimal getCotPorcentajeGanancia() {
        return cotPorcentajeGanancia;
    }

    public void setCotPorcentajeGanancia(BigDecimal cotPorcentajeGanancia) {
        this.cotPorcentajeGanancia = cotPorcentajeGanancia;
    }

    public String getCotTipoCotizacion() {
        return cotTipoCotizacion;
    }

    public void setCotTipoCotizacion(String cotTipoCotizacion) {
        this.cotTipoCotizacion = cotTipoCotizacion;
    }

    @XmlTransient
    public Collection<DetalleCotizacion> getDetalleCotizacionCollection() {
        return detalleCotizacionCollection;
    }

    public void setDetalleCotizacionCollection(Collection<DetalleCotizacion> detalleCotizacionCollection) {
        this.detalleCotizacionCollection = detalleCotizacionCollection;
    }

    public EstadoCotizacion getCodEstCotizacion() {
        return codEstCotizacion;
    }

    public void setCodEstCotizacion(EstadoCotizacion codEstCotizacion) {
        this.codEstCotizacion = codEstCotizacion;
    }

    public ParametrizacionPorcentajes getCodParametrizacion() {
        return codParametrizacion;
    }

    public void setCodParametrizacion(ParametrizacionPorcentajes codParametrizacion) {
        this.codParametrizacion = codParametrizacion;
    }

    public Cliente getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Cliente codCliente) {
        this.codCliente = codCliente;
    }

    public String getCotUnificar() {
        return cotUnificar;
    }

    public void setCotUnificar(String cotUnificar) {
        this.cotUnificar = cotUnificar;
    }

    public String getCotObservacion() {
        return cotObservacion;
    }

    public void setCotObservacion(String cotObservacion) {
        this.cotObservacion = cotObservacion;
    }

    public String getTipoCotizacion() {
        if (getCotTipoCotizacion().equals("CIG")) {
            tipoCotizacion = "IMPRESO GENERAL";
        }
        if (getCotTipoCotizacion().equals("CIC")) {
            tipoCotizacion = "IMPRESO COMPAGINADO";
        }
        if (getCotTipoCotizacion().equals("CIDIG")) {
            tipoCotizacion = "DIGITAL GENERAL";
        }
        if (getCotTipoCotizacion().equals("CIDIC")) {
            tipoCotizacion = "DIGITAL COMPAGINADO";
        }
        if (getCotTipoCotizacion().equals("CIGI")) {
            tipoCotizacion = "GIGANTOGRAFIA";
        }
        return tipoCotizacion;
    }

    public void setTipoCotizacion(String tipoCotizacion) {
        this.tipoCotizacion = tipoCotizacion;
    }

    public String getCotNumeroFacturado() {
        return cotNumeroFacturado;
    }

    public void setCotNumeroFacturado(String cotNumeroFacturado) {
        this.cotNumeroFacturado = cotNumeroFacturado;
    }

    public Date getCotFechaFacturacion() {
        return cotFechaFacturacion;
    }

    public void setCotFechaFacturacion(Date cotFechaFacturacion) {
        this.cotFechaFacturacion = cotFechaFacturacion;
    }
    
    

    @XmlTransient
    public Collection<DocumentoGenerado> getDocumentoGeneradoCollection() {
        return documentoGeneradoCollection;
    }

    public void setDocumentoGeneradoCollection(Collection<DocumentoGenerado> documentoGeneradoCollection) {
        this.documentoGeneradoCollection = documentoGeneradoCollection;
    }

    @XmlTransient
    public Collection<OrdenDeProduccion> getOrdenDeProduccionCollection() {
        return ordenDeProduccionCollection;
    }

    public void setOrdenDeProduccionCollection(Collection<OrdenDeProduccion> ordenDeProduccionCollection) {
        this.ordenDeProduccionCollection = ordenDeProduccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCotizacion != null ? codCotizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cotizacion)) {
            return false;
        }
        Cotizacion other = (Cotizacion) object;
        if ((this.codCotizacion == null && other.codCotizacion != null) || (this.codCotizacion != null && !this.codCotizacion.equals(other.codCotizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.Cotizacion[ codCotizacion=" + codCotizacion + " ]";
    }
}
