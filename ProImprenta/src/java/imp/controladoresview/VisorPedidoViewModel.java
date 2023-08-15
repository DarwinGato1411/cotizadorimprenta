/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.utilitario.DescripcionPedido;
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
public class VisorPedidoViewModel {

    private List<DescripcionPedido> listaPedido = new ArrayList<DescripcionPedido>();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("pedido") List<DescripcionPedido> pedido, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        listaPedido = pedido;
    }

    public List<DescripcionPedido> getListaPedido() {
        return listaPedido;
    }

    public void setListaPedido(List<DescripcionPedido> listaPedido) {
        this.listaPedido = listaPedido;
    }
}
