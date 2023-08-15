/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.utilitario.DetalleMateria;
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
public class DetalleMateriaViewModel {

    private DetalleMateria detalleMateria = new DetalleMateria();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("detalle") DetalleMateria detalle, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        detalleMateria = detalle;
    }

    public DetalleMateria getDetalleMateria() {
        return detalleMateria;
    }

    public void setDetalleMateria(DetalleMateria detalleMateria) {
        this.detalleMateria = detalleMateria;
    }
}
