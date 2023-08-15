/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Terciarizado;
import  imp.servicios.ServicioTerceriarizado;
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
public class TercearizadoViewModel {

    @Wire
    Window windowTerciarizado;
    private Terciarizado tercearizado = new Terciarizado();
    ServicioTerceriarizado servicioTerceriarizado = new ServicioTerceriarizado();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("tercearizado") Terciarizado tercearizadoParam, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        if (tercearizadoParam != null) {
            tercearizado = tercearizadoParam;
        } else {
            tercearizado = new Terciarizado();
        }

    }

    @Command
    @NotifyChange({"tercearizado"})
    public void guardar() {
        if (tercearizado.getTerProveedor()!= null) {

            servicioTerceriarizado.modificar(tercearizado);
//            Messagebox.show("Guardado con exito");
            tercearizado = new Terciarizado();
            windowTerciarizado.detach();

        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Terciarizado getTercearizado() {
        return tercearizado;
    }

    public void setTercearizado(Terciarizado tercearizado) {
        this.tercearizado = tercearizado;
    }
}
