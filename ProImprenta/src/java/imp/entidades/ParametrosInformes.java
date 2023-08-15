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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "parametros_informes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametrosInformes.findAll", query = "SELECT p FROM ParametrosInformes p"),
    @NamedQuery(name = "ParametrosInformes.findByCodParamReportes", query = "SELECT p FROM ParametrosInformes p WHERE p.codParamReportes = :codParamReportes"),
    @NamedQuery(name = "ParametrosInformes.findByCodigo", query = "SELECT p FROM ParametrosInformes p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "ParametrosInformes.findByDescripcion", query = "SELECT p FROM ParametrosInformes p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "ParametrosInformes.findByNivel", query = "SELECT p FROM ParametrosInformes p WHERE p.nivel = :nivel")})
public class ParametrosInformes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_param_reportes")
    private Integer codParamReportes;
    @Size(max = 50)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 10)
    @Column(name = "nivel")
    private String nivel;
    @Size(max = 10)
    @Column(name = "mailEnvio")
    private String mailEnvio;
    @Size(max = 10)
    @Column(name = "contrasena")
    private String contrasena;

    public ParametrosInformes() {
    }

    public ParametrosInformes(Integer codParamReportes) {
        this.codParamReportes = codParamReportes;
    }

    public Integer getCodParamReportes() {
        return codParamReportes;
    }

    public void setCodParamReportes(Integer codParamReportes) {
        this.codParamReportes = codParamReportes;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getMailEnvio() {
        return mailEnvio;
    }

    public void setMailEnvio(String mailEnvio) {
        this.mailEnvio = mailEnvio;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codParamReportes != null ? codParamReportes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametrosInformes)) {
            return false;
        }
        ParametrosInformes other = (ParametrosInformes) object;
        if ((this.codParamReportes == null && other.codParamReportes != null) || (this.codParamReportes != null && !this.codParamReportes.equals(other.codParamReportes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.ParametrosInformes[ codParamReportes=" + codParamReportes + " ]";
    }
}
