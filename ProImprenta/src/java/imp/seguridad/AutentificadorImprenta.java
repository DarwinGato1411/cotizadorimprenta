/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.seguridad;


import imp.entidades.UsuarioSistema;
import imp.servicios.ServicioUsuario;
import java.io.Serializable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class AutentificadorImprenta implements AutentificadorService, Serializable {

    private static final long serialVersionUID = 1L;
    ServicioUsuario modelo = new ServicioUsuario();

    public UserCredential getUserCredential() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        if (cre == null) {
            cre = new UserCredential();
            sess.setAttribute(EnumSesion.userCredential.getNombre(), cre);
        }
        return cre;
    }


    /*
     * Cambiar el método en el ModeloUsuario para traer datos de los usuarios de hibernate
     */
    public boolean login(String nombreUsuario, String claveUsuario) {
        UsuarioSistema dato = (UsuarioSistema) modelo.FindALlUsuarioPorUsuario(nombreUsuario);
       
        if (dato.getUsuUsuario().equals("")) {
            return false;
        }
        if (!dato.getUsuUsuario().equals(nombreUsuario) || !dato.getUsuContrasena().equals(claveUsuario)) {
            return false;
        }
        
        Session sess = Sessions.getCurrent();
        UserCredential cre = new UserCredential(dato, dato.getUsuUsuario(), dato.getUsuContrasena(), dato.getUsuNivelAcceso(),dato.getUsuNombreUsuario());
        // System.out.println("VALOR DE LA CREDENCIAL ASIGNADA A LA SESSION"+EnumSesion.userCredential.getNombre());
       
            sess.setAttribute(EnumSesion.userCredential.getNombre(), cre);
      
        return true;
    }

    public void logout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute(EnumSesion.userCredential.getNombre());
    }
}
