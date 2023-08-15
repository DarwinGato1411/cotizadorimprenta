/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

import imp.entidades.Cabida;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gato
 */
public class CabidaParametro {

    private List<Cabida> listaCabida = new ArrayList<Cabida>();
    private Integer tipoCotizacion = 0; //1--> general 2-->compaginado ....etc

    public CabidaParametro(List<Cabida> lstCabida, Integer tipo) {
        this.listaCabida = lstCabida;
        this.tipoCotizacion = tipo;
    }

    public CabidaParametro() {
    }

    public List<Cabida> getListaCabida() {
        return listaCabida;
    }

    public void setListaCabida(List<Cabida> listaCabida) {
        this.listaCabida = listaCabida;
    }

    public Integer getTipoCotizacion() {
        return tipoCotizacion;
    }

    public void setTipoCotizacion(Integer tipoCotizacion) {
        this.tipoCotizacion = tipoCotizacion;
    }
}
