/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Categoria;
import imp.servicios.ServicioCategoria;
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
public class NuevaCategoria {

    @Wire
    Window windowCategoria;
    private Categoria categoria = new Categoria();
    ServicioCategoria servicioCategoria = new ServicioCategoria();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("parametro") Categoria categoria, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        this.categoria = new Categoria();


    }

    @Command
    @NotifyChange("dispositivo")
    public void guardarParametro() {

        if (!categoria.getCatDescripcion().equals("")) {
            servicioCategoria.crear(categoria);
            Messagebox.show("Guardo con exito");
            windowCategoria.detach();
        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }



    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
