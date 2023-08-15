/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "_seq_fac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeqFac.findAll", query = "SELECT s FROM SeqFac s ORDER BY s.idSec DESC"),
    @NamedQuery(name = "SeqFac.findBySeqVal", query = "SELECT s FROM SeqFac s WHERE s.seqVal = :seqVal"),
    @NamedQuery(name = "SeqFac.findByIdSec", query = "SELECT s FROM SeqFac s WHERE s.idSec = :idSec")})
public class SeqFac implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "seq_val")
    private int seqVal;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sec")
    private Integer idSec;

    public SeqFac() {
    }

    public SeqFac(Integer idSec) {
        this.idSec = idSec;
    }

    public SeqFac(Integer idSec, int seqVal) {
        this.idSec = idSec;
        this.seqVal = seqVal;
    }

    public int getSeqVal() {
        return seqVal;
    }

    public void setSeqVal(int seqVal) {
        this.seqVal = seqVal;
    }

    public Integer getIdSec() {
        return idSec;
    }

    public void setIdSec(Integer idSec) {
        this.idSec = idSec;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSec != null ? idSec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeqFac)) {
            return false;
        }
        SeqFac other = (SeqFac) object;
        if ((this.idSec == null && other.idSec != null) || (this.idSec != null && !this.idSec.equals(other.idSec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.SeqFac[ idSec=" + idSec + " ]";
    }
    
}
