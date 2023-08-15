/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cabida;
import imp.entidades.Cliente;
import imp.entidades.CortezPosibles;
import imp.entidades.Cotizacion;
import imp.entidades.DetalleCotizacion;
import imp.entidades.ManoDeObra;
import imp.entidades.Materiales;
import imp.entidades.OrdenDeProduccion;
import imp.entidades.RecorteMaterial;
import imp.entidades.RecorteMaterialPK;
import imp.entidades.Terciarizado;
import imp.entidades.TipoTrabajo;
import  imp.servicios.HelperPersistencia;
import  imp.servicios.ServicioCabida;
import  imp.servicios.ServicioCliente;
import  imp.servicios.ServicioCorteMaterial;
import  imp.servicios.ServicioCortez;
import  imp.servicios.ServicioCotizacion;
import  imp.servicios.ServicioDetalleCotizacion;
import  imp.servicios.ServicioManoObra;
import  imp.servicios.ServicioMateriales;
import  imp.servicios.ServicioOrden;
import  imp.servicios.ServicioParametros;
import  imp.servicios.ServicioTerceriarizado;
import  imp.servicios.ServicioTipoTrabajo;
import imp.utilitario.ConexionReportes;
import imp.utilitario.DescripcionPedido;
import imp.utilitario.DetalleMateria;
import imp.utilitario.Utilitario;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class CotizacionDigitalViewModel {

    @Wire
    Window windowClienteBuscarDigital;
    @Wire("#idTipoCotizacion")
    Radiogroup idTipoCotizacion;
    @Wire("#idDescripcionTrabajo")
    Textbox idDescripcionTrabajo;
    @Wire
    Window winOpcionesImpresion;
    AMedia fileContent = null;
    Connection con = null;
    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioMateriales servicioMateriales = new ServicioMateriales();
    ServicioTerceriarizado servicioTerceriarizado = new ServicioTerceriarizado();
    ServicioManoObra servicioManoObra = new ServicioManoObra();
    ServicioCabida servicioCabida = new ServicioCabida();
    ServicioCortez servicioCortez = new ServicioCortez();
    ServicioCotizacion servicioCotizacion = new ServicioCotizacion();
    ServicioDetalleCotizacion servicioDetalleCotizacion = new ServicioDetalleCotizacion();
    ServicioParametros servicioParametros = new ServicioParametros();
    ServicioOrden servicioOrden = new ServicioOrden();
    List<Cliente> listaClientesAll = new ArrayList<Cliente>();
    List<Materiales> listaMaterialAll = new ArrayList<Materiales>();
    List<Materiales> listaMaterialOtrosGeneral = new ArrayList<Materiales>();
    private Date fechaEmision;
    private List<Terciarizado> listaTercearizadoNombre = new ArrayList<Terciarizado>();
    private List<ManoDeObra> listaManoObraNombre = new ArrayList<ManoDeObra>();
    //variables
    private String buscarMaterial = "";
    private String buscarTercearizado = "";
    private String buscarManoObra = "";
    private String materialSeleccionado = "";
    private String corteSeleccionado = "";
    private Cotizacion cotizacion = new Cotizacion();
    private List<CortezPosibles> listaCortesPorMaterial = new ArrayList<CortezPosibles>();
    private CortezPosibles corteSeleccionadoComboGeneral = new CortezPosibles();
    private AImage fotoGeneral;
    //para los combos
    private Cliente clienteBuscado = new Cliente();
    private Materiales materialSeleccionadoCombo = new Materiales();
    private Materiales materialOtrosCombo = new Materiales();
    private ManoDeObra manoDeObraCombo = new ManoDeObra();
    //variables de busqueda
    private String buscarTipoTrabajo = "";
    //Cotizacion
    private Cotizacion nuevaCotizacion = new Cotizacion();
    private List<Cotizacion> listaNuevaCotizacion = new ArrayList<Cotizacion>();
    //tipo de trabajo
    ServicioTipoTrabajo servicioTipoTrabajo = new ServicioTipoTrabajo();
    List<TipoTrabajo> listaTipoTrabajo = new ArrayList<TipoTrabajo>();
    TipoTrabajo tipoTrabajo = new TipoTrabajo();
    private String descripcionTrabajo = "";
    //hace referencia a si es compaginado aplica portada y hojas
    private String detalleTrabajo = "";
    private String seleccionTrabajoGeneral = "";
    ServicioCorteMaterial servicioCorteMaterial = new ServicioCorteMaterial();
    //descripcion del material
    private ListModelList<DescripcionPedido> listaDescripcionPedido;
    private List<DescripcionPedido> lstDEscripcionPedidoGeneral = new ArrayList<DescripcionPedido>();
    private DescripcionPedido descripcionPedido = new DescripcionPedido();
    private Set<DescripcionPedido> registrosSeleccionados;
    private String matTipoCobro = "";
    private String tipoCobroManoObra = "";
    private String tipoCotizacion = "CIDIG";
    private List<Cabida> listaCabidasBuscadasGeneral = new ArrayList<Cabida>();
    private BigDecimal valorTotalMaterialGeneral = BigDecimal.ZERO;
    private BigDecimal horasManoObraGeneran = BigDecimal.ZERO;
    private boolean mostrarHorasMOGeneral = Boolean.TRUE;
    //materiales adicionales
    private ListModelList<DescripcionPedido> listaDescripcionPedidoVarios;
    private List<DescripcionPedido> lstDEscripcionPedidoGeneralVarios = new ArrayList<DescripcionPedido>();
    private BigDecimal valorTotalMaterialGeneralVarios = BigDecimal.ZERO;
    private Set<DescripcionPedido> registrosSeleccionadosVarios;
    //materiales pre post
    private ListModelList<DescripcionPedido> listaDescripcionPedidoPrePost;
    private List<DescripcionPedido> lstDescripcionPedidoGeneralPrePost = new ArrayList<DescripcionPedido>();
    private BigDecimal valorTotalManoObraGeneralPrePost = BigDecimal.ZERO;
    private Set<DescripcionPedido> registrosSeleccionadosPrePost;
    private List<ManoDeObra> listaManoObraPrePost = new ArrayList<ManoDeObra>();
    private ManoDeObra manoDeObraComboPrePost = new ManoDeObra();
    private String tipoCobroManoObraPrePost = "H";
    private BigDecimal horasManoObraGeneranPrePost = BigDecimal.ZERO;
    //tercerizado
    private ListModelList<DescripcionPedido> listaDescripcionPedidoTercerizado;
    private List<DescripcionPedido> lstDescripcionPedidoGeneralTercerizado = new ArrayList<DescripcionPedido>();
    private Set<DescripcionPedido> registrosSeleccionadosTercerizado;
    private BigDecimal valorTotalMaterialGeneralTercerizado = BigDecimal.ZERO;
    private BigDecimal valorSubtotalTotalCotziacionGeneral = BigDecimal.ZERO;
    private BigDecimal porcentajeUtilidadGeneral = BigDecimal.ZERO;
    private BigDecimal valorGananciaGeneral = BigDecimal.ZERO;
    private BigDecimal valorTotalCotizaciónGeneralConUtilidad = BigDecimal.ZERO;
    //subtotales
    private BigDecimal subtotalManoObraGenral = BigDecimal.ZERO;
    private BigDecimal subtotalManoObraGenralPrePost = BigDecimal.ZERO;
    private BigDecimal subtotalMateriales = BigDecimal.ZERO;
    private BigDecimal subtotalTercerizados = BigDecimal.ZERO;
    private BigDecimal numeroPlacas = BigDecimal.ZERO;
    private BigDecimal subtotalPlacas = BigDecimal.ZERO;
    private BigDecimal subtotalMateriaPrima = BigDecimal.ZERO;
    private String numeroCotizacionGeneral = "";
    private BigDecimal totalManoObra = BigDecimal.ZERO;
    private BigDecimal totalCostos = BigDecimal.ZERO;
    private BigDecimal porcentajeContribucion = BigDecimal.ZERO;
    private BigDecimal porcentajeCosto = BigDecimal.ZERO;
    private List<DetalleCotizacion> detalleCotizacion = new ArrayList<DetalleCotizacion>();
    private Cotizacion encabezado = null;
    //orden de produccion
    private OrdenDeProduccion ordenDeProduccion = new OrdenDeProduccion();
    private String tipoGuardado = "NUEVO";
    private Cotizacion cotizacionRecuperada = new Cotizacion();
    //DETALLE DE LA MATERIA PRIMA
    private BigDecimal detcostoPliegos = BigDecimal.ZERO;
    private BigDecimal detcostoPacas = BigDecimal.ZERO;
    private BigDecimal valorUnitario = BigDecimal.ZERO;
    BigDecimal cantidadProSolicitado = BigDecimal.ZERO;
    private BigDecimal porcentajeContrib = BigDecimal.ZERO;
    //buscar cliente
    public static String buscarCliente = "";
    private String busTipoTrabajo = "";
    private String buscarRazon = "";
    private String buscarNombreComercial = "";
    public static int contador = 0;
    private String cotObservacion = "";

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("numcotizacion") String numcotizacion, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        cosultarClientesALl();

        contador++;
        System.out.println("valor num  " + contador);
        if (numcotizacion != null) {
            System.out.println(numcotizacion);
            numeroCotizacionGeneral = numcotizacion;
            ((ListModelList<DescripcionPedido>) listaDescripcionPedido).clear();
            ((ListModelList<DescripcionPedido>) listaDescripcionPedidoPrePost).clear();
            ((ListModelList<DescripcionPedido>) listaDescripcionPedidoVarios).clear();
            ((ListModelList<DescripcionPedido>) listaDescripcionPedidoTercerizado).clear();

            System.out.println("entra una vesssss");
            recuperarCotizacion();
            tipoGuardado = "MODIFICAR";


        } else {
            ((ListModelList<DescripcionPedido>) listaDescripcionPedido).clear();
            ((ListModelList<DescripcionPedido>) listaDescripcionPedidoPrePost).clear();
            System.out.println("es nulo");
            tipoGuardado = "NUEVO";


        }

        if (contador <= 1) {
            tipoCotizacion();
        }


    }

    public CotizacionDigitalViewModel() {
        //  consultarClienteLike(buscarCliente);
        consultarClientePorCedula(buscarCliente);
        consultarMatriales(buscarMaterial);
        consultarTercearizadoNombre(buscarTercearizado);
        consultarManoObra(buscarManoObra);
        consultarTipoTrabaLike("");
        consultarMatrialesOtrosGeneral("");
        consultarManoObraPrePost("");
        // lstDEscripcionPedidoGeneral.add(new DescripcionPedido(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0, 0, "",new Materiales(),new CortezPosibles()));
        getDescripcionGeneral();
        getDescripcionGeneralVarios();
        getDescripcionGeneralPrePost();
        getDescripcionGeneralTercerizado();
        porcentajeUtilidad();



        //  Clients.showNotification("La información necesaria es el largo, ancho, y cantidad", "error", null, "middle_center", 9000, true);
    }

    private void numeroCotizacion() {
        Cotizacion cot = servicioCotizacion.ultimaCotizacion();
        if (cot != null) {
            numeroCotizacionGeneral = Utilitario.NumeroFactura(cot.getCotNumero());
            System.out.println("NUEVO Numero de cotizacion " + numeroCotizacionGeneral);
        } else {
            numeroCotizacionGeneral = Utilitario.NumeroFactura("0");
        }
    }

//    @Command
//    @NotifyChange({"listaDescripcionPedidoVarios", "descriGenral", "buscarCliente", "clienteBuscado",
//        "totalManoObra", "totalCostos","listaDescripcionPedido","listaDescripcionPedidoTercerizado",
//        "porcentajeContribucion", "porcentajeCosto",
//        "valorTotalMaterialGeneralVarios", "valorSubtotalTotalCotziacionGeneral",
//        "valorTotalCotizaciónGeneralConUtilidad", "valorGananciaGeneral", "listaDescripcionPedidoPrePost",
//        "totalManoObra", "totalCostos",
//        "valorTotalManoObraGeneralPrePost", "porcentajeContrib","listaDescripcionPedido"})
    @Command
    @NotifyChange({"listaDescripcionPedidoTercerizado", "descriGenral", "buscarCliente", "clienteBuscado",
        "totalManoObra", "totalCostos",
        "porcentajeContribucion", "porcentajeCosto",
        "valorTotalMaterialGeneralVarios", "valorSubtotalTotalCotziacionGeneral",
        "valorTotalCotizaciónGeneralConUtilidad", "valorGananciaGeneral", "listaDescripcionPedidoVarios",
        "totalManoObra", "totalCostos",
        "valorTotalManoObraGeneralPrePost",
        "listaDescripcionPedidoPrePost", "listaDescripcionPedido", "valorManoObraDI", "valorMaterialDI", "valorTintaDI", "porcentajeCosto", "porcentajeContrib"})
    private void recuperarCotizacion() {
        try {

            cotizacionRecuperada = servicioCotizacion.findByCotNumero(numeroCotizacionGeneral);
            tipoCotizacion = cotizacionRecuperada.getCotTipoCotizacion();
            cotObservacion = cotizacionRecuperada.getCotObservacion();
            fechaEmision = cotizacionRecuperada.getCotFechaEmision();
            valorSubtotalTotalCotziacionGeneral = cotizacionRecuperada.getCotSubtotal();
            valorTotalCotizaciónGeneralConUtilidad = cotizacionRecuperada.getCotTotal();
            porcentajeUtilidadGeneral = cotizacionRecuperada.getCotPorcentajeGanancia();
            clienteBuscado = cotizacionRecuperada.getCodCliente();
            buscarCliente = cotizacionRecuperada.getCodCliente().getRuc();
            DescripcionPedido descriGenral = new DescripcionPedido();
            DescripcionPedido detPrePost = new DescripcionPedido();
            DescripcionPedido detVarios = new DescripcionPedido();
            DescripcionPedido detTercerizados = new DescripcionPedido();

            System.out.println("longitud de la lista " + servicioDetalleCotizacion.FindDetalleCotizacionPorCotizacion(cotizacionRecuperada).size());
            for (DetalleCotizacion item : servicioDetalleCotizacion.FindDetalleCotizacionPorCotizacion(cotizacionRecuperada)) {
                descriGenral = new DescripcionPedido();
                detPrePost = new DescripcionPedido();
                detVarios = new DescripcionPedido();
                detTercerizados = new DescripcionPedido();
                // System.out.println("numero de items " + item.getDetUnidadCantidad());
                System.out.println("id detalle de cotizacion " + item.getCodDetalle());
                if (item.getDetTipoDetalle().equals("MD")) {
                    System.out.println("ENtra en el material");
                    descripcionTrabajo = item.getDetProductoSolicitado();
                    // descriGenral.setTipoTrabajo((TipoTrabajo) servicioTipoTrabajo.FindALlTipoTrabajoLike(item.getDetProductoSolicitado()));
                    descriGenral.setDescripcionTrabajo(item.getDetProductoSolicitado());
                    descriGenral.setIdDetalle(item.getCodDetalle());
                    descriGenral.setIdDetCotizacion(item.getCodDetalle());
                    descriGenral.setMateriales(item.getRecorteMaterial().getMateriales());
                    descriGenral.setCortezPosibles(item.getRecorteMaterial().getCortezPosibles());
                    System.out.println("item.getRecorteMaterial().getCortezPosibles()" + item.getRecorteMaterial().getCortezPosibles());
                    descriGenral.setCantidad(item.getDetCantidadSolicitada());
                    System.out.println("item.getDetCantidadSolicitada()" + item.getDetCantidadSolicitada());
                    descriGenral.setDetTipoTrabajo(item.getDetTipoTrabajo());
                    System.out.println("item.getDetTipoTrabajo()" + item.getDetTipoTrabajo());
//                    descriGenral.setDescuentoMano(item.getDetDescuentoMano());
//                    System.out.println("item.getDetDescuentoMano()"+item.getDetDescuentoMano());
                    System.out.println("ENtra en el material" + item.getRecorteMaterial().getMateriales().getMatNombre());
                    ((ListModelList<DescripcionPedido>) listaDescripcionPedido).add(descriGenral);
                }

                if (item.getDetTipoDetalle().equals("PP")) {
                    detPrePost.setDetalleTrabajo(item.getDetProductoSolicitado());
                    detPrePost.setIdDetalle(item.getCodDetalle());
                    detPrePost.setCatidadPrePost(item.getDetCantidadSolicitada());
                    detPrePost.setManoObraPrePost(item.getCodManObra());
                    detPrePost.setTipoCobroManoObraPrePost(item.getDetTipoCobro());
                    detPrePost.setValorTotalPrePost(item.getDetSubTotal());
                    detPrePost.setCantidadHoraMillar(BigDecimal.valueOf(Double.valueOf(item.getDetCantHoraMillar())));

                    ((ListModelList<DescripcionPedido>) listaDescripcionPedidoPrePost).add(detPrePost);
                }

                if (item.getDetTipoDetalle().equals("V")) {
                    detVarios.setDetalleTrabajo(item.getDetProductoSolicitado());
                    detVarios.setIdDetalle(item.getCodDetalle());
                    detVarios.setMaterialesVarios(item.getRecorteMaterial().getMateriales());
                    detVarios.setCortezPosibles(item.getRecorteMaterial().getCortezPosibles());
                    detVarios.setCatidadVarios(item.getDetCantidadSolicitada());
                    detVarios.setValorVariosTotal(item.getDetSubTotal());
                    ((ListModelList<DescripcionPedido>) listaDescripcionPedidoVarios).add(detVarios);
                }

                if (item.getDetTipoDetalle().equals("T")) {
                    detTercerizados.setIdDetalle(item.getCodDetalle());
                    detTercerizados.setDetalleTercerizado(item.getDetProductoSolicitado());
                    detTercerizados.setTerciarizado(item.getCodTercearizado());
                    detTercerizados.setCantidadTercerizado(item.getDetCantidadSolicitada());
                    detTercerizados.setTotalTercerizado(item.getDetSubTotal());
                    detTercerizados.setValorUnitarioTercerizado(item.getDetValorUnitario());
                    List<Terciarizado> tercerizadoNuevo = servicioTerceriarizado.FindALlTerciarizadoPorNombre("");
                    detTercerizados.setListaTercerizados(tercerizadoNuevo);
                    ((ListModelList<DescripcionPedido>) listaDescripcionPedidoTercerizado).add(detTercerizados);

                }


            }
        } catch (Exception e) {
        }





    }

    private void armarCotizacion() {
        try {
            //encabezado del pedido
            if (numeroCotizacionGeneral.equals("")) {
                numeroCotizacion();
            }
            encabezado = new Cotizacion(numeroCotizacionGeneral, fechaEmision,
                    valorSubtotalTotalCotziacionGeneral,
                    valorTotalCotizaciónGeneralConUtilidad,
                    porcentajeUtilidadGeneral,
                    clienteBuscado, cotObservacion);

            encabezado.setCotTipoCotizacion(tipoCotizacion);
            detalleCotizacion.clear();
            DetalleCotizacion det = new DetalleCotizacion();
            RecorteMaterial recorteMaterial;


            RecorteMaterialPK rPK = new RecorteMaterialPK();
            List<DescripcionPedido> listaPedido = listaDescripcionPedido.getInnerList();
            for (DescripcionPedido item : listaPedido) {

                det = new DetalleCotizacion();

                //inicio mano obra
                det.setDetProductoSolicitado(item.getDescripcionTrabajo());
//                det.setCodManObra(item.getManoObra());
                //agrega la tabla de rompimieto entre recorte y material
                rPK = new RecorteMaterialPK();
                System.out.println("CORTE VARIOSDDDDDDDDDDDD " + servicioCortez.FindALlCortezPorOtros().getCodCorte());
                rPK.setCodCorte(servicioCortez.FindALlCortezPorOtros().getCodCorte());
                rPK.setCodMaterial(item.getMateriales().getCodMaterial());
                recorteMaterial = new RecorteMaterial(rPK);
                det.setRecorteMaterial(recorteMaterial);
                det.setDetPliegos(item.getPliegos().toString());
                det.setDetCantidadSolicitada(item.getCantidad());
                det.setDetValorUnitario(BigDecimal.valueOf(item.getMateriales().getMatCosto()));
                det.setDetSubTotal(item.getValorTotalMaterial());
                det.setDetTipoTrabajo(item.getDetTipoTrabajo());
                if (tipoGuardado.equals("MODIFICAR") && item.getIdDetalle() != 0) {
                    det.setCodDetalle(item.getIdDetalle());
                }
                det.setDetTipoDetalle("MD");
                detalleCotizacion.add(det);

            }
            List<DescripcionPedido> listaPedidoPrePost = listaDescripcionPedidoPrePost.getInnerList();

            for (DescripcionPedido item : listaPedidoPrePost) {
                det = new DetalleCotizacion();
                det.setDetCantidadSolicitada(item.getCatidadPrePost());
                det.setDetProductoSolicitado(item.getDetalleTrabajo());
                det.setCodManObra(item.getManoObraPrePost());

                if (item.getTipoCobroManoObra().equals("M")) {
                    det.setDetValorUnitario(BigDecimal.valueOf(item.getManoObraPrePost().getManCostoMillar()));

                } else {
                    det.setDetTipoCobro(item.getTipoCobroManoObra());
                    det.setDetValorUnitario(BigDecimal.valueOf(item.getManoObraPrePost().getManCostoTiempo()));
                }
                det.setDetTipoCobro(item.getTipoCobroManoObraPrePost());
                det.setDetSubTotal(item.getValorTotalPrePost());
                det.setDetCantHoraMillar(String.valueOf(item.getCantidadHoraMillar()));

                if (tipoGuardado.equals("MODIFICAR") && item.getIdDetalle() != 0) {
                    det.setCodDetalle(item.getIdDetalle());
                }
                det.setDetTipoDetalle("PP");
                detalleCotizacion.add(det);

            }
            List<DescripcionPedido> listaPedidoVarios = listaDescripcionPedidoVarios.getInnerList();

            CortezPosibles obtenerCorte = new CortezPosibles();
            for (DescripcionPedido item : listaPedidoVarios) {
                det = new DetalleCotizacion();
                det.setDetProductoSolicitado(item.getDetalleTrabajo());
//            det.setCodManObra(item.getManoObra());
                //agrega la tabla de rompimieto entre recorte y material
                rPK = new RecorteMaterialPK();
                rPK.setCodMaterial(item.getMaterialesVarios().getCodMaterial());
                System.out.println("material codigo   " + item.getMaterialesVarios().getCodMaterial());
                List<CortezPosibles> listaCortes = servicioCorteMaterial.FindALlRecorteMaterialPorMaterial(item.getMaterialesVarios());
                for (CortezPosibles corte : listaCortes) {
                    obtenerCorte = corte;
                }
                rPK.setCodCorte(obtenerCorte.getCodCorte());
                recorteMaterial = new RecorteMaterial(rPK);

                det.setRecorteMaterial(recorteMaterial);

                det.setDetCantidadSolicitada(item.getCatidadVarios());
                det.setDetValorUnitario(BigDecimal.valueOf(item.getMaterialesVarios().getMatCosto()));
                det.setDetSubTotal(item.getValorVariosTotal());
//            det.setDetMotivo(item.getMotivo().toString());
                if (tipoGuardado.equals("MODIFICAR") && item.getIdDetalle() != 0) {
                    det.setCodDetalle(item.getIdDetalle());

                }
                det.setDetTipoDetalle("V");
                detalleCotizacion.add(det);


            }

            List<DescripcionPedido> listaPedidoTercerizado = listaDescripcionPedidoTercerizado.getInnerList();

            for (DescripcionPedido item : listaPedidoTercerizado) {
                det = new DetalleCotizacion();
                det.setDetProductoSolicitado(item.getDetalleTercerizado());
                det.setCodTercearizado(item.getTerciarizado());
                det.setDetCantidadSolicitada(item.getCantidadTercerizado());
                det.setDetSubTotal(item.getTotalTercerizado());
                det.setDetValorUnitario(item.getValorUnitarioTercerizado());
//            det.setDetMotivo(item.getMotivo().toString());
                //mano de obra
                det.setDetTipoDetalle("T");
                if (tipoGuardado.equals("MODIFICAR") && item.getIdDetalle() != 0) {
                    det.setCodDetalle(item.getIdDetalle());
                }
                detalleCotizacion.add(det);

            }

            for (DetalleCotizacion item : detalleCotizacion) {
                System.out.println("valores del detalle " + item.getDetProductoSolicitado());
            }

        } catch (Exception e) {
            System.out.println("error " + e.toString());
        }

    }

    @Command
    @NotifyChange({"porcentajeUtilidadGeneral"})
    public void RegistrarCotizacion() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        if (fechaEmision != null && clienteBuscado != null) {
            armarCotizacion();
            System.out.println("Informacion..... " + clienteBuscado.getRuc() + "  " + fechaEmision + "  " + detalleCotizacion.size());
            if (clienteBuscado.getRuc() != null && fechaEmision != null && detalleCotizacion.size() > 0) {

                if (tipoGuardado.equals("NUEVO")) {
                    servicioCotizacion.guardarCotizacion(encabezado, detalleCotizacion);
                } else {
                    servicioCotizacion.eliminar(cotizacionRecuperada);
                    servicioCotizacion.guardarCotizacion(encabezado, detalleCotizacion);
                }
                reporteGeneral();
                Executions.sendRedirect("/cotizacion/cotizacionDigital.zul");
            }
        } else {
            Messagebox.show("Verifique el Cliente, la Fecha", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void opcionImpresion() {


        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/opcionCotOrden.zul", null, new HashMap<String, String>());
        window.doModal();


    }

    @Command
    @NotifyChange({"porcentajeUtilidadGeneral"})
    private void porcentajeUtilidad() {
        porcentajeUtilidadGeneral = servicioParametros.porcentajeUtilidad().getPorGanancia();
    }

    @Command
    @NotifyChange("cliente")
    public void cerrar() {

        winOpcionesImpresion.detach();


    }

    private void getDescripcionGeneral() {
        setListaDescripcionPedido(new ListModelList<DescripcionPedido>(getLstDEscripcionPedidoGeneral()));
        ((ListModelList<DescripcionPedido>) listaDescripcionPedido).setMultiple(true);
    }

    private void getDescripcionGeneralVarios() {
        setListaDescripcionPedidoVarios(new ListModelList<DescripcionPedido>(getLstDEscripcionPedidoGeneralVarios()));
        ((ListModelList<DescripcionPedido>) listaDescripcionPedidoVarios).setMultiple(true);
    }

    private void getDescripcionGeneralPrePost() {
        setListaDescripcionPedidoPrePost(new ListModelList<DescripcionPedido>(getLstDescripcionPedidoGeneralPrePost()));
        ((ListModelList<DescripcionPedido>) listaDescripcionPedidoPrePost).setMultiple(true);
    }

    private void getDescripcionGeneralTercerizado() {
        setListaDescripcionPedidoTercerizado(new ListModelList<DescripcionPedido>(getLstDescripcionPedidoGeneralTercerizado()));
        ((ListModelList<DescripcionPedido>) listaDescripcionPedidoTercerizado).setMultiple(true);
    }

    private void consultarTipoTrabaLike(String valor) {
        listaTipoTrabajo = servicioTipoTrabajo.FindALlTipoTrabajoLike(valor);
    }

    private void consultarMatriales(String valor) {
        listaMaterialAll = servicioMateriales.FindDigitalLike(valor);

    }

    private void consultarMatrialesOtrosGeneral(String valor) {
        listaMaterialOtrosGeneral = servicioMateriales.FindALlMaterialesOtrosLike(valor);
    }

    private void consultarTercearizadoNombre(String valor) {
        listaTercearizadoNombre = servicioTerceriarizado.FindALlTerciarizadoPorNombre(valor);
    }

    private void consultarClienteLike(String valor) {
        listaClientesAll = servicioCliente.FindALlClienteLike(valor);
    }

    private void consultarManoObra(String manoObra) {
        listaManoObraNombre = servicioManoObra.FindALlManoDeObraLikeNombre(manoObra);
    }

    private void consultarManoObraPrePost(String manoObra) {
        listaManoObraPrePost = servicioManoObra.FindALlManoDeObraLikeNombrePrePost(manoObra);
    }

    private void buscarCabidaGeneral(Integer valor) {
        listaCabidasBuscadasGeneral = servicioCabida.FindALlCabiPorUXF(valor);
    }

    public void reporteGeneral() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            if (tipoCotizacion.equals("CIDIG")) {
                reportPath = reportFile + "/cotdigen.jasper";
            } else {
                reportPath = reportFile + "/cotdigcom.jasper";
            }

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numcotizacion", numeroCotizacionGeneral);


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
            map1.put("mensaje", numeroCotizacionGeneral);
            org.zkoss.zul.Window window1 = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/mensajeGenCot.zul", null, map1);
            window1.doModal();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }
        }
    }

    private void consultarClientePorCedula(String valor) {
        Cliente cliente = new Cliente();
        listaClientesAll.clear();
        cliente = servicioCliente.FindClientePorCedula(valor);
        System.out.println("Valor del clinete consultado" + cliente.getNombreComercial());

        listaClientesAll.add(cliente);
    }

    @Command
    @NotifyChange({"listaMaterialAll", "buscarMaterial"})
    public void buscarMaterial(@BindingParam("valor") String valor) {
        buscarMaterial = valor;
        consultarMatriales(valor);
    }

    @Command
    @NotifyChange({"listaDescripcionPedido", "buscarCabidaLike", "valorTotalMaterialGeneral", "porcentajeContrib"})
    public void nuevaDescripcionGeneral() {

        if (materialSeleccionadoCombo.getMatNombre() != null) {
            DescripcionPedido nuevoRegistro = new DescripcionPedido(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0, 0, BigDecimal.ZERO, materialSeleccionadoCombo, corteSeleccionadoComboGeneral, tipoTrabajo, descripcionTrabajo, matTipoCobro, new ArrayList<Cabida>());
            nuevoRegistro.setFotoGeneral(fotoGeneral);
            nuevoRegistro.setDetTipoTrabajo(detalleTrabajo);
//            nuevoRegistro.setManoObra(manoDeObraCombo);
//            nuevoRegistro.setDescuentoMano(BigDecimal.ZERO);
//            System.out.println("Seagregarar el tipo de mano de obra  " + tipoCobroManoObra);
//            nuevoRegistro.setDetalleTrabajo(tipoTrabajo.getTipoDescripcion());
//            nuevoRegistro.setTipoCobroManoObra(tipoCobroManoObra);
            ((ListModelList<DescripcionPedido>) listaDescripcionPedido).add(nuevoRegistro);
            //((ListModelList<DescripcionPedido>) listaDescripcionPedido).add(new DescripcionPedido(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0, 0, "", new Materiales(), new CortezPosibles()));
            calcularValorTotalesGeneral();
        } else {
            Messagebox.show("Verifique la información seleccionada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }


    }

    //materiales varios
    @Command
    @NotifyChange({"listaDescripcionPedidoVarios", "porcentajeContrib"})
    public void nuevaDescripcionGeneralVarios() {


        DescripcionPedido nuevoRegistro = new DescripcionPedido(materialOtrosCombo);

        System.out.println("Seagregarar el tipo de mano de obra  " + tipoCobroManoObra);
        nuevoRegistro.setDetalleTrabajo(tipoTrabajo.getTipoDescripcion());

        ((ListModelList<DescripcionPedido>) listaDescripcionPedidoVarios).add(nuevoRegistro);
    }

    //pre post
    //materiales varios
    @Command
    @NotifyChange({"listaDescripcionPedidoPrePost", "valorTotalManoObraGeneralPrePost", "porcentajeContrib"})
    public void nuevaDescripcionGeneralPrePost() {

        if (!tipoCobroManoObraPrePost.equals("")) {
            DescripcionPedido nuevoRegistro = new DescripcionPedido(manoDeObraComboPrePost);
            nuevoRegistro.setTipoCobroManoObraPrePost(tipoCobroManoObraPrePost);
            nuevoRegistro.setDetalleTrabajo(tipoTrabajo.getTipoDescripcion());
            ((ListModelList<DescripcionPedido>) listaDescripcionPedidoPrePost).add(nuevoRegistro);
        } else {

            Messagebox.show("Verifique la información seleccionada", "Atención", Messagebox.OK, Messagebox.ERROR);

        }

    }
    //materiales varios

    @Command
    @NotifyChange({"listaDescripcionPedidoVarios", "porcentajeContrib"})
    public void nuevaDescripcionTercerizado() {

        List<Terciarizado> tercerizadoNuevo = servicioTerceriarizado.FindALlTerciarizadoPorNombre("");
        DescripcionPedido nuevoRegistro = new DescripcionPedido(tercerizadoNuevo);

        System.out.println("Seagregarar el tipo de mano de obra  " + tipoCobroManoObra);

        ((ListModelList<DescripcionPedido>) listaDescripcionPedidoTercerizado).add(nuevoRegistro);
    }

    @NotifyChange("valorTotalMaterialGeneralVarios")
    private void calcularValorTotalesGeneralVarios() {
        valorTotalMaterialGeneralVarios = BigDecimal.ZERO;
        BigDecimal valorTotalAux = BigDecimal.ZERO;

        List<DescripcionPedido> listaPedido = listaDescripcionPedidoVarios.getInnerList();
        System.out.println("LISTA VARIOS " + listaPedido.size());
        for (DescripcionPedido descripcionPedido1 : listaPedido) {
            valorTotalAux = valorTotalAux.add(descripcionPedido1.getValorVariosTotal());
            System.out.println("valor auxiliar " + valorTotalAux);
        }
        valorTotalMaterialGeneralVarios = valorTotalAux;
        valorTotalMaterialGeneralVarios.setScale(4, BigDecimal.ROUND_FLOOR);
        System.out.println("VALOR VSRIOOOOOOS  " + valorTotalMaterialGeneralVarios);

    }

    @NotifyChange("valorTotalMaterialGeneralTercerizado")
    private void calcularValorTotalesGeneralTercerizado() {
        valorTotalMaterialGeneralTercerizado = BigDecimal.ZERO;
        BigDecimal valorTotalAux = BigDecimal.ZERO;

        List<DescripcionPedido> listaPedido = listaDescripcionPedidoTercerizado.getInnerList();
        System.out.println("LISTA VARIOS " + listaPedido.size());
        for (DescripcionPedido descripcionPedido1 : listaPedido) {
            valorTotalAux = valorTotalAux.add(descripcionPedido1.getTotalTercerizado());
            System.out.println("valor auxiliar " + valorTotalAux);
        }
        valorTotalMaterialGeneralTercerizado = valorTotalAux;
        valorTotalMaterialGeneralTercerizado.setScale(4, BigDecimal.ROUND_FLOOR);
        System.out.println("VALOR VSRIOOOOOOS  " + valorTotalMaterialGeneralTercerizado);
    }

    //pre post
    @NotifyChange("valorTotalManoObraGeneralPrePost")
    private void calcularValorTotalesGeneralPrePost() {
        valorTotalManoObraGeneralPrePost = BigDecimal.ZERO;
        BigDecimal valorTotalAux = BigDecimal.ZERO;

        List<DescripcionPedido> listaPedido = listaDescripcionPedidoPrePost.getInnerList();
        System.out.println("LISTA VARIOS " + listaPedido.size());
        for (DescripcionPedido descripcionPedido1 : listaPedido) {
            valorTotalAux = valorTotalAux.add(descripcionPedido1.getValorTotalPrePost());
            System.out.println("valor auxiliar " + valorTotalAux);

        }
        valorTotalManoObraGeneralPrePost = valorTotalAux;
        valorTotalManoObraGeneralPrePost.setScale(4, BigDecimal.ROUND_FLOOR);

        totalCostos.setScale(4, BigDecimal.ROUND_FLOOR);
        System.out.println("VALOR VSRIOOOOOOS  " + valorTotalManoObraGeneralPrePost);
    }

    @Command
    @NotifyChange({"listaDescripcionPedidoPrePost", "totalManoObra", "totalCostos",
        "porcentajeContribucion", "porcentajeCosto", "valorTotalManoObraGeneralPrePost",
        "valorTotalCotizaciónGeneralConUtilidad", "valorGananciaGeneral",
        "valorSubtotalTotalCotziacionGeneral", "valorUnitario", "porcentajeContrib"})
    public void procesarPrePost(@BindingParam("valor") DescripcionPedido valor) throws IOException {
        BigDecimal valorManoObra = BigDecimal.ZERO;
        BigDecimal valorSubtotalAux = valor.getCatidadPrePost().multiply(BigDecimal.valueOf(valor.getManoObraPrePost().getManCostoMillar()));
        BigDecimal numeroMillares = valor.getCatidadPrePost().divide(BigDecimal.valueOf(1000));

        if (valor.getTipoCobroManoObraPrePost().equals("M")) {
            valor.setCantidadHoraMillar(numeroMillares);
            if (true) {
                valor.setCantidadHoraMillar(numeroMillares);
                valorManoObra = valorManoObra.add(numeroMillares.multiply(BigDecimal.valueOf(valor.getManoObraPrePost().getManCostoMillar())));
                valorManoObra.setScale(4, BigDecimal.ROUND_FLOOR);
                System.out.println("mano de obra  Millar mayor 1::: " + numeroMillares.multiply(BigDecimal.valueOf(valor.getManoObraPrePost().getManCostoMillar())));
            } else {
                valor.setCantidadHoraMillar(BigDecimal.ONE);
                valorManoObra = valorManoObra.add(BigDecimal.valueOf(valor.getManoObraPrePost().getManCostoMillar()));
                System.out.println("valorManoOBra Millar menor 1:: " + BigDecimal.valueOf(valor.getManoObraPrePost().getManCostoMinMillar()));
            }

        } else if (valor.getTipoCobroManoObraPrePost().equals("H")) {
            BigDecimal numeroHoras = valor.getCatidadPrePost().divide(BigDecimal.valueOf(valor.getManoObraPrePost().getManImpresionHora()), 2, RoundingMode.DOWN);
            System.out.println("NUMERO DE HORAS--->  " + numeroHoras);

            if (true) {
                valorManoObra = valorManoObra.add(numeroHoras.multiply(BigDecimal.valueOf(valor.getManoObraPrePost().getManCostoTiempo())));
                valor.setCantidadHoraMillar(numeroHoras);
                valorManoObra.setScale(4, BigDecimal.ROUND_FLOOR);
                System.out.println("MAno de obra hora mayor 1:: " + numeroHoras.multiply(BigDecimal.valueOf(valor.getManoObraPrePost().getManCostoTiempo())));
            } else {
                valor.setCantidadHoraMillar(BigDecimal.ONE);
                valorManoObra = valorManoObra.add(BigDecimal.valueOf(valor.getManoObraPrePost().getManCostoTiempo()));
                System.out.println("Mano de obra hora menor 1:: " + valor.getManoObraPrePost().getManCostoTiempo());
            }


        }
        valor.setValorTotalPrePost(valorManoObra);
        calcularValorTotalesGeneralPrePost();
        calcularSubtotalTotalPedicoGeneral();

    }

    @Command
    @NotifyChange({"listaDescripcionPedidoVarios", "totalManoObra", "totalCostos",
        "porcentajeContribucion", "porcentajeCosto", "valorTotalMaterialGeneralVarios",
        "valorSubtotalTotalCotziacionGeneral", "valorTotalCotizaciónGeneralConUtilidad",
        "valorGananciaGeneral", "valorTotalCotiacionGenral", "valorUnitario", "porcentajeContrib"})
    public void procesarVarios(@BindingParam("valor") DescripcionPedido valor) throws IOException {

        BigDecimal valorSubtotalAux = valor.getCatidadVarios().multiply(BigDecimal.valueOf(valor.getMaterialesVarios().getMatCosto()));
        valor.setValorVariosTotal(valorSubtotalAux);
        calcularValorTotalesGeneralVarios();
        calcularSubtotalTotalPedicoGeneral();

    }

    @Command
    @NotifyChange({"listaDescripcionPedidoTercerizado", "totalManoObra", "totalCostos",
        "porcentajeContribucion", "porcentajeCosto", "valorSubtotalTotalCotziacionGeneral",
        "valorTotalMaterialGeneralTercerizado", "valorTotalCotizaciónGeneralConUtilidad",
        "valorGananciaGeneral", "valorUnitario", "porcentajeContrib"})
    public void procesarTercerizados(@BindingParam("valor") DescripcionPedido valor) throws IOException {

        if (valor.getCantidadTercerizado().doubleValue() > 0
                && valor.getValorUnitarioTercerizado().doubleValue() > 0
                && valor.getTerciarizado().getTerProveedor() != null) {
            BigDecimal valorSubtotalAux = valor.getCantidadTercerizado().multiply(valor.getValorUnitarioTercerizado());
            valor.setTotalTercerizado(valorSubtotalAux);
            calcularValorTotalesGeneralTercerizado();
            calcularSubtotalTotalPedicoGeneral();
        } else {
            Messagebox.show("Verifique la información seleccionada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    private void calcularValorTotalesGeneral() {
        try {
            valorTotalMaterialGeneral = BigDecimal.ZERO;
            BigDecimal valorPliegos = BigDecimal.ZERO;
            BigDecimal valorManoObra = BigDecimal.ZERO;
            BigDecimal costoPlacas = BigDecimal.ZERO;
            BigDecimal cantidadTotMillares = BigDecimal.ZERO;

            List<DescripcionPedido> listaPedido = listaDescripcionPedido.getInnerList();
            cantidadProSolicitado = BigDecimal.ZERO;
            if (listaPedido.size() > 0) {
                for (DescripcionPedido item : listaPedido) {

                    valorTotalMaterialGeneral = valorTotalMaterialGeneral.add(item.getValorTotalMaterial());
                }
                System.out.println("**********************************************************");
                System.out.println("valor de los pliegos:::: " + valorPliegos);
                System.out.println("valor de los placas:::: " + costoPlacas);
                System.out.println("valor de mano de obra::: " + valorManoObra);
//                valorTotalMaterialGeneral = subtotalManoObraGenral;
                valorTotalMaterialGeneral.setScale(4, BigDecimal.ROUND_FLOOR);
                aplicarUtilidad();
                System.out.println("Costo de la cotizacion SUbtotal" + valorTotalMaterialGeneral);
            }
        } catch (Exception e) {
            System.out.println("ERROR " + e);
        }
    }

    @NotifyChange({"totalManoObra", "totalCostos", "porcentajeContribucion", "porcentajeCosto", "porcentajeContrib"})
    private void calculoTotalesPorcentajes() {
        totalManoObra = valorTotalMaterialGeneral.add(valorTotalManoObraGeneralPrePost);
        totalCostos = valorTotalMaterialGeneral;
        valorTotalCotizaciónGeneralConUtilidad.setScale(2, BigDecimal.ROUND_FLOOR);
        System.out.println("**************************************************************************+");
        System.out.println("valor de material general    " + valorGananciaGeneral);
        System.out.println("valor de mano de obra post       " + valorTotalManoObraGeneralPrePost);
        System.out.println("TOTAL MANO DE OBRA      " + totalManoObra);

        System.out.println("valo materia prima      " + subtotalMateriaPrima);
        System.out.println("valo materia varios     " + valorTotalMaterialGeneralVarios);
        System.out.println("valo materia valor tercerizados     " + valorTotalMaterialGeneralTercerizado);
        System.out.println("COSTO TOTAL      " + totalCostos);
        System.out.println("Total mano de valorTotalCotizaciónGeneralConUtilidad         " + valorTotalCotizaciónGeneralConUtilidad);

        if (valorTotalCotizaciónGeneralConUtilidad.doubleValue() > 0) {
            BigDecimal manoutilidad = totalManoObra.add(valorGananciaGeneral);
            porcentajeContribucion = manoutilidad.divide(valorTotalCotizaciónGeneralConUtilidad, 4, RoundingMode.FLOOR);
            porcentajeContribucion = porcentajeContribucion.multiply(BigDecimal.valueOf(100));
            porcentajeCosto = totalCostos.divide(valorTotalCotizaciónGeneralConUtilidad, 4, RoundingMode.FLOOR);
            porcentajeCosto = porcentajeCosto.multiply(BigDecimal.valueOf(100));
            if (cantidadProSolicitado.doubleValue() != 0) {
                valorUnitario = valorTotalCotizaciónGeneralConUtilidad.divide(cantidadProSolicitado, 4, RoundingMode.FLOOR);
            }

        } else {
            porcentajeContribucion = BigDecimal.ZERO;
            porcentajeCosto = BigDecimal.ZERO;
        }
    }

    @Command
    public void seleccionarRegistros() {
        registrosSeleccionados = ((ListModelList<DescripcionPedido>) getListaDescripcionPedido()).getSelection();
    }

    @Command
    public void seleccionarRegistrosVarios() {
        registrosSeleccionadosVarios = ((ListModelList<DescripcionPedido>) getListaDescripcionPedidoVarios()).getSelection();
    }

    @Command
    public void seleccionarRegistrosPrePost() {
        registrosSeleccionadosPrePost = ((ListModelList<DescripcionPedido>) getListaDescripcionPedidoPrePost()).getSelection();
    }

    @Command
    public void seleccionarRegistrosTercerizado() {
        registrosSeleccionadosTercerizado = ((ListModelList<DescripcionPedido>) getListaDescripcionPedidoTercerizado()).getSelection();
    }

    @Command
    @NotifyChange({"valorTotalMaterialGeneral", "totalManoObra", "totalCostos", "porcentajeContribucion",
        "porcentajeCosto", "listaDescripcionPedido", "subtotalMateriaPrima",
        "valorSubtotalTotalCotziacionGeneral", "buscarCabidaLike", "valorTotalCotizaciónGeneralConUtilidad",
        "valorGananciaGeneral", "valorUnitario", "porcentajeContrib"})
    public void eliminarRegistros() {
        ((ListModelList<DescripcionPedido>) listaDescripcionPedido).removeAll(registrosSeleccionados);
        calcularValorTotalesGeneral();
        calcularSubtotalTotalPedicoGeneral();
        aplicarUtilidad();
    }

    @Command
    @NotifyChange({"listaDescripcionPedidoVarios", "totalManoObra", "totalCostos",
        "porcentajeContribucion", "porcentajeCosto", "valorTotalMaterialGeneralVarios",
        "valorSubtotalTotalCotziacionGeneral", "valorTotalCotizaciónGeneralConUtilidad",
        "valorGananciaGeneral", "valorTotalCotiacionGenral", "valorUnitario", "porcentajeContrib"})
    public void eliminarRegistrosVarios() {
        ((ListModelList<DescripcionPedido>) listaDescripcionPedidoVarios).removeAll(registrosSeleccionadosVarios);
        calcularValorTotalesGeneralVarios();
        calcularSubtotalTotalPedicoGeneral();
        aplicarUtilidad();
    }

    @Command
    @NotifyChange({"listaDescripcionPedidoPrePost", "totalManoObra", "totalCostos",
        "porcentajeContribucion", "porcentajeCosto", "valorTotalManoObraGeneralPrePost",
        "valorTotalCotizaciónGeneralConUtilidad", "valorGananciaGeneral",
        "valorSubtotalTotalCotziacionGeneral", "valorUnitario", "porcentajeContrib"})
    public void eliminarRegistrosPrePost() {
        ((ListModelList<DescripcionPedido>) listaDescripcionPedidoPrePost).removeAll(registrosSeleccionadosPrePost);
        calcularValorTotalesGeneralPrePost();
        calcularSubtotalTotalPedicoGeneral();
        aplicarUtilidad();
    }

    @Command
    @NotifyChange({"listaDescripcionPedidoTercerizado", "totalManoObra", "totalCostos",
        "porcentajeContribucion", "porcentajeCosto", "valorSubtotalTotalCotziacionGeneral",
        "valorTotalMaterialGeneralTercerizado", "valorTotalCotizaciónGeneralConUtilidad",
        "valorGananciaGeneral", "valorUnitario", "porcentajeContrib"})
    public void eliminarRegistrosTercerizados() {
        ((ListModelList<DescripcionPedido>) listaDescripcionPedidoTercerizado).removeAll(registrosSeleccionadosTercerizado);
        calcularValorTotalesGeneralTercerizado();
        calcularSubtotalTotalPedicoGeneral();
        aplicarUtilidad();

    }

    @Command
    @NotifyChange({"listaCortesPorMaterial", "materialSeleccionadoCombo", "fotoGeneral", "porcentajeContrib"})
    public void buscarCortesPosiblesGeneral() {
        listaCortesPorMaterial = servicioCorteMaterial.FindALlRecorteMaterialPorMaterial(materialSeleccionadoCombo);
        if (listaCortesPorMaterial.size() > 0) {
        } else {
            fotoGeneral = null;
        }

    }

    private void calcularSubtotalTotalPedicoGeneral() {
        totalManoObra = valorTotalMaterialGeneral.add(valorTotalManoObraGeneralPrePost);
        totalCostos = valorTotalMaterialGeneralVarios.add(valorTotalMaterialGeneralTercerizado);
        totalManoObra.setScale(2, BigDecimal.ROUND_FLOOR);
        totalCostos.setScale(2, BigDecimal.ROUND_FLOOR);
//        BigDecimal auxiliar = BigDecimal.ZERO;
//        System.out.println("valor costos " + totalCostos);
//        totalCostos = valorTotalMaterialGeneral;
//        System.out.println("valor mano de obra " + totalManoObra);
//        auxiliar = totalCostos;
        valorSubtotalTotalCotziacionGeneral = totalManoObra.add(totalCostos);
        aplicarUtilidad();



    }

    @Command
    @NotifyChange({"valorTotalCotizaciónGeneralConUtilidad", "valorGananciaGeneral",
        "totalManoObra", "totalCostos", "porcentajeContribucion", "porcentajeCosto", "porcentajeContrib"})
    public void aplicarUtilidad() {
        BigDecimal auxiliar = valorSubtotalTotalCotziacionGeneral.multiply(porcentajeUtilidadGeneral);
        valorGananciaGeneral = (auxiliar.divide(BigDecimal.valueOf(100)));
        valorGananciaGeneral.setScale(4, BigDecimal.ROUND_FLOOR);
        valorTotalCotizaciónGeneralConUtilidad = valorSubtotalTotalCotziacionGeneral.add(valorGananciaGeneral);
        valorTotalCotizaciónGeneralConUtilidad.setScale(4, BigDecimal.ROUND_FLOOR);

        porcentajeContrib = BigDecimal.ZERO;
        porcentajeCosto = BigDecimal.ZERO;

//        porcentajeCosto = (totalCostos.divide(valorTotalCotizaciónGeneralConUtilidad, 4, RoundingMode.FLOOR)).multiply(BigDecimal.valueOf(Double.valueOf("100")));
//        porcentajeCosto.setScale(4, RoundingMode.FLOOR);
        BigDecimal manoutilidad = totalManoObra.add(valorGananciaGeneral);
        porcentajeContrib = (manoutilidad.divide(valorTotalCotizaciónGeneralConUtilidad, 4, RoundingMode.FLOOR)).multiply(BigDecimal.valueOf(Double.valueOf("100")));
        porcentajeContrib.setScale(4, RoundingMode.FLOOR);
        porcentajeCosto = BigDecimal.valueOf(100).add(porcentajeContrib.negate());
//        calculoTotalesPorcentajes();

    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarCliente"})
    public void buscarCliente(@BindingParam("valor") String valor) {
        buscarCliente = valor;
        consultarClienteLike(valor);
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
    @NotifyChange({"clienteBuscado", "buscarCliente", "fechaEmision"})
    public void buscarClientePorCedula() {
//        if (validateCedula(buscarCliente.trim()))
        if (validateCedula(buscarCliente.trim())) {
//            if (Utilitario.validar.validadorDeCedula(buscarCliente))
            if (true) {
                clienteBuscado = servicioCliente.FindClientePorCedula(buscarCliente);
                fechaEmision = new Date();
                if (clienteBuscado != null) {
                } else {
                    if (Messagebox.show("¿Desea registrar el cliente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
                        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
                        map.put("cliente", new Cliente());
                        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                                "/cotizacion/nuevoCliente.zul", null, map);
                        window.doModal();
                    }
                }
            } else {
                Messagebox.show("La cédula ingresada no es una cédula valida", "Atención", Messagebox.OK, Messagebox.ERROR);
            }
        } else {
            Messagebox.show("Verifique la cedula ingresada solo puede contener números", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @NotifyChange({"clienteBuscado", "buscarCliente"})
    @Listen("onOK=#txtCedula")
    public void buscarClientePorCedulaText() {
//        if (validateCedula(buscarCliente.trim()))
        if (validateCedula(buscarCliente.trim())) {
//            if (Utilitario.validar.validadorDeCedula(buscarCliente))
            if (true) {
                clienteBuscado = servicioCliente.FindClientePorCedula(buscarCliente);

                if (!clienteBuscado.equals("")) {
                } else {
                    if (Messagebox.show("¿Desea registrar el cliente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
                        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
                        map.put("cliente", new Cliente());
                        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                                "/cotizacion/nuevoCliente.zul", null, map);
                        window.doModal();
                    }
                }
            } else {
                Messagebox.show("La cédula ingresada no es una cédula valida", "Atención", Messagebox.OK, Messagebox.ERROR);
            }
        } else {
            Messagebox.show("Verifique la cedula ingresada solo puede contener números", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"listaTercearizadoNombre", "buscarTercearizado"})
    public void buscarTercearizadoLike(@BindingParam("valor") String valor) {

        buscarTercearizado = valor;
        consultarTercearizadoNombre(buscarTercearizado);
        System.out.println("Consulta la lista de tercerizado");
    }

    @Command
    @NotifyChange({"listaManoObraNombre", "buscarManoObra"})
    public void buscarManoObraLike(@BindingParam("valor") String valor) {

        buscarManoObra = valor;
        consultarManoObra(buscarManoObra);
        System.out.println("Consulta la lista de manoObra");
    }

    //eventos de seleccion de producto tercerizado mano de obra
    @Command
    @NotifyChange({"listaCortesPorMaterial", "materialSeleccionado"})
    public void seleccionarMaterial(@BindingParam("valor") Materiales materiales1) {
        System.out.println("material seleccionado " + materiales1.getMatNombre());
        // consultarCortez(materiales1);
        materialSeleccionado = materiales1.getMatNombre();
    }

    @Command
    @NotifyChange({"listaDescripcionPedido"})
    public void agregarCabida(@BindingParam("valor") DescripcionPedido valor) throws IOException {
        valor.setFotoCabida(new AImage("fotoGeneral", valor.getCabida().getCabGrafico()));
    }

    @Command
    @NotifyChange({"valorTotalMaterialGeneral", "totalManoObra", "totalCostos", "porcentajeContribucion",
        "porcentajeCosto", "listaDescripcionPedido", "subtotalMateriaPrima",
        "valorSubtotalTotalCotziacionGeneral", "buscarCabidaLike", "valorTotalCotizaciónGeneralConUtilidad",
        "valorGananciaGeneral", "detcostoPliegos", "detcostoPacas", "valorUnitario", "porcentajeContrib"})
    public void calcularCabida(@BindingParam("valor") DescripcionPedido valor) throws IOException {
        BigDecimal calculo = BigDecimal.ZERO;
        BigDecimal cabidasPorCorte = BigDecimal.ZERO;

        BigDecimal placas = BigDecimal.ZERO;
        Cabida cabida = new Cabida();
        if (true) {

            valor.setValorTotalMaterial(valor.getCantidad().multiply(BigDecimal.valueOf(valor.getMateriales().getMatCosto())));
            valor.setPliegos(valor.getCantidad());
            calcularValorTotalesGeneral();
            calcularSubtotalTotalPedicoGeneral();
//            valor.setValorTotalManoObra(subtotalManoObraGenral);
//            valor.setValorTotalMaterial(subtotalMateriales);
//            valor.setCostoTotalPlacas(subtotalPlacas);
//            calculoTotalesPorcentajes();


        } else {
            Messagebox.show("Verifique: \n 1. Cantidad \n 2. Largo abierto \n 3. Ancho abierto ", "Atención", Messagebox.OK, Messagebox.ERROR);
        }


    }

    @Command
    @NotifyChange("listaClientesAll")
    public void visorDetallePedido() {
        List<DescripcionPedido> listaPedido = listaDescripcionPedido.getInnerList();
        final HashMap<String, List<DescripcionPedido>> map = new HashMap<String, List<DescripcionPedido>>();
        map.put("pedido", listaPedido);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/visorPedido.zul", null, map);
        window.doModal();

    }

    @Command
    @NotifyChange("listaClientesAll")
    public void detMateriaPrima() {
        DetalleMateria detalleMateria = new DetalleMateria();
        detalleMateria.setCostoPlacas(detcostoPacas);
        detalleMateria.setCostoPliegos(detcostoPliegos);

        final HashMap<String, DetalleMateria> map = new HashMap<String, DetalleMateria>();
        map.put("detalle", detalleMateria);

        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/detalleMateria.zul", null, map);
        window.doModal();

    }

    @Command
    @NotifyChange("listaCabidasBuscadasGeneral")
    public void asignarCabidaGeneral(@BindingParam("valor") DescripcionPedido valor) {
        System.out.println("valor de UXF " + Integer.parseInt(valor.getUxf().toString()));
        setListaCabidasBuscadasGeneral(servicioCabida.FindALlCabiPorUXF(Integer.parseInt(valor.getUxf().toString())));
        System.out.println("Longitud de la lista de cabidas " + Integer.parseInt(valor.getUxf().toString()));
        Window window = (Window) Executions.createComponents(
                "/cotizacion/cabidaCotizacionGeneral.zul", null, null);
        window.doModal();
//        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
//                "/cotizacion/cabidaCotizacionGeneral.zul", null, null);
//        window.doModal();

    }

    public List<Cliente> getListaClientesAll() {
        return listaClientesAll;
    }

    public void setListaClientesAll(List<Cliente> listaClientesAll) {
        this.listaClientesAll = listaClientesAll;

    }

    public List<Materiales> getListaMaterialAll() {
        return listaMaterialAll;
    }

    public void setListaMaterialAll(List<Materiales> listaMaterialAll) {
        this.listaMaterialAll = listaMaterialAll;
    }
    //terciarizados

    public List<Terciarizado> getListaTercearizadoNombre() {
        return listaTercearizadoNombre;
    }

    public void setListaTercearizadoNombre(List<Terciarizado> listaTercearizadoNombre) {
        this.listaTercearizadoNombre = listaTercearizadoNombre;
    }

    public List<ManoDeObra> getListaManoObraNombre() {
        return listaManoObraNombre;
    }

    public void setListaManoObraNombre(List<ManoDeObra> listaManoObraNombre) {
        this.listaManoObraNombre = listaManoObraNombre;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getBuscarMaterial() {
        return buscarMaterial;
    }

    public void setBuscarMaterial(String buscarMaterial) {
        this.buscarMaterial = buscarMaterial;
    }

    public String getBuscarTercearizado() {
        return buscarTercearizado;
    }

    public void setBuscarTercearizado(String buscarTercearizado) {
        this.buscarTercearizado = buscarTercearizado;
    }

    public String getBuscarCliente() {
        return buscarCliente;
    }

    public void setBuscarCliente(String buscarCliente) {
        this.buscarCliente = buscarCliente;
    }

    public String getBuscarManoObra() {
        return buscarManoObra;
    }

    public void setBuscarManoObra(String buscarManoObra) {
        this.buscarManoObra = buscarManoObra;
    }

    @DependsOn({"fotoGeneral"})
    public List<CortezPosibles> getListaCortesPorMaterial() {
        return listaCortesPorMaterial;
    }

    public void setListaCortesPorMaterial(List<CortezPosibles> listaCortesPorMaterial) {
        this.listaCortesPorMaterial = listaCortesPorMaterial;

    }

    @Command
    @NotifyChange({"listaCortesPorMaterial", "fotoGeneral"})
    public void verFoto() throws IOException {

        fotoGeneral = new AImage("fotoGeneral", corteSeleccionadoComboGeneral.getCortImagen());

    }

    @Command
    @NotifyChange({"listaCortesPorMaterial", "horasManoObraGeneran", "horasManoObraGeneran"})
    public void tipoCotizacion() {

        if (tipoCotizacion.equals("CIDIG")) {
            idDescripcionTrabajo.setVisible(Boolean.FALSE);
//            ((ListModelList<DescripcionPedido>) listaDescripcionPedido).clear();
        } else {
            idDescripcionTrabajo.setVisible(Boolean.TRUE);


        }
    }

    @Command
    @NotifyChange({"listaCortesPorMaterial", "horasManoObraGeneran", "horasManoObraGeneran"})
    public void tipoCobroManoObraGeneralPrePost() throws IOException {

        if (tipoCobroManoObraPrePost.equals("H")) {
        } else if (tipoCobroManoObraPrePost.equals("M")) {
            horasManoObraGeneranPrePost = BigDecimal.ZERO;


        }
    }

    @DependsOn({"fotoGeneral"})
    public AImage getFotoGeneral() {
        return fotoGeneral;
    }

    @Command
    @NotifyChange({"listaDescripcionPedido", "fotoGeneral"})
    public void seleccinarFoto(CortezPosibles recorte) throws IOException {

        recorte.setCortImagen(recorte.getCortImagen());

    }

    public void setFotoGeneral(AImage fotoGeneral) {
        this.fotoGeneral = fotoGeneral;
    }

    //selecciones
    public String getMaterialSeleccionado() {
        return materialSeleccionado;
    }

    public void setMaterialSeleccionado(String materialSeleccionado) {
        this.materialSeleccionado = materialSeleccionado;
    }

    public String getCorteSeleccionado() {
        return corteSeleccionado;
    }

    public void setCorteSeleccionado(String corteSeleccionado) {
        this.corteSeleccionado = corteSeleccionado;
    }

    public Materiales getMaterialSeleccionadoCombo() {
        return materialSeleccionadoCombo;

    }

    public void setMaterialSeleccionadoCombo(Materiales materiaSeleccionado) {
        this.materialSeleccionadoCombo = materiaSeleccionado;

    }

    public Cliente getClienteBuscado() {
        return clienteBuscado;
    }

    public void setClienteBuscado(Cliente clienteBuscado) {
        this.clienteBuscado = clienteBuscado;
    }

    public Cotizacion getNuevaCotizacion() {
        return nuevaCotizacion;
    }

    public void setNuevaCotizacion(Cotizacion nuevaCotizacion) {
        this.nuevaCotizacion = nuevaCotizacion;
    }

    public List<Cotizacion> getListaNuevaCotizacion() {
        return listaNuevaCotizacion;
    }

    public void setListaNuevaCotizacion(List<Cotizacion> listaNuevaCotizacion) {
        this.listaNuevaCotizacion = listaNuevaCotizacion;
    }

    public List<TipoTrabajo> getListaTipoTrabajo() {
        return listaTipoTrabajo;
    }

    public void setListaTipoTrabajo(List<TipoTrabajo> listaTipoTrabajo) {
        this.listaTipoTrabajo = listaTipoTrabajo;
    }

    public String getBuscarTipoTrabajo() {
        return buscarTipoTrabajo;
    }

    public void setBuscarTipoTrabajo(String buscarTipoTrabajo) {
        this.buscarTipoTrabajo = buscarTipoTrabajo;
    }

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    public String getSeleccionTrabajoGeneral() {
        return seleccionTrabajoGeneral;
    }

    public void setSeleccionTrabajoGeneral(String seleccionTrabajoGeneral) {
        this.seleccionTrabajoGeneral = seleccionTrabajoGeneral;
    }

    public CortezPosibles getCorteSeleccionadoComboGeneral() {
        return corteSeleccionadoComboGeneral;
    }

    public void setCorteSeleccionadoComboGeneral(CortezPosibles corteSeleccionadoComboGeneral) throws IOException {
        fotoGeneral = new AImage("fotoGeneral", corteSeleccionadoComboGeneral.getCortImagen());
        this.corteSeleccionadoComboGeneral = corteSeleccionadoComboGeneral;
    }

    public DescripcionPedido getDescripcionPedido() {
        return descripcionPedido;
    }

    public void setDescripcionPedido(DescripcionPedido descripcionPedido) {
        this.descripcionPedido = descripcionPedido;
    }

    public ListModelList<DescripcionPedido> getListaDescripcionPedido() {
        return listaDescripcionPedido;
    }

    public void setListaDescripcionPedido(ListModelList<DescripcionPedido> listaDescripcionPedido) {
        this.listaDescripcionPedido = listaDescripcionPedido;
    }

    public List<DescripcionPedido> getLstDEscripcionPedidoGeneral() {
        return lstDEscripcionPedidoGeneral;
    }

    public void setLstDEscripcionPedidoGeneral(List<DescripcionPedido> lstDEscripcionPedidoGeneral) {
        this.lstDEscripcionPedidoGeneral = lstDEscripcionPedidoGeneral;
    }

    public Set<DescripcionPedido> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<DescripcionPedido> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    public List<Materiales> getListaMaterialOtrosGeneral() {
        return listaMaterialOtrosGeneral;
    }

    public void setListaMaterialOtrosGeneral(List<Materiales> listaMaterialOtrosGeneral) {
        this.listaMaterialOtrosGeneral = listaMaterialOtrosGeneral;
    }

    public Materiales getMaterialOtrosCombo() {
        return materialOtrosCombo;
    }

    public void setMaterialOtrosCombo(Materiales materialOtrosCombo) {
        this.materialOtrosCombo = materialOtrosCombo;
    }

    public String getMatTipoCobro() {
        return matTipoCobro;
    }

    public void setMatTipoCobro(String matTipoCobro) {
        this.matTipoCobro = matTipoCobro;
    }

    public List<Cabida> getListaCabidasBuscadasGeneral() {
        return listaCabidasBuscadasGeneral;
    }

    public void setListaCabidasBuscadasGeneral(List<Cabida> listaCabidasBuscadasGeneral) {
        this.listaCabidasBuscadasGeneral = listaCabidasBuscadasGeneral;
    }

    public ManoDeObra getManoDeObraCombo() {
        return manoDeObraCombo;
    }

    public void setManoDeObraCombo(ManoDeObra manoDeObraCombo) {
        this.manoDeObraCombo = manoDeObraCombo;
    }

    public String getTipoCobroManoObra() {
        return tipoCobroManoObra;
    }

    public void setTipoCobroManoObra(String tipoCobroManoObra) {
        this.tipoCobroManoObra = tipoCobroManoObra;
    }

    public BigDecimal getValorTotalMaterialGeneral() {
        return valorTotalMaterialGeneral;
    }

    public void setValorTotalMaterialGeneral(BigDecimal valorTotalMaterialGeneral) {
        this.valorTotalMaterialGeneral = valorTotalMaterialGeneral;
    }

    public BigDecimal getHorasManoObraGeneran() {
        return horasManoObraGeneran;
    }

    public void setHorasManoObraGeneran(BigDecimal horasManoObraGeneran) {
        this.horasManoObraGeneran = horasManoObraGeneran;
    }

    public boolean isMostrarHorasMOGeneral() {
        return mostrarHorasMOGeneral;
    }

    public void setMostrarHorasMOGeneral(boolean mostrarHorasMOGeneral) {
        this.mostrarHorasMOGeneral = mostrarHorasMOGeneral;
    }

    public ListModelList<DescripcionPedido> getListaDescripcionPedidoVarios() {
        return listaDescripcionPedidoVarios;
    }

    public void setListaDescripcionPedidoVarios(ListModelList<DescripcionPedido> listaDescripcionPedidoVarios) {
        this.listaDescripcionPedidoVarios = listaDescripcionPedidoVarios;
    }

    public List<DescripcionPedido> getLstDEscripcionPedidoGeneralVarios() {
        return lstDEscripcionPedidoGeneralVarios;
    }

    public void setLstDEscripcionPedidoGeneralVarios(List<DescripcionPedido> lstDEscripcionPedidoGeneralVarios) {
        this.lstDEscripcionPedidoGeneralVarios = lstDEscripcionPedidoGeneralVarios;
    }

    public BigDecimal getValorTotalMaterialGeneralVarios() {
        return valorTotalMaterialGeneralVarios;
    }

    public void setValorTotalMaterialGeneralVarios(BigDecimal valorTotalMaterialGeneralVarios) {
        this.valorTotalMaterialGeneralVarios = valorTotalMaterialGeneralVarios;
    }

    public Set<DescripcionPedido> getRegistrosSeleccionadosVarios() {
        return registrosSeleccionadosVarios;
    }

    public void setRegistrosSeleccionadosVarios(Set<DescripcionPedido> registrosSeleccionadosVarios) {
        this.registrosSeleccionadosVarios = registrosSeleccionadosVarios;
    }

    public ListModelList<DescripcionPedido> getListaDescripcionPedidoPrePost() {
        return listaDescripcionPedidoPrePost;
    }

    public void setListaDescripcionPedidoPrePost(ListModelList<DescripcionPedido> listaDescripcionPedidoPrePost) {
        this.listaDescripcionPedidoPrePost = listaDescripcionPedidoPrePost;
    }

    public BigDecimal getValorTotalManoObraGeneralPrePost() {
        return valorTotalManoObraGeneralPrePost;
    }

    public void setValorTotalManoObraGeneralPrePost(BigDecimal valorTotalManoObraGeneralPrePost) {
        this.valorTotalManoObraGeneralPrePost = valorTotalManoObraGeneralPrePost;
    }

    public Set<DescripcionPedido> getRegistrosSeleccionadosPrePost() {
        return registrosSeleccionadosPrePost;
    }

    public void setRegistrosSeleccionadosPrePost(Set<DescripcionPedido> registrosSeleccionadosPrePost) {
        this.registrosSeleccionadosPrePost = registrosSeleccionadosPrePost;
    }

    public List<ManoDeObra> getListaManoObraPrePost() {
        return listaManoObraPrePost;
    }

    public void setListaManoObraPrePost(List<ManoDeObra> listaManoObraPrePost) {
        this.listaManoObraPrePost = listaManoObraPrePost;
    }

    public ManoDeObra getManoDeObraComboPrePost() {
        return manoDeObraComboPrePost;
    }

    public void setManoDeObraComboPrePost(ManoDeObra manoDeObraComboPrePost) {
        this.manoDeObraComboPrePost = manoDeObraComboPrePost;
    }

    public List<DescripcionPedido> getLstDescripcionPedidoGeneralPrePost() {
        return lstDescripcionPedidoGeneralPrePost;
    }

    public void setLstDescripcionPedidoGeneralPrePost(List<DescripcionPedido> lstDescripcionPedidoGeneralPrePost) {
        this.lstDescripcionPedidoGeneralPrePost = lstDescripcionPedidoGeneralPrePost;
    }

    public String getTipoCobroManoObraPrePost() {
        return tipoCobroManoObraPrePost;
    }

    public void setTipoCobroManoObraPrePost(String tipoCobroManoObraPrePost) {
        this.tipoCobroManoObraPrePost = tipoCobroManoObraPrePost;
    }

    public BigDecimal getHorasManoObraGeneranPrePost() {
        return horasManoObraGeneranPrePost;
    }

    public void setHorasManoObraGeneranPrePost(BigDecimal horasManoObraGeneranPrePost) {
        this.horasManoObraGeneranPrePost = horasManoObraGeneranPrePost;
    }

    public ListModelList<DescripcionPedido> getListaDescripcionPedidoTercerizado() {
        return listaDescripcionPedidoTercerizado;
    }

    public void setListaDescripcionPedidoTercerizado(ListModelList<DescripcionPedido> listaDescripcionPedidoTercerizado) {
        this.listaDescripcionPedidoTercerizado = listaDescripcionPedidoTercerizado;
    }

    public List<DescripcionPedido> getLstDescripcionPedidoGeneralTercerizado() {
        return lstDescripcionPedidoGeneralTercerizado;
    }

    public void setLstDescripcionPedidoGeneralTercerizado(List<DescripcionPedido> lstDescripcionPedidoGeneralTercerizado) {
        this.lstDescripcionPedidoGeneralTercerizado = lstDescripcionPedidoGeneralTercerizado;
    }

    public Set<DescripcionPedido> getRegistrosSeleccionadosTercerizado() {
        return registrosSeleccionadosTercerizado;
    }

    public void setRegistrosSeleccionadosTercerizado(Set<DescripcionPedido> registrosSeleccionadosTercerizado) {
        this.registrosSeleccionadosTercerizado = registrosSeleccionadosTercerizado;
    }

    public BigDecimal getValorTotalMaterialGeneralTercerizado() {
        return valorTotalMaterialGeneralTercerizado;
    }

    public void setValorTotalMaterialGeneralTercerizado(BigDecimal valorTotalMaterialGeneralTercerizado) {
        this.valorTotalMaterialGeneralTercerizado = valorTotalMaterialGeneralTercerizado;
    }

    public BigDecimal getValorSubtotalTotalCotziacionGeneral() {
        return valorSubtotalTotalCotziacionGeneral;
    }

    public void setValorSubtotalTotalCotziacionGeneral(BigDecimal valorSubtotalTotalCotziacionGeneral) {
        this.valorSubtotalTotalCotziacionGeneral = valorSubtotalTotalCotziacionGeneral;
    }

    public BigDecimal getPorcentajeUtilidadGeneral() {
        return porcentajeUtilidadGeneral;
    }

    public void setPorcentajeUtilidadGeneral(BigDecimal porcentajeUtilidadGeneral) {
        this.porcentajeUtilidadGeneral = porcentajeUtilidadGeneral;
    }

    public BigDecimal getValorTotalCotizaciónGeneralConUtilidad() {
        return valorTotalCotizaciónGeneralConUtilidad;
    }

    public void setValorTotalCotizaciónGeneralConUtilidad(BigDecimal valorTotalCotizaciónGeneralConUtilidad) {
        this.valorTotalCotizaciónGeneralConUtilidad = valorTotalCotizaciónGeneralConUtilidad;
    }

    public BigDecimal getValorGananciaGeneral() {
        return valorGananciaGeneral;
    }

    public void setValorGananciaGeneral(BigDecimal valorGananciaGeneral) {
        this.valorGananciaGeneral = valorGananciaGeneral;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getSubtotalManoObraGenral() {
        return subtotalManoObraGenral;
    }

    public void setSubtotalManoObraGenral(BigDecimal subtotalManoObraGenral) {
        this.subtotalManoObraGenral = subtotalManoObraGenral;
    }

    public BigDecimal getSubtotalManoObraGenralPrePost() {
        return subtotalManoObraGenralPrePost;
    }

    public void setSubtotalManoObraGenralPrePost(BigDecimal subtotalManoObraGenralPrePost) {
        this.subtotalManoObraGenralPrePost = subtotalManoObraGenralPrePost;
    }

    public BigDecimal getSubtotalMateriales() {
        return subtotalMateriales;
    }

    public void setSubtotalMateriales(BigDecimal subtotalMateriales) {
        this.subtotalMateriales = subtotalMateriales;
    }

    public BigDecimal getSubtotalTercerizados() {
        return subtotalTercerizados;
    }

    public void setSubtotalTercerizados(BigDecimal subtotalTercerizados) {
        this.subtotalTercerizados = subtotalTercerizados;
    }

    public BigDecimal getNumeroPlacas() {
        return numeroPlacas;
    }

    public void setNumeroPlacas(BigDecimal numeroPlacas) {
        this.numeroPlacas = numeroPlacas;
    }

    public BigDecimal getSubtotalPlacas() {
        return subtotalPlacas;
    }

    public void setSubtotalPlacas(BigDecimal subtotalPlacas) {
        this.subtotalPlacas = subtotalPlacas;
    }

    public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

    public String getNumeroCotizacionGeneral() {
        return numeroCotizacionGeneral;
    }

    public void setNumeroCotizacionGeneral(String numeroCotizacionGeneral) {
        this.numeroCotizacionGeneral = numeroCotizacionGeneral;
    }

    public BigDecimal getSubtotalMateriaPrima() {
        return subtotalMateriaPrima;
    }

    public void setSubtotalMateriaPrima(BigDecimal subtotalMateriaPrima) {
        this.subtotalMateriaPrima = subtotalMateriaPrima;
    }

    public BigDecimal getTotalManoObra() {
        return totalManoObra;
    }

    public void setTotalManoObra(BigDecimal totalManoObra) {
        this.totalManoObra = totalManoObra;
    }

    public BigDecimal getTotalCostos() {
        return totalCostos;
    }

    public void setTotalCostos(BigDecimal totalCostos) {
        this.totalCostos = totalCostos;
    }

    public BigDecimal getPorcentajeContribucion() {
        return porcentajeContribucion;
    }

    public void setPorcentajeContribucion(BigDecimal porcentajeContribucion) {
        this.porcentajeContribucion = porcentajeContribucion;
    }

    public BigDecimal getPorcentajeCosto() {
        return porcentajeCosto;
    }

    public void setPorcentajeCosto(BigDecimal porcentajeCosto) {
        this.porcentajeCosto = porcentajeCosto;
    }

    public List<DetalleCotizacion> getDetalleCotizacion() {
        return detalleCotizacion;
    }

    public void setDetalleCotizacion(List<DetalleCotizacion> detalleCotizacion) {
        this.detalleCotizacion = detalleCotizacion;
    }

    public Cotizacion getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(Cotizacion encabezado) {
        this.encabezado = encabezado;
    }

    public OrdenDeProduccion getOrdenDeProduccion() {
        return ordenDeProduccion;
    }

    public void setOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion) {
        this.ordenDeProduccion = ordenDeProduccion;
    }

    public String getTipoGuardado() {
        return tipoGuardado;
    }

    public void setTipoGuardado(String tipoGuardado) {
        this.tipoGuardado = tipoGuardado;
    }

    public BigDecimal getDetcostoPliegos() {
        return detcostoPliegos;
    }

    public void setDetcostoPliegos(BigDecimal detcostoPliegos) {
        this.detcostoPliegos = detcostoPliegos;
    }

    public BigDecimal getDetcostoPacas() {
        return detcostoPacas;
    }

    public void setDetcostoPacas(BigDecimal detcostoPacas) {
        this.detcostoPacas = detcostoPacas;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getCantidadProSolicitado() {
        return cantidadProSolicitado;
    }

    public void setCantidadProSolicitado(BigDecimal cantidadProSolicitado) {
        this.cantidadProSolicitado = cantidadProSolicitado;
    }

    public Cotizacion getCotizacionRecuperada() {
        return cotizacionRecuperada;
    }

    public void setCotizacionRecuperada(Cotizacion cotizacionRecuperada) {
        this.cotizacionRecuperada = cotizacionRecuperada;
    }

    public BigDecimal getPorcentajeContrib() {
        return porcentajeContrib;
    }

    public void setPorcentajeContrib(BigDecimal porcentajeContrib) {
        this.porcentajeContrib = porcentajeContrib;
    }

    public String getDetalleTrabajo() {
        return detalleTrabajo;
    }

    public void setDetalleTrabajo(String detalleTrabajo) {
        this.detalleTrabajo = detalleTrabajo;
    }

    public String getTipoCotizacion() {
        return tipoCotizacion;
    }

    public void setTipoCotizacion(String tipoCotizacion) {
        this.tipoCotizacion = tipoCotizacion;
    }

    //metodos de busqueda del cliente
    //busquedas de cliente
    private void consultarRazonLike(String valor) {
        listaClientesAll = servicioCliente.FindALlRazonSocialLike(valor);
    }

    private void cosultarClientesALl() {
        listaClientesAll = servicioCliente.FindALlCliente();
    }

    private void consultarNombreComercialLike(String valor) {
        listaClientesAll = servicioCliente.FindALlNombreComercialLike(valor);
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
        windowClienteBuscarDigital.detach();

    }

    @Command
    @NotifyChange({"listaClientesAll", "clienteBuscado", "fechaEmision"})
    public void buscarClienteEnLista() {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("numcotizacion", "buscarCliente");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/buscarClienteDigital.zul", null, null);
        window.doModal();
        buscarClientePorCedula();
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

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        contador = contador;
    }

    public String getCotObservacion() {
        return cotObservacion;
    }

    public void setCotObservacion(String cotObservacion) {
        this.cotObservacion = cotObservacion;
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
    @NotifyChange({"listaTipoTrabajo", "busTipoTrabajo"})
    public void buscarTipoTrabajo(@BindingParam("valor") String valor) {
        busTipoTrabajo = valor;
        consultarTipoTrabaLike(valor);
    }

    public String getBusTipoTrabajo() {
        return busTipoTrabajo;
    }

    public void setBusTipoTrabajo(String busTipoTrabajo) {
        this.busTipoTrabajo = busTipoTrabajo;
    }
}
