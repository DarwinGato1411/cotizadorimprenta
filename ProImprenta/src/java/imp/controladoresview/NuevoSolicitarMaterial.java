/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.SolicitarMaterial;
import imp.entidades.UsuarioSistema;
import imp.seguridad.UserCredential;
import  imp.servicios.ServicioSolicitarMaterial;
import  imp.servicios.ServicioUsuario;
import imp.utilitario.MailerClass;
import java.rmi.RemoteException;
import java.util.Date;
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
public class NuevoSolicitarMaterial {

    @Wire
    Window windowSolMaterial;
    ServicioSolicitarMaterial servicioSolicitarMaterial = new ServicioSolicitarMaterial();
    ServicioUsuario modelo = new ServicioUsuario();
    private SolicitarMaterial solicitarMaterial = new SolicitarMaterial();
    UserCredential credential = new UserCredential();
    private String usuario;
    private String contrasena;
    MailerClass mailerClass = new MailerClass();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("solicitar") SolicitarMaterial solicitarMaterial1, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (solicitarMaterial1 != null) {
            solicitarMaterial = solicitarMaterial1;
            solicitarMaterial.setSolFecha(new Date());
            solicitarMaterial.setSolHora(new Date());
            solicitarMaterial.setSolDescripcion("");
        } else {
            solicitarMaterial = new SolicitarMaterial();
            solicitarMaterial.setSolFecha(new Date());
            solicitarMaterial.setSolDescripcion("");
            solicitarMaterial.setSolHora(new Date());
        }


    }

    public SolicitarMaterial getSolicitarMaterial() {
        return solicitarMaterial;
    }

    public void setSolicitarMaterial(SolicitarMaterial solicitarMaterial) {
        this.solicitarMaterial = solicitarMaterial;
    }

    @Command
    @NotifyChange("cliente")
    public void guardar() throws RemoteException {

        UsuarioSistema dato = (UsuarioSistema) modelo.FindALlUsuarioPorUsuario(usuario);
        boolean validar = true;


        if (dato.getUsuUsuario().equals("")) {
            validar = false;
        }
        if (!dato.getUsuUsuario().equals(usuario) || !dato.getUsuContrasena().equals(contrasena)) {
            validar = false;
        }
        if (validar) {


            if (!solicitarMaterial.getSolDescripcion().equals("")) {
                solicitarMaterial.setCodUsuarioSistema(dato);
                solicitarMaterial.setSolEstado("SOLICITADO");
                servicioSolicitarMaterial.guardar(solicitarMaterial);
//                info@imagenec.com
//                mailerClass.sendMailSimplePedido("info@imagenec.com", solicitarMaterial.getSolCantidad() + "  " + solicitarMaterial.getSolDescripcion(), "PEDIDO DE " + dato.getUsuNombreUsuario().toUpperCase(), dato.getUsuNombreUsuario());
//                Messagebox.show("Guardado con éxito");
                windowSolMaterial.detach();
            } else {
                Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
            }
        } else {
            Messagebox.show("Usuario o contraseña incorrecta", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
