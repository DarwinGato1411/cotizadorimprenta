/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cliente;
import imp.entidades.DetalleOrdenSinCotizar;
import imp.entidades.Materiales;
import imp.entidades.Operacion;
import imp.entidades.OrdenSinCotizacion;
import imp.entidades.Producto;
import imp.entidades.SegOrdSinCot;
import imp.entidades.Terciarizado;
import imp.entidades.TipoTrabajo;
import imp.seguridad.EnumSesion;
import imp.seguridad.UserCredential;
import imp.servicios.HelperPersistencia;
import imp.servicios.ServicioCliente;
import imp.servicios.ServicioDetalleOrdenSinCotizar;
import imp.servicios.ServicioMateriales;
import imp.servicios.ServicioOperacion;
import imp.servicios.ServicioOrdenSinCotizar;
import imp.servicios.ServicioParametros;
import imp.servicios.ServicioProducto;
import imp.servicios.ServicioSegOrdSinCotizar;
import imp.servicios.ServicioTerceriarizado;
import imp.servicios.ServicioTipoTrabajo;
import imp.utilitario.DescripcionPedido;
import imp.utilitario.DetalleOrdenSinCotizarArmar;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class OrdenSinCotizarViewModel {

    @Wire
    Window windowClienteOrden;
    @Wire("#idTipoCotizacion")
    Radiogroup idTipoCotizacion;
    @Wire("#idDescripcionTrabajo")
    Textbox idDescripcionTrabajo;
    @Wire
    Window winOpcionesImpresion;
    AMedia fileContent = null;
    Connection con = null;
    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioOrdenSinCotizar servicioOrdenSinCotizar = new ServicioOrdenSinCotizar();
    ServicioParametros servicioParametros = new ServicioParametros();
    ServicioSegOrdSinCotizar servicioSegOrdSinCotizar = new ServicioSegOrdSinCotizar();
    List<Cliente> listaClientesAll = new ArrayList<Cliente>();

    //Producto tercerizado
    ServicioTerceriarizado servicioTerceriarizado = new ServicioTerceriarizado();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioTipoTrabajo servicioTipoTrabajo = new ServicioTipoTrabajo();

    //para los combos
    private Cliente clienteBuscado = new Cliente();
    private Date fechaEmision;
    //cabecera de la orden
    private OrdenSinCotizacion ordenSinCotizacion = new OrdenSinCotizacion();
    ServicioDetalleOrdenSinCotizar servicioDetalleOrdenSinCotizar = new ServicioDetalleOrdenSinCotizar();
    //detalle de orden
    private ListModelList<DetalleOrdenSinCotizarArmar> listaDetalleOrdenSinCotizar;
    private List<DetalleOrdenSinCotizarArmar> listaDetalleOrdenSinCotizarData = new ArrayList<DetalleOrdenSinCotizarArmar>();
    private Set<DetalleOrdenSinCotizarArmar> registrosSeleccionados;
    //buscar cliente
    public static String buscarCliente = "";
    private String buscarRazon = "";
    private String buscarNombreComercial = "";
    private BigDecimal porcentajeUtilidadGeneral = BigDecimal.ZERO;
    private String tipoGuardado = "NUEVO";
    UserCredential credential = new UserCredential();

    //<editor-fold defaultstate="collapsed" desc="Producto tercerizado tipo trabajo">
    private TipoTrabajo selectedTipoTrabajo = null;
    private Terciarizado selectedTercerizado = null;
    private Producto selectedProducto = null;
    public String buscarProducto = "";
    public String descripcionTercerizado = "";
    private List<TipoTrabajo> listaTipoTrabajo = new ArrayList<TipoTrabajo>();
    private List<Terciarizado> listaTercerizado = new ArrayList<Terciarizado>();
    private List<Producto> listaProducto = new ArrayList<Producto>();

    //materiales para usar en la orden con el costo
    ServicioMateriales servicioMateriales = new ServicioMateriales();
    List<Materiales> listaMaterialAll = new ArrayList<Materiales>();
    private String consultarMateriales = "";
    //manejar el Kardex
    ServicioOperacion servicioOperacion = new ServicioOperacion();
    private Operacion operacion = new Operacion();
    /*TERCERIZADO*/
    private ListModelList<DetalleOrdenSinCotizarArmar> listaDescripcionPedidoTercerizado;
    private List<DetalleOrdenSinCotizarArmar> lstDescripcionPedidoGeneralTercerizado = new ArrayList<DetalleOrdenSinCotizarArmar>();
    private Set<DetalleOrdenSinCotizarArmar> registrosSeleccionadosTercerizado;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("ordenSinCotizacion") OrdenSinCotizacion ordenSinCotizacion, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        cosultarClientesALl();
        productoTercerizadoTipoTrabajo();
        if (ordenSinCotizacion != null) {

            tipoGuardado = "MODIFICAR";
            this.ordenSinCotizacion = ordenSinCotizacion;
            fechaEmision = ordenSinCotizacion.getSinFechaEmision();
            try {
                ordenSinCotizacion.setFotoGeneralOriginal(ordenSinCotizacion.getFotoGeneral());
                ordenSinCotizacion.setFileContentOriginal(ordenSinCotizacion.getFileContent());
            } catch (IOException ex) {
                Logger.getLogger(OrdenSinCotizarViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }

            ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDetalleOrdenSinCotizar).clear();

            recuperarOrdenSinCotizar();
        } else {
            tipoGuardado = "NUEVO";
            this.ordenSinCotizacion = new OrdenSinCotizacion();
            this.ordenSinCotizacion.setSinTipoCotizacion("GENERAL");

            System.out.println("nueva ordenn");
        }

    }

    /*Tercerizado*/
    private void getDescripcionGeneralTercerizado() {
        setListaDescripcionPedidoTercerizado(new ListModelList<DetalleOrdenSinCotizarArmar>(getLstDescripcionPedidoGeneralTercerizado()));
        ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDescripcionPedidoTercerizado).setMultiple(true);
    }

    @Command
    public void seleccionarRegistrosTercerizado() {
        registrosSeleccionadosTercerizado = ((ListModelList<DetalleOrdenSinCotizarArmar>) getListaDescripcionPedidoTercerizado()).getSelection();
    }

    @Command
    @NotifyChange({"listaDescripcionPedidoTercerizado", "totalItem", "totalItemConUtilidad", "ganancia"})
    public void eliminarRegistrosTercerizados() {
        ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDescripcionPedidoTercerizado).removeAll(registrosSeleccionadosTercerizado);
        calcularTotalItems();

    }

    @Command
    @NotifyChange({"listaDescripcionPedidoTercerizado", "totalItem", "totalItemConUtilidad", "ganancia"})
    public void nuevaDescripcionTercerizado() {

        List<Terciarizado> tercerizadoNuevo = servicioTerceriarizado.FindALlTerciarizadoPorNombre("");
        DetalleOrdenSinCotizarArmar nuevoRegistro = new DetalleOrdenSinCotizarArmar(tercerizadoNuevo);
        nuevoRegistro.setSinTipo("T");
        nuevoRegistro.setTipoItem("TER");
        ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDescripcionPedidoTercerizado).add(nuevoRegistro);
        calcularTotalItems();

    }

    private void consultarMatriales() {
        listaMaterialAll = servicioMateriales.FindALlMaterialesLikeNombre(consultarMateriales);
    }

    private void productoTercerizadoTipoTrabajo() {
        listaProducto = servicioProducto.FindLikeNombre(buscarProducto);
        listaTercerizado = servicioTerceriarizado.FindALlTerciarizadoPorNombre("");
        listaTipoTrabajo = servicioTipoTrabajo.FindALlTipoTrabajoLike("");

    }

    private void recuperarOrdenSinCotizar() {
        numeroOrden = ordenSinCotizacion.getSinNumero();
        clienteBuscado = ordenSinCotizacion.getCodCliente();

        DetalleOrdenSinCotizarArmar armar = new DetalleOrdenSinCotizarArmar();
        for (DetalleOrdenSinCotizar item : servicioDetalleOrdenSinCotizar.listaDetalleOrdenFindOrden(ordenSinCotizacion)) {
            armar = new DetalleOrdenSinCotizarArmar(item.getDetsinCatidadCantida(), item.getDetsinSubtotal(), item.getDetsinDescripcion(), item.getDetsinValor());
            armar.setTipoItem(item.getTipoItem());
            armar.setSinTipo(item.getSinTipo());
            calcularValoresItem(armar);
            if (item.getSinTipo().equals("M")) {
                ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDetalleOrdenSinCotizar).add(armar);
            } else {
                armar.setListaTercerizados(listaTercerizado);
                armar.setTercerizado(item.getCodTercearizado());
                ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDescripcionPedidoTercerizado).add(armar);
            }

        }
        calcularTotalItems();
    }

    public OrdenSinCotizarViewModel() {

        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        //CONSTRUCTOR PARA LOS DATOS
        getDetalleOrdenSinCotizar();
        consultarMatriales();
        porcentajeUtilidadGeneral = servicioParametros.porcentajeUtilidad().getPorGanancia();
        getDescripcionGeneralTercerizado();
    }

    private void getDetalleOrdenSinCotizar() {
        setListaDetalleOrdenSinCotizar(new ListModelList<DetalleOrdenSinCotizarArmar>(getListaDetalleOrdenSinCotizarData()));
        ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDetalleOrdenSinCotizar).setMultiple(true);
    }

    @Command
    public void seleccionarRegistrosDetalleOrdenSinCotizar() {
        registrosSeleccionados = ((ListModelList<DetalleOrdenSinCotizarArmar>) getListaDetalleOrdenSinCotizar()).getSelection();
    }

    /*<editor-fold defaultstate="collapsed" desc="Nuevo detalle de la orden">*/
    @Command
    @NotifyChange({"listaProducto", "buscarProducto"})
    public void buscarmaterial() {
        listaProducto = servicioProducto.FindLikeNombre(buscarProducto);
    }

    @Command
    @NotifyChange({"listaMaterialAll", "consultarMateriales"})
    public void buscarMaterialesImp() {
        consultarMatriales();
    }

//    @Command
//    @NotifyChange({"listaDetalleOrdenSinCotizar", "buscarCabidaLike", "valorTotalMaterialGeneral"})
//    public void nuevoDetalleOrdenTercerizado() {
//        DetalleOrdenSinCotizarArmar detalle = new DetalleOrdenSinCotizarArmar();
//        if (selectedTercerizado != null) {
//
//            if (selectedTercerizado != null) {
//                detalle.setTercerizado(selectedTercerizado);
//                detalle.setDetsinCatidadCantida(BigDecimal.ONE);
//                detalle.setDetsinSubtotal(BigDecimal.ZERO);
//                detalle.setDetsinValor(BigDecimal.ZERO);
//                detalle.setDetsinDescripcion(selectedTercerizado.getTerProveedor() + " : " + descripcionTercerizado);
//                detalle.setTipoItem("TER");
//                detalle.setTipoTrabajo(selectedTipoTrabajo);
////                detalle.setTercerizado(selectedTercerizado);
//                selectedProducto = null;
//                selectedTercerizado = null;
//                descripcionTercerizado = "";
//
//                calcularValoresItem(detalle);
//            }
//            ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDetalleOrdenSinCotizar).add(detalle);
//        } else {
//            Clients.showNotification("Verifique tercerizado y tipo de trabajo", "Error", null, "end_center", 4000, true);
//        }
//
//    }
    @Command
    @NotifyChange({"listaDetalleOrdenSinCotizar", "buscarCabidaLike", "valorTotalMaterialGeneral"})
    public void nuevoDetalleOrdenMaterial(@BindingParam("valor") Materiales valor) {
        DetalleOrdenSinCotizarArmar detalle = new DetalleOrdenSinCotizarArmar();
        if (valor != null) {
//            detalle.setTercerizado(selectedTercerizado);
            detalle.setDetsinCatidadCantida(BigDecimal.ONE);
            detalle.setDetsinSubtotal(BigDecimal.valueOf(valor.getMatCosto()));
            detalle.setDetsinValor(BigDecimal.ZERO);
            detalle.setDetsinDescripcion(valor.getMatNombre());
            detalle.setTipoItem("MAT");
            detalle.setTipoTrabajo(selectedTipoTrabajo);
            selectedProducto = null;
            selectedTercerizado = null;

            calcularValoresItem(detalle);

            ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDetalleOrdenSinCotizar).add(detalle);
        } else {
            Clients.showNotification("Verifique el material y tipo de trabajo", "Error", null, "end_center", 4000, true);
        }

    }

    @Command
    @NotifyChange({"listaDetalleOrdenSinCotizar", "buscarCabidaLike", "valorTotalMaterialGeneral"})
    public void nuevoDetalleOrdenVacio() {
        DetalleOrdenSinCotizarArmar detalle = new DetalleOrdenSinCotizarArmar();

//            detalle.setTercerizado(selectedTercerizado);
        detalle.setDetsinCatidadCantida(BigDecimal.ONE);
        detalle.setDetsinSubtotal(BigDecimal.ZERO);
        detalle.setDetsinValor(BigDecimal.ZERO);
        detalle.setDetsinDescripcion("");
        detalle.setTipoItem("MAT");
        detalle.setSinTipo("M");
        detalle.setTipoTrabajo(selectedTipoTrabajo);
        selectedProducto = null;
        selectedTercerizado = null;

        calcularValoresItem(detalle);

        ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDetalleOrdenSinCotizar).add(detalle);

    }

    @Command
    @NotifyChange({"listaDetalleOrdenSinCotizar", "totalItem", "totalItemConUtilidad", "ganancia", "listaDescripcionPedidoTercerizado"})
    public void eliminarRegistros() {
        ((ListModelList<DetalleOrdenSinCotizarArmar>) listaDetalleOrdenSinCotizar).removeAll(registrosSeleccionados);
        calcularTotalItems();
    }

    @Command
    @NotifyChange({"listaClientesAll", "clienteBuscado", "fechaEmision", "ordenSinCotizacion"})
    public void buscarClienteEnLista() {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("numcotizacion", "");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/buscarClienteOrden.zul", null, null);
        window.doModal();
        buscarClientePorCedula();
    }
    private static Integer numeroOrden = 0;

    private void numeroOrden() {
        OrdenSinCotizacion orden = servicioOrdenSinCotizar.ultimaOrden();
        if (orden != null) {
            numeroOrden = orden.getSinNumero() + 1;
            System.out.println("NUEVO Numero de cotizacion " + numeroOrden);
        } else {
            numeroOrden = 1;
        }
    }

    @Command
    @NotifyChange({"clienteBuscado", "buscarCliente", "fechaEmision", "ordenSinCotizacion"})
    public void buscarClientePorCedula() {
//        if (validateCedula(buscarCliente.trim()))
        if (validateCedula(buscarCliente.trim())) {
//            if (Utilitario.validar.validadorDeCedula(buscarCliente))
            if (true) {
//                if (numeroOrden == 0) {

//                }
                clienteBuscado = servicioCliente.FindClientePorCedula(buscarCliente);
                fechaEmision = new Date();
                Date fechaActual = new Date();
                //agrega el cliente buscado
                ordenSinCotizacion.setCodCliente(clienteBuscado);
                ordenSinCotizacion.setSinFechaInicio(fechaActual);
                ordenSinCotizacion.setSinFechaCierre(fechaActual);
                ordenSinCotizacion.setSinHoraInicio(fechaActual);
                ordenSinCotizacion.setSinHoraCierre(fechaActual);
                ordenSinCotizacion.setSinFechaEmision(fechaActual);
                ordenSinCotizacion.setSinEstado("GENERADA");

            } else {
                Messagebox.show("La cédula ingresada no es una cédula valida", "Atención", Messagebox.OK, Messagebox.ERROR);
            }
        } else {
            Messagebox.show("Verifique la cedula ingresada solo puede contener números", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    private boolean validateCedula(String cedula) {
        boolean validar = true;
        System.out.println("Valor del clinete cedula" + cedula);
        if (cedula == null) {
            System.out.println("entro nulo");
            validar = false;
        } else if (!cedula.matches("\\d*")) {
            System.out.println("Entro al match");
            validar = false;
        } else {
            validar = true;
        }
        System.out.println("Valor del clinete cedula" + validar);
        return validar;
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarRazon"})
    public void buscarClienteRazon(@BindingParam("valor") String valor) {
        buscarRazon = valor;
        consultarRazonLike(valor);
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarNombreComercial"})
    public void buscarNombreComercial(@BindingParam("valor") String valor) {
        buscarNombreComercial = valor;
        consultarNombreComercialLike(valor);
    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionarClienteLista(@BindingParam("cliente") Cliente valor) {
        System.out.println("cliente seleccionado " + valor.getRuc());
        buscarCliente = valor.getRuc();
        windowClienteOrden.detach();

    }
    //registra la orden

    @Command
    @NotifyChange({"listaDetalleOrdenSinCotizar", "buscarCabidaLike", "valorTotalMaterialGeneral"})
    public void RegistrarOrden() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        List<DetalleOrdenSinCotizarArmar> listaDetalle = listaDetalleOrdenSinCotizar.getInnerList();
        List<DetalleOrdenSinCotizarArmar> listaDetalleTer = listaDescripcionPedidoTercerizado.getInnerList();

        if (credential.getUsuarioSistema().getUsuNivelAcceso() != 1) {
            for (DetalleOrdenSinCotizarArmar itemDet : listaDetalle) {
                if (itemDet.getDetsinSubtotal().doubleValue() == 0) {

                    Clients.showNotification("Ingrese el precio de cada item ",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 2000, true);
                    return;
                }
            }
        }
        if (ordenSinCotizacion != null && listaDetalle.size() > 0) {
            if (ordenSinCotizacion.getSinFechaCierre() != null && ordenSinCotizacion.getSinHoraCierre() != null
                    && ordenSinCotizacion.getSinTipoCotizacion() != null) {
//                numeroOrden();
                ordenSinCotizacion.setSinCosto(totalItem);
                ordenSinCotizacion.setSinUtilidad(porcentajeUtilidadGeneral);
                ordenSinCotizacion.setSinTotalUtilidad(totalItemConUtilidad);
                if (tipoGuardado.equals("NUEVO")) {
                    numeroOrden();
                    ordenSinCotizacion.setSinNumero(numeroOrden);
                    ordenSinCotizacion.setSinQuienCrea(credential.getUsuarioSistema().getUsuNombreUsuario());
                    servicioOrdenSinCotizar.guardarOrden(ordenSinCotizacion, listaDetalle, listaDetalleTer);

                } else {
                    System.out.println("EDITA LA ORDEN");

                    /*GUARDA LA COTIZACION*/
                    ordenSinCotizacion.setSinQuienModifica(credential.getUsuarioSistema().getUsuNombreUsuario());
                    ordenSinCotizacion.setSinFechaModifica(new Date());
                    servicioOrdenSinCotizar.eliminar(ordenSinCotizacion);
                    servicioOrdenSinCotizar.guardarOrden(ordenSinCotizacion, listaDetalle, listaDetalleTer);

                    SegOrdSinCot segOrdSinCot = new SegOrdSinCot();
//                    segOrdSinCot.setOrdCodOrdenSinCotizacion(ordenSinCotizacion);
                    segOrdSinCot.setSegSinNumero(ordenSinCotizacion.getSinNumero());
                    segOrdSinCot.setSegFechaModifica(new Date());
                    segOrdSinCot.setSegObservacion(ordenSinCotizacion.toString());
                    segOrdSinCot.setSegResponsable(credential.getUsuarioSistema().getUsuNombreUsuario());
                    segOrdSinCot.setSegSinDescripcion(ordenSinCotizacion.toString());
                    /*GUARDA EL SEGUIMIENTO*/
                    servicioSegOrdSinCotizar.crear(segOrdSinCot);

                }

                reporteGeneral();
                if (tipoGuardado.equals("NUEVO")) {
                    Executions.sendRedirect("/cotizacion/ordenSinCotizar.zul");
                } else {
                    Executions.sendRedirect("/cotizacion/listaOrdenSinCotizar.zul");
                }
            } else {
                Messagebox.show("Verifique las fechas el tipo de orden", "Atención", Messagebox.OK, Messagebox.ERROR);
            }

        } else {
            Messagebox.show("Verifique la informacion de la orden", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void reporteGeneral() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();
        System.out.println("Entra al reporte ");
        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);
            //con = ConexionReportes.Conexion.conexion();

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";

            reportPath = reportFile + "/ordensincot.jasper";

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numorden", numeroOrden);

            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);

            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte Cotizacion", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/ContenedorReporte.zul", null, map);
            window.doModal();
            // con.close();

            final HashMap<String, String> map1 = new HashMap<String, String>();
            map1.put("mensaje", numeroOrden.toString());
            org.zkoss.zul.Window window1 = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/mensajeGenCot.zul", null, map1);
            window1.doModal();
            con.close();
//            emf.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

    private void consultarRazonLike(String valor) {
        listaClientesAll = servicioCliente.FindALlRazonSocialLike(valor);
    }

    private void cosultarClientesALl() {
        listaClientesAll = servicioCliente.FindALlCliente();
    }

    private void consultarNombreComercialLike(String valor) {
        listaClientesAll = servicioCliente.FindALlNombreComercialLike(valor);
    }

    public static String getBuscarCliente() {
        return buscarCliente;
    }

    public static void setBuscarCliente(String buscarCliente) {
        OrdenSinCotizarViewModel.buscarCliente = buscarCliente;
    }

    public List<Cliente> getListaClientesAll() {
        return listaClientesAll;
    }

    public void setListaClientesAll(List<Cliente> listaClientesAll) {
        this.listaClientesAll = listaClientesAll;
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

    public Cliente getClienteBuscado() {
        return clienteBuscado;
    }

    public void setClienteBuscado(Cliente clienteBuscado) {
        this.clienteBuscado = clienteBuscado;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public ListModelList<DetalleOrdenSinCotizarArmar> getListaDetalleOrdenSinCotizar() {
        return listaDetalleOrdenSinCotizar;
    }

    public void setListaDetalleOrdenSinCotizar(ListModelList<DetalleOrdenSinCotizarArmar> listaDetalleOrdenSinCotizar) {
        this.listaDetalleOrdenSinCotizar = listaDetalleOrdenSinCotizar;
    }

    public List<DetalleOrdenSinCotizarArmar> getListaDetalleOrdenSinCotizarData() {
        return listaDetalleOrdenSinCotizarData;
    }

    public void setListaDetalleOrdenSinCotizarData(List<DetalleOrdenSinCotizarArmar> listaDetalleOrdenSinCotizarData) {
        this.listaDetalleOrdenSinCotizarData = listaDetalleOrdenSinCotizarData;
    }

    public Set<DetalleOrdenSinCotizarArmar> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<DetalleOrdenSinCotizarArmar> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    public OrdenSinCotizacion getOrdenSinCotizacion() {
        return ordenSinCotizacion;
    }

    public void setOrdenSinCotizacion(OrdenSinCotizacion ordenSinCotizacion) {
        this.ordenSinCotizacion = ordenSinCotizacion;
    }

    public void calcularValoresItem(DetalleOrdenSinCotizarArmar valor) {
        BigDecimal totalRegistro = valor.getDetsinCatidadCantida().multiply(valor.getDetsinSubtotal());
        totalRegistro.setScale(4, RoundingMode.FLOOR);
        valor.setDetsinValor(totalRegistro);
        calcularTotalItems();
    }

    @Command
    @NotifyChange({"listaDetalleOrdenSinCotizar", "totalItem", "totalItemConUtilidad", "ganancia", "listaDescripcionPedidoTercerizado"})
    public void calcularValores(@BindingParam("valor") DetalleOrdenSinCotizarArmar valor) throws IOException {
        BigDecimal totalRegistro = valor.getDetsinCatidadCantida().multiply(valor.getDetsinSubtotal());
        totalRegistro.setScale(4, RoundingMode.FLOOR);
        valor.setDetsinValor(totalRegistro);
//        valor.setTercerizado(servicioTerceriarizado.findCodTerciarizado(valor.getTercerizado().getCodTercearizado()));
        calcularTotalItems();

    }

    @Command
    @NotifyChange({"listaDescripcionPedidoTercerizado"})
    public void selectedItemTercer(@BindingParam("valor") Terciarizado valor) throws IOException {
        System.out.println("valore tercer "+valor.getTerNombreProducto());

    }
    private BigDecimal totalItem = BigDecimal.ZERO;
    private BigDecimal totalItemConUtilidad = BigDecimal.ZERO;
    private BigDecimal ganancia = BigDecimal.ZERO;

    private void calcularTotalItems() {
        totalItem = BigDecimal.ZERO;
        List<DetalleOrdenSinCotizarArmar> listaDetalle = listaDetalleOrdenSinCotizar.getInnerList();
        for (DetalleOrdenSinCotizarArmar detalle : listaDetalle) {
            totalItem = totalItem.add(detalle.getDetsinValor());
        }
        List<DetalleOrdenSinCotizarArmar> listaDetalleTerce = listaDescripcionPedidoTercerizado.getInnerList();
        for (DetalleOrdenSinCotizarArmar detalle : listaDetalleTerce) {
            totalItem = totalItem.add(detalle.getDetsinValor());
        }
        BigDecimal auxiliar = totalItem.multiply(porcentajeUtilidadGeneral);
        ganancia = (auxiliar.divide(BigDecimal.valueOf(100)));
        totalItemConUtilidad = ganancia.add(totalItem);

//        totalItemConUtilidad = totalItem.multiply(porcentajeUtilidadGeneral.divide(BigDecimal.valueOf(100)));
        totalItemConUtilidad.setScale(4, BigDecimal.ROUND_FLOOR);
//        totalItemConUtilidad = totalItem.multiply(porcentajeUtilidadGeneral);
    }

    public BigDecimal getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(BigDecimal totalItem) {
        this.totalItem = totalItem;
    }

    public static Integer getNumeroOrden() {
        return numeroOrden;
    }

    public static void setNumeroOrden(Integer numeroOrden) {
        OrdenSinCotizarViewModel.numeroOrden = numeroOrden;
    }

    public BigDecimal getPorcentajeUtilidadGeneral() {
        return porcentajeUtilidadGeneral;
    }

    public void setPorcentajeUtilidadGeneral(BigDecimal porcentajeUtilidadGeneral) {
        this.porcentajeUtilidadGeneral = porcentajeUtilidadGeneral;
    }

    public BigDecimal getTotalItemConUtilidad() {
        return totalItemConUtilidad;
    }

    public void setTotalItemConUtilidad(BigDecimal totalItemConUtilidad) {
        this.totalItemConUtilidad = totalItemConUtilidad;
    }

    public BigDecimal getGanancia() {
        return ganancia;
    }

    public void setGanancia(BigDecimal ganancia) {
        this.ganancia = ganancia;
    }

    @Command
    @NotifyChange({"totalItemConUtilidad", "ganancia"})
    public void recularUtilidad() {
        BigDecimal auxiliar = totalItem.multiply(porcentajeUtilidadGeneral);
        ganancia = (auxiliar.divide(BigDecimal.valueOf(100)));
        totalItemConUtilidad = ganancia.add(totalItem);
//        totalItemConUtilidad = totalItem.multiply(porcentajeUtilidadGeneral.divide(BigDecimal.valueOf(100)));
        totalItemConUtilidad.setScale(4, BigDecimal.ROUND_FLOOR);
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

    public TipoTrabajo getSelectedTipoTrabajo() {
        return selectedTipoTrabajo;
    }

    public void setSelectedTipoTrabajo(TipoTrabajo selectedTipoTrabajo) {
        this.selectedTipoTrabajo = selectedTipoTrabajo;
    }

    public Terciarizado getSelectedTercerizado() {
        return selectedTercerizado;
    }

    public void setSelectedTercerizado(Terciarizado selectedTercerizado) {
        this.selectedTercerizado = selectedTercerizado;
    }

    public Producto getSelectedProducto() {
        return selectedProducto;
    }

    public void setSelectedProducto(Producto selectedProducto) {
        this.selectedProducto = selectedProducto;
    }

    public List<TipoTrabajo> getListaTipoTrabajo() {
        return listaTipoTrabajo;
    }

    public void setListaTipoTrabajo(List<TipoTrabajo> listaTipoTrabajo) {
        this.listaTipoTrabajo = listaTipoTrabajo;
    }

    public List<Terciarizado> getListaTercerizado() {
        return listaTercerizado;
    }

    public void setListaTercerizado(List<Terciarizado> listaTercerizado) {
        this.listaTercerizado = listaTercerizado;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getBuscarProducto() {
        return buscarProducto;
    }

    public void setBuscarProducto(String buscarProducto) {
        this.buscarProducto = buscarProducto;
    }

    public String getDescripcionTercerizado() {
        return descripcionTercerizado;
    }

    public void setDescripcionTercerizado(String descripcionTercerizado) {
        this.descripcionTercerizado = descripcionTercerizado;
    }

    public List<Materiales> getListaMaterialAll() {
        return listaMaterialAll;
    }

    public void setListaMaterialAll(List<Materiales> listaMaterialAll) {
        this.listaMaterialAll = listaMaterialAll;
    }

    public String getConsultarMateriales() {
        return consultarMateriales;
    }

    public void setConsultarMateriales(String consultarMateriales) {
        this.consultarMateriales = consultarMateriales;
    }

    /*SUBIR ARCHIVOD */
    @Command
    @NotifyChange("ordenSinCotizacion")
    public void subirFotografia() throws InterruptedException, IOException {
        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 10 * 1024 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 10MB .\n Por favor seleccione un archivo más pequeño.");
                return;
            }
            this.ordenSinCotizacion.setSinImagen(media.getByteData());
//            foto1 = new AImage("foto", this.cortezPosibles.getCortImagen());
//            consultarCortez("");
//            Messagebox.show("La fotografía fue grabada exitosamente.");
        } else {
            Messagebox.show("El arhivo seleccionado no es una imagen.\n Selecione un archivo con extensión .jpg, png o gif.");
        }

    }
    //subir pdf
    private String filePath;
    byte[] buffer = new byte[1024 * 1024];

    @Command
    @NotifyChange({"fileContent", "ordenSinCotizacion"})
    public void subirArchivo() throws InterruptedException, IOException {

        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.util.media.AMedia) {
            String nombre = media.getName().toString();
            if (media != null && nombre.contains("pdf")) {
                if (media.getByteData().length > 10 * 1024 * 1024) {
                    Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 10Mb.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                    return;
                }

                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);// Note: zero based!
                int day = now.get(Calendar.DAY_OF_MONTH);
                filePath = Executions.getCurrent().getDesktop().getWebApp()
                        .getRealPath("/");
//                filePath = "F:";
                String yearPath = "PDFs" + "" + year + File.separator + month + File.separator
                        + day + File.separator;
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
                File f = new File(filePath);
                // Messagebox.show(" dfdfdfdsfdsf" + filePath);

                FileInputStream fs = new FileInputStream(f);
                fs.read(buffer);
                fs.close();

                ByteArrayInputStream is = new ByteArrayInputStream(buffer);
                fileContent = new AMedia("report", "pdf", "application/pdf", is);

                byte[] bFile = new byte[(int) f.length()];
                FileInputStream fileInputStream = null;
                try {
                    //convert file into array of bytes
                    fileInputStream = new FileInputStream(f);
                    fileInputStream.read(bFile);
                    fileInputStream.close();

                    ordenSinCotizacion.setSinPdf(bFile);

                    f.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Messagebox.show("El arhivo seleccionado no es un archivo valido.\n Seleccione un archivo pdf", "Atención", Messagebox.OK, Messagebox.ERROR);
            }
        }

    }

    @Command
    public void modificarOdenSinCotizar() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
//            
            final HashMap<String, OrdenSinCotizacion> map = new HashMap<String, OrdenSinCotizacion>();
            ordenSinCotizacion.setTipoOperacion(tipoGuardado);
            map.put("valor", ordenSinCotizacion);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/subirarchivoordensincotizar.zul", null, map);
            window.doModal();

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public ListModelList<DetalleOrdenSinCotizarArmar> getListaDescripcionPedidoTercerizado() {
        return listaDescripcionPedidoTercerizado;
    }

    public void setListaDescripcionPedidoTercerizado(ListModelList<DetalleOrdenSinCotizarArmar> listaDescripcionPedidoTercerizado) {
        this.listaDescripcionPedidoTercerizado = listaDescripcionPedidoTercerizado;
    }

    public List<DetalleOrdenSinCotizarArmar> getLstDescripcionPedidoGeneralTercerizado() {
        return lstDescripcionPedidoGeneralTercerizado;
    }

    public void setLstDescripcionPedidoGeneralTercerizado(List<DetalleOrdenSinCotizarArmar> lstDescripcionPedidoGeneralTercerizado) {
        this.lstDescripcionPedidoGeneralTercerizado = lstDescripcionPedidoGeneralTercerizado;
    }

}
