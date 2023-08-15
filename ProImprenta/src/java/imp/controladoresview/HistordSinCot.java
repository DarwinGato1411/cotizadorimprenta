/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cliente;
import imp.entidades.OrdenSinCotizacion;
import imp.entidades.Producto;
import imp.entidades.SegOrdSinCot;
import imp.servicios.ServicioCliente;
import imp.servicios.ServicioSegOrdSinCotizar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

/**
 *
 * @author gato
 */
public class HistordSinCot {

    ServicioSegOrdSinCotizar servicioSegOrdSinCotizar = new ServicioSegOrdSinCotizar();
    List<SegOrdSinCot> listaSeguimientoAll = new ArrayList<SegOrdSinCot>();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("parametro") OrdenSinCotizacion valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

       listaSeguimientoAll=servicioSegOrdSinCotizar.findByOrden(valor);

    }
    public HistordSinCot() {

        
    }

    public List<SegOrdSinCot> getListaSeguimientoAll() {
        return listaSeguimientoAll;
    }

    public void setListaSeguimientoAll(List<SegOrdSinCot> listaSeguimientoAll) {
        this.listaSeguimientoAll = listaSeguimientoAll;
    }

}
