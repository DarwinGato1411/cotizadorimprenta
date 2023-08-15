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
@Table(name = "materiales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materiales.findAll", query = "SELECT m FROM Materiales m"),
    @NamedQuery(name = "Materiales.findAllNombre", query = "SELECT m FROM Materiales m where m.matNombre like :nombreMaterial"),
    @NamedQuery(name = "Materiales.findImpresionLike", query = "SELECT m FROM Materiales m where m.matNombre like :nombreMaterial AND m.matTipo='IMPRESION' ORDER BY m.matNombre"),
    @NamedQuery(name = "Materiales.findImpresionLikeGiganto", query = "SELECT m FROM Materiales m where m.matNombre like :nombreMaterial AND m.matTipo='GIGANTOGRAFIA' ORDER BY m.matNombre"),
    @NamedQuery(name = "Materiales.findOtrosLike", query = "SELECT m FROM Materiales m where m.matNombre like :nombreMaterial AND m.matTipo='OTROS' ORDER BY m.matNombre"),
    @NamedQuery(name = "Materiales.findDigitalLike", query = "SELECT m FROM Materiales m where m.matNombre like :nombreMaterial AND m.matTipo='DIGITAL' ORDER BY m.matNombre"),
    @NamedQuery(name = "Materiales.findByCodMaterial", query = "SELECT m FROM Materiales m WHERE m.codMaterial = :codMaterial"),
    @NamedQuery(name = "Materiales.findByMatNombre", query = "SELECT m FROM Materiales m WHERE m.matNombre = :matNombre"),
    @NamedQuery(name = "Materiales.findByMatTipo", query = "SELECT m FROM Materiales m WHERE m.matTipo = :matTipo"),
    @NamedQuery(name = "Materiales.findByMatAlto", query = "SELECT m FROM Materiales m WHERE m.matAlto = :matAlto"),
    @NamedQuery(name = "Materiales.findByMatAncho", query = "SELECT m FROM Materiales m WHERE m.matAncho = :matAncho"),
    @NamedQuery(name = "Materiales.findByMatCosto", query = "SELECT m FROM Materiales m WHERE m.matCosto = :matCosto")})
public class Materiales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_material")
    private Integer codMaterial;
    @Size(max = 150)
    @Column(name = "mat_nombre")
    private String matNombre;
    @Size(max = 100)
    @Column(name = "mat_tipo")
    private String matTipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mat_alto")
    private BigDecimal matAlto;
    @Column(name = "mat_ancho")
    private BigDecimal matAncho;
    @Lob
    @Column(name = "mat_imagen")
    private byte[] matImagen;
    @Column(name = "mat_costo")
    private Float matCosto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materiales")
    private Collection<RecorteMaterial> recorteMaterialCollection;

    public Materiales() {
    }

    public Materiales(String matNombre) {
        this.matNombre = matNombre;
    }

    public Materiales(String matNombre, String matTipo) {
        this.matNombre = matNombre;
        this.matTipo = matTipo;
    }

    public Materiales(Integer codMaterial) {
        this.codMaterial = codMaterial;
    }

    public Integer getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(Integer codMaterial) {
        this.codMaterial = codMaterial;
    }

    public String getMatNombre() {
        return matNombre;
    }

    public void setMatNombre(String matNombre) {
        this.matNombre = matNombre;
    }

    public String getMatTipo() {
        return matTipo;
    }

    public void setMatTipo(String matTipo) {
        this.matTipo = matTipo;
    }

    public BigDecimal getMatAlto() {
        return matAlto;
    }

    public void setMatAlto(BigDecimal matAlto) {
        this.matAlto = matAlto;
    }

    public BigDecimal getMatAncho() {
        return matAncho;
    }

    public void setMatAncho(BigDecimal matAncho) {
        this.matAncho = matAncho;
    }

    public byte[] getMatImagen() {
        return matImagen;
    }

    public void setMatImagen(byte[] matImagen) {
        this.matImagen = matImagen;
    }

    public Float getMatCosto() {
        return matCosto;
    }

    public void setMatCosto(Float matCosto) {
        this.matCosto = matCosto;
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
        hash += (codMaterial != null ? codMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materiales)) {
            return false;
        }
        Materiales other = (Materiales) object;
        if ((this.codMaterial == null && other.codMaterial != null) || (this.codMaterial != null && !this.codMaterial.equals(other.codMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.Materiales[ codMaterial=" + codMaterial + " ]";
    }
}
