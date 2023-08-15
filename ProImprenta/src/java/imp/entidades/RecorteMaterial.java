/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "recorte_material")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecorteMaterial.findAll", query = "SELECT r FROM RecorteMaterial r"),
    @NamedQuery(name = "RecorteMaterial.findByCodMaterial", query = "SELECT r FROM RecorteMaterial r WHERE r.recorteMaterialPK.codMaterial = :codMaterial"),
    @NamedQuery(name = "RecorteMaterial.findByCodCorte", query = "SELECT r FROM RecorteMaterial r WHERE r.recorteMaterialPK.codCorte = :codCorte"),
    @NamedQuery(name = "RecorteMaterial.findByCmDescripcion", query = "SELECT r FROM RecorteMaterial r WHERE r.cmDescripcion = :cmDescripcion")})
public class RecorteMaterial implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecorteMaterialPK recorteMaterialPK;
    @Size(max = 150)
    @Column(name = "cm_descripcion")
    private String cmDescripcion;
    @OneToMany(mappedBy = "recorteMaterial")
    private Collection<DetalleCotizacion> detalleCotizacionCollection;
    @JoinColumn(name = "cod_corte", referencedColumnName = "cod_corte", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CortezPosibles cortezPosibles;
    @JoinColumn(name = "cod_material", referencedColumnName = "cod_material", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materiales materiales;

    public RecorteMaterial() {
    }

    public RecorteMaterial(RecorteMaterialPK recorteMaterialPK) {
        this.recorteMaterialPK = recorteMaterialPK;
    }

    public RecorteMaterial(int codMaterial, int codCorte) {
        this.recorteMaterialPK = new RecorteMaterialPK(codMaterial, codCorte);
    }

    public RecorteMaterialPK getRecorteMaterialPK() {
        return recorteMaterialPK;
    }

    public void setRecorteMaterialPK(RecorteMaterialPK recorteMaterialPK) {
        this.recorteMaterialPK = recorteMaterialPK;
    }

    public String getCmDescripcion() {
        return cmDescripcion;
    }

    public void setCmDescripcion(String cmDescripcion) {
        this.cmDescripcion = cmDescripcion;
    }

    @XmlTransient
    public Collection<DetalleCotizacion> getDetalleCotizacionCollection() {
        return detalleCotizacionCollection;
    }

    public void setDetalleCotizacionCollection(Collection<DetalleCotizacion> detalleCotizacionCollection) {
        this.detalleCotizacionCollection = detalleCotizacionCollection;
    }

    public CortezPosibles getCortezPosibles() {
        return cortezPosibles;
    }

    public void setCortezPosibles(CortezPosibles cortezPosibles) {
        this.cortezPosibles = cortezPosibles;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recorteMaterialPK != null ? recorteMaterialPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecorteMaterial)) {
            return false;
        }
        RecorteMaterial other = (RecorteMaterial) object;
        if ((this.recorteMaterialPK == null && other.recorteMaterialPK != null) || (this.recorteMaterialPK != null && !this.recorteMaterialPK.equals(other.recorteMaterialPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.RecorteMaterial[ recorteMaterialPK=" + recorteMaterialPK + " ]";
    }
    
}
