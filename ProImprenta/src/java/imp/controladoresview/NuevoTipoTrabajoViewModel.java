/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.TipoTrabajo;
import imp.servicios.ServicioTipoTrabajo;
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
public class NuevoTipoTrabajoViewModel {

    @Wire
    Window windowTipoTrabajo;

    private TipoTrabajo tipoTrabajo = new TipoTrabajo();
    ServicioTipoTrabajo servicioTipoTrabajo = new ServicioTipoTrabajo();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("tipo") TipoTrabajo tipoTrabajo, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        if (tipoTrabajo != null) {
            this.tipoTrabajo = tipoTrabajo;
        } else {
            this.tipoTrabajo = new TipoTrabajo();
        }

    }

    @Command
    @NotifyChange({"tipoTrabajo"})
    public void guardar() {
        if (!tipoTrabajo.getTipoDescripcion().equals("")) {

            servicioTipoTrabajo.modificar(tipoTrabajo);
//            Messagebox.show("Guardado con exito");
            tipoTrabajo = new TipoTrabajo();
            windowTipoTrabajo.detach();

        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

}
