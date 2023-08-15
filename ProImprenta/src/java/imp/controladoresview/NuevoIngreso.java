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
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class NuevoIngreso {
    
    private List<Producto> listaProducto = new ArrayList<Producto>();
    ServicioProducto servicioProducto = new ServicioProducto();
    private Operacion operacion = new Operacion();
    ServicioOperacion servicioOperacion = new ServicioOperacion();
    private Producto selected = null;
    UserCredential credential = new UserCredential();
    ServicioTipoOperacion servicioTipoOperacion = new ServicioTipoOperacion();
    BigDecimal stock = BigDecimal.ZERO;
    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private List<Ubicacion> listaUbicacions = new ArrayList<Ubicacion>();
    ServicioCategoria servicioCategoria = new ServicioCategoria();
    ServicioUbicacion servicioUbicacion = new ServicioUbicacion();
    private String nombreProducto = "";
    private String ingresoEgreso = "ING";
    
    public NuevoIngreso() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
    }
    
    @AfterCompose
    public void afterCompose(@ExecutionArgParam("parametro") Operacion operacion, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        
        if (operacion != null) {
            this.operacion = operacion;
            System.out.println("diferente de null");
            
        } else {
            System.out.println("Es  null");
            this.operacion = new Operacion(BigDecimal.ZERO, "", "", "", "", "", "", BigDecimal.ZERO);
            this.operacion.setOpeFecha(new Date());
            
        }
        consultarProductos();
        consultarUbicaciones();
        consultarCategorias();
    }
    
    private void consultarProductos() {
        listaProducto = servicioProducto.FindLikeNombre(nombreProducto);
    }
    
    private void consultarUbicaciones() {
        listaUbicacions = servicioUbicacion.FindAll();
    }
    
    private void consultarCategorias() {
        listaCategorias = servicioCategoria.FindAll();
    }
    
    @Command
    @NotifyChange({"operacion", "selected", "stock"})
    public void guardar() {
        if (selected != null) {
            TipoOperacion tipoOperacion = servicioTipoOperacion.FindForNombre("INGRESO");
            if (!operacion.getOpeEstadoProducto().equals("")
                    && operacion.getOpeCatidad().doubleValue() >= 0
                    && operacion.getCodCategoria() != null
                    && operacion.getCodUbicacion() != null) {
//                operacion.setOpeFecha(new Date());
                operacion.setCodProducto(selected);
                operacion.setCodTipoOperacion(tipoOperacion);
                operacion.setIdUsuario(credential.getUsuarioSistema());
                servicioOperacion.modificar(operacion);
                
                if (ingresoEgreso.equals("INGSAL")) {
                    Operacion op = new Operacion();
                    op.setOpeEstadoProducto(operacion.getOpeEstadoProducto());
                    op.setOpeCatidad(operacion.getOpeCatidad());
                    op.setCodCategoria(operacion.getCodCategoria());
                    op.setCodUbicacion(operacion.getCodUbicacion());
                    op.setCodProducto(selected);
                    op.setCodTipoOperacion(tipoOperacion);
                    op.setIdUsuario(credential.getUsuarioSistema());
                    op.setOpeFecha(operacion.getOpeFecha());
                    op.setOpeConcepo("INGRESO Y DESPACHO DIRECTO");
                    op.setOpeNombreDespacho("DESPACHO DIRECTO OFICINA");
                    op.setOpeAreaDespacho("DESPACHO DIRECTO");
                    op.setOpeFactura("S/N");
                    op.setOpeReferencia("S/N");
                    TipoOperacion tipoOperacionSal = servicioTipoOperacion.FindForNombre("SALIDA");
                    op.setCodTipoOperacion(tipoOperacionSal);
                    servicioOperacion.modificar(op);
//                    servicioOperacion.modificar(operacion);
                }
                Messagebox.show("Guardo con exito");
                calcularStock();
                this.operacion = new Operacion(BigDecimal.ZERO, "", "", "", "", "", "", BigDecimal.ZERO);
                this.operacion.setOpeFecha(new Date());
            } else {
                Messagebox.show("Verifique la informacion ingresada Categoria, Ubicacion", "Atención", Messagebox.OK, Messagebox.ERROR);
            }
        } else {
            Messagebox.show("Debe seleccionar un producto", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
        
    }
    
    private void calcularStock() {
        List<Operacion> listaOperacions = servicioOperacion.FindForProducto(selected);
        BigDecimal ingreso = BigDecimal.ZERO;
        BigDecimal egreso = BigDecimal.ZERO;
        for (Operacion item : listaOperacions) {
            if (item.getCodTipoOperacion().getTipOperacion().equalsIgnoreCase("INGRESO")) {
                ingreso = ingreso.add(item.getOpeCatidad());
            } else {
                egreso = egreso.add(item.getOpeCatidad());
            }
        }
        stock = ingreso.add(egreso.negate());
    }
    
    @Command
    @NotifyChange({"selected", "stock"})
    public void seleccionarMaterial(@BindingParam("valor") Producto valor) {
        selected = valor;
        calcularStock();
        
    }
    
    public Operacion getOperacion() {
        return operacion;
    }
    
    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }
    
    public List<Producto> getListaProducto() {
        return listaProducto;
    }
    
    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }
    
    public Producto getSelected() {
        return selected;
    }
    
    public void setSelected(Producto selected) {
        this.selected = selected;
    }
    
    public UserCredential getCredential() {
        return credential;
    }
    
    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }
    
    public BigDecimal getStock() {
        return stock;
    }
    
    public void setStock(BigDecimal stock) {
        this.stock = stock;
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
    
    @Command
    @NotifyChange({"listaProducto", "nombreProducto"})
    public void buscarProductoForNombre() {
        
        consultarProductos();
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }
    
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    
    public String getIngresoEgreso() {
        return ingresoEgreso;
    }
    
    public void setIngresoEgreso(String ingresoEgreso) {
        this.ingresoEgreso = ingresoEgreso;
    }
    
}
