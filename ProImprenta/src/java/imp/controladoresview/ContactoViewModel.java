/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cliente;
import imp.entidades.PersonaContacto;
import  imp.servicios.ServicioContacto;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
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
public class ContactoViewModel {

    private Cliente cliente = new Cliente();
    private PersonaContacto personaContacto = new PersonaContacto();
    private List<PersonaContacto> listaPersonaContactos = new ArrayList<PersonaContacto>();
    ServicioContacto servicioContacto = new ServicioContacto();
    
    @Wire
    Window windowContacto;


    @AfterCompose
    public void afterCompose(@ExecutionArgParam("cliente") Cliente cliente, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        if (cliente != null) {
            this.cliente = cliente;
        } else {
            this.cliente = new Cliente();
        }
        consultarContactos();
    }

    @Command
    @NotifyChange("listaPersonaContactos")
    public void guardar() {
        if (!personaContacto.getPerConNombre().equals("")) {
            personaContacto.setCodCliente(cliente);
            servicioContacto.modificar(personaContacto);
            Messagebox.show("Guardado con exito");
            consultarContactos();
//            windowContacto.detach();
        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange("listaPersonaContactos")
    public void modificar(@BindingParam("contacto") PersonaContacto contacto) {
        if (!contacto.getPerConNombre().equals("")) {
            servicioContacto.modificar(contacto);
            Messagebox.show("Guardado con exito");
            consultarContactos();
//            windowContacto.detach();
            
        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange("listaPersonaContactos")
    public void eliminarContacto(@BindingParam("contacto") PersonaContacto contacto) {

        if (Messagebox.show("¿Seguro que desea eliminar el documento recibido?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            servicioContacto.eliminar(contacto);
            consultarContactos();
            //Messagebox.show("Contacto eliminado correctamente");
        }


    }

    private void consultarContactos() {
        listaPersonaContactos = servicioContacto.FindALlContactoPorCliente(cliente);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ContactoViewModel() {
    }

    public PersonaContacto getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(PersonaContacto personaContacto) {
        this.personaContacto = personaContacto;
    }

    public List<PersonaContacto> getListaPersonaContactos() {
        return listaPersonaContactos;
    }

    public void setListaPersonaContactos(List<PersonaContacto> listaPersonaContactos) {
        this.listaPersonaContactos = listaPersonaContactos;
    }
}
