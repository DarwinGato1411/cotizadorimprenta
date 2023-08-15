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

/**
 *
 * @author gato
 */
@Entity
@Table(name = "tipo_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTrabajo.findAll", query = "SELECT t FROM TipoTrabajo t ORDER BY t.tipoDescripcion")
    ,
    @NamedQuery(name = "TipoTrabajo.findAllLike", query = "SELECT t FROM TipoTrabajo t WHERE t.tipoDescripcion like :buscar ORDER BY t.tipoDescripcion")
    ,
    @NamedQuery(name = "TipoTrabajo.findByIdTipoTrabajo", query = "SELECT t FROM TipoTrabajo t WHERE t.idTipoTrabajo = :idTipoTrabajo")
    ,
    @NamedQuery(name = "TipoTrabajo.findByTipoDescripcion", query = "SELECT t FROM TipoTrabajo t WHERE t.tipoDescripcion = :tipoDescripcion")
    ,
    @NamedQuery(name = "TipoTrabajo.findByTipoComentario", query = "SELECT t FROM TipoTrabajo t WHERE t.tipoComentario = :tipoComentario")})
public class TipoTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_trabajo")
    private Integer idTipoTrabajo;
    @Size(max = 150)
    @Column(name = "tipo_descripcion")
    private String tipoDescripcion;
    @Size(max = 150)
    @Column(name = "tipo_comentario")
    private String tipoComentario;

    public TipoTrabajo() {
    }

    public TipoTrabajo(Integer idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
    }

    public Integer getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    public void setIdTipoTrabajo(Integer idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
    }

    public String getTipoDescripcion() {
        return tipoDescripcion;
    }

    public void setTipoDescripcion(String tipoDescripcion) {
        this.tipoDescripcion = tipoDescripcion;
    }

    public String getTipoComentario() {
        return tipoComentario;
    }

    public void setTipoComentario(String tipoComentario) {
        this.tipoComentario = tipoComentario;
    }

    @OneToMany(mappedBy = "idTipoTrabajo")
    private Collection<DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoTrabajo != null ? idTipoTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTrabajo)) {
            return false;
        }
        TipoTrabajo other = (TipoTrabajo) object;
        if ((this.idTipoTrabajo == null && other.idTipoTrabajo != null) || (this.idTipoTrabajo != null && !this.idTipoTrabajo.equals(other.idTipoTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.TipoTrabajo[ idTipoTrabajo=" + idTipoTrabajo + " ]";
    }

}
