/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "usuario_sistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioSistema.findAll", query = "SELECT u FROM UsuarioSistema u")
    ,
    @NamedQuery(name = "UsuarioSistema.findPorNombre", query = "SELECT u FROM UsuarioSistema u where u.usuUsuario=:usuUsuario AND u.usuActivo=TRUE")
    ,
    @NamedQuery(name = "UsuarioSistema.findLikeNombre", query = "SELECT u FROM UsuarioSistema u where u.usuUsuario like :usuUsuario AND u.usuActivo=TRUE")
    ,
    @NamedQuery(name = "UsuarioSistema.findByCodUsuarioSistema", query = "SELECT u FROM UsuarioSistema u WHERE u.codUsuarioSistema = :codUsuarioSistema")
    ,
    @NamedQuery(name = "UsuarioSistema.findByUsuUsuario", query = "SELECT u FROM UsuarioSistema u WHERE u.usuUsuario = :usuUsuario")
    ,
    @NamedQuery(name = "UsuarioSistema.findByUsuUsuarioNombreUsuario", query = "SELECT u FROM UsuarioSistema u WHERE u.usuNombreUsuario = :usuUsuario")
    ,
    @NamedQuery(name = "UsuarioSistema.findByUsuContrasena", query = "SELECT u FROM UsuarioSistema u WHERE u.usuContrasena = :usuContrasena")
    ,
    @NamedQuery(name = "UsuarioSistema.findByUsuCorreo", query = "SELECT u FROM UsuarioSistema u WHERE u.usuCorreo = :usuCorreo")
    ,
    @NamedQuery(name = "UsuarioSistema.findByUsuNivelAcceso", query = "SELECT u FROM UsuarioSistema u WHERE u.usuNivelAcceso = :usuNivelAcceso")
    ,
    @NamedQuery(name = "UsuarioSistema.findByUsuFechaCreacion", query = "SELECT u FROM UsuarioSistema u WHERE u.usuFechaCreacion = :usuFechaCreacion")
    ,
    @NamedQuery(name = "UsuarioSistema.findByUsuFechaModificacion", query = "SELECT u FROM UsuarioSistema u WHERE u.usuFechaModificacion = :usuFechaModificacion")
    ,
    @NamedQuery(name = "UsuarioSistema.findByUsuPregunta", query = "SELECT u FROM UsuarioSistema u WHERE u.usuPregunta = :usuPregunta")
    ,
    @NamedQuery(name = "UsuarioSistema.findByUsuRespuesta", query = "SELECT u FROM UsuarioSistema u WHERE u.usuRespuesta = :usuRespuesta")})
public class UsuarioSistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_usuario_sistema")
    private Integer codUsuarioSistema;
    @Size(max = 100)
    @Column(name = "usu_usuario")
    private String usuUsuario;
    @Size(max = 100)
    @Column(name = "usu_contrasena")
    private String usuContrasena;
    @Size(max = 150)
    @Column(name = "usu_correo")
    private String usuCorreo;
    @Column(name = "usu_nivel_acceso")
    private Integer usuNivelAcceso;
    @Column(name = "usu_fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date usuFechaCreacion;
    @Column(name = "usu_fecha_modificacion")
    @Temporal(TemporalType.DATE)
    private Date usuFechaModificacion;
    @Size(max = 100)
    @Column(name = "usu_pregunta")
    private String usuPregunta;
    @Size(max = 100)
    @Column(name = "usu_respuesta")
    private String usuRespuesta;
    @Column(name = "usu_nombre_usuario")
    private String usuNombreUsuario;
    @Column(name = "usu_activo")
    private Boolean usuActivo;
    @Transient
    private String tipoUsuario = "";
    @OneToMany(mappedBy = "codUsuarioSistema")
    private Collection<SolicitarMaterial> solicitarMaterialCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<Operacion> operacionCollection;

    public UsuarioSistema() {
    }

    public UsuarioSistema(String usuUsuario, String usuContrasena) {
        this.usuUsuario = usuUsuario;
        this.usuContrasena = usuContrasena;
    }

    public UsuarioSistema(Integer codUsuarioSistema) {
        this.codUsuarioSistema = codUsuarioSistema;
    }

    public Integer getCodUsuarioSistema() {
        return codUsuarioSistema;
    }

    public void setCodUsuarioSistema(Integer codUsuarioSistema) {
        this.codUsuarioSistema = codUsuarioSistema;
    }

    public String getUsuUsuario() {
        return usuUsuario;
    }

    public void setUsuUsuario(String usuUsuario) {
        this.usuUsuario = usuUsuario;
    }

    public String getUsuContrasena() {
        return usuContrasena;
    }

    public void setUsuContrasena(String usuContrasena) {
        this.usuContrasena = usuContrasena;
    }

    public String getUsuCorreo() {
        return usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

    public Integer getUsuNivelAcceso() {
        return usuNivelAcceso;
    }

    public void setUsuNivelAcceso(Integer usuNivelAcceso) {
        this.usuNivelAcceso = usuNivelAcceso;
    }

    public Date getUsuFechaCreacion() {
        return usuFechaCreacion;
    }

    public void setUsuFechaCreacion(Date usuFechaCreacion) {
        this.usuFechaCreacion = usuFechaCreacion;
    }

    public Date getUsuFechaModificacion() {
        return usuFechaModificacion;
    }

    public void setUsuFechaModificacion(Date usuFechaModificacion) {
        this.usuFechaModificacion = usuFechaModificacion;
    }

    public String getUsuPregunta() {
        return usuPregunta;
    }

    public void setUsuPregunta(String usuPregunta) {
        this.usuPregunta = usuPregunta;
    }

    public String getUsuRespuesta() {
        return usuRespuesta;
    }

    public void setUsuRespuesta(String usuRespuesta) {
        this.usuRespuesta = usuRespuesta;
    }

    public String getUsuNombreUsuario() {
        return usuNombreUsuario;
    }

    public void setUsuNombreUsuario(String usuNombreUsuario) {
        this.usuNombreUsuario = usuNombreUsuario;
    }

    public Boolean getUsuActivo() {
        return usuActivo;
    }

    public void setUsuActivo(Boolean usuActivo) {
        this.usuActivo = usuActivo;
    }

    public String getTipoUsuario() {
        if (getUsuNivelAcceso() == 1) {
            tipoUsuario = "ADMINISTRADOR";
        } else if (getUsuNivelAcceso() == 2) {
            tipoUsuario = "COTIZADOR";
        } else if (getUsuNivelAcceso() == 3) {
            tipoUsuario = "OPERADOR";
        } else if (getUsuNivelAcceso() == 4) {
            tipoUsuario = "FACTURACION";
        } else if (getUsuNivelAcceso() == 5) {
            tipoUsuario = "PRODUCCION";
        }
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @XmlTransient
    public Collection<SolicitarMaterial> getSolicitarMaterialCollection() {
        return solicitarMaterialCollection;
    }

    public void setSolicitarMaterialCollection(Collection<SolicitarMaterial> solicitarMaterialCollection) {
        this.solicitarMaterialCollection = solicitarMaterialCollection;
    }

    @XmlTransient
    public Collection<Operacion> getOperacionCollection() {
        return operacionCollection;
    }

    public void setOperacionCollection(Collection<Operacion> operacionCollection) {
        this.operacionCollection = operacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUsuarioSistema != null ? codUsuarioSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioSistema)) {
            return false;
        }
        UsuarioSistema other = (UsuarioSistema) object;
        if ((this.codUsuarioSistema == null && other.codUsuarioSistema != null) || (this.codUsuarioSistema != null && !this.codUsuarioSistema.equals(other.codUsuarioSistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.UsuarioSistema[ codUsuarioSistema=" + codUsuarioSistema + " ]";
    }
}
