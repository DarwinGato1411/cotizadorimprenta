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
public class DetalleMateria {
    
    private String etiquetaPlacas;
    private String etiquetaPliegos;
    private BigDecimal costoPlacas=BigDecimal.ZERO;;
    private BigDecimal costoPliegos=BigDecimal.ZERO;
    private String tipoCotizacion="";

    public BigDecimal getCostoPlacas() {
        return costoPlacas;
    }

    public void setCostoPlacas(BigDecimal costoPlacas) {
        this.costoPlacas = costoPlacas;
    }

    public BigDecimal getCostoPliegos() {
        return costoPliegos;
    }

    public void setCostoPliegos(BigDecimal costoPliegos) {
        this.costoPliegos = costoPliegos;
    }

    public String getTipoCotizacion() {
        return tipoCotizacion;
    }

    public void setTipoCotizacion(String tipoCotizacion) {
        this.tipoCotizacion = tipoCotizacion;
    }

    public String getEtiquetaPlacas() {
        return etiquetaPlacas;
    }

    public void setEtiquetaPlacas(String etiquetaPlacas) {
        this.etiquetaPlacas = etiquetaPlacas;
    }

    public String getEtiquetaPliegos() {
        return etiquetaPliegos;
    }

    public void setEtiquetaPliegos(String etiquetaPliegos) {
        this.etiquetaPliegos = etiquetaPliegos;
    }
    
    
    
    
}
