/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Darwin
 */
@Entity
@Table(name = "seg_ord_sin_cot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegOrdSinCot.findAll", query = "SELECT s FROM SegOrdSinCot s")
    , @NamedQuery(name = "SegOrdSinCot.findByCodSegSinCotizar", query = "SELECT s FROM SegOrdSinCot s WHERE s.codSegSinCotizar = :codSegSinCotizar")
    , @NamedQuery(name = "SegOrdSinCot.findBySegSinDescripcion", query = "SELECT s FROM SegOrdSinCot s WHERE s.segSinDescripcion = :segSinDescripcion")
    , @NamedQuery(name = "SegOrdSinCot.findBySegResponsable", query = "SELECT s FROM SegOrdSinCot s WHERE s.segResponsable = :segResponsable")
    , @NamedQuery(name = "SegOrdSinCot.findBySegFechaModifica", query = "SELECT s FROM SegOrdSinCot s WHERE s.segFechaModifica = :segFechaModifica")
    , @NamedQuery(name = "SegOrdSinCot.findBySegObservacion", query = "SELECT s FROM SegOrdSinCot s WHERE s.segObservacion = :segObservacion")})
public class SegOrdSinCot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_seg_sin_cotizar")
    private Integer codSegSinCotizar;
 
    @Column(name = "seg_sin_descripcion")
    private String segSinDescripcion;
    @Size(max = 50)
    @Column(name = "seg_responsable")
    private String segResponsable;
    @Column(name = "seg_fecha_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date segFechaModifica;
    @Column(name = "seg_observacion")
    private String segObservacion;
    @Column(name = "seg_sin_numero")
    private Integer segSinNumero;
    @JoinColumn(name = "ord_cod_orden_sin_cotizacion", referencedColumnName = "cod_orden_sin_cotizacion")
    @ManyToOne
    private OrdenSinCotizacion ordCodOrdenSinCotizacion;

    public SegOrdSinCot() {
    }

    public SegOrdSinCot(Integer codSegSinCotizar) {
        this.codSegSinCotizar = codSegSinCotizar;
    }

    public Integer getCodSegSinCotizar() {
        return codSegSinCotizar;
    }

    public void setCodSegSinCotizar(Integer codSegSinCotizar) {
        this.codSegSinCotizar = codSegSinCotizar;
    }

    public String getSegSinDescripcion() {
        return segSinDescripcion;
    }

    public void setSegSinDescripcion(String segSinDescripcion) {
        this.segSinDescripcion = segSinDescripcion;
    }

    public String getSegResponsable() {
        return segResponsable;
    }

    public void setSegResponsable(String segResponsable) {
        this.segResponsable = segResponsable;
    }

    public Date getSegFechaModifica() {
        return segFechaModifica;
    }

    public void setSegFechaModifica(Date segFechaModifica) {
        this.segFechaModifica = segFechaModifica;
    }

    public String getSegObservacion() {
        return segObservacion;
    }

    public void setSegObservacion(String segObservacion) {
        this.segObservacion = segObservacion;
    }

    public OrdenSinCotizacion getOrdCodOrdenSinCotizacion() {
        return ordCodOrdenSinCotizacion;
    }

    public void setOrdCodOrdenSinCotizacion(OrdenSinCotizacion ordCodOrdenSinCotizacion) {
        this.ordCodOrdenSinCotizacion = ordCodOrdenSinCotizacion;
    }

    public Integer getSegSinNumero() {
        return segSinNumero;
    }

    public void setSegSinNumero(Integer segSinNumero) {
        this.segSinNumero = segSinNumero;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSegSinCotizar != null ? codSegSinCotizar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegOrdSinCot)) {
            return false;
        }
        SegOrdSinCot other = (SegOrdSinCot) object;
        if ((this.codSegSinCotizar == null && other.codSegSinCotizar != null) || (this.codSegSinCotizar != null && !this.codSegSinCotizar.equals(other.codSegSinCotizar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.SegOrdSinCot[ codSegSinCotizar=" + codSegSinCotizar + " ]";
    }

}
