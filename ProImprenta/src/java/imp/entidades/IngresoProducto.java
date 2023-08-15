/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "ingreso_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IngresoProducto.findAll", query = "SELECT i FROM IngresoProducto i"),
    @NamedQuery(name = "IngresoProducto.findByCodigo", query = "SELECT i FROM IngresoProducto i WHERE i.codigo = :codigo"),
    @NamedQuery(name = "IngresoProducto.findByCantidad", query = "SELECT i FROM IngresoProducto i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "IngresoProducto.findByProdNombre", query = "SELECT i FROM IngresoProducto i WHERE i.prodNombre = :prodNombre"),
    @NamedQuery(name = "IngresoProducto.findByUbiNombre", query = "SELECT i FROM IngresoProducto i WHERE i.ubiNombre = :ubiNombre"),
    @NamedQuery(name = "IngresoProducto.findByCatDescripcion", query = "SELECT i FROM IngresoProducto i WHERE i.catDescripcion = :catDescripcion"),
    @NamedQuery(name = "IngresoProducto.findByTipOperacion", query = "SELECT i FROM IngresoProducto i WHERE i.tipOperacion = :tipOperacion")})
public class IngresoProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private int codigo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Size(max = 100)
    @Column(name = "prod_nombre")
    private String prodNombre;
    @Size(max = 50)
    @Column(name = "ubi_nombre")
    private String ubiNombre;
    @Size(max = 50)
    @Column(name = "cat_descripcion")
    private String catDescripcion;
    @Size(max = 50)
    @Column(name = "tip_operacion")
    private String tipOperacion;

    public IngresoProducto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public String getUbiNombre() {
        return ubiNombre;
    }

    public void setUbiNombre(String ubiNombre) {
        this.ubiNombre = ubiNombre;
    }

    public String getCatDescripcion() {
        return catDescripcion;
    }

    public void setCatDescripcion(String catDescripcion) {
        this.catDescripcion = catDescripcion;
    }

    public String getTipOperacion() {
        return tipOperacion;
    }

    public void setTipOperacion(String tipOperacion) {
        this.tipOperacion = tipOperacion;
    }
}
