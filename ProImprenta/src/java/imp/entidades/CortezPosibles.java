/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "cortez_posibles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CortezPosibles.findAll", query = "SELECT c FROM CortezPosibles c"),
    @NamedQuery(name = "CortezPosibles.findAllLike", query = "SELECT c FROM CortezPosibles c where c.cortDescripcion LIKE :buscar"),
    @NamedQuery(name = "CortezPosibles.findByCodCorte", query = "SELECT c FROM CortezPosibles c WHERE c.codCorte = :codCorte"),
    @NamedQuery(name = "CortezPosibles.findParaOtros", query = "SELECT c FROM CortezPosibles c WHERE c.cortAncho IS NULL AND c.cortAlto IS NULL AND c.codFxp IS NULL"),
    @NamedQuery(name = "CortezPosibles.findByCortDescripcion", query = "SELECT c FROM CortezPosibles c WHERE c.cortDescripcion = :cortDescripcion")})
public class CortezPosibles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_corte")
    private Integer codCorte;
    @Size(max = 100)
    @Column(name = "cort_descripcion")
    private String cortDescripcion;
    @Lob
    @Column(name = "cort_imagen")
    private byte[] cortImagen;
    @Column(name = "cort_fxp")
    private Integer codFxp;
    @Column(name = "cort_alto")
    private BigDecimal cortAlto;
    @Column(name = "cort_ancho")
    private BigDecimal cortAncho;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cortezPosibles")
    private Collection<RecorteMaterial> recorteMaterialCollection;

    public CortezPosibles() {
    }

    public CortezPosibles(Integer codCorte) {
        this.codCorte = codCorte;
    }

    public Integer getCodCorte() {
        return codCorte;
    }

    public void setCodCorte(Integer codCorte) {
        this.codCorte = codCorte;
    }

    public String getCortDescripcion() {
        return cortDescripcion;
    }

    public void setCortDescripcion(String cortDescripcion) {
        this.cortDescripcion = cortDescripcion;
    }

    public byte[] getCortImagen() {
        return cortImagen;
    }

    public void setCortImagen(byte[] cortImagen) {
        this.cortImagen = cortImagen;
    }

    public Integer getCodFxp() {
        return codFxp;
    }

    public void setCodFxp(Integer codFxp) {
        this.codFxp = codFxp;
    }

    public BigDecimal getCortAlto() {
        return cortAlto;
    }

    public void setCortAlto(BigDecimal cortAlto) {
        this.cortAlto = cortAlto;
    }

    public BigDecimal getCortAncho() {
        return cortAncho;
    }

    public void setCortAncho(BigDecimal cortAncho) {
        this.cortAncho = cortAncho;
    }

    @XmlTransient
    public Collection<RecorteMaterial> getRecorteMaterialCollection() {
        return recorteMaterialCollection;
    }

    public void setRecorteMaterialCollection(Collection<RecorteMaterial> recorteMaterialCollection) {
        this.recorteMaterialCollection = recorteMaterialCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCorte != null ? codCorte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CortezPosibles)) {
            return false;
        }
        CortezPosibles other = (CortezPosibles) object;
        if ((this.codCorte == null && other.codCorte != null) || (this.codCorte != null && !this.codCorte.equals(other.codCorte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.CortezPosibles[ codCorte=" + codCorte + " ]";
    }
}
