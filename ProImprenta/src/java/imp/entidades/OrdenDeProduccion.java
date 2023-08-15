/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "orden_de_produccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenDeProduccion.findAllDes", query = "SELECT o FROM OrdenDeProduccion o ORDER BY  o.ordSecuencial  DESC"),
    @NamedQuery(name = "OrdenDeProduccion.findAllPendientes", query = "SELECT o FROM OrdenDeProduccion o WHERE o.codEstadoOrden.estOrdSigla='G' ORDER BY o.fechaDespacho ASC"),
    @NamedQuery(name = "OrdenDeProduccion.findAllLikeNumero", query = "SELECT o FROM OrdenDeProduccion o WHERE o.ordNumero LIKE :numero"),
    @NamedQuery(name = "OrdenDeProduccion.findAllPendientesCliente", query = "SELECT o FROM OrdenDeProduccion o WHERE o.codEstadoOrden.estOrdSigla='G' AND o.codCotizacion.codCliente.nombreComercial LIKE :nombre ORDER BY o.fechaDespacho DESC"),
    @NamedQuery(name = "OrdenDeProduccion.findAllFechas", query = "SELECT o FROM OrdenDeProduccion o WHERE o.fechaCreacion  BETWEEN :fechaInicio AND :fechaFin  AND o.codEstadoOrden.estOrdSigla='G' ORDER BY o.fechaDespacho DESC"),
    @NamedQuery(name = "OrdenDeProduccion.findAllNombreFechas", query = "SELECT o FROM OrdenDeProduccion o WHERE o.fechaCreacion  BETWEEN :fechaInicio AND :fechaFin AND o.codCotizacion.codCliente.nombreComercial LIKE :nombre AND o.codEstadoOrden.estOrdSigla='G' ORDER BY o.fechaDespacho DESC"),
    @NamedQuery(name = "OrdenDeProduccion.findAllCerradas", query = "SELECT o FROM OrdenDeProduccion o WHERE o.codEstadoOrden.estOrdSigla='F' ORDER BY o.fechaDespacho ASC"),
    @NamedQuery(name = "OrdenDeProduccion.findAllLikeNumero", query = "SELECT o FROM OrdenDeProduccion o WHERE o.ordNumero LIKE :numero"),
    @NamedQuery(name = "OrdenDeProduccion.findAllCerradasCliente", query = "SELECT o FROM OrdenDeProduccion o WHERE o.codEstadoOrden.estOrdSigla='F' AND o.codCotizacion.codCliente.nombreComercial LIKE :nombre ORDER BY o.fechaDespacho DESC"),
    @NamedQuery(name = "OrdenDeProduccion.findAllPendientesNumero", query = "SELECT o FROM OrdenDeProduccion o WHERE o.codEstadoOrden.estOrdSigla='G' AND o.ordSecuencial LIKE :numero ORDER BY o.fechaDespacho DESC"),
    @NamedQuery(name = "OrdenDeProduccion.findAllCerradasNumero", query = "SELECT o FROM OrdenDeProduccion o WHERE o.codEstadoOrden.estOrdSigla='F' AND o.ordSecuencial LIKE :numero ORDER BY o.fechaDespacho DESC"),
    @NamedQuery(name = "OrdenDeProduccion.findByCodOrdenProduccion", query = "SELECT o FROM OrdenDeProduccion o WHERE o.codOrdenProduccion = :codOrdenProduccion"),
    @NamedQuery(name = "OrdenDeProduccion.findByOrdNumero", query = "SELECT o FROM OrdenDeProduccion o WHERE o.ordNumero = :ordNumero"),
    @NamedQuery(name = "OrdenDeProduccion.findByFechaCreacion", query = "SELECT o FROM OrdenDeProduccion o WHERE o.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "OrdenDeProduccion.findCotizacion", query = "SELECT o FROM OrdenDeProduccion o WHERE o.codCotizacion= :codCotizacion"),
    @NamedQuery(name = "OrdenDeProduccion.findOrdenForCotizacion", query = "SELECT o FROM OrdenDeProduccion o WHERE o.codCotizacion= :codCotizacion"),
    @NamedQuery(name = "OrdenDeProduccion.findByFechaDespacho", query = "SELECT o FROM OrdenDeProduccion o WHERE o.fechaDespacho = :fechaDespacho")})
public class OrdenDeProduccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_orden_produccion")
    private Integer codOrdenProduccion;
    @Size(max = 100)
    @Column(name = "ord_numero")
    private String ordNumero;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "fecha_despacho")
    @Temporal(TemporalType.DATE)
    private Date fechaDespacho;
    @JoinColumn(name = "cod_estado_orden", referencedColumnName = "cod_estado_orden")
    @ManyToOne
    private EstadoOrdenProduccion codEstadoOrden;
    @JoinColumn(name = "cod_cotizacion", referencedColumnName = "cod_cotizacion")
    @ManyToOne
    private Cotizacion codCotizacion;
    @JoinColumn(name = "cod_responsable", referencedColumnName = "cod_responsable")
    @ManyToOne
    private Responsable codResponsable;
    @Column(name = "ord_descripcion")
    private String ordDescripcion;
    @Column(name = "ord_secuencial")
    private String ordSecuencial;
    @Lob
    @Column(name = "ord_pdf")
//    private byte[] ordPdf = new byte[1024 * 1024];
    private byte[] ordPdf = null;
    @Lob
    @Column(name = "ord_img")
    private byte[] ordImg = null;
    @Column(name = "ord_responsable")
    private String ordResponsable;
    @Column(name = "ord_hora_cerre")
    @Temporal(TemporalType.TIME)
    private Date ordHoraCerre;
    @Column(name = "ord_fecha_cierre")
    @Temporal(TemporalType.DATE)
    private Date ordFechaCierre;
    @Transient
    private AMedia fileContent = null;
    @Transient
    private AImage fotoGeneral = null;
    @Column(name = "ord_estado_cierre")
    private String ordEstadoCierre;
    @Column(name = "ord_hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date ordHoraInicio;
    @Column(name = "ord_hora_despacho")
    @Temporal(TemporalType.TIME)
    private Date ordHoraDespacho;
    @Transient
    private String contrasena;
    @Transient
    private String usuario;

    public OrdenDeProduccion() {
    }

    public OrdenDeProduccion(Integer codOrdenProduccion) {
        this.codOrdenProduccion = codOrdenProduccion;
    }

    public Integer getCodOrdenProduccion() {
        return codOrdenProduccion;
    }

    public void setCodOrdenProduccion(Integer codOrdenProduccion) {
        this.codOrdenProduccion = codOrdenProduccion;
    }

    public String getOrdNumero() {
        return ordNumero;
    }

    public void setOrdNumero(String ordNumero) {
        this.ordNumero = ordNumero;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(Date fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public EstadoOrdenProduccion getCodEstadoOrden() {
        return codEstadoOrden;
    }

    public void setCodEstadoOrden(EstadoOrdenProduccion codEstadoOrden) {
        this.codEstadoOrden = codEstadoOrden;
    }

    public Cotizacion getCodCotizacion() {
        return codCotizacion;
    }

    public void setCodCotizacion(Cotizacion codCotizacion) {
        this.codCotizacion = codCotizacion;
    }

    public Responsable getCodResponsable() {
        return codResponsable;
    }

    public void setCodResponsable(Responsable codResponsable) {
        this.codResponsable = codResponsable;
    }

    public String getOrdDescripcion() {
        return ordDescripcion;
    }

    public void setOrdDescripcion(String ordDescripcion) {
        this.ordDescripcion = ordDescripcion;
    }

    public String getOrdSecuencial() {
        return ordSecuencial;
    }

    public void setOrdSecuencial(String ordSecuencial) {
        this.ordSecuencial = ordSecuencial;
    }

    public byte[] getOrdPdf() {
        return ordPdf;
    }

    public void setOrdPdf(byte[] ordPdf) {
        this.ordPdf = ordPdf;
    }

    public String getOrdResponsable() {
        return ordResponsable;
    }

    public void setOrdResponsable(String ordResponsable) {
        this.ordResponsable = ordResponsable;
    }

    public Date getOrdHoraCerre() {
        return ordHoraCerre;
    }

    public void setOrdHoraCerre(Date ordHoraCerre) {
        this.ordHoraCerre = ordHoraCerre;
    }

    public Date getOrdFechaCierre() {
        return ordFechaCierre;
    }

    public void setOrdFechaCierre(Date ordFechaCierre) {
        this.ordFechaCierre = ordFechaCierre;
    }

    public AMedia getFileContent() {

        if (getOrdPdf() != null) {
            fileContent = new AMedia("report", "pdf", "application/pdf", getOrdPdf());
        }

        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

    public byte[] getOrdImg() {
        return ordImg;
    }

    public void setOrdImg(byte[] ordImg) {
        this.ordImg = ordImg;
    }

    public AImage getFotoGeneral() throws IOException {
        if (getOrdImg() != null) {
            fotoGeneral = new AImage("fotoGeneral", getOrdImg());
        }
        return fotoGeneral;
    }

    public void setFotoGeneral(AImage fotoGeneral) {
        this.fotoGeneral = fotoGeneral;
    }

    public String getOrdEstadoCierre() {
        return ordEstadoCierre;
    }

    public void setOrdEstadoCierre(String ordEstadoCierre) {
        this.ordEstadoCierre = ordEstadoCierre;
    }

    public Date getOrdHoraInicio() {
        return ordHoraInicio;
    }

    public void setOrdHoraInicio(Date ordHoraInicio) {
        this.ordHoraInicio = ordHoraInicio;
    }

    public Date getOrdHoraDespacho() {
        return ordHoraDespacho;
    }

    public void setOrdHoraDespacho(Date ordHoraDespacho) {
        this.ordHoraDespacho = ordHoraDespacho;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codOrdenProduccion != null ? codOrdenProduccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenDeProduccion)) {
            return false;
        }
        OrdenDeProduccion other = (OrdenDeProduccion) object;
        if ((this.codOrdenProduccion == null && other.codOrdenProduccion != null) || (this.codOrdenProduccion != null && !this.codOrdenProduccion.equals(other.codOrdenProduccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.OrdenDeProduccion[ codOrdenProduccion=" + codOrdenProduccion + " ]";
    }
}
