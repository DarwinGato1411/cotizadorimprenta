/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;

/**
 *
 * @author gato
 */
@Entity
@Cacheable(false)
@Table(name = "orden_sin_cotizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenSinCotizacion.findAll", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinEstado='GENERADA'  ORDER BY o.sinHoraCierre, o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllFacturadas", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinEstado='FACTURADO'  ORDER BY o.sinHoraCierre, o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllCerradas", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinEstado='FINALIZADA' ORDER BY o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllDesCierre", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinEstado='GENERADA' ORDER BY o.sinFechaCierre,o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllPendienteCliente", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.codCliente.razonSocial like :nombre and o.sinEstado='GENERADA' ORDER BY o.sinFechaCierre, o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllPendienteFechasSin", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaEmision BETWEEN :fechaInicio AND :fechaFin  and o.sinEstado='GENERADA' ORDER BY o.sinFechaCierre, o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllPendienteNombreFechasSin", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaEmision BETWEEN :fechaInicio AND :fechaFin  and o.sinEstado='GENERADA' AND o.codCliente.razonSocial like :nombre ORDER BY o.sinFechaCierre, o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllPendienteClienteFacturado", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.codCliente.razonSocial like :nombre and o.sinEstado='FACTURADO' ORDER BY o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllCerradaCliente", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.codCliente.razonSocial like :nombre and o.sinEstado='FINALIZADA' ORDER BY o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllCerradanombreComercial", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.codCliente.nombreComercial like :nombre and o.sinEstado = :sinEstado ORDER BY o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllPendienteNumero", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinNumero =:numero and o.sinEstado='GENERADA' ORDER BY o.sinFechaCierre, o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllPendienteNumeroFacturado", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinNumero =:numero and o.sinEstado='FACTURADO' ORDER BY o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllFechasFacturada", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaFacturacion BETWEEN :inicio AND :fin and o.sinEstado='FACTURADO' ORDER BY o.sinFechaFacturacion DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllNombreFechasFacturada", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaFacturacion BETWEEN :inicio AND :fin and o.sinEstado='FACTURADO' AND o.codCliente.razonSocial like :nombre ORDER BY o.sinFechaFacturacion DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllFechasFacturadaGenerada", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaEmision BETWEEN :inicio AND :fin and o.sinEstado='GENERADA' ORDER BY o.sinFechaFacturacion DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllNombreFechasFacturadaGenerada", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaEmision BETWEEN :inicio AND :fin and o.sinEstado='GENERADA' AND o.codCliente.razonSocial like :nombre ORDER BY o.sinFechaFacturacion DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllFechasFacturadaCerrada", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaEmision BETWEEN :inicio AND :fin and o.sinEstado='FINALIZADA' ORDER BY o.sinFechaFacturacion DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllNombreFechasFacturadaCerrada", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaEmision BETWEEN :inicio AND :fin and o.sinEstado='FINALIZADA' AND o.codCliente.razonSocial like :nombre ORDER BY o.sinFechaFacturacion DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllCerradaNumero", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinNumero =:numero and o.sinEstado='FINALIZADA' ORDER BY o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findAllDes", query = "SELECT o FROM OrdenSinCotizacion o  ORDER BY o.sinNumero DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findByCodOrdenSinCotizacion", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.codOrdenSinCotizacion = :codOrdenSinCotizacion")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinNumero", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinNumero = :sinNumero")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinCosto", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinCosto = :sinCosto")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinObservacion", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinObservacion = :sinObservacion")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinFechaEmision", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaEmision = :sinFechaEmision")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinFechaInicio", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaInicio = :sinFechaInicio")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinHoraInicio", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinHoraInicio = :sinHoraInicio")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinFechaCierre", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinFechaCierre = :sinFechaCierre")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinHoraCierre", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinHoraCierre = :sinHoraCierre")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinEstado", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinEstado = :sinEstado")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinNumeroFactura", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinNumeroFactura LIKE :sinNumeroFactura and o.sinEstado= :sinEstado ORDER BY o.sinFechaCierre, o.sinHoraCierre DESC")
    ,
    @NamedQuery(name = "OrdenSinCotizacion.findBySinResponsable", query = "SELECT o FROM OrdenSinCotizacion o WHERE o.sinResponsable = :sinResponsable")})
public class OrdenSinCotizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_orden_sin_cotizacion")
    private Integer codOrdenSinCotizacion;
    @Column(name = "sin_numero")
    private Integer sinNumero;
//    @Column(name = "sin_cliente")
//    private String sinCliente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Column(name = "sin_cantidad")
//    private BigDecimal sinCantidad;
    @Size(max = 150)
    @Column(name = "sin_descripcion")
    private String sinDescripcion;
    @Column(name = "sin_costo")
    private BigDecimal sinCosto;
    @Column(name = "sin_observacion")
    private String sinObservacion;
    @Lob
    @Column(name = "sin_pdf")
    private byte[] sinPdf;
    @Lob
    @Column(name = "sin_imagen")
    private byte[] sinImagen;
    @Column(name = "sin_fecha_emision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sinFechaEmision;
    @Column(name = "sin_fecha_facturacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sinFechaFacturacion;
    @Column(name = "sin_fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sinFechaInicio;
    @Column(name = "sin_hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date sinHoraInicio;
    @Column(name = "sin_fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sinFechaCierre;
    @Column(name = "sin_hora_cierre")
    @Temporal(TemporalType.TIME)
    private Date sinHoraCierre;
    @Column(name = "sin_estado")
    private String sinEstado;
    @Column(name = "sin_responsable")
    private String sinResponsable;
    @Column(name = "sin_utilidad")
    private BigDecimal sinUtilidad;
    @Column(name = "sin_total_utilidad")
    private BigDecimal sinTotalUtilidad;
    @Size(max = 40)
    @Column(name = "sin_numero_factura")
    private String sinNumeroFactura;
    @Size(max = 40)
    @Column(name = "sin_tipo_cotizacion")
    private String sinTipoCotizacion;
    @Column(name = "sin_fecha_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sinFechaModifica;
    @Size(max = 40)
    @Column(name = "sin_quien_crea")
    private String sinQuienCrea;
    @Size(max = 40)
    @Column(name = "sin_quien_modifica")
    private String sinQuienModifica;

    @Column(name = "sin_detalle_trabajo")
    private String sinDetalleTrabajo;
    @Transient
    private String contrasena;
    @Transient
    private String usuario;
    @Transient
    private AMedia fileContent = null;
    @Transient
    private AImage fotoGeneral = null;
    @Transient
    private AMedia fileContentOriginal = null;
    @Transient
    private AImage fotoGeneralOriginal = null;
    @Transient
    private Boolean editaImagen = Boolean.FALSE;
    @Transient
    private Boolean editaPDF = Boolean.FALSE;
    @Column(name = "sin_adj_pdf")
    private Boolean sinAdjPdf;
    @Column(name = "sin_adj_img")
    private Boolean sinDdjImg;
    @Transient
    private String tipoOperacion = "NUEVO";
    
    @JoinColumn(name = "cod_cliente", referencedColumnName = "cod_cliente")
    @ManyToOne
    private Cliente codCliente;
    @OneToMany(mappedBy = "codOrdenSinCotizacion")
    private Collection<DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection;
    @OneToMany(mappedBy = "ordCodOrdenSinCotizacion")
    private Collection<SegOrdSinCot> detalleSegOrdSinCotCollection;

    public OrdenSinCotizacion() {
    }

    public OrdenSinCotizacion(Integer codOrdenSinCotizacion) {
        this.codOrdenSinCotizacion = codOrdenSinCotizacion;
    }

    public Integer getCodOrdenSinCotizacion() {
        return codOrdenSinCotizacion;
    }

    public void setCodOrdenSinCotizacion(Integer codOrdenSinCotizacion) {
        this.codOrdenSinCotizacion = codOrdenSinCotizacion;
    }

    public Integer getSinNumero() {
        return sinNumero;
    }

    public void setSinNumero(Integer sinNumero) {
        this.sinNumero = sinNumero;
    }

    //    public String getSinCliente() {
    //        return sinCliente;
    //    }
    //
    //    public void setSinCliente(String sinCliente) {
    //        this.sinCliente = sinCliente;
    //    }
    //
    //    public BigDecimal getSinCantidad() {
    //        return sinCantidad;
    //    }
    //
    //    public void setSinCantidad(BigDecimal sinCantidad) {
    //        this.sinCantidad = sinCantidad;
    //    }
    //
    //    public String getSinDescripcion() {
    //        return sinDescripcion;
    //    }
    //
    //    public void setSinDescripcion(String sinDescripcion) {
    //        this.sinDescripcion = sinDescripcion;
    //    }
    public String getSinNumeroFactura() {
        return sinNumeroFactura;
    }

    public void setSinNumeroFactura(String sinNumeroFactura) {
        this.sinNumeroFactura = sinNumeroFactura;
    }

    public BigDecimal getSinCosto() {
        return sinCosto;
    }

    public void setSinCosto(BigDecimal sinCosto) {
        this.sinCosto = sinCosto;
    }

    public String getSinObservacion() {
        return sinObservacion;
    }

    public void setSinObservacion(String sinObservacion) {
        this.sinObservacion = sinObservacion;
    }

    public byte[] getSinPdf() {
        return sinPdf;
    }

    public void setSinPdf(byte[] sinPdf) {
        this.sinPdf = sinPdf;
    }

    public byte[] getSinImagen() {
        return sinImagen;
    }

    public void setSinImagen(byte[] sinImagen) {
        this.sinImagen = sinImagen;
    }

    public Date getSinFechaEmision() {
        return sinFechaEmision;
    }

    public void setSinFechaEmision(Date sinFechaEmision) {
        this.sinFechaEmision = sinFechaEmision;
    }

    public Date getSinFechaInicio() {
        return sinFechaInicio;
    }

    public void setSinFechaInicio(Date sinFechaInicio) {
        this.sinFechaInicio = sinFechaInicio;
    }

    public Date getSinHoraInicio() {
        return sinHoraInicio;
    }

    public void setSinHoraInicio(Date sinHoraInicio) {
        this.sinHoraInicio = sinHoraInicio;
    }

    public Date getSinFechaCierre() {
        return sinFechaCierre;
    }

    public void setSinFechaCierre(Date sinFechaCierre) {
        this.sinFechaCierre = sinFechaCierre;
    }

    public Date getSinHoraCierre() {
        return sinHoraCierre;
    }

    public void setSinHoraCierre(Date sinHoraCierre) {
        this.sinHoraCierre = sinHoraCierre;
    }

    public String getSinEstado() {
        return sinEstado;
    }

    public void setSinEstado(String sinEstado) {
        this.sinEstado = sinEstado;
    }

    public String getSinResponsable() {
        return sinResponsable;
    }

    public void setSinResponsable(String sinResponsable) {
        this.sinResponsable = sinResponsable;
    }

    public Cliente getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Cliente codCliente) {
        this.codCliente = codCliente;
    }

    public AMedia getFileContent() {
        if (getSinPdf() != null) {
            fileContent = new AMedia("report", "pdf", "application/pdf", getSinPdf());
        }
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

    public AImage getFotoGeneral() throws IOException {
        if (getSinImagen() != null) {
            fotoGeneral = new AImage("fotoGeneral", getSinImagen());
        }
        return fotoGeneral;
    }

    public void setFotoGeneral(AImage fotoGeneral) {
        this.fotoGeneral = fotoGeneral;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSinUtilidad() {
        return sinUtilidad;
    }

    public void setSinUtilidad(BigDecimal sinUtilidad) {
        this.sinUtilidad = sinUtilidad;
    }

    public BigDecimal getSinTotalUtilidad() {
        return sinTotalUtilidad;
    }

    public void setSinTotalUtilidad(BigDecimal sinTotalUtilidad) {
        this.sinTotalUtilidad = sinTotalUtilidad;
    }

    public String getSinTipoCotizacion() {
        return sinTipoCotizacion;
    }

    public void setSinTipoCotizacion(String sinTipoCotizacion) {
        this.sinTipoCotizacion = sinTipoCotizacion;
    }

    public String getSinDescripcion() {
        return sinDescripcion;
    }

    public void setSinDescripcion(String sinDescripcion) {
        this.sinDescripcion = sinDescripcion;
    }

    public Date getSinFechaFacturacion() {
        return sinFechaFacturacion;
    }

    public void setSinFechaFacturacion(Date sinFechaFacturacion) {
        this.sinFechaFacturacion = sinFechaFacturacion;
    }

    public Date getSinFechaModifica() {
        return sinFechaModifica;
    }

    public void setSinFechaModifica(Date sinFechaModifica) {
        this.sinFechaModifica = sinFechaModifica;
    }

    public String getSinQuienCrea() {
        return sinQuienCrea;
    }

    public void setSinQuienCrea(String sinQuienCrea) {
        this.sinQuienCrea = sinQuienCrea;
    }

    public String getSinQuienModifica() {
        return sinQuienModifica;
    }

    public void setSinQuienModifica(String sinQuienModifica) {
        this.sinQuienModifica = sinQuienModifica;
    }

    public Boolean isSinAdjPdf() {
        return sinAdjPdf;
    }

    public void setSinAdjPdf(Boolean sinAdjPdf) {
        this.sinAdjPdf = sinAdjPdf;
    }

    public Boolean isSinDdjImg() {
        return sinDdjImg;
    }

    public void setSinDdjImg(Boolean sinDdjImg) {
        this.sinDdjImg = sinDdjImg;
    }

    public String getSinDetalleTrabajo() {
        return sinDetalleTrabajo;
    }

    public void setSinDetalleTrabajo(String sinDetalleTrabajo) {
        this.sinDetalleTrabajo = sinDetalleTrabajo;
    }

    @XmlTransient
    public Collection<DetalleOrdenSinCotizar> getDetalleOrdenSinCotizarCollection() {
        return detalleOrdenSinCotizarCollection;
    }

    public void setDetalleOrdenSinCotizarCollection(Collection<DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection) {
        this.detalleOrdenSinCotizarCollection = detalleOrdenSinCotizarCollection;
    }

    public Collection<SegOrdSinCot> getDetalleSegOrdSinCotCollection() {
        return detalleSegOrdSinCotCollection;
    }

    public void setDetalleSegOrdSinCotCollection(Collection<SegOrdSinCot> detalleSegOrdSinCotCollection) {
        this.detalleSegOrdSinCotCollection = detalleSegOrdSinCotCollection;
    }

    public AMedia getFileContentOriginal() {
        return fileContentOriginal;
    }

    public void setFileContentOriginal(AMedia fileContentOriginal) {
        this.fileContentOriginal = fileContentOriginal;
    }

    public AImage getFotoGeneralOriginal() {
        return fotoGeneralOriginal;
    }

    public void setFotoGeneralOriginal(AImage fotoGeneralOriginal) {
        this.fotoGeneralOriginal = fotoGeneralOriginal;
    }

    public Boolean getEditaImagen() {
        return editaImagen;
    }

    public void setEditaImagen(Boolean editaImagen) {
        this.editaImagen = editaImagen;
    }

    public Boolean getEditaPDF() {
        return editaPDF;
    }

    public void setEditaPDF(Boolean editaPDF) {
        this.editaPDF = editaPDF;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codOrdenSinCotizacion != null ? codOrdenSinCotizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenSinCotizacion)) {
            return false;
        }
        OrdenSinCotizacion other = (OrdenSinCotizacion) object;
        if ((this.codOrdenSinCotizacion == null && other.codOrdenSinCotizacion != null) || (this.codOrdenSinCotizacion != null && !this.codOrdenSinCotizacion.equals(other.codOrdenSinCotizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        // myDate is the java.util.Date in yyyy-mm-dd format
        // Converting it into String using formatter

        String cambiosGenerados = "OrdenSinCotizacion, " + "codOrdenSinCotizacion="
                + codOrdenSinCotizacion + ", sinNumero=" + sinNumero + ", sinDescripcion="
                + sinDescripcion + ", sinObservacion="
                + sinObservacion + ", sinFechaEmision=" + sm.format(sinFechaEmision)
                + ", sinFechaInicio=" + sm.format(sinFechaInicio) + ", sinHoraInicio="
                + fhora.format(sinHoraInicio) + ", sinFechaCierre=" + sm.format(sinFechaCierre) + ", sinHoraCierre="
                + fhora.format(sinHoraCierre) + ", sinEstado=" + sinEstado + ", sinResponsable="
                + sinResponsable + ", sinTipoCotizacion="
                + sinTipoCotizacion + ", sinQuienCrea="
                + sinQuienCrea + ", sinQuienModifica=" + sinQuienModifica + ", sinFechaModifica=" + sm.format(sinFechaModifica)
                + ", sinDetalleTrabajo=" + sinDetalleTrabajo + ", Cliente=" + codCliente.getNombreComercial();
        String valorPDF = " ";
        if (fileContent != null) {
            valorPDF = (editaPDF) ? ", Cambio el PDF" : "";
        }
        String valorIMG = " ";
        if (fotoGeneral != null) {
            valorIMG = (editaImagen) ? ", Cambio la Imagen " : "";
        }

        cambiosGenerados = cambiosGenerados + valorPDF;
        cambiosGenerados = cambiosGenerados + valorIMG;
        return cambiosGenerados;
    }

}
