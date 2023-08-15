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
@Table(name = "responsable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Responsable.findAll", query = "SELECT r FROM Responsable r"),
    @NamedQuery(name = "Responsable.findByCodResponsable", query = "SELECT r FROM Responsable r WHERE r.codResponsable = :codResponsable"),
    @NamedQuery(name = "Responsable.findByResCedula", query = "SELECT r FROM Responsable r WHERE r.resCedula = :resCedula"),
    @NamedQuery(name = "Responsable.findByResApellido", query = "SELECT r FROM Responsable r WHERE r.resApellido = :resApellido"),
    @NamedQuery(name = "Responsable.findByResNombre", query = "SELECT r FROM Responsable r WHERE r.resNombre = :resNombre"),
    @NamedQuery(name = "Responsable.findByResTelefono", query = "SELECT r FROM Responsable r WHERE r.resTelefono = :resTelefono"),
    @NamedQuery(name = "Responsable.findByResCelular", query = "SELECT r FROM Responsable r WHERE r.resCelular = :resCelular"),
    @NamedQuery(name = "Responsable.findByResDireccion", query = "SELECT r FROM Responsable r WHERE r.resDireccion = :resDireccion"),
    @NamedQuery(name = "Responsable.findByResCorreo", query = "SELECT r FROM Responsable r WHERE r.resCorreo = :resCorreo"),
    @NamedQuery(name = "Responsable.findByResWeb", query = "SELECT r FROM Responsable r WHERE r.resWeb = :resWeb"),
    @NamedQuery(name = "Responsable.findByResCargo", query = "SELECT r FROM Responsable r WHERE r.resCargo = :resCargo"),
    @NamedQuery(name = "Responsable.findByResContrasena", query = "SELECT r FROM Responsable r WHERE r.resContrasena = :resContrasena")})
public class Responsable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_responsable")
    private Integer codResponsable;
    @Size(max = 13)
    @Column(name = "res_cedula")
    private String resCedula;
    @Size(max = 100)
    @Column(name = "res_apellido")
    private String resApellido;
    @Size(max = 100)
    @Column(name = "res_nombre")
    private String resNombre;
    @Size(max = 25)
    @Column(name = "res_telefono")
    private String resTelefono;
    @Size(max = 25)
    @Column(name = "res_celular")
    private String resCelular;
    @Size(max = 150)
    @Column(name = "res_direccion")
    private String resDireccion;
    @Size(max = 150)
    @Column(name = "res_correo")
    private String resCorreo;
    @Size(max = 150)
    @Column(name = "res_web")
    private String resWeb;
    @Size(max = 50)
    @Column(name = "res_cargo")
    private String resCargo;
    @Size(max = 100)
    @Column(name = "res_contrasena")
    private String resContrasena;
    @OneToMany(mappedBy = "codResponsable")
    private Collection<OrdenDeProduccion> ordenDeProduccionCollection;

    public Responsable() {
    }

    public Responsable(Integer codResponsable) {
        this.codResponsable = codResponsable;
    }

    public Integer getCodResponsable() {
        return codResponsable;
    }

    public void setCodResponsable(Integer codResponsable) {
        this.codResponsable = codResponsable;
    }

    public String getResCedula() {
        return resCedula;
    }

    public void setResCedula(String resCedula) {
        this.resCedula = resCedula;
    }

    public String getResApellido() {
        return resApellido;
    }

    public void setResApellido(String resApellido) {
        this.resApellido = resApellido;
    }

    public String getResNombre() {
        return resNombre;
    }

    public void setResNombre(String resNombre) {
        this.resNombre = resNombre;
    }

    public String getResTelefono() {
        return resTelefono;
    }

    public void setResTelefono(String resTelefono) {
        this.resTelefono = resTelefono;
    }

    public String getResCelular() {
        return resCelular;
    }

    public void setResCelular(String resCelular) {
        this.resCelular = resCelular;
    }

    public String getResDireccion() {
        return resDireccion;
    }

    public void setResDireccion(String resDireccion) {
        this.resDireccion = resDireccion;
    }

    public String getResCorreo() {
        return resCorreo;
    }

    public void setResCorreo(String resCorreo) {
        this.resCorreo = resCorreo;
    }

    public String getResWeb() {
        return resWeb;
    }

    public void setResWeb(String resWeb) {
        this.resWeb = resWeb;
    }

    public String getResCargo() {
        return resCargo;
    }

    public void setResCargo(String resCargo) {
        this.resCargo = resCargo;
    }

    public String getResContrasena() {
        return resContrasena;
    }

    public void setResContrasena(String resContrasena) {
        this.resContrasena = resContrasena;
    }

    @XmlTransient
    public Collection<OrdenDeProduccion> getOrdenDeProduccionCollection() {
        return ordenDeProduccionCollection;
    }

    public void setOrdenDeProduccionCollection(Collection<OrdenDeProduccion> ordenDeProduccionCollection) {
        this.ordenDeProduccionCollection = ordenDeProduccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codResponsable != null ? codResponsable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Responsable)) {
            return false;
        }
        Responsable other = (Responsable) object;
        if ((this.codResponsable == null && other.codResponsable != null) || (this.codResponsable != null && !this.codResponsable.equals(other.codResponsable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.Responsable[ codResponsable=" + codResponsable + " ]";
    }
    
}
