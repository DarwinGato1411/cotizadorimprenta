/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author gato
 */
@Embeddable
public class RecorteMaterialPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_material")
    private int codMaterial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_corte")
    private int codCorte;

    public RecorteMaterialPK() {
    }

    public RecorteMaterialPK(int codMaterial, int codCorte) {
        this.codMaterial = codMaterial;
        this.codCorte = codCorte;
    }

    public int getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(int codMaterial) {
        this.codMaterial = codMaterial;
    }

    public int getCodCorte() {
        return codCorte;
    }

    public void setCodCorte(int codCorte) {
        this.codCorte = codCorte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codMaterial;
        hash += (int) codCorte;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecorteMaterialPK)) {
            return false;
        }
        RecorteMaterialPK other = (RecorteMaterialPK) object;
        if (this.codMaterial != other.codMaterial) {
            return false;
        }
        if (this.codCorte != other.codCorte) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.RecorteMaterialPK[ codMaterial=" + codMaterial + ", codCorte=" + codCorte + " ]";
    }
    
}
