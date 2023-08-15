/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

import java.math.BigDecimal;

/**
 *
 * @author gato
 */
public class InventarioGeneral {

    private String producto = "";
    private BigDecimal inicial = BigDecimal.ZERO;
    private BigDecimal ingreso = BigDecimal.ZERO;
    private BigDecimal salida = BigDecimal.ZERO;
    private BigDecimal stock = BigDecimal.ZERO;
    private BigDecimal costoCompra = BigDecimal.ZERO;
    private BigDecimal costoFinal = BigDecimal.ZERO;
    private BigDecimal cantidadMinima = BigDecimal.ZERO;
    private String estado = "";
    private String categoria = "";
    private String ubicacion = "";

    public InventarioGeneral(String producto, BigDecimal inicial, BigDecimal ingreso, BigDecimal salida, BigDecimal stock, BigDecimal costoCompra, BigDecimal costoFinal, BigDecimal cantidadMinima, String estado) {
        this.producto = producto;
        this.ingreso = ingreso;
        this.salida = salida;
        this.stock = stock;
        this.costoCompra = costoCompra;
        this.costoFinal = costoFinal;
        this.inicial = inicial;
        this.cantidadMinima = cantidadMinima;
        this.estado = estado;
    }

    public InventarioGeneral(String producto,
            BigDecimal inicial, BigDecimal ingreso,
            BigDecimal salida, BigDecimal stock, BigDecimal costoCompra,
            BigDecimal costoFinal, BigDecimal cantidadMinima, String estado,
            String categoria, String ubicacion) {
        this.producto = producto;
        this.ingreso = ingreso;
        this.salida = salida;
        this.stock = stock;
        this.costoCompra = costoCompra;
        this.costoFinal = costoFinal;
        this.inicial = inicial;
        this.cantidadMinima = cantidadMinima;
        this.estado = estado;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public BigDecimal getIngreso() {
        return ingreso;
    }

    public void setIngreso(BigDecimal ingreso) {
        this.ingreso = ingreso;
    }

    public BigDecimal getSalida() {
        return salida;
    }

    public void setSalida(BigDecimal salida) {
        this.salida = salida;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getCostoCompra() {
        return costoCompra;
    }

    public void setCostoCompra(BigDecimal costoCompra) {
        this.costoCompra = costoCompra;
    }

    public BigDecimal getCostoFinal() {
        return costoFinal;
    }

    public void setCostoFinal(BigDecimal costoFinal) {
        this.costoFinal = costoFinal;
    }

    public BigDecimal getInicial() {
        return inicial;
    }

    public void setInicial(BigDecimal inicial) {
        this.inicial = inicial;
    }

    public BigDecimal getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(BigDecimal cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
