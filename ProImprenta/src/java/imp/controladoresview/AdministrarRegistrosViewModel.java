/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cabida;
import imp.entidades.Categoria;
import imp.entidades.Cliente;
import imp.entidades.CortezPosibles;
import imp.entidades.MailMasivo;
import imp.entidades.ManoDeObra;
import imp.entidades.Materiales;
import imp.entidades.Operacion;
import imp.entidades.ParametrizacionPorcentajes;
import imp.entidades.Producto;
import imp.entidades.RecorteMaterial;
import imp.entidades.RecorteMaterialPK;
import imp.entidades.Terciarizado;
import imp.entidades.TipoOperacion;
import imp.entidades.TipoTrabajo;
import imp.entidades.Ubicacion;
import imp.entidades.UsuarioSistema;
import imp.seguridad.EnumSesion;
import imp.seguridad.UserCredential;
import imp.servicios.ServicioCabida;
import imp.servicios.ServicioCategoria;
import imp.servicios.ServicioCliente;
import imp.servicios.ServicioCorteMaterial;
import imp.servicios.ServicioCortez;
import imp.servicios.ServicioMailMasivo;
import imp.servicios.ServicioManoObra;
import imp.servicios.ServicioMateriales;
import imp.servicios.ServicioOperacion;
import imp.servicios.ServicioParametros;
import imp.servicios.ServicioProducto;
import imp.servicios.ServicioTerceriarizado;
import imp.servicios.ServicioTipoOperacion;
import imp.servicios.ServicioTipoTrabajo;
import imp.servicios.ServicioUbicacion;
import imp.servicios.ServicioUsuario;
import imp.utilitario.MaterialIsertarModificar;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class AdministrarRegistrosViewModel {

    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioMateriales servicioMateriales = new ServicioMateriales();
    ServicioParametros servicioParametros = new ServicioParametros();
    ServicioCorteMaterial servicioCorteMaterial = new ServicioCorteMaterial();
    ServicioTipoTrabajo servicioTipoTrabajo = new ServicioTipoTrabajo();
    ServicioMailMasivo servicioMailMasivo = new ServicioMailMasivo();
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    List<Cliente> listaClientesAll = new ArrayList<Cliente>();
    List<Materiales> listaMaterialAll = new ArrayList<Materiales>();
    List<Materiales> listaMaterialParaAsignarCorte = new ArrayList<Materiales>();
    private String buscarMaterial = "";
    private String buscarTercearizado = "";
    private String buscarManoObra = "";
    private String buscarManoObraGiganto = "";
    private String buscarCliente = "";
    private String buscarRazon = "";
    private String buscarNombreComercial = "";
    private String buscarMaterialParaAsignar = "";
    private String buscarTipoTrabajo = "";
    ServicioTerceriarizado servicioTerceriarizado = new ServicioTerceriarizado();
    private List<Terciarizado> listaTercearizadoNombre = new ArrayList<Terciarizado>();
    ServicioManoObra servicioManoObra = new ServicioManoObra();
    private List<ManoDeObra> listaManoObraNombre = new ArrayList<ManoDeObra>();
    private List<ManoDeObra> listaManoObraNombreGiganto = new ArrayList<ManoDeObra>();
    private List<ParametrizacionPorcentajes> listaParametrizacion = new ArrayList<ParametrizacionPorcentajes>();
    //para cortes
    ServicioCortez servicioCortez = new ServicioCortez();
    private List<CortezPosibles> listaCortesPorMaterial = new ArrayList<CortezPosibles>();
    private Materiales materiales = new Materiales();
    private AImage foto;
    private AImage foto1;
    private AImage foto2;
    private AImage fotoCabida;
    private AImage fotoCabidaNueva;
    AMedia fileContent = null;
    private CortezPosibles cortezPosibles = new CortezPosibles();
    private String buscarCorteLike = "";
    private Materiales materialSeleccionadoParaAsignar = new Materiales("", "");
    //model para el combo
    private ListModelList<CortezPosibles> listadoCortesModelCombo = new ListModelList<CortezPosibles>();
    private Set<CortezPosibles> registrosSeleccionados;
    private List<RecorteMaterial> listaRecorteMaterial = new ArrayList<RecorteMaterial>();
    private List<TipoTrabajo> listatTipoTrabajos = new ArrayList<TipoTrabajo>();
    //cabida
    ServicioCabida servicioCabida = new ServicioCabida();
    private Cabida cabida = new Cabida();
    private List<Cabida> listaCabida = new ArrayList<Cabida>();
    private String buscarCabidaLike = "";
    private List<UsuarioSistema> listaUsuarios = new ArrayList<UsuarioSistema>();
    //mailing
    private List<MailMasivo> listaContactoMail = new ArrayList<MailMasivo>();
    private String buscarEmail = "";

    public AdministrarRegistrosViewModel() {
        cosultarClientesALl();
        consultarMatriales(buscarMaterial);
        consultarMaterialesParaComboAsignacion(buscarMaterialParaAsignar);
        consultarTercearizadoNombre(buscarTercearizado);
        consultarManoObra(buscarManoObra);
        consultarManoObraGiganto(buscarManoObraGiganto);
//        consultarClienteLike(buscarCliente);
        consultarParametrizacion();
        consultarCortez("");

        cargarCortesPosiblesAll();
        consultarTipoTrabajoLike(buscarTipoTrabajo);
        consultarCabida("");
        cosultarUsuarios("");
        consultarMail();
        /*INVENTARIO*/
        consultarProductos();
        consultarCategorias();
        consultarUbicaciones();
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
    }

    private void consultarMail() {
        listaContactoMail = servicioMailMasivo.FindAllLike(buscarEmail);
    }

    private void cosultarUsuarios(String buscar) {
        listaUsuarios = servicioUsuario.FindALlUsuarioPorLikeNombre(buscar);
    }

    private void cosultarClientesALl() {
        listaClientesAll = servicioCliente.FindALlCliente();
    }

    private void consultarParametrizacion() {
        listaParametrizacion = servicioParametros.FindALlParametrizacionPorcentajes();
    }

    public List<Cliente> getListaClientesAll() {
        return listaClientesAll;
    }

    public void setListaClientesAll(List<Cliente> listaClientesAll) {
        this.listaClientesAll = listaClientesAll;
    }

    private void consultarClienteLike(String valor) {
        listaClientesAll = servicioCliente.FindALlClienteLike(valor);
    }

    private void consultarRazonLike(String valor) {
        listaClientesAll = servicioCliente.FindALlRazonSocialLike(valor);
    }

    private void consultarNombreComercialLike(String valor) {
        listaClientesAll = servicioCliente.FindALlNombreComercialLike(valor);
    }

    private void consultarTipoTrabajoLike(String buscar) {
        listatTipoTrabajos = servicioTipoTrabajo.FindALlTipoTrabajoLike(buscar);
    }

    @Command
    @NotifyChange("listaClientesAll")
    public void nuevoCliente() {

        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
        map.put("cliente", new Cliente());
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoCliente.zul", null, map);
        window.doModal();
        cosultarClientesALl();
    }

    @Command
    @NotifyChange("listaClientesAll")
    public void verCliente(@BindingParam("cliente") Cliente cliente) {

        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
        map.put("cliente", cliente);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/vistas/verCliente.zul", null, map);
        window.doModal();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarCliente"})
    public void buscarCliente() {

        consultarClienteLike(buscarCliente);
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarRazon"})
    public void buscarClienteRazon() {
//        buscarRazon = valor;
        consultarRazonLike(buscarRazon);
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarNombreComercial"})
    public void buscarNombreComercial() {
//        buscarNombreComercial = valor;
        consultarNombreComercialLike(buscarNombreComercial);
    }

    @Command
    @NotifyChange("listaClientesAll")
    public void modificarCliente(@BindingParam("cliente") Cliente cliente) {

        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
        map.put("cliente", cliente);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoCliente.zul", null, map);
        window.doModal();
        cosultarClientesALl();
        Clients.showNotification("Modificado con exito", "Error", null, "end_center", 4000, true);
    }

    @Command
    @NotifyChange("listaClientesAll")
    public void eiminarClientes(@BindingParam("eliminar") Cliente cliente) {

        if (Messagebox.show("¿Seguro que desea eliminar el cliente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            System.out.println("Entra ala metodo de borrar");
            cliente.setEstadoCliente(0);
            servicioCliente.modificar(cliente);
            cosultarClientesALl();
        } else {
            System.out.println("No entra  ala metodo de borrar");
        }

    }

    @Command
    @NotifyChange("listaRecibidos")
    public void eliminarRecibido(@BindingParam("eliminar") Cliente docRecibido) {
        if (Messagebox.show("¿Seguro que desea eliminar el documento recibido?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            System.out.println("entra correctamente a borrar el Cliente");
        }

    }

    @Command
    @NotifyChange("listaClientesAll")
    public void agregarContacto(@BindingParam("cliente") Cliente cliente) {

        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
        map.put("cliente", cliente);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/agregarContacto.zul", null, map);
        window.doModal();
        System.out.println("Consulta la lista de materiales");
    }

    @Command
    @NotifyChange({"listaMaterialAll", "buscarMaterial"})
    public void nuevoMaterial() {
        Materiales materiales = new Materiales("", "IMPRESION");
        MaterialIsertarModificar insertar = new MaterialIsertarModificar(materiales, "N");
        final HashMap<String, MaterialIsertarModificar> map = new HashMap<String, MaterialIsertarModificar>();
        map.put("material", insertar);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoMaterial.zul", null, map);
        window.doModal();
        consultarMatriales(buscarMaterial);
        System.out.println("Consulta la lista de materiales");
    }

    @Command
    @NotifyChange({"listaTercearizadoNombre", "buscarTercearizado"})
    public void buscarTercearizadoLike() {

//        buscarTercearizado = valor;
        consultarTercearizadoNombre(buscarTercearizado);
        System.out.println("Consulta la lista de materiales");
    }

    @Command
    @NotifyChange({"listaManoObraNombre", "buscarManoObra"})
    public void buscarManoObraLike(@BindingParam("valor") String valor) {

        buscarManoObra = valor;
        consultarManoObra(buscarManoObra);
        System.out.println("Consulta la lista de manoObra");
    }

    @Command
    @NotifyChange({"listaManoObraNombreGiganto", "buscarManoObraGiganto"})
    public void buscarManoObraLikeGiganto(@BindingParam("valor") String valor) {

        buscarManoObraGiganto = valor;
        consultarManoObraGiganto(buscarManoObraGiganto);
        System.out.println("Consulta la lista de manoObra");
    }

    @Command
    @NotifyChange({"listaTercearizadoNombre", "buscarTercearizado"})
    public void modificarTercearizado(@BindingParam("tercearizado") Terciarizado terciarizado) {

        final HashMap<String, Terciarizado> map = new HashMap<String, Terciarizado>();
        map.put("tercearizado", terciarizado);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoTercearizado.zul", null, map);
        window.doModal();
        consultarTercearizadoNombre(buscarTercearizado);
        System.out.println("entra a cerrar la ventana");
        window.detach();
    }

    @Command
    @NotifyChange({"listatTipoTrabajos", "buscarTipoTrabajo"})
    public void modificarTipoTrabajo(@BindingParam("tipo") TipoTrabajo tipoTrabajo) {

        final HashMap<String, TipoTrabajo> map = new HashMap<String, TipoTrabajo>();
        map.put("tipo", tipoTrabajo);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoTipoTrabajo.zul", null, map);
        window.doModal();
        consultarTipoTrabajoLike(buscarTipoTrabajo);
        System.out.println("entra a cerrar la ventana");
//        window.detach();
    }

    @Command
    @NotifyChange({"listaTercearizadoNombre", "buscarTercearizado"})
    public void nuevoTercearizado() {
        final HashMap<String, Terciarizado> map = new HashMap<String, Terciarizado>();
        map.put("tercearizado", new Terciarizado());
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoTercearizado.zul", null, map);
        window.doModal();
        consultarTercearizadoNombre(buscarTercearizado);
    }

    @Command
    @NotifyChange({"listatTipoTrabajos", "buscarTipoTrabajo"})
    public void nuevoTipoTrabajo() {
        final HashMap<String, TipoTrabajo> map = new HashMap<String, TipoTrabajo>();
        map.put("tipo", new TipoTrabajo());
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoTipoTrabajo.zul", null, map);
        window.doModal();
        consultarTipoTrabajoLike("");
    }

    @Command
    @NotifyChange({"listaMaterialAll", "buscarMaterial"})
    public void modificarMaterial(@BindingParam("material") Materiales material) {
        MaterialIsertarModificar insertar = new MaterialIsertarModificar(material, "M");
        final HashMap<String, MaterialIsertarModificar> map = new HashMap<String, MaterialIsertarModificar>();
        map.put("material", insertar);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoMaterial.zul", null, map);
        window.doModal();
        consultarMatriales(buscarMaterial);
    }

    @Command
    @NotifyChange({"listaMaterialAll", "buscarMaterial"})
    public void modificarMaterialVarios(@BindingParam("material") Materiales material) {

        final HashMap<String, Materiales> map = new HashMap<String, Materiales>();
        map.put("material", material);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoMaterialVarios.zul", null, map);
        window.doModal();
        consultarMatriales(buscarMaterial);
    }

    @Command
    @NotifyChange({"listaMaterialAll", "buscarMaterial"})
    public void buscarMaterial(@BindingParam("valor") String valor) {
        buscarMaterial = valor;
        consultarMatriales(valor);
    }

    @Command
    @NotifyChange({"listatTipoTrabajos", "buscarTipoTrabajo"})
    public void buscarTipoTrabajoLike() {

        consultarTipoTrabajoLike(buscarTipoTrabajo);
    }

    @Command
    @NotifyChange("listaClientesAll")
    public void cortesPosibles(@BindingParam("material") Materiales material) {

        final HashMap<String, Materiales> map = new HashMap<String, Materiales>();
        map.put("material", material);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/cortesMateriales.zul", null, map);
        window.doModal();
    }

    //metodos para mano de obra
    @Command
    @NotifyChange({"listaManoObraNombre", "buscarManoObra"})
    public void nuevaManoObra() {
        ManoDeObra mano = new ManoDeObra("IMPRESION");
        final HashMap<String, ManoDeObra> map = new HashMap<String, ManoDeObra>();
        map.put("manoObra", mano);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevaManoDeObra.zul", null, map);
        window.doModal();
        consultarManoObra(buscarManoObra);

    }

    @Command
    @NotifyChange({"listaManoObraNombreGiganto", "buscarManoObra"})
    public void nuevaManoObraGiganto() {
        ManoDeObra mano = new ManoDeObra("GIGANTOGRAFIA");
        final HashMap<String, ManoDeObra> map = new HashMap<String, ManoDeObra>();
        map.put("manoObra", mano);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevaManoDeObraGiganto.zul", null, map);
        window.doModal();
        consultarManoObraGiganto(buscarManoObraGiganto);

    }

    @Command
    @NotifyChange({"listaManoObraNombre", "buscarManoObraGiganto"})
    public void modificarManoObra(@BindingParam("manoObra") ManoDeObra manoDeObra) {
        final HashMap<String, ManoDeObra> map = new HashMap<String, ManoDeObra>();
        map.put("manoObra", manoDeObra);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevaManoDeObra.zul", null, map);
        window.doModal();
        consultarManoObra(buscarManoObra);

    }

    @Command
    @NotifyChange({"listaManoObraNombreGiganto", "buscarManoObraGiganto"})
    public void modificarManoObraGiganto(@BindingParam("manoObra") ManoDeObra manoDeObra) {
        final HashMap<String, ManoDeObra> map = new HashMap<String, ManoDeObra>();
        map.put("manoObra", manoDeObra);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevaManoDeObraGiganto.zul", null, map);
        window.doModal();
        consultarManoObraGiganto(buscarManoObraGiganto);

    }

    @Command
    @NotifyChange("listaCortesPorMaterial")
    public void modificarCorte(@BindingParam("corte") CortezPosibles corte) {
        servicioCortez.modificar(corte);
        consultarCortez("");
        //  Messagebox.show("Guardado con exito");
    }

    @Command
    @NotifyChange("listaParametrizacion")
    public void modificarParametrizacion(@BindingParam("parametro") ParametrizacionPorcentajes parametrizacion) {
        servicioParametros.modificar(parametrizacion);
        consultarParametrizacion();
//        Messagebox.show("Guardado con exito");
        Clients.showNotification("Modificado con exito", "Error", null, "end_center", 4000, true);
    }
//usuarios

    @Command
    @NotifyChange("listaUsuarios")
    public void agregarUsario() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/agregarUsuario.zul", null, null);
        window.doModal();
        cosultarUsuarios("");
    }

    @Command
    @NotifyChange("listaUsuarios")
    public void modificarUsario(@BindingParam("usuario") UsuarioSistema usuario) {
        final HashMap<String, UsuarioSistema> map = new HashMap<String, UsuarioSistema>();
        map.put("usuario", usuario);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/agregarUsuario.zul", null, map);
        window.doModal();

        cosultarUsuarios("");
    }

    @Command
    @NotifyChange("listaUsuarios")
    public void desactivarUsario(@BindingParam("usuario") UsuarioSistema usuario) {
        if (Messagebox.show("¿Desea eliminar el usuario?", "Atención",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

            usuario.setUsuActivo(Boolean.FALSE);
            servicioUsuario.modificar(usuario);
            Clients.showNotification("Eliminado con éxito",
                    "Info", null, "end_center", 3000, true);
            cosultarUsuarios("");
        }
    }
    //metodo de busqueda del material

    private void consultarMatriales(String valor) {
        listaMaterialAll = servicioMateriales.FindALlMaterialesLikeNombre(valor);
    }

    public String getBuscarMaterial() {
        return buscarMaterial;
    }

    public void setBuscarMaterial(String buscarMaterial) {
        this.buscarMaterial = buscarMaterial;
    }

    public List<Materiales> getListaMaterialAll() {
        return listaMaterialAll;
    }

    public void setListaMaterialAll(List<Materiales> listaMaterialAll) {
        this.listaMaterialAll = listaMaterialAll;
    }

    //terciarizados
    private void consultarTercearizadoNombre(String valor) {
        listaTercearizadoNombre = servicioTerceriarizado.FindALlTerciarizadoPorNombre(valor);
    }

    public List<Terciarizado> getListaTercearizadoNombre() {
        return listaTercearizadoNombre;
    }

    public void setListaTercearizadoNombre(List<Terciarizado> listaTercearizadoNombre) {
        this.listaTercearizadoNombre = listaTercearizadoNombre;
    }

    public String getBuscarTercearizado() {
        return buscarTercearizado;
    }

    public void setBuscarTercearizado(String buscarTercearizado) {
        this.buscarTercearizado = buscarTercearizado;
    }
//mano de obra

    private void consultarManoObra(String manoObra) {
        listaManoObraNombre = servicioManoObra.FindALlManoDeObraImpreso(manoObra);
    }

    public List<ManoDeObra> getListaManoObraNombre() {
        return listaManoObraNombre;
    }

    public void setListaManoObraNombre(List<ManoDeObra> listaManoObraNombre) {
        this.listaManoObraNombre = listaManoObraNombre;
    }

    public String getBuscarManoObra() {
        return buscarManoObra;
    }

    public void setBuscarManoObra(String buscarManoObra) {
        this.buscarManoObra = buscarManoObra;
    }

    public String getBuscarCliente() {
        return buscarCliente;
    }

    public void setBuscarCliente(String buscarCliente) {
        this.buscarCliente = buscarCliente;
    }

    public List<ParametrizacionPorcentajes> getListaParametrizacion() {
        return listaParametrizacion;
    }

    public void setListaParametrizacion(List<ParametrizacionPorcentajes> listaParametrizacion) {
        this.listaParametrizacion = listaParametrizacion;
    }

    //parte de cortes
    @Command
    @NotifyChange({"listaCortesPorMaterial", "cortezPosibles", "foto1"})
    public void guardar() {
        if (cortezPosibles.getCortDescripcion() != null) {
            //cortezPosibles.setCodMaterial(materiales);
            servicioCortez.crear(cortezPosibles);
//            cortezPosibles.setCortImagen(null);
            foto1 = null;

            Messagebox.show("Registro guardado con exito.");
            consultarCortez("");
            cortezPosibles = new CortezPosibles();

        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @Command
    @NotifyChange({"listaCabida", "cabida", "buscarCabidaLike", "fotoCabidaNueva"})
    public void guardarCabida() {

        if (cabida.getCabDescripcion() != null) {
            //cortezPosibles.setCodMaterial(materiales);
            servicioCabida.modificar(cabida);
//            cortezPosibles.setCortImagen(null);
            fotoCabidaNueva = null;

            Messagebox.show("Registro guardado con exito.");
            consultarCabida("");
            cabida = new Cabida();

        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @Command
    @NotifyChange({"listaCortesPorMaterial", "foto"})
    public void verFoto(@BindingParam("material") CortezPosibles materiales) throws IOException {

        foto = new AImage("foto", materiales.getCortImagen());
    }

    @Command
    @NotifyChange({"foto2"})
    public void verFotoAsignar(@BindingParam("material") CortezPosibles materiales) throws IOException {

        foto2 = new AImage("foto", materiales.getCortImagen());
    }

    @Command
    @NotifyChange({"fotoCabida"})
    public void verFotoCabida(@BindingParam("cabida") Cabida cabida1) throws IOException {

        fotoCabida = new AImage("foto", cabida1.getCabGrafico());
    }

    @Command
    @NotifyChange("foto1")
    public void subirArchivo() throws InterruptedException, IOException {
        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 300 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 300KB.\n Por favor seleccione un archivo más pequeño.");
                return;
            }
            this.cortezPosibles.setCortImagen(media.getByteData());
            foto1 = new AImage("foto", this.cortezPosibles.getCortImagen());
            consultarCortez("");
            Messagebox.show("La fotografía fue grabada exitosamente.");
        } else {
            Messagebox.show("El arhivo seleccionado no es una imagen.\n Selecione un archivo con extensión .jpg, png o gif.");
        }

    }

    @Command
    @NotifyChange({"listaCortesPorMaterial", "buscarCorteLike"})
    public void buscarCortesPosiblesLike(@BindingParam("valor") String valor) {
        buscarCorteLike = valor;
        consultarCortez(valor);
    }

    @Command
    @NotifyChange({"listaCabida", "buscarCabidaLike"})
    public void buscarCabidaPosiblesLike(@BindingParam("valor") String valor) {
        buscarCabidaLike = valor;
        consultarCabida(valor);
    }

    @Command
    @NotifyChange("fotoCabidaNueva")
    public void subirArchivoCabida() throws InterruptedException, IOException {
        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 300 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 300KB.\n Por favor seleccione un archivo más pequeño.");
                return;
            }
            this.cabida.setCabGrafico(media.getByteData());
            fotoCabidaNueva = new AImage("fotoCabida", this.cabida.getCabGrafico());
            consultarCabida("");
            Messagebox.show("La fotografía fue grabada exitosamente.");
        } else {
            Messagebox.show("El arhivo seleccionado no es una imagen.\n Selecione un archivo con extensión .jpg, png o gif.");
        }

    }

    @Command
    public void modificarArchivoCabida(@BindingParam("cabida") Cabida cabidaMod) throws InterruptedException, IOException {
        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 300 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 300KB.\n Por favor seleccione un archivo más pequeño.");
                return;
            }
            cabidaMod.setCabGrafico(media.getByteData());
            servicioCabida.modificar(cabidaMod);
            consultarCabida("");
            Clients.showNotification("Modificado con éxito", "Info", null, "end_center", 4000, true);
        } else {
            Messagebox.show("El arhivo seleccionado no es una imagen.\n Selecione un archivo con extensión .jpg, png o gif.");
        }

    }

    @Command
    public void modificarArchivoCorte(@BindingParam("corte") CortezPosibles corteP) throws InterruptedException, IOException {
        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 300 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 300KB.\n Por favor seleccione un archivo más pequeño.");
                return;
            }
            corteP.setCortImagen(media.getByteData());
            servicioCortez.modificar(corteP);
            consultarCabida("");
            Clients.showNotification("Modificado con éxito", "Info", null, "end_center", 4000, true);
        } else {
            Messagebox.show("El arhivo seleccionado no es una imagen.\n Selecione un archivo con extensión .jpg, png o gif.");
        }

    }

    @Command
    @NotifyChange({"listaMaterialParaAsignarCorte", "buscarMaterialParaAsignar"})
    public void buscarMaterialParaAsignarCortes(@BindingParam("valor") String valor) {
        listaMaterialParaAsignarCorte.clear();
        buscarMaterialParaAsignar = valor;
        consultarMaterialesParaComboAsignacion(buscarMaterialParaAsignar);
    }

    @Command
    @NotifyChange({"listadoCortesModelCombo", "listaMaterialAll", "materialSeleccionadoParaAsignar", "listaMaterialParaAsignarCorte", "buscarMaterialParaAsignar"})
    public void grabarMaterialCorte() {
        if (!materialSeleccionadoParaAsignar.getMatNombre().equals("") && registrosSeleccionados != null) {
            System.out.println("Valor del material seleccionado::" + materialSeleccionadoParaAsignar.getMatNombre());
            System.out.println("Cantidad de seleciones de materiales::" + registrosSeleccionados.size());
            RecorteMaterial recorteMaterial;

            RecorteMaterialPK recorteMaterialPK;
            for (CortezPosibles item : registrosSeleccionados) {
                recorteMaterialPK = new RecorteMaterialPK();
                recorteMaterial = new RecorteMaterial();
                recorteMaterialPK.setCodCorte(item.getCodCorte());
                recorteMaterialPK.setCodMaterial(materialSeleccionadoParaAsignar.getCodMaterial());

                recorteMaterial.setRecorteMaterialPK(recorteMaterialPK);

                //recorteMaterial.setMateriales(materialSeleccionadoParaAsignar);
                System.out.println("Valores seleccionados " + item.getCortDescripcion());
                servicioCorteMaterial.crear(recorteMaterial);

            }
            Messagebox.show("Fue grabada exitosamente.");
            materialSeleccionadoParaAsignar = new Materiales("", "");

            consultarMaterialesParaComboAsignacion("");
            cargarCortesPosiblesAll();
            buscarMaterialParaAsignar = "";
        } else {
            Messagebox.show("Verifique la información");
        }
    }

    private void consultarMaterialesParaComboAsignacion(String valor) {
        listaMaterialParaAsignarCorte = servicioMateriales.FindALlMaterialesImpresionLike(valor);
    }

    private void consultarCortez(String cosultar) {
        listaCortesPorMaterial = servicioCortez.FindALlCortezPosiblesLikeNombre(cosultar);
    }

    private void consultarCabida(String cosultar) {
        listaCabida = servicioCabida.FindALlCabidaLikeNombre(cosultar);
    }

    public List<CortezPosibles> getListaCortesPorMaterial() {
        return listaCortesPorMaterial;
    }

    public void setListaCortesPorMaterial(List<CortezPosibles> listaCortesPorMaterial) {
        this.listaCortesPorMaterial = listaCortesPorMaterial;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public AImage getFoto() {
        return foto;
    }

    public void setFoto(AImage foto) {
        this.foto = foto;
    }

    public CortezPosibles getCortezPosibles() {
        return cortezPosibles;
    }

    public void setCortezPosibles(CortezPosibles cortezPosibles) {
        this.cortezPosibles = cortezPosibles;
    }

    public AImage getFoto1() {
        return foto1;
    }

    public void setFoto1(AImage foto1) {
        this.foto1 = foto1;
    }

    public String getBuscarCorteLike() {
        return buscarCorteLike;
    }

    public void setBuscarCorteLike(String buscarCorteLike) {
        this.buscarCorteLike = buscarCorteLike;
    }

    public List<Materiales> getListaMaterialParaAsignarCorte() {
        return listaMaterialParaAsignarCorte;
    }

    public void setListaMaterialParaAsignarCorte(List<Materiales> listaMaterialParaAsignarCorte) {
        this.listaMaterialParaAsignarCorte = listaMaterialParaAsignarCorte;
    }

    public Materiales getMaterialSeleccionadoParaAsignar() {
        return materialSeleccionadoParaAsignar;
    }

    public void setMaterialSeleccionadoParaAsignar(Materiales materialSeleccionadoParaAsignar) {
        this.materialSeleccionadoParaAsignar = materialSeleccionadoParaAsignar;
    }

    public String getBuscarMaterialParaAsignar() {
        return buscarMaterialParaAsignar;
    }

    public void setBuscarMaterialParaAsignar(String buscarMaterialParaAsignar) {
        this.buscarMaterialParaAsignar = buscarMaterialParaAsignar;
    }

    public ListModelList<CortezPosibles> getListadoCortesModelCombo() {
        return listadoCortesModelCombo;
    }

    public void setListadoCortesModelCombo(ListModelList<CortezPosibles> listadoCortesModelCombo) {
        this.listadoCortesModelCombo = listadoCortesModelCombo;
    }

    private void cargarCortesPosiblesAll() {
        setListadoCortesModelCombo(new ListModelList<CortezPosibles>(servicioCortez.FindALlCortezPosiblesLikeNombre("")));
        ((ListModelList<CortezPosibles>) listadoCortesModelCombo).setMultiple(true);
    }

    @Command
    public void seleccionarAsignarCortes() {
        registrosSeleccionados = ((ListModelList<CortezPosibles>) getListadoCortesModelCombo()).getSelection();
        for (CortezPosibles item : registrosSeleccionados) {
            System.out.println("Valores seleccionados " + item.getCortDescripcion());
        }
    }

    public AImage getFoto2() {
        return foto2;
    }

    public void setFoto2(AImage foto2) {
        this.foto2 = foto2;
    }

    public List<RecorteMaterial> getListaRecorteMaterial() {
        return listaRecorteMaterial;
    }

    public void setListaRecorteMaterial(List<RecorteMaterial> listaRecorteMaterial) {
        this.listaRecorteMaterial = listaRecorteMaterial;
    }

    public String getBuscarTipoTrabajo() {
        return buscarTipoTrabajo;
    }

    public void setBuscarTipoTrabajo(String buscarTipoTrabajo) {
        this.buscarTipoTrabajo = buscarTipoTrabajo;
    }

    public List<TipoTrabajo> getListatTipoTrabajos() {
        return listatTipoTrabajos;
    }

    public void setListatTipoTrabajos(List<TipoTrabajo> listatTipoTrabajos) {
        this.listatTipoTrabajos = listatTipoTrabajos;
    }

    public Cabida getCabida() {
        return cabida;
    }

    public void setCabida(Cabida cabida) {
        this.cabida = cabida;
    }

    public List<Cabida> getListaCabida() {
        return listaCabida;
    }

    public void setListaCabida(List<Cabida> listaCabida) {
        this.listaCabida = listaCabida;
    }

    public String getBuscarCabidaLike() {
        return buscarCabidaLike;
    }

    public void setBuscarCabidaLike(String buscarCabidaLike) {
        this.buscarCabidaLike = buscarCabidaLike;
    }

    public AImage getFotoCabida() {
        return fotoCabida;
    }

    public void setFotoCabida(AImage fotoCabida) {
        this.fotoCabida = fotoCabida;
    }

    public AImage getFotoCabidaNueva() {
        return fotoCabidaNueva;
    }

    public void setFotoCabidaNueva(AImage fotoCabidaNueva) {
        this.fotoCabidaNueva = fotoCabidaNueva;
    }

    private void consultarManoObraGiganto(String manoObra) {
        listaManoObraNombreGiganto = servicioManoObra.FindALlManoDeObraGiganto(manoObra);
    }

    public List<ManoDeObra> getListaManoObraNombreGiganto() {

        return listaManoObraNombreGiganto;
    }

    public void setListaManoObraNombreGiganto(List<ManoDeObra> listaManoObraNombreGiganto) {
        this.listaManoObraNombreGiganto = listaManoObraNombreGiganto;
    }

    public String getBuscarManoObraGiganto() {
        return buscarManoObraGiganto;
    }

    public void setBuscarManoObraGiganto(String buscarManoObraGiganto) {
        this.buscarManoObraGiganto = buscarManoObraGiganto;
    }

    public String getBuscarRazon() {
        return buscarRazon;
    }

    public void setBuscarRazon(String buscarRazon) {
        this.buscarRazon = buscarRazon;
    }

    public String getBuscarNombreComercial() {
        return buscarNombreComercial;
    }

    public void setBuscarNombreComercial(String buscarNombreComercial) {
        this.buscarNombreComercial = buscarNombreComercial;
    }

    public List<UsuarioSistema> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<UsuarioSistema> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<MailMasivo> getListaContactoMail() {
        return listaContactoMail;
    }

    public void setListaContactoMail(List<MailMasivo> listaContactoMail) {
        this.listaContactoMail = listaContactoMail;
    }

    public String getBuscarEmail() {
        return buscarEmail;
    }

    public void setBuscarEmail(String buscarEmail) {
        this.buscarEmail = buscarEmail;
    }

    @Command
    @NotifyChange("listaContactoMail")
    public void agregarMailing() {
//
//        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
//        map.put("cliente", null);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoMailing.zul", null, null);
        window.doModal();
        consultarMail();
    }

    @Command
    @NotifyChange("listaContactoMail")
    public void modificarMailing(@BindingParam("valor") MailMasivo valor) {
//
        final HashMap<String, MailMasivo> map = new HashMap<String, MailMasivo>();
        map.put("valor", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/nuevoMailing.zul", null, map);
        window.doModal();
        consultarMail();
    }

    @Command
    @NotifyChange({"listaContactoMail", "buscarEmail"})
    public void buscarMailing(@BindingParam("valor") String valor) {
        buscarEmail = valor;
        consultarMail();
    }
    private String filePath = "";
    File f = null;
    byte[] buffer = new byte[9 * 1024 * 1024];

    @Command
    @NotifyChange("listaContactoMail")
    public void cargarMailExcel() throws java.text.ParseException {

        try {

            org.zkoss.util.media.Media media = Fileupload.get();
            if (media instanceof org.zkoss.util.media.AMedia) {
                String nombre = media.getName().toString();
                if (media != null && nombre.contains(".xls")) {
                    if (media.getByteData().length > 9 * 1024 * 1024) {
                        Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 9 MB.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                        return;
                    }

                    Calendar now = Calendar.getInstance();
                    int year = now.get(Calendar.YEAR);
                    int month = now.get(Calendar.MONTH);// Note: zero based!
                    int day = now.get(Calendar.DAY_OF_MONTH);
//                filePath = Executions.getCurrent().getDesktop().getWebApp()
//                        .getRealPath("/");
                    filePath = "D:";
                    String yearPath = year + File.separator + month + File.separator;
                    filePath = filePath + yearPath;

                    File baseDir = new File(filePath);
                    if (!baseDir.exists()) {
                        baseDir.mkdirs();
                    }

                    Files.copy(new File(filePath + media.getName()),
                            media.getStreamData());

                    filePath = filePath + media.getName();
                    System.out.println("pathhhhhhh " + filePath);
                    f = new File(filePath);

                    Workbook workbook = Workbook.getWorkbook(f); //Pasamos el excel que vamos a leer

                    Sheet sheet = workbook.getSheet(0);
                    //Seleccionamos la hoja que vamos a leer
                    MailMasivo mailMasivo;
                    for (int fila = 1; fila < sheet.getRows(); fila++) { //recorremos las filas
                        if (!sheet.getCell(0, fila).getContents().equals("")) {
                            System.out.println("Valor " + sheet.getCell(0, fila).getContents());
                            mailMasivo = new MailMasivo(sheet.getCell(1, fila).getContents().toString(), sheet.getCell(0, fila).getContents().toString());

                            servicioMailMasivo.crear(mailMasivo);
                        }
                    }
//                    Messagebox.show("Contactos ingresados con éxito");
                } else {
                    Messagebox.show("Seleccione un archivo .xls", "Atención", Messagebox.OK, Messagebox.ERROR);
                }
            }
            buscarEmail = "";
            consultarMail();

        } catch (IOException ex) {
            System.out.println("error " + ex);
        } catch (BiffException ex) {
            System.out.println("error " + ex);

        }
    }

    @Command
    @NotifyChange("listaContactoMail")
    public void eliminarMailCont(@BindingParam("valor") MailMasivo valor) throws java.text.ParseException {
        if (Messagebox.show("¿Seguro que desea eliminar el contacto?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            servicioMailMasivo.eliminar(valor);
            Messagebox.show("Eliminado con exito");
            consultarMail();
        }
    }

    /*INVENTARIO METODOS*/
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioCategoria servicioCategoria = new ServicioCategoria();
    ServicioUbicacion servicioUbicacion = new ServicioUbicacion();
    ServicioTipoOperacion servicioTipoOperacion = new ServicioTipoOperacion();
    ServicioOperacion servicioOperacion = new ServicioOperacion();
    private List<Producto> listaProducto = new ArrayList<Producto>();
    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private List<Ubicacion> listaUbicacions = new ArrayList<Ubicacion>();

    private Operacion operacion = new Operacion();
    private Producto producto = new Producto();
    UserCredential credential = new UserCredential();

    private void consultarProductos() {
        listaProducto = servicioProducto.FindAll();
    }

    private void consultarCategorias() {
        listaCategorias = servicioCategoria.FindAll();
    }

    private void consultarUbicaciones() {
        listaUbicacions = servicioUbicacion.FindAll();
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<Ubicacion> getListaUbicacions() {
        return listaUbicacions;
    }

    public void setListaUbicacions(List<Ubicacion> listaUbicacions) {
        this.listaUbicacions = listaUbicacions;
    }

    //nuevos registros
    @Command
    @NotifyChange("listaCategorias")
    public void nuevaCategoria() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/categoria.zul", null, null);
        window.doModal();
        consultarCategorias();

    }

    @Command
    @NotifyChange("listaUbicacions")
    public void nuevaUbicacion() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/ubicacion.zul", null, null);
        window.doModal();
        consultarUbicaciones();

    }

    @Command
    @NotifyChange("listaProducto")
    public void nuevoProducto() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/producto.zul", null, null);
        window.doModal();
        consultarProductos();

    }

//    Modificacions
    @Command
    @NotifyChange("listaCategorias")
    public void modificarCategoria(@BindingParam("valor") Categoria valor) {
        servicioCategoria.modificar(valor);
        Messagebox.show("Modificado con exito");
        consultarCategorias();

    }

    @Command
    @NotifyChange("listaUbicacions")
    public void modificarUbicacion(@BindingParam("valor") Ubicacion valor) {
        servicioUbicacion.modificar(valor);
        Messagebox.show("Modificado con exito");
        consultarUbicaciones();

    }

    @Command
    @NotifyChange("listaProducto")
    public void modificarProducto(@BindingParam("valor") Producto valor) {
        final HashMap<String, Producto> map = new HashMap<String, Producto>();
        map.put("parametro", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/producto.zul", null, map);
        window.doModal();
        consultarProductos();

    }

    @Command
    public void cargarInventarioInicial() throws java.text.ParseException {

        try {

            org.zkoss.util.media.Media media = Fileupload.get();
            if (media instanceof org.zkoss.util.media.AMedia) {
                String nombre = media.getName().toString();
                if (media != null && nombre.contains("xls")) {
                    if (media.getByteData().length > 9 * 1024 * 1024) {
                        Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 9 MB.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                        return;
                    }

                    Calendar now = Calendar.getInstance();
                    int year = now.get(Calendar.YEAR);
                    int month = now.get(Calendar.MONTH);// Note: zero based!
                    int day = now.get(Calendar.DAY_OF_MONTH);
//                filePath = Executions.getCurrent().getDesktop().getWebApp()
//                        .getRealPath("/");
                    filePath = "D:";
                    String yearPath = year + File.separator + month + File.separator;
                    filePath = filePath + yearPath;

                    File baseDir = new File(filePath);
                    if (!baseDir.exists()) {
                        baseDir.mkdirs();
                    }
//                Filedownload.save(media);
                    Files.copy(new File(filePath + media.getName()),
                            media.getStreamData());

                    filePath = filePath + media.getName();
                    System.out.println("pathhhhhhh " + filePath);
                    f = new File(filePath);
                    // TODO add your handling code here:

//            txtEstado.setText("EN EJECUCIÓN ESPERE UN MOMENTO");
//            txtEstado.setText("FINALIZADO CON ÈXITO");
//            Workbook workbook = Workbook.getWorkbook(new File("C:" + File.separator + "usr" + File.separator + "local" + File.separator + "usuarios.xls")); //Pasamos el excel que vamos a leer
                    Workbook workbook = Workbook.getWorkbook(f); //Pasamos el excel que vamos a leer
//                    Workbook workbook = Workbook.getWorkbook(new File("/usr" + File.separator + "local" + File.separator + "usuarios.xls")); //Pasamos el excel que vamos a leer
                    Sheet sheet = workbook.getSheet(0);
                    //Seleccionamos la hoja que vamos a leer

                    for (int fila = 1; fila < sheet.getRows(); fila++) { //recorremos las filas
                        if (!sheet.getCell(0, fila).getContents().equals("")) {
                            System.out.println("Valor " + sheet.getCell(0, fila).getContents());
                            producto = new Producto();
                            //0 fecha
                            //1 valor inicial
                            //2 nombre
                            //3 marca
                            //4 serie
                            //5 unidad medida
                            //6 categoria
                            //7 estado
                            //8 ubicacion
                            //9 costo
                            //10 proveedor
                            //11 factura
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                            String strFecha = sheet.getCell(0, fila).getContents();
                            Date fechaDate = null;
                            try {
                                fechaDate = formato.parse(strFecha);
                                System.out.println(fechaDate.toString());

                            } catch (Exception ex) {
                                System.out.println("error " + ex);
                            }

                            TipoOperacion tipoOperacion = servicioTipoOperacion.FindForNombre("INGRESO");
                            Categoria cat = servicioCategoria.FindAllForDescripcion(sheet.getCell(6, fila).getContents());
                            Ubicacion ubicacion = servicioUbicacion.FindAllForNombre(sheet.getCell(8, fila).getContents());

                            producto.setCodCategoria(cat);
                            producto.setCodUbicacion(ubicacion);
                            producto.setProdSerie(sheet.getCell(4, fila).getContents());
                            producto.setProdNombre(sheet.getCell(2, fila).getContents());
                            producto.setProdCostoCompra(BigDecimal.valueOf(Double.valueOf(sheet.getCell(9, fila).getContents().replace(",", "."))));
                            producto.setProdCostoVenta(BigDecimal.ZERO);
                            producto.setProdValorIncial(BigDecimal.valueOf(Double.valueOf(sheet.getCell(1, fila).getContents().replace(",", "."))));
                            producto.setProdUnidadMedida(sheet.getCell(5, fila).getContents());
                            producto.setProdFechaRegistro(fechaDate);
                            producto.setProdCantidadMinima(BigDecimal.ZERO);
                            producto.setProdMarca(sheet.getCell(3, fila).getContents());

                            operacion = new Operacion(producto.getProdValorIncial(),
                                    producto.getProdFechaRegistro(),
                                    sheet.getCell(7, fila).getContents(),
                                    "INICIO VENTARIO",
                                    "INICIO VENTARIO",
                                    "INICIO VENTARIO",
                                    sheet.getCell(10, fila).getContents(),
                                    sheet.getCell(11, fila).getContents(),
                                    producto.getProdCostoCompra(),
                                    producto.getCodCategoria(),
                                    producto.getCodUbicacion(),
                                    credential.getUsuarioSistema(),
                                    tipoOperacion,
                                    producto,
                                    producto.getProdCostoCompra().multiply(producto.getProdValorIncial()));
                            servicioProducto.crear(producto);

                            servicioOperacion.crear(operacion);

                        }
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("error " + ex);
        } catch (BiffException ex) {
            System.out.println("error " + ex);

        }
    }
}
