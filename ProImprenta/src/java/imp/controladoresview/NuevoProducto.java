/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Categoria;
import imp.entidades.Operacion;
import imp.entidades.Producto;
import imp.entidades.TipoOperacion;
import imp.entidades.Ubicacion;
import imp.seguridad.EnumSesion;
import imp.seguridad.UserCredential;
import imp.servicios.ServicioCategoria;
import imp.servicios.ServicioOperacion;
import imp.servicios.ServicioProducto;
import imp.servicios.ServicioTipoOperacion;
import imp.servicios.ServicioUbicacion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevoProducto {

    @Wire
    Window windowMaterial;
    private Producto producto = new Producto();
    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private List<Ubicacion> listaUbicacions = new ArrayList<Ubicacion>();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioCategoria servicioCategoria = new ServicioCategoria();
    ServicioUbicacion servicioUbicacion = new ServicioUbicacion();
    ServicioOperacion servicioOperacion = new ServicioOperacion();
    ServicioTipoOperacion servicioTipoOperacion = new ServicioTipoOperacion();
    private Operacion operacion = new Operacion();
    private String accion = "update";
    UserCredential credential = new UserCredential();

    public NuevoProducto() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("parametro") Producto producto, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        if (producto != null) {
            this.producto = producto;

        } else {
            accion = "create";
//            this.producto = new Producto();
            this.producto = new Producto("", "",
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    "",
                    "",
                    BigDecimal.ZERO);
//            this.producto.setProdValorIncial(BigDecimal.ZERO);
        }
        consultarCategorias();
        consultarUbicaciones();

    }

    @Command
    @NotifyChange("producto")
    public void guardar() {

        if (!producto.getProdNombre().equals("")
                && !producto.getProdSerie().equals("")
                && producto.getProdValorIncial().doubleValue() >= 0
                && producto.getCodCategoria() != null
                && producto.getCodUbicacion() != null) {
            if (accion.equalsIgnoreCase("create")) {
                TipoOperacion tipoOperacion = servicioTipoOperacion.FindForNombre("INGRESO");
                producto.setProdFechaRegistro(new Date());
                operacion = new Operacion(producto.getProdValorIncial(),
                        new Date(),
                        "BUENO",
                        "INICIO VENTARIO",
                        "INICIO VENTARIO",
                        "INICIO VENTARIO",
                        "INICIO VENTARIO",
                        "S/N",
                        producto.getProdCostoCompra(),
                        producto.getCodCategoria(),
                        producto.getCodUbicacion(),
                        credential.getUsuarioSistema(),
                        tipoOperacion,
                        producto,
                        producto.getProdCostoCompra().multiply(producto.getProdValorIncial()));
                servicioProducto.crear(producto);

                servicioOperacion.crear(operacion);

            } else {
                producto.setProdFechaRegistro(new Date());
                servicioProducto.modificar(producto);
            }
//            Messagebox.show("Guardo con exito");
            windowMaterial.detach();
        } else {
            Messagebox.show("Verifique la informacion ingresada Numero serie, Categoria, Ubicacion", "Atención", Messagebox.OK, Messagebox.ERROR);
        }



    }

    private void consultarUbicaciones() {
        listaUbicacions = servicioUbicacion.FindAll();
    }

    private void consultarCategorias() {
        listaCategorias = servicioCategoria.FindAll();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }
}
