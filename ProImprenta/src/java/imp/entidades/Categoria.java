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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
    @NamedQuery(name = "Categoria.findByCodCategoria", query = "SELECT c FROM Categoria c WHERE c.codCategoria = :codCategoria"),
    @NamedQuery(name = "Categoria.findByCatDescripcion", query = "SELECT c FROM Categoria c WHERE c.catDescripcion = :catDescripcion")})
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_categoria")
    private Integer codCategoria;
    @Size(max = 50)
    @Column(name = "cat_descripcion")
    private String catDescripcion;
    @OneToMany(mappedBy = "codCategoria")
    private Collection<Producto> productoCollection;
    @OneToMany(mappedBy = "codCategoria")
    private Collection<Operacion> operacionCollection;

    public Categoria() {
    }

    public Categoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getCatDescripcion() {
        return catDescripcion;
    }

    public void setCatDescripcion(String catDescripcion) {
        this.catDescripcion = catDescripcion;
    }

    @XmlTransient
    public Collection<Operacion> getOperacionCollection() {
        return operacionCollection;
    }

    public void setOperacionCollection(Collection<Operacion> operacionCollection) {
        this.operacionCollection = operacionCollection;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCategoria != null ? codCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.codCategoria == null && other.codCategoria != null) || (this.codCategoria != null && !this.codCategoria.equals(other.codCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidades.Categoria[ codCategoria=" + codCategoria + " ]";
    }
}
