/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.UsuarioSistema;
import  imp.servicios.ServicioUsuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class UsuarioViewModel {

    ServicioUsuario servicioUsuario = new ServicioUsuario();
    UsuarioSistema usuarioSistema = new UsuarioSistema();
    private List<UsuarioSistema> listaUsuarios = new ArrayList<UsuarioSistema>();
    private String nombreUSuario = "";

    public UsuarioViewModel() {
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        listaUsuarios = servicioUsuario.FindALlUsuarioPorLikeNombre(nombreUSuario);
    }

    public List<UsuarioSistema> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<UsuarioSistema> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public String getNombreUSuario() {
        return nombreUSuario;
    }

    public void setNombreUSuario(String nombreUSuario) {
        this.nombreUSuario = nombreUSuario;
    }

    @Command
    @NotifyChange("listaUsuarios")
    public void agregarUsario(@BindingParam("usuario") UsuarioSistema usuarioSistema) {

        final HashMap<String, UsuarioSistema> map = new HashMap<String, UsuarioSistema>();
        map.put("usuario", usuarioSistema);

        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/agregarUsuario.zul", null, map);
        window.doModal();
        cargarUsuarios();


    }

    @Command
    @NotifyChange("listaUsuarios")
    public void modificarUsuario(@BindingParam("usuario") UsuarioSistema usuario) {
        if (Messagebox.show("¿Seguro que desea modificar el usuario?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            servicioUsuario.modificar(usuario);
            cargarUsuarios();
        }
    }
}
