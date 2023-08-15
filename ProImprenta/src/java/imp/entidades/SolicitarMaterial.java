/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "solicitar_material")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitarMaterial.findAll", query = "SELECT s FROM SolicitarMaterial s"),
    @NamedQuery(name = "SolicitarMaterial.findAllEstado", query = "SELECT s FROM SolicitarMaterial s WHERE s.solEstado=:estado"),
    @NamedQuery(name = "SolicitarMaterial.findAllUsuario", query = "SELECT s FROM SolicitarMaterial s WHERE s.codUsuarioSistema=:codUsuarioSistema"),
    @NamedQuery(name = "SolicitarMaterial.findByIdSolicita", query = "SELECT s FROM SolicitarMaterial s WHERE s.idSolicita = :idSolicita"),
    @NamedQuery(name = "SolicitarMaterial.findBySolCantidad", query = "SELECT s FROM SolicitarMaterial s WHERE s.solCantidad = :solCantidad"),
    @NamedQuery(name = "SolicitarMaterial.findBySolDescripcion", query = "SELECT s FROM SolicitarMaterial s WHERE s.solDescripcion = :solDescripcion"),
    @NamedQuery(name = "SolicitarMaterial.findBySolFecha", query = "SELECT s FROM SolicitarMaterial s WHERE s.solFecha = :solFecha")})
public class SolicitarMaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicita")
    private Integer idSolicita;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sol_cantidad")
    private BigDecimal solCantidad;
    @Size(max = 700)
    @Column(name = "sol_descripcion")
    private String solDescripcion;
    @Size(max = 40)
    @Column(name = "sol_estado")
    private String solEstado;
    @Column(name = "sol_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date solFecha;
    @Column(name = "sol_valor_compra")
    private BigDecimal solValorCompra;
    @Size(max = 200)
    @Column(name = "sol_proveedor")
    private String solProveedor;
    @Size(max = 50)
    @Column(name = "sol_num_fac_compra")
    private String solNumFacCompra;
    @Column(name = "sol_hora")
    @Temporal(TemporalType.TIME)
    private Date solHora;
    @JoinColumn(name = "cod_usuario_sistema", referencedColumnName = "cod_usuario_sistema")
    @ManyToOne
    private UsuarioSistema codUsuarioSistema;

    public SolicitarMaterial() {
    }

    public SolicitarMaterial(Integer idSolicita) {
        this.idSolicita = idSolicita;
    }

    public Integer getIdSolicita() {
        return idSolicita;
    }

    public void setIdSolicita(Integer idSolicita) {
        this.idSolicita = idSolicita;
    }

    public BigDecimal getSolCantidad() {
        return solCantidad;
    }

    public void setSolCantidad(BigDecimal solCantidad) {
        this.solCantidad = solCantidad;
    }

    public String getSolDescripcion() {
        return solDescripcion;
    }

    public void setSolDescripcion(String solDescripcion) {
        this.solDescripcion = solDescripcion;
    }

    public Date getSolFecha() {
        return solFecha;
    }

    public void setSolFecha(Date solFecha) {
        this.solFecha = solFecha;
    }

    public UsuarioSistema getCodUsuarioSistema() {
        return codUsuarioSistema;
    }

    public void setCodUsuarioSistema(UsuarioSistema codUsuarioSistema) {
        this.codUsuarioSistema = codUsuarioSistema;
    }

    public String getSolEstado() {
        return solEstado;
    }

    public void setSolEstado(String solEstado) {
        this.solEstado = solEstado;
    }

    public BigDecimal getSolValorCompra() {
        return solValorCompra;
    }

    public void setSolValorCompra(BigDecimal solValorCompra) {
        this.solValorCompra = solValorCompra;
    }

    public String getSolProveedor() {
        return solProveedor;
    }

    public void setSolProveedor(String solProveedor) {
        this.solProveedor = solProveedor;
    }

    public String getSolNumFacCompra() {
        return solNumFacCompra;
    }

    public void setSolNumFacCompra(String solNumFacCompra) {
        this.solNumFacCompra = solNumFacCompra;
    }

    public Date getSolHora() {
        return solHora;
    }

    public void setSolHora(Date solHora) {
        this.solHora = solHora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicita != null ? idSolicita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitarMaterial)) {
            return false;
        }
        SolicitarMaterial other = (SolicitarMaterial) object;
        if ((this.idSolicita == null && other.idSolicita != null) || (this.idSolicita != null && !this.idSolicita.equals(other.idSolicita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidadess.SolicitarMaterial[ idSolicita=" + idSolicita + " ]";
    }
}
