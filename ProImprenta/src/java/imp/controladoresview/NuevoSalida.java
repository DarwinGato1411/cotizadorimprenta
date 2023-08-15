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
import imp.utilitario.ConexionReportes;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class NuevoSalida {

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
    String tipoSalida = "SALIDA";
    private String nombreProducto = "";
    AMedia fileContent = null;
    Connection con = null;
    private List<Operacion> listaOperacion = new ArrayList<Operacion>();
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();

    public NuevoSalida() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("parametro") Operacion operacion, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        if (operacion != null) {
            this.operacion = operacion;

        } else {
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
    public void guardar() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

//        System.out.println("usuario " + credential.getUsuarioSistema().getUsuNombre());
        if (selected != null) {

            TipoOperacion tipoOperacion = servicioTipoOperacion.FindForNombre(tipoSalida);
            if (!operacion.getOpeNombreDespacho().equals("") 
                    && operacion.getOpeCatidad().doubleValue() > 0
                    && operacion.getOpeCatidad().doubleValue() <= stock.doubleValue()
                    && operacion.getCodCategoria()!=null
                    && operacion.getCodUbicacion()!=null) {
//                operacion.setOpeFecha(new Date());
                operacion.setValorTotal(BigDecimal.ZERO);
                operacion.setOpeCostoUltimaCompra(BigDecimal.ZERO);
                operacion.setCodProducto(selected);
                operacion.setCodTipoOperacion(tipoOperacion);
                operacion.setIdUsuario(credential.getUsuarioSistema());
                servicioOperacion.modificar(operacion);
//                Messagebox.show("Guardo con exito");
                reporteGeneral();
                calcularStock();
                this.operacion = new Operacion(BigDecimal.ZERO, "", "", "", "", "", "", BigDecimal.ZERO);
                this.operacion.setOpeFecha(new Date());

            } else {
                Messagebox.show("Verifique la cantidad y persona que retira el producto,Categoria, Ubicacion", "Atención", Messagebox.OK, Messagebox.ERROR);
            }
        } else {
            Messagebox.show("Debe seleccionar un producto ", "Atención", Messagebox.OK, Messagebox.ERROR);
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

    public String getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(String tipoSalida) {
        this.tipoSalida = tipoSalida;
    }

    @Command
    @NotifyChange({"listaProducto", "nombreProducto"})
    public void buscarProductoForNombre() {
//        nombreProducto = valor;
        consultarProductos();
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void reporteGeneral() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

        if (true) {
            con = ConexionReportes.Conexion.conexion();
            //con = conexionReportes.conexion();


            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");

            String reportPath = "";
            reportPath = reportFile + "/salida.jasper";




            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            Operacion opera = servicioOperacion.FindUltimaSalida();
            parametros.put("cod_operacion", opera.getCodOperacion());


            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);


            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Orden de Despacho", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/inventario/ContenedorReporte.zul", null, map);
            window.doModal();
            con.close();
        } else {
            Messagebox.show("Para realizar un reporte debe buscar por estado o número de tramite", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    //rango de fechas
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    //reporte de la lista
    @Command
    public void reporteLista(@BindingParam("valor") Operacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

        if (true) {
            con = ConexionReportes.Conexion.conexion();
            //con = conexionReportes.conexion();


            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");

            String reportPath = "";
            reportPath = reportFile + "/salida.jasper";




            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            //Operacion opera=servicioOperacion.FindUltimaSalida();
            parametros.put("cod_operacion", valor.getCodOperacion());


            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);


            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Orden de Despacho", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/inventario/ContenedorReporte.zul", null, map);
            window.doModal();
            con.close();
        } else {
            Messagebox.show("Para realizar un reporte debe buscar por estado o número de tramite", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"listaOperacion", "fechaInicio", "fechaFin"})
    public void buscarForFecha() {

        buscarOperacionFechas();
    }

    private void buscarOperacionFechas() {
        listaOperacion = servicioOperacion.FindForSalidaRangoFechas(fechaInicio, fechaFin);
    }

    public List<Operacion> getListaOperacion() {
        return listaOperacion;
    }

    public void setListaOperacion(List<Operacion> listaOperacion) {
        this.listaOperacion = listaOperacion;
    }
}
