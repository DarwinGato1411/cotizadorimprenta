/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.UsuarioSistema;
import imp.servicios.ServicioUsuario;
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
public class AgregarUsuarioViewModel {

    @Wire
    Window windowUsuario;
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    UsuarioSistema usuarioSistema = new UsuarioSistema();
    private String tipoUSuario = "1";

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("usuario") UsuarioSistema usuarioSistema, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (usuarioSistema != null) {
            this.usuarioSistema = usuarioSistema;
        } else {
            this.usuarioSistema = new UsuarioSistema();
        }
    }

    public UsuarioSistema getUsuarioSistema() {
        return usuarioSistema;
    }

    public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
        this.usuarioSistema = usuarioSistema;
    }

    public String getTipoUSuario() {
        return tipoUSuario;
    }

    public void setTipoUSuario(String tipoUSuario) {
        this.tipoUSuario = tipoUSuario;
    }

    @Command
    @NotifyChange("usuarioSistema")
    public void guardar() {
        if (usuarioSistema != null && !usuarioSistema.getUsuNombreUsuario().equals("")
                && !usuarioSistema.getUsuContrasena().equals("")
                && !usuarioSistema.getUsuUsuario().equals("")) {
            usuarioSistema.setUsuNivelAcceso(Integer.valueOf(tipoUSuario));
            usuarioSistema.setUsuActivo(Boolean.TRUE );
            servicioUsuario.modificar(usuarioSistema);

            usuarioSistema = new UsuarioSistema();
            windowUsuario.detach();
//            Messagebox.show("Guardado con exito");

        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }
}
