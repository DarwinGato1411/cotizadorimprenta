/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Categoria;
import imp.entidades.Ubicacion;
import imp.servicios.ServicioCategoria;
import imp.servicios.ServicioUbicacion;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevaUbicacion {

    @Wire
    Window windowUbicacion;
    private Ubicacion ubicacion = new Ubicacion();
    ServicioUbicacion servicioUbicacion = new ServicioUbicacion();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("parametro") Ubicacion ubicacion, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        this.ubicacion = new Ubicacion();


    }

    @Command
    @NotifyChange("ubicacion")
    public void guardarParametro() {

        if (!ubicacion.getUbiNombre().equals("")) {
            servicioUbicacion.crear(ubicacion);
            Messagebox.show("Guardo con exito");
            windowUbicacion.detach();
        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }



    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}
