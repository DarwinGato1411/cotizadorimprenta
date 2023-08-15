/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cotizacion;
import imp.entidades.DetalleCotizacion;
import imp.entidades.EstadoCotizacion;
import imp.entidades.OrdenDeProduccion;
import imp.entidades.SeqFac;
import  imp.servicios.HelperPersistencia;
import  imp.servicios.ServicioCotizacion;
import  imp.servicios.ServicioDetalleCotizacion;
import  imp.servicios.ServicioEstadoCotizacion;
import  imp.servicios.ServicioOrden;
import  imp.servicios.ServicioSecuencial;
import imp.utilitario.ConexionReportes;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.activation.MimetypesFileTypeMap;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.zkoss.zul.Filedownload;

/**
 *
 * @author gato
 */
public class ListaCotizacionesCerradas {

    private String busCliente = "";
    private String busNumero = "";
//    ServicioSecuencia servicioSecuencia = new ServicioSecuencia();
    ServicioCotizacion servicioCotizacion = new ServicioCotizacion();
    ServicioEstadoCotizacion servicioEstadoCotizacion = new ServicioEstadoCotizacion();
    ServicioDetalleCotizacion servicioDetalleCotizacion = new ServicioDetalleCotizacion();
    ServicioSecuencial servicioSecuencial = new ServicioSecuencial();
    ServicioOrden servicioOrden = new ServicioOrden();
    List<Cotizacion> listaCotizacionesData = new ArrayList<Cotizacion>();
    private ListModelList<Cotizacion> listaCotizaciones;
    private Set<Cotizacion> registrosSeleccionados;
    AMedia fileContent = null;
    Connection con = null;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("numcotizacion") String numcotizacion, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        ((ListModelList<Cotizacion>) listaCotizaciones).clear();
        getDescripcionGeneral();
    }

    private void getDescripcionGeneral() {
        setListaCotizaciones(new ListModelList<Cotizacion>(getListaCotizacionesData()));
        ((ListModelList<Cotizacion>) listaCotizaciones).setMultiple(true);
    }

    public ListaCotizacionesCerradas() {

        consultarListaCotizacion();
        getDescripcionGeneral();
    }

    @Command
    public void reporteGeneral(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {


        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);


            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            if (valor.getCotTipoCotizacion().equals("CIGI")) {
                reportPath = reportFile + "/ordenGiganto.jasper";
            } else if (valor.getCotTipoCotizacion().equals("CIDIG")) {
                reportPath = reportFile + "/ordenDig.jasper";
            } else if (valor.getCotTipoCotizacion().equals("CIDIC")) {
                reportPath = reportFile + "/ordenDigCom.jasper";
            } else {
                reportPath = reportFile + "/ordenpro.jasper";
            }




            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numorden", valor.getCotNumero());


            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);


            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte Orden Produccion", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/ContenedorReporte.zul", null, map);
            window.doModal();

        } catch (Exception e) {
            System.out.println("Error del reporte  " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }

        }

    }

    @Command
    public void reporteGeneralTercerizado(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);


            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            reportPath = reportFile + "/tercerizado.jasper";
//            if (valor.getCotTipoCotizacion().equals("CIGI")) {
//                reportPath = reportFile + "/ordenGiganto.jasper";
//            } else if (valor.getCotTipoCotizacion().equals("CIDI")) {
//                reportPath = reportFile + "/ordenpro.jasper";
//            } else {
//                reportPath = reportFile + "/ordenpro.jasper";
//            }




            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numorden", valor.getCotNumero());


            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);


            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte Orden Produccion", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/ContenedorReporte.zul", null, map);
            window.doModal();
            //con.close();
        } catch (Exception e) {
            System.out.println("Error del reporte  " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }

        }
    }

    @Command
    public void reporteGeneralCotizacion(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);
            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";

            if (valor.getCotTipoCotizacion().equals("CIGI")) {

                reportPath = reportFile + "/cotGiganto.jasper";


            } else if (valor.getCotTipoCotizacion().equals("CIC")) {

                reportPath = reportFile + "/cotCompaginado.jasper";

            }
            if (valor.getCotTipoCotizacion().equals("CIDIG")) {

                reportPath = reportFile + "/cotdigen.jasper";

            } else if (valor.getCotTipoCotizacion().equals("CIDIC")) {
                reportPath = reportFile + "/cotdigcom.jasper";
            } else if (valor.getCotTipoCotizacion().equals("CIG")) {
                reportPath = reportFile + "/cotGeneral.jasper";


            } else if (valor.getCotTipoCotizacion().equals("CIGI")) {
                reportPath = reportFile + "/cotGiganto.jasper";

            }
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("numcotizacion", valor.getCotNumero());
            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);


            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte Orden Produccion", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/ContenedorReporte.zul", null, map);
            window.doModal();

        } catch (Exception e) {
            System.out.println("Error del reporte  " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }

        }
    }

    @Command
    public void modificarCotizacion(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {


        final HashMap<String, String> map = new HashMap<String, String>();

        map.put("numcotizacion", valor.getCotNumero());
        if (valor.getCotTipoCotizacion().equals("CIG")) {
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/cotizacionedit.zul", null, map);
            window.doModal();
        } else if (valor.getCotTipoCotizacion().equals("CIC")) {
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/cotizacioneditComp.zul", null, map);
            window.doModal();
        } else if (valor.getCotTipoCotizacion().equals("CIGI")) {
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/cotizacionGigantoEdit.zul", null, map);
            window.doModal();
        } else if (valor.getCotTipoCotizacion().equals("CIDIC") || valor.getCotTipoCotizacion().equals("CIDIG")) {
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/cotizacionDigitalEdit.zul", null, map);
            window.doModal();
        }


    }

    @Command
    public void eliminar(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            servicioCotizacion.eliminar(valor);
            Messagebox.show("Guardado con exito");
        } catch (Exception e) {
            Messagebox.show("Eliminado con exito" + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    public void cambiarEstadoCotizacion(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            final HashMap<String, Cotizacion> map = new HashMap<String, Cotizacion>();

            map.put("cotizacion", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/estadoCotizacion.zul", null, map);
            window.doModal();
//            window.detach();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    public void unificarCotizaciones() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    public void modificarOden(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            System.out.println("encuentra la cotizacion " + servicioOrden.listaCotizacionNumeroCot(valor.getCotNumero()));
            if (servicioOrden.listaCotizacionNumeroCot(valor.getCotNumero()) != null) {
                final HashMap<String, Cotizacion> map = new HashMap<String, Cotizacion>();

                map.put("cotizacion", valor);
                org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/cotizacion/actualizarOrden.zul", null, map);
                window.doModal();
            } else {
                Messagebox.show("Verifique el estado de la cotizacion para generar la orden de producción", "Atención", Messagebox.OK, Messagebox.INFORMATION);
            }

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    public void registrosSeleccionados() {
        registrosSeleccionados = ((ListModelList<Cotizacion>) getListaCotizaciones()).getSelection();
    }

    @Command
    @NotifyChange({"listaCotizaciones"})
    public void unificarCotizacion() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            if (registrosSeleccionados.size() > 0) {
                if (Messagebox.show("¿Se facturara " + registrosSeleccionados.size() + " registros ?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION) == Messagebox.YES) {
                    SeqFac sec = servicioSecuencial.nuevaSecuencia();
                    System.out.println("secuencial en la vista " + sec.getIdSec());
                    for (Cotizacion cotizacion : registrosSeleccionados) {
                        EstadoCotizacion estadoCotizacion = servicioEstadoCotizacion.FindALlEstadoSigla("F");
                        cotizacion.setCodEstCotizacion(estadoCotizacion);
                        cotizacion.setCotUnificar("S-F-" + String.valueOf(sec.getIdSec()));
                        cotizacion.setCotFechaFacturacion(new Date());
                        servicioCotizacion.modificar(cotizacion);
                        buscarListaPorCliente();

                    }
                    Messagebox.show("Cotizacion(es) facturada(s) correctamente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
                }
            } else {
                Messagebox.show("Verifique que ha seleccionado dos o mas registros", "Atención", Messagebox.OK, Messagebox.ERROR);
            }

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaCotizaciones"})
    public void buscarListaPorCliente() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaCotizacionPorCliente();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaCotizaciones"})
    public void buscarListaPorNumero() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaCotizacion();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void consultarListaCotizacion() {
        listaCotizacionesData = servicioCotizacion.listaCotizacionLikeNumeroCerradas(busNumero);
        getDescripcionGeneral();
    }

    private void consultarListaCotizacionPorCliente() {
        listaCotizacionesData = servicioCotizacion.findAllLikeClienteCerradas(busCliente);
        getDescripcionGeneral();
    }

    public List<Cotizacion> getListaCotizacionesData() {
        return listaCotizacionesData;
    }

    public void setListaCotizacionesData(List<Cotizacion> listaCotizacionesData) {
        this.listaCotizacionesData = listaCotizacionesData;
    }

    public ListModelList<Cotizacion> getListaCotizaciones() {
        return listaCotizaciones;
    }

    public void setListaCotizaciones(ListModelList<Cotizacion> listaCotizaciones) {
        this.listaCotizaciones = listaCotizaciones;
    }

    public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

//    @Command
//    public void enviarMail() {
//
//        try {
//
//            MailerClass mailerClass = new MailerClass();
//            mailerClass.sendMail("darwinvinicio14_11@hotmail.com", "IMAGENDIGITAL");
//            Messagebox.show("Envio con exito");
//        } catch (Exception e) {
//            Messagebox.show("Fallo envio" + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
//        }
//    }
    public String getBusCliente() {
        return busCliente;
    }

    public void setBusCliente(String busCliente) {
        this.busCliente = busCliente;
    }

    public String getBusNumero() {
        return busNumero;
    }

    public void setBusNumero(String busNumero) {
        this.busNumero = busNumero;
    }

    public Set<Cotizacion> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<Cotizacion> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }
//    String name, ArrayList<String> key,ArrayList<String> value

    @Command
    public void generateXml(@BindingParam("valor") Cotizacion valor) throws Exception {

        String name = "archivo";


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, "factura", null);
        document.setXmlVersion("1.0");

        //Main Node
        Element raiz = document.getDocumentElement();
        //Por cada key creamos un item que contendrá la key y el value

        //Item Node
        Element itemNode = document.createElement("datos_personales");
        //Key Node
        Element ruc = document.createElement("Ruc");
        Text nodeKeyValue = document.createTextNode(valor.getCodCliente().getRuc());
        ruc.appendChild(nodeKeyValue);
        //Value Node
        Element nom_comercial = document.createElement("Nombre_comercial");
        Text nodeValueValue = document.createTextNode(valor.getCodCliente().getNombreComercial());
        nom_comercial.appendChild(nodeValueValue);
        //Value Node
        Element razon_social = document.createElement("Razon_social");
        Text nodeRazonSocial = document.createTextNode(valor.getCodCliente().getRazonSocial());
        razon_social.appendChild(nodeRazonSocial);
        //Value Node
        Element direccion = document.createElement("Direccion");
        Text nodeDireccion = document.createTextNode(valor.getCodCliente().getDireccion());
        direccion.appendChild(nodeDireccion);
        //Value Node
        Element telefono = document.createElement("Telefono");
        Text nodeTelefono = document.createTextNode(valor.getCodCliente().getTelefono());
        telefono.appendChild(nodeTelefono);
        //Value Node

        //append keyNode and valueNode to itemNode
        itemNode.appendChild(ruc);
        itemNode.appendChild(nom_comercial);
        itemNode.appendChild(razon_social);
        itemNode.appendChild(direccion);
        itemNode.appendChild(telefono);


        //Item Node
        Element itemfactura = document.createElement("datos_factura");
        //Key Node
        Element num_cot = document.createElement("Numero_cotizacion");
        Text nodeNumCot = document.createTextNode(valor.getCotNumero());
        num_cot.appendChild(nodeNumCot);
        //Value Node
        Element cot_val_total = document.createElement("valor_total");
        Text nodeValTot = document.createTextNode(valor.getCotTotal().toString());
        cot_val_total.appendChild(nodeValTot);



        List<DetalleCotizacion> detalleCotizacions = servicioDetalleCotizacion.FindDetalleCotizacionPorCotizacionXml(valor);
        Element itemdetalleFactura = document.createElement("detalle_factura");
        int aux = 0;

        for (DetalleCotizacion item : detalleCotizacions) {
            System.out.println("cotizacion " + valor.getCotNumero() + "  producto" + item.getDetProductoSolicitado());

            if ((!item.getDetProductoSolicitado().equals("")) && !(item.getDetProductoSolicitado().isEmpty()) && aux < 1) {
                //Key Node
                aux++;
                Element detcantidad = document.createElement("cantidad");
                Text nodedetcant = document.createTextNode(item.getDetCantidadSolicitada().toString());
                detcantidad.appendChild(nodedetcant);

                //Key Node
                Element det_solitado = document.createElement("det_solicitado");
                Text nodedetsol = document.createTextNode(item.getDetProductoSolicitado());
                det_solitado.appendChild(nodedetsol);
                //Key Node
                Element det_sub_total = document.createElement("det_sub_total");
                Text nodeSub_total = document.createTextNode(item.getDetSubTotal().toString());
                det_sub_total.appendChild(nodeSub_total);

//                //agrega al detalle de cotizacion
//                itemdetalleFactura.appendChild(detcantidad);
//                itemdetalleFactura.appendChild(det_solitado);
//                itemdetalleFactura.appendChild(det_sub_total);

                //agrega la cotizacion
                itemfactura.appendChild(num_cot);
                itemfactura.appendChild(detcantidad);
                itemfactura.appendChild(det_solitado);
                itemfactura.appendChild(cot_val_total);
            }

        }
        //Value Node
        //append itemNode to raiz
        raiz.appendChild(itemNode);
        raiz.appendChild(itemfactura);
//        raiz.appendChild(itemdetalleFactura);

        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");
        String pathSalida = directorioReportes + File.separator + "archivo.xml";
        //Generate XML
        Source source = new DOMSource(document);
        //Indicamos donde lo queremos almacenar
        System.out.println("Direccion del reporte  " + pathSalida);
        Result result = new StreamResult(pathSalida); //nombre del archivo
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);

        FileInputStream inputStream;

        File dosfile = new File(pathSalida);
        if (dosfile.exists()) {
            inputStream = new FileInputStream(dosfile);
            Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
        } else {
        }
    }
    private Date fechaInicio;
    private Date fechaFin;

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

    @Command
    @NotifyChange({"listaCotizaciones"})
    public void consultarPorRangoFechas() {
        try {
            consultarPorFechas();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }

    }

    private void consultarPorFechas() {
        listaCotizacionesData = servicioCotizacion.listaPorFechasCerradas(fechaInicio, fechaFin);
        getDescripcionGeneral();
    }

    @Command
    @NotifyChange({"orden"})
    public void VerImagen(@BindingParam("valor") Cotizacion valor) {

        try {
            OrdenDeProduccion OrdenDeProduccion = servicioOrden.ordenForCotizacion(valor);
            final HashMap<String, OrdenDeProduccion> map = new HashMap<String, OrdenDeProduccion>();
            //para pasar al visor
            map.put("img", OrdenDeProduccion);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/visorImagen.zul", null, map);
            window.doModal();
        } catch (Exception e) {

            System.out.println("error en la imagen " + e);
        }
    }
    
    //cotizadas
    @Command
    @NotifyChange({"listaCotizaciones"})
    public void ordenarForNombreCot() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Collections.sort(listaCotizaciones, new Comparator() {
            public int compare(Object o1, Object o2) {
                Cotizacion p1 = (Cotizacion) o1;
                Cotizacion p2 = (Cotizacion) o2;
                return new String(p1.getCodCliente().getRazonSocial()).compareTo(new String(p2.getCodCliente().getRazonSocial()));
            }
        });
    }

    @Command
    @NotifyChange({"listaOrdenProduccion"})
    public void ordenarForNumeroCot() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Collections.sort(listaCotizaciones, new Comparator() {
            public int compare(Object o1, Object o2) {
                Cotizacion p1 = (Cotizacion) o1;
                Cotizacion p2 = (Cotizacion) o2;
                return new Integer(p1.getCotNumero()).compareTo(new Integer(p2.getCotNumero()));
            }
        });
    }
}
