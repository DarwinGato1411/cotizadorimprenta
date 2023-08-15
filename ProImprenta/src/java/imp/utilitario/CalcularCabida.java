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
public class CalcularCabida {

    private BigDecimal cantidadSolicitada=BigDecimal.ZERO;
    private BigDecimal anchoTotal=BigDecimal.ZERO;
    private BigDecimal largoTotal=BigDecimal.ZERO;
    private BigDecimal anchorequerido=BigDecimal.ZERO;
    private BigDecimal largoRequerido=BigDecimal.ZERO;
    private BigDecimal anchoDescuento=BigDecimal.ZERO;
    private BigDecimal largoDescuento=BigDecimal.ZERO;
    private BigDecimal numeroCabidas=BigDecimal.ZERO;
    private BigDecimal numeroPlacas=BigDecimal.ZERO;
    private String mensaje = "";

    public CalcularCabida() {
    }

    public BigDecimal getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(BigDecimal cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public BigDecimal getAnchoTotal() {
        return anchoTotal;
    }

    public void setAnchoTotal(BigDecimal anchoTotal) {
        this.anchoTotal = anchoTotal;
    }

    public BigDecimal getLargoTotal() {
        return largoTotal;
    }

    public void setLargoTotal(BigDecimal largoTotal) {
        this.largoTotal = largoTotal;
    }

    public BigDecimal getAnchorequerido() {
        return anchorequerido;
    }

    public void setAnchorequerido(BigDecimal anchorequerido) {
        this.anchorequerido = anchorequerido;
    }

    public BigDecimal getLargoRequerido() {
        return largoRequerido;
    }

    public void setLargoRequerido(BigDecimal largoRequerido) {
        this.largoRequerido = largoRequerido;
    }

    public BigDecimal getAnchoDescuento() {
        return anchoDescuento;
    }

    public void setAnchoDescuento(BigDecimal anchoDescuento) {
        this.anchoDescuento = anchoDescuento;
    }

    public BigDecimal getLargoDescuento() {
        return largoDescuento;
    }

    public void setLargoDescuento(BigDecimal largoDescuento) {
        this.largoDescuento = largoDescuento;
    }

    public BigDecimal getNumeroPlacas() {
        return numeroPlacas;
    }

    public void setNumeroPlacas(BigDecimal numeroPlacas) {
        this.numeroPlacas = numeroPlacas;
    }

    public BigDecimal getNumeroCabidas() {
        return numeroCabidas;
    }

    public void setNumeroCabidas(BigDecimal numeroCabidas) {
        this.numeroCabidas = numeroCabidas;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
