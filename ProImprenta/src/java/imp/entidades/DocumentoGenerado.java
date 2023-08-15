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
@Table(name = "documento_generado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoGenerado.findAll", query = "SELECT d FROM DocumentoGenerado d"),
    @NamedQuery(name = "DocumentoGenerado.findByCodDocGenerado", query = "SELECT d FROM DocumentoGenerado d WHERE d.codDocGenerado = :codDocGenerado"),
    @NamedQuery(name = "DocumentoGenerado.findByDocGenFecha", query = "SELECT d FROM DocumentoGenerado d WHERE d.docGenFecha = :docGenFecha"),
    @NamedQuery(name = "DocumentoGenerado.findByDocGenResponsable", query = "SELECT d FROM DocumentoGenerado d WHERE d.docGenResponsable = :docGenResponsable")})
public class DocumentoGenerado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_doc_generado")
    private Integer codDocGenerado;
    @Column(name = "doc_gen_fecha")
    @Temporal(TemporalType.DATE)
    private Date docGenFecha;
    @Size(max = 150)
    @Column(name = "doc_gen_responsable")
    private String docGenResponsable;
    @JoinColumn(name = "cod_cotizacion", referencedColumnName = "cod_cotizacion")
    @ManyToOne
    private Cotizacion codCotizacion;

    public DocumentoGenerado() {
    }

    public DocumentoGenerado(Integer codDocGenerado) {
        this.codDocGenerado = codDocGenerado;
    }

    public Integer getCodDocGenerado() {
        return codDocGenerado;
    }

    public void setCodDocGenerado(Integer codDocGenerado) {
        this.codDocGenerado = codDocGenerado;
    }

    public Date getDocGenFecha() {
        return docGenFecha;
    }

    public void setDocGenFecha(Date docGenFecha) {
        this.docGenFecha = docGenFecha;
    }

    public String getDocGenResponsable() {
        return docGenResponsable;
    }

    public void setDocGenResponsable(String docGenResponsable) {
        this.docGenResponsable = docGenResponsable;
    }

    public Cotizacion getCodCotizacion() {
        return codCotizacion;
    }

    public void setCodCotizacion(Cotizacion codCotizacion) {
        this.codCotizacion = codCotizacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDocGenerado != null ? codDocGenerado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentoGenerado)) {
            return false;
        }
        DocumentoGenerado other = (DocumentoGenerado) object;
        if ((this.codDocGenerado == null && other.codDocGenerado != null) || (this.codDocGenerado != null && !this.codDocGenerado.equals(other.codDocGenerado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.DocumentoGenerado[ codDocGenerado=" + codDocGenerado + " ]";
    }
    
}
