/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cliente;
import  imp.servicios.ServicioCliente;
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
public class VerCliente {

    private Cliente cliente = new Cliente();
    ServicioCliente servicioCliente = new ServicioCliente();
    @Wire
    Window windowCliente;

    public VerCliente() {
    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("cliente") Cliente cliente, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (cliente != null) {
            this.cliente = cliente;
        } else {
            this.cliente = new Cliente();
        }

    }

    @Command
    @NotifyChange("cliente")
    public void guardar() {
        if (cliente.getRuc() != null
                && cliente.getDireccion() != null
                && cliente.getNombreComercial() != null
                && cliente.getRazonSocial() != null) {
            cliente.setEstadoCliente(1);
            servicioCliente.modificar(cliente);
//            Messagebox.show("Guardado con exito");
            cliente = new Cliente();
            windowCliente.detach();
        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
