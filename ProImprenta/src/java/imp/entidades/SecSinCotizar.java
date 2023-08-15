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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "sec_sin_cotizar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecSinCotizar.findAll", query = "SELECT s FROM SecSinCotizar s"),
    @NamedQuery(name = "SecSinCotizar.findByCodSinContizar", query = "SELECT s FROM SecSinCotizar s WHERE s.codSinContizar = :codSinContizar"),
    @NamedQuery(name = "SecSinCotizar.findByNumSinCot", query = "SELECT s FROM SecSinCotizar s WHERE s.numSinCot = :numSinCot")})
public class SecSinCotizar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_sin_contizar")
    private Integer codSinContizar;
    @Column(name = "num_sin_cot")
    private Integer numSinCot;

    public SecSinCotizar() {
    }

    public SecSinCotizar(Integer codSinContizar) {
        this.codSinContizar = codSinContizar;
    }

    public Integer getCodSinContizar() {
        return codSinContizar;
    }

    public void setCodSinContizar(Integer codSinContizar) {
        this.codSinContizar = codSinContizar;
    }

    public Integer getNumSinCot() {
        return numSinCot;
    }

    public void setNumSinCot(Integer numSinCot) {
        this.numSinCot = numSinCot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSinContizar != null ? codSinContizar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecSinCotizar)) {
            return false;
        }
        SecSinCotizar other = (SecSinCotizar) object;
        if ((this.codSinContizar == null && other.codSinContizar != null) || (this.codSinContizar != null && !this.codSinContizar.equals(other.codSinContizar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.SecSinCotizar[ codSinContizar=" + codSinContizar + " ]";
    }
    
}
