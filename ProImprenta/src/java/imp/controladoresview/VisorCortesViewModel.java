/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

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
public class VisorCortesViewModel {
    
    
       @AfterCompose
    public void afterCompose(@ExecutionArgParam("prueba") String prueba, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        
    }
     
}
