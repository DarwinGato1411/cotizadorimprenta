/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "mano_de_obra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ManoDeObra.findAll", query = "SELECT m FROM ManoDeObra m"),
    @NamedQuery(name = "ManoDeObra.findAllImpresos", query = "SELECT m FROM ManoDeObra m where m.manNombreProducto like :nombreManoObra AND (m.manTipoProducto='IMPRESION' OR m.manTipoProducto='PRE-POST') ORDER BY m.manNombreProducto"),
    @NamedQuery(name = "ManoDeObra.findAllGiganto", query = "SELECT m FROM ManoDeObra m where m.manNombreProducto like :nombreManoObra AND (m.manTipoProducto='GIGANTOGRAFIA' OR m.manTipoProducto='PRE-POST-GIGANTO') ORDER BY m.manNombreProducto"),
    @NamedQuery(name = "ManoDeObra.findAllNombreLike", query = "SELECT m FROM ManoDeObra m where m.manNombreProducto like :nombreManoObra AND m.manTipoProducto='IMPRESION' ORDER BY m.manNombreProducto"),
    @NamedQuery(name = "ManoDeObra.findAllNombreLikeGiganto", query = "SELECT m FROM ManoDeObra m where m.manNombreProducto like :nombreManoObra AND m.manTipoProducto='GIGANTOGRAFIA' ORDER BY m.manNombreProducto"),
    @NamedQuery(name = "ManoDeObra.findAllNombrePrePost", query = "SELECT m FROM ManoDeObra m where m.manNombreProducto like :nombreManoObra AND m.manTipoProducto = 'PRE-POST' ORDER BY m.manNombreProducto"),
    @NamedQuery(name = "ManoDeObra.findAllNombrePrePostGiganto", query = "SELECT m FROM ManoDeObra m where m.manNombreProducto like :nombreManoObra AND m.manTipoProducto = 'PRE-POST-GIGANTO' ORDER BY m.manNombreProducto"),
    @NamedQuery(name = "ManoDeObra.findByCodManObra", query = "SELECT m FROM ManoDeObra m WHERE m.codManObra = :codManObra"),
    @NamedQuery(name = "ManoDeObra.findByManNombreProducto", query = "SELECT m FROM ManoDeObra m WHERE m.manNombreProducto = :manNombreProducto"),
    @NamedQuery(name = "ManoDeObra.findByManCostoMillar", query = "SELECT m FROM ManoDeObra m WHERE m.manCostoMillar = :manCostoMillar"),
    @NamedQuery(name = "ManoDeObra.findByManCostoTiempo", query = "SELECT m FROM ManoDeObra m WHERE m.manCostoTiempo = :manCostoTiempo"),
    @NamedQuery(name = "ManoDeObra.findByManCostoOpcional", query = "SELECT m FROM ManoDeObra m WHERE m.manCostoOpcional = :manCostoOpcional"),
    @NamedQuery(name = "ManoDeObra.findByManAlto", query = "SELECT m FROM ManoDeObra m WHERE m.manAlto = :manAlto"),
    @NamedQuery(name = "ManoDeObra.findByManAncho", query = "SELECT m FROM ManoDeObra m WHERE m.manAncho = :manAncho"),
    @NamedQuery(name = "ManoDeObra.findByManTipoProducto", query = "SELECT m FROM ManoDeObra m WHERE m.manTipoProducto = :manTipoProducto")})
public class ManoDeObra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_man_obra")
    private Integer codManObra;
    @Size(max = 100)
    @Column(name = "man_nombre_producto")
    private String manNombreProducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "man_costo_millar")
    private Float manCostoMillar;
    @Column(name = "man_costo_tiempo")
    private Float manCostoTiempo;
    @Column(name = "man_costo_opcional")
    private BigDecimal manCostoOpcional;
    @Column(name = "man_alto")
    private BigDecimal manAlto;
    @Column(name = "man_ancho")
    private BigDecimal manAncho;
    @Column(name = "man_tipo_producto")
    private String manTipoProducto;
    @OneToMany(mappedBy = "codManObra")
    private Collection<DetalleCotizacion> detalleCotizacionCollection;
    @Column(name = "man_costo_plancha")
    private Float manCostoPlancha;
    @Column(name = "man_numero_torres_impresion")
    private Integer manNumeroTorresImpresion;
    @Column(name = "man_permite_volteo")
    private String manPermiteVolteo;
    @Column(name = "man_alto_min")
    private BigDecimal manAltoMin;
    @Column(name = "man_ancho_min")
    private BigDecimal manAnchoMin;
    @Column(name = "man_impresion_hora")
    private Integer manImpresionHora;
    @Column(name = "man_costo_min_hora")
    private Float manCostoMinHora;
    @Column(name = "man_costo_min_millar")
    private Float manCostoMinMillar;

    public ManoDeObra() {
    }

    public ManoDeObra(String tipoManoObra) {
        this.manTipoProducto = tipoManoObra;
    }

    public ManoDeObra(Integer codManObra) {
        this.codManObra = codManObra;
    }

    public Integer getCodManObra() {
        return codManObra;
    }

    public void setCodManObra(Integer codManObra) {
        this.codManObra = codManObra;
    }

    public String getManNombreProducto() {
        return manNombreProducto;
    }

    public void setManNombreProducto(String manNombreProducto) {
        this.manNombreProducto = manNombreProducto;
    }

    public Float getManCostoMillar() {
        return manCostoMillar;
    }

    public void setManCostoMillar(Float manCostoMillar) {
        this.manCostoMillar = manCostoMillar;
    }

    public Float getManCostoTiempo() {
        return manCostoTiempo;
    }

    public void setManCostoTiempo(Float manCostoTiempo) {
        this.manCostoTiempo = manCostoTiempo;
    }

    public BigDecimal getManCostoOpcional() {
        return manCostoOpcional;
    }

    public void setManCostoOpcional(BigDecimal manCostoOpcional) {
        this.manCostoOpcional = manCostoOpcional;
    }

    public BigDecimal getManAlto() {
        return manAlto;
    }

    public void setManAlto(BigDecimal manAlto) {
        this.manAlto = manAlto;
    }

    public BigDecimal getManAncho() {
        return manAncho;
    }

    public void setManAncho(BigDecimal manAncho) {
        this.manAncho = manAncho;
    }

    public String getManTipoProducto() {
        return manTipoProducto;
    }

    public void setManTipoProducto(String manTipoProducto) {
        this.manTipoProducto = manTipoProducto;
    }

    public Float getManCostoPlancha() {
        return manCostoPlancha;
    }

    public void setManCostoPlancha(Float manCostoPlancha) {
        this.manCostoPlancha = manCostoPlancha;
    }

    public Integer getManNumeroTorresImpresion() {
        return manNumeroTorresImpresion;
    }

    public void setManNumeroTorresImpresion(Integer manNumeroTorresImpresion) {
        this.manNumeroTorresImpresion = manNumeroTorresImpresion;
    }

    public String getManPermiteVolteo() {
        return manPermiteVolteo;
    }

    public void setManPermiteVolteo(String manPermiteVolteo) {
        this.manPermiteVolteo = manPermiteVolteo;
    }

    public BigDecimal getManAltoMin() {
        return manAltoMin;
    }

    public void setManAltoMin(BigDecimal manAltoMin) {
        this.manAltoMin = manAltoMin;
    }

    public BigDecimal getManAnchoMin() {
        return manAnchoMin;
    }

    public void setManAnchoMin(BigDecimal manAnchoMin) {
        this.manAnchoMin = manAnchoMin;
    }

    public Integer getManImpresionHora() {
        return manImpresionHora;
    }

    public void setManImpresionHora(Integer manImpresionHora) {
        this.manImpresionHora = manImpresionHora;
    }

    public Float getManCostoMinHora() {
        return manCostoMinHora;
    }

    public void setManCostoMinHora(Float manCostoMinHora) {
        this.manCostoMinHora = manCostoMinHora;
    }

    public Float getManCostoMinMillar() {
        return manCostoMinMillar;
    }

    public void setManCostoMinMillar(Float manCostoMinMillar) {
        this.manCostoMinMillar = manCostoMinMillar;
    }

    @XmlTransient
    public Collection<DetalleCotizacion> getDetalleCotizacionCollection() {
        return detalleCotizacionCollection;
    }

    public void setDetalleCotizacionCollection(Collection<DetalleCotizacion> detalleCotizacionCollection) {
        this.detalleCotizacionCollection = detalleCotizacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codManObra != null ? codManObra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManoDeObra)) {
            return false;
        }
        ManoDeObra other = (ManoDeObra) object;
        if ((this.codManObra == null && other.codManObra != null) || (this.codManObra != null && !this.codManObra.equals(other.codManObra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.ManoDeObra[ codManObra=" + codManObra + " ]";
    }
}
