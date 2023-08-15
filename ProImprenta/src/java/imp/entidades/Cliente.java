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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c where c.estadoCliente=1"),
    @NamedQuery(name = "Cliente.findByCodCliente", query = "SELECT c FROM Cliente c WHERE c.codCliente = :codCliente"),
    @NamedQuery(name = "Cliente.findByNombreLike", query = "SELECT c FROM Cliente c WHERE c.ruc like :buscar AND c.estadoCliente=1"),
    @NamedQuery(name = "Cliente.findByNombreComercialLike", query = "SELECT c FROM Cliente c WHERE c.nombreComercial like :buscar AND c.estadoCliente=1"),
    @NamedQuery(name = "Cliente.findByRazonSocialLike", query = "SELECT c FROM Cliente c WHERE c.razonSocial like :buscar AND c.estadoCliente=1"),
    @NamedQuery(name = "Cliente.findByRuc", query = "SELECT c FROM Cliente c WHERE c.ruc = :ruc"),
    @NamedQuery(name = "Cliente.findByNombreComercial", query = "SELECT c FROM Cliente c WHERE c.nombreComercial = :nombreComercial"),
    @NamedQuery(name = "Cliente.findByRazonSocial", query = "SELECT c FROM Cliente c WHERE c.razonSocial = :razonSocial"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cliente.findByCelular", query = "SELECT c FROM Cliente c WHERE c.celular = :celular"),
    @NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Cliente.findByCorreo", query = "SELECT c FROM Cliente c WHERE c.correo = :correo"),
    @NamedQuery(name = "Cliente.findByDireccionWeb", query = "SELECT c FROM Cliente c WHERE c.direccionWeb = :direccionWeb"),
    @NamedQuery(name = "Cliente.findByTipoCliente", query = "SELECT c FROM Cliente c WHERE c.tipoCliente = :tipoCliente"),
    @NamedQuery(name = "Cliente.findByEstadoCliente", query = "SELECT c FROM Cliente c WHERE c.estadoCliente = :estadoCliente")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_cliente")
    private Integer codCliente;
    @Size(max = 13)
    @Column(name = "ruc")
    private String ruc;
    @Size(max = 200)
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    @Size(max = 100)
    @Column(name = "razon_social")
    private String razonSocial;
    @Size(max = 25)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 25)
    @Column(name = "celular")
    private String celular;
    @Size(max = 200)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 200)
    @Column(name = "correo")
    private String correo;
    @Size(max = 150)
    @Column(name = "direccion_web")
    private String direccionWeb;
    @Size(max = 100)
    @Column(name = "tipo_cliente")
    private String tipoCliente;
    @Column(name = "estado_cliente")
    private Integer estadoCliente;
    @Column(name = "vendedor_asignado")
    private String vendedorAsignado;
    @OneToMany(mappedBy = "codCliente")
    private Collection<Cotizacion> cotizacionCollection;
    @OneToMany(mappedBy = "codCliente")
    private Collection<PersonaContacto> personaContactoCollection;
    @OneToMany(mappedBy = "codCliente")
    private Collection<OrdenSinCotizacion> ordenSinCotizacionCollection;

    public Cliente() {
    }

    public Cliente(String ruc) {
        this.ruc = ruc;
    }

    public Cliente(Integer codCliente) {
        this.codCliente = codCliente;
    }

    public Integer getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Integer codCliente) {
        this.codCliente = codCliente;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccionWeb() {
        return direccionWeb;
    }

    public void setDireccionWeb(String direccionWeb) {
        this.direccionWeb = direccionWeb;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Integer getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(Integer estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public String getVendedorAsignado() {
        return vendedorAsignado;
    }

    public void setVendedorAsignado(String vendedorAsignado) {
        this.vendedorAsignado = vendedorAsignado;
    }

    @XmlTransient
    public Collection<Cotizacion> getCotizacionCollection() {
        return cotizacionCollection;
    }

    public void setCotizacionCollection(Collection<Cotizacion> cotizacionCollection) {
        this.cotizacionCollection = cotizacionCollection;
    }

    @XmlTransient
    public Collection<PersonaContacto> getPersonaContactoCollection() {
        return personaContactoCollection;
    }

    public void setPersonaContactoCollection(Collection<PersonaContacto> personaContactoCollection) {
        this.personaContactoCollection = personaContactoCollection;
    }

    @XmlTransient
    public Collection<OrdenSinCotizacion> getOrdenSinCotizacionCollection() {
        return ordenSinCotizacionCollection;
    }

    public void setOrdenSinCotizacionCollection(Collection<OrdenSinCotizacion> ordenSinCotizacionCollection) {
        this.ordenSinCotizacionCollection = ordenSinCotizacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCliente != null ? codCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.codCliente == null && other.codCliente != null) || (this.codCliente != null && !this.codCliente.equals(other.codCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.Cliente[ codCliente=" + codCliente + " ]";
    }
}
