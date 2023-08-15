/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

import imp.entidades.Producto;
import imp.entidades.Terciarizado;
import imp.entidades.TipoTrabajo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gato
 */
public class DetalleOrdenSinCotizarArmar implements Serializable {

    private BigDecimal detsinCatidadCantida = BigDecimal.ZERO;
    private BigDecimal detsinSubtotal = BigDecimal.ZERO;
    private String detsinDescripcion;
    private BigDecimal detsinValor = BigDecimal.ZERO;
    private TipoTrabajo tipoTrabajo = new TipoTrabajo();
    private Terciarizado tercerizado = new Terciarizado();
    private Producto producto = new Producto();
    private String tipoItem = "";
    private String sinTipo = "G";
    //tercerizado
    private List<Terciarizado> listaTercerizados = new ArrayList<Terciarizado>();
    private Terciarizado terciarizado = new Terciarizado();

    public DetalleOrdenSinCotizarArmar(BigDecimal detsinCatidadCantida, BigDecimal detsinSubtotal, String detsinDescripcion, BigDecimal detsinValor) {
        this.detsinCatidadCantida = detsinCatidadCantida;
        this.detsinSubtotal = detsinSubtotal;
        this.detsinDescripcion = detsinDescripcion;
        this.detsinValor = detsinValor;
    }

    public DetalleOrdenSinCotizarArmar(List<Terciarizado> listaTercerizados) {
        this.listaTercerizados = listaTercerizados;
    }

    public DetalleOrdenSinCotizarArmar() {
    }

    public BigDecimal getDetsinCatidadCantida() {
        return detsinCatidadCantida;
    }

    public void setDetsinCatidadCantida(BigDecimal detsinCatidadCantida) {
        this.detsinCatidadCantida = detsinCatidadCantida;
    }

    public BigDecimal getDetsinSubtotal() {
        return detsinSubtotal;
    }

    public void setDetsinSubtotal(BigDecimal detsinSubtotal) {
        this.detsinSubtotal = detsinSubtotal;
    }

    public String getDetsinDescripcion() {
        return detsinDescripcion;
    }

    public void setDetsinDescripcion(String detsinDescripcion) {
        this.detsinDescripcion = detsinDescripcion;
    }

    public BigDecimal getDetsinValor() {
        return detsinValor;
    }

    public void setDetsinValor(BigDecimal detsinValor) {
        this.detsinValor = detsinValor;
    }

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public Terciarizado getTercerizado() {
        return tercerizado;
    }

    public void setTercerizado(Terciarizado tercerizado) {
        this.tercerizado = tercerizado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public List<Terciarizado> getListaTercerizados() {
        return listaTercerizados;
    }

    public void setListaTercerizados(List<Terciarizado> listaTercerizados) {
        this.listaTercerizados = listaTercerizados;
    }

    public Terciarizado getTerciarizado() {
        return terciarizado;
    }

    public void setTerciarizado(Terciarizado terciarizado) {
        this.terciarizado = terciarizado;
    }

    public String getSinTipo() {
        return sinTipo;
    }

    public void setSinTipo(String sinTipo) {
        this.sinTipo = sinTipo;
    }

}
