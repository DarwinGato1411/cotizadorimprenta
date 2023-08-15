/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class MensajeCotGenViewModel {

    @Wire("#windowMensajeNumero")
    Window windowMensajeNumero;

    private String mensaje = "";

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("mensaje") String mensaje, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        this.mensaje = mensaje;
    }

    @Command
    @NotifyChange("dispositivo")
    public void cerrarVentana() {
        System.out.println("si ejecuta el metodfo");
        windowMensajeNumero.detach();
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
