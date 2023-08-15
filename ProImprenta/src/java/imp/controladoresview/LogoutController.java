/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;



import imp.seguridad.AutentificadorImprenta;
import imp.seguridad.AutentificadorService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

public class LogoutController extends SelectorComposer<Component> {
     
    //services
    AutentificadorService authService = new AutentificadorImprenta();
     
    @Listen("onClick=#logout")
    public void doLogout(){
        authService.logout();      
        Executions.sendRedirect("/inicio.zul");
    }
}