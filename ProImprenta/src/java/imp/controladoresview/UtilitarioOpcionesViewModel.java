/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import java.util.HashMap;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class UtilitarioOpcionesViewModel {

    @Command
    public void abrirCalculadora() {

        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("detalle", "");
        Window window = (Window) Executions.createComponents(
                "/cotizacion/calculadoracabidas.zul", null, map);
        window.doModal();


    }

    @Command
    public void opcionExcel() {

        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("detalle", "");
        Window window = (Window) Executions.createComponents(
                "/cotizacion/exportarExcel.zul", null, map);
        window.doModal();


    }

    @Command
    public void opcionExcelGiganto() {

        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("detalle", "");
        Window window = (Window) Executions.createComponents(
                "/cotizacion/exportarExcelGiganto.zul", null, map);
        window.doModal();


    }
}
