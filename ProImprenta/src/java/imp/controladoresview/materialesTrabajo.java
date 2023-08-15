/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Operacion;
import imp.entidades.TipoOperacion;
import  imp.servicios.ServicioOperacion;
import  imp.servicios.ServicioTipoOperacion;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

/**
 *
 * @author gato
 */
public class materialesTrabajo {

    private List<Operacion> listaOperaciones = new ArrayList<Operacion>();
    ServicioOperacion servicioOperacion = new ServicioOperacion();
    ServicioTipoOperacion servicioTipoOperacion = new ServicioTipoOperacion();
    TipoOperacion tipoOperacion = new TipoOperacion();

    public materialesTrabajo() {
        tipoOperacion = servicioTipoOperacion.FindForNombre("TRABAJO");
        consultarOperacionesTrabajo();
    }

    private void consultarOperacionesTrabajo() {

        listaOperaciones = servicioOperacion.FindForTipoOperacion(tipoOperacion);
    }

    @Command
    @NotifyChange({"listaOperaciones", "nombreProducto"})
    public void actualizarOperacion(@BindingParam("valor") Operacion valor) {
        TipoOperacion tipo = servicioTipoOperacion.FindForNombre("SALIDA");
        System.out.println("tipo opera "+tipo.getTipOperacion());
        valor.setCodTipoOperacion(tipo);
        servicioOperacion.modificar(valor);
        consultarOperacionesTrabajo();

    }

    public List<Operacion> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<Operacion> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
}
