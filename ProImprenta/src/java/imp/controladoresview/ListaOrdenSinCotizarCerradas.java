/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cliente;
import imp.entidades.Cotizacion;
import imp.entidades.EstadoCotizacion;
import imp.entidades.EstadoOrdenProduccion;
import imp.entidades.OrdenDeProduccion;
import imp.entidades.OrdenSinCotizacion;
import imp.seguridad.EnumSesion;
import imp.seguridad.UserCredential;
import imp.servicios.HelperPersistencia;
import imp.servicios.ServicioCotizacion;
import imp.servicios.ServicioEstadoCotizacion;
import imp.servicios.ServicioEstadoOrden;
import imp.servicios.ServicioOrden;
import imp.servicios.ServicioOrdenSinCotizar;
import imp.utilitario.ConexionReportes;
import java.io.ByteArrayInputStream;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class ListaOrdenSinCotizarCerradas {

    private String busCliente = "";
    private String nombreComercial = "";
    private String numeroFactura = "";
    private Integer busNumero = 0;
    ServicioCotizacion servicioCotizacion = new ServicioCotizacion();
    List<OrdenDeProduccion> listaOrdenProduccion = new ArrayList<OrdenDeProduccion>();
    ServicioEstadoOrden servicioEstadoOrden = new ServicioEstadoOrden();
    ServicioOrden servicioOrden = new ServicioOrden();
    ServicioEstadoCotizacion servicioEstadoCotizacion = new ServicioEstadoCotizacion();
    AMedia fileContent = null;
    Connection con = null;
    //ordenes sin cotizar
    private ListModelList<OrdenSinCotizacion> listaOrdenSinCotizacionModel;
    private Set<OrdenSinCotizacion> registrosSeleccionados = new HashSet<OrdenSinCotizacion>();
    List<OrdenSinCotizacion> listaOrdenSinCotizacion = new ArrayList<OrdenSinCotizacion>();
    ServicioOrdenSinCotizar servicioOrdenSinCotizar = new ServicioOrdenSinCotizar();

    public ListaOrdenSinCotizarCerradas() {
        findAllOrdenSinCotizar();
        getOrdeneSinCotizarCerradas();
    }

    private void getOrdeneSinCotizarCerradas() {
        setListaOrdenSinCotizacionModel(new ListModelList<OrdenSinCotizacion>(getListaOrdenSinCotizacion()));
        ((ListModelList<OrdenSinCotizacion>) listaOrdenSinCotizacionModel).setMultiple(true);
    }

    @Command
    public void registrosSeleccionados() {
        registrosSeleccionados = ((ListModelList<OrdenSinCotizacion>) getListaOrdenSinCotizacionModel()).getSelection();
    }

    @Command
    @NotifyChange({"listaCotizaciones", "listaOrdenSinCotizacionModel"})
    public void unificarCotizacion() {

        try {
            if (registrosSeleccionados.size() > 0) {
                if (Messagebox.show("¿Se facturara " + registrosSeleccionados.size() + " registros ?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION) == Messagebox.YES) {
//                SeqFac sec = servicioSecuencial.nuevaSecuencia();
//                System.out.println("secuencial en la vista " + sec.getIdSec());
                    for (OrdenSinCotizacion item : registrosSeleccionados) {
//                    item.setCotUnificar("S-F-" + String.valueOf(sec.getIdSec()));
                        item.setSinEstado("FACTURADO");
                        item.setSinFechaFacturacion(new Date());
                        servicioOrdenSinCotizar.modificar(item);
                        buscarListaPorCliente();
                    }
                    Clients.showNotification("Cotizacion(es) facturada(s) correctamente", "Error", null, "end_center", 4000, true);
//                    Messagebox.show("Cotizacion(es) facturada(s) correctamente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
                }

            } else {
                Messagebox.show("Verifique que ha seleccionado un registro registros", "Atención", Messagebox.OK, Messagebox.ERROR);
            }

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    public void reporteOrdenSinCotizar(@BindingParam("valor") OrdenSinCotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

        EntityManager emf = HelperPersistencia.getEMF();
        emf.getTransaction().begin();
        try {

            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";

            reportPath = reportFile + "/ordensincot.jasper";

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numorden", valor.getSinNumero());

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
            //con.close();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();

            }
        }
    }

    @Command
    public void reporteGeneralTercerizado(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

        EntityManager emf = HelperPersistencia.getEMF();
        emf.getTransaction().begin();
        try {

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

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();

            }
        }
    }

    @Command
    public void reporteGeneralCotizacion(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();
        emf.getTransaction().begin();
        try {

            con = emf.unwrap(Connection.class);
            if (valor.getCotTipoCotizacion().equals("CIGI")) {
                //con = ConexionReportes.Conexion.conexion();
                //con = conexionReportes.conexion();

                String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                        .getRealPath("/reportes");
                String reportPath = "";
                reportPath = reportFile + "/cotGiganto.jasper";

                Map<String, Object> parametros = new HashMap<String, Object>();

                //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
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
                //con.close();

            } else if (valor.getCotTipoCotizacion().equals("CIDI")) {
                // con = ConexionReportes.Conexion.conexion();
                //con = conexionReportes.conexion();

                String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                        .getRealPath("/reportes");
                String reportPath = "";
                reportPath = reportFile + "/cotDigital.jasper";

                Map<String, Object> parametros = new HashMap<String, Object>();

                //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
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
                // con.close();
            } else {
                //con = ConexionReportes.Conexion.conexion();
                //con = conexionReportes.conexion();

                String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                        .getRealPath("/reportes");
                String reportPath = "";
                reportPath = reportFile + "/cotGeneral.jasper";

                Map<String, Object> parametros = new HashMap<String, Object>();

                //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
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
                //con.close();

            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
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
        } else if (valor.getCotTipoCotizacion().equals("CIDI")) {
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
            window.detach();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    public void modificarOden(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            final HashMap<String, Cotizacion> map = new HashMap<String, Cotizacion>();

            map.put("cotizacion", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/actualizarOrden.zul", null, map);
            window.doModal();

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion", "listaOrdenSinCotizacionModel"})
    public void buscarListaPorCliente() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            buscarForCliente();
            getOrdeneSinCotizarCerradas();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenProduccion", "listaOrdenSinCotizacionModel"})
    public void buscarListaOrdenesAll() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaOrdenPendientes();
            getOrdeneSinCotizarCerradas();

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion", "listaOrdenSinCotizacionModel"})
    public void buscarListaPorNumero() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            buscarForNumero();
            getOrdeneSinCotizarCerradas();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void consultarListaOrdenPendientes() {
        listaOrdenProduccion = servicioOrden.listaOrdenProduccionCerradas();
    }

    private void consultarListaCotizacionPorCliente() {
        listaOrdenProduccion = servicioOrden.listaOrdenProduccionCerradasCliente(busCliente);
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

    public Integer getBusNumero() {
        return busNumero;
    }

    public void setBusNumero(Integer busNumero) {
        this.busNumero = busNumero;
    }

    @Command
    @NotifyChange({"listaOrdenProduccion"})
    public void finalizarOrden(@BindingParam("valor") OrdenDeProduccion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            if (valor.getOrdResponsable() != null) {
                if (Messagebox.show("¿Desea finalizar la orden de producción?", "Atención",
                        Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
//                valor.setCodEstCotizacion(servicioEstadoCotizacion.FindALlEstadoSigla(busNumero));
//                servicioCotizacion.modificar(valor);
                    OrdenDeProduccion ord = servicioOrden.listaCotizacionNumeroCot(valor.getCodCotizacion().getCotNumero());
                    System.out.println("ORDEN RECUPEERADA " + ord.getOrdNumero());
                    EstadoOrdenProduccion estOrd = servicioEstadoOrden.FindALlEstadoLikeSigla("F");
                    EstadoCotizacion estCot = servicioEstadoCotizacion.FindALlEstadoSigla("T");
                    System.out.println("estdo RECUPEERADA " + estOrd.getEstOrdNombre());
                    ord.setCodEstadoOrden(estOrd);
                    ord.setOrdFechaCierre(new Date());
                    ord.setOrdHoraCerre(new Date());
                    servicioOrden.modificar(ord);
                    Cotizacion cotizacion = valor.getCodCotizacion();
                    cotizacion.setCodEstCotizacion(estCot);
                    servicioCotizacion.modificar(cotizacion);
                    consultarListaOrdenPendientes();

                }
            } else {
                Messagebox.show("Debe ingresar un Responsable", "Atención", Messagebox.OK, Messagebox.ERROR);
            }
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public List<OrdenDeProduccion> getListaOrdenProduccion() {
        return listaOrdenProduccion;
    }

    public void setListaOrdenProduccion(List<OrdenDeProduccion> listaOrdenProduccion) {
        this.listaOrdenProduccion = listaOrdenProduccion;
    }

    @Command
    @NotifyChange({"orden"})
    public void verAdjuntoSinCotizar(@BindingParam("valor") OrdenSinCotizacion valor) {
        if (valor.getSinPdf() != null) {
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
            //para pasar al visor
            map.put("pdf", valor.getFileContent());
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/ContenedorReporte.zul", null, map);
            window.doModal();
        } else {
            Messagebox.show("No registro un documento PDF", "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"orden"})
    public void VerImagenSinCotizar(@BindingParam("valor") OrdenSinCotizacion valor) {
        final HashMap<String, OrdenSinCotizacion> map = new HashMap<String, OrdenSinCotizacion>();
        //para pasar al visor
        map.put("img", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/visorImagenOrdenSinCotizar.zul", null, map);
        window.doModal();
    }

    public List<OrdenSinCotizacion> getListaOrdenSinCotizacion() {
        return listaOrdenSinCotizacion;
    }

    public void setListaOrdenSinCotizacion(List<OrdenSinCotizacion> listaOrdenSinCotizacion) {
        this.listaOrdenSinCotizacion = listaOrdenSinCotizacion;
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarOrdenesSinCotizar() {
        findAllOrdenSinCotizar();
    }

    @Command
    public void modificarOdenSinCotizar(@BindingParam("valor") OrdenSinCotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
//            
            final HashMap<String, OrdenSinCotizacion> map = new HashMap<String, OrdenSinCotizacion>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/actualizarOrdenSinCotizar.zul", null, map);
            window.doModal();

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void findAllOrdenSinCotizar() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllCerradas();
    }

    private void buscarForCliente() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindClienteCerradas(busCliente);
    }

    private void buscarForNumero() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllNumeroCerrada(busNumero);
    }

    public ListModelList<OrdenSinCotizacion> getListaOrdenSinCotizacionModel() {
        return listaOrdenSinCotizacionModel;
    }

    public void setListaOrdenSinCotizacionModel(ListModelList<OrdenSinCotizacion> listaOrdenSinCotizacionModel) {
        this.listaOrdenSinCotizacionModel = listaOrdenSinCotizacionModel;
    }

    public Set<OrdenSinCotizacion> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<OrdenSinCotizacion> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    @Command
    @NotifyChange({"listaCotizaciones", "listaOrdenSinCotizacionModel"})
    public void finalizarOrdenSinCotizar(@BindingParam("valor") OrdenSinCotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            if (Messagebox.show("¿Desea elimnar la orden ?", "Atención",
                    Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                Session sess = Sessions.getCurrent();
                UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
                valor.setSinResponsable(cre.getUsuarioSistema().getUsuNombreUsuario());

                valor.setSinEstado("ELIMINADA");
                valor.setSinFechaCierre(new Date());
                valor.setSinHoraCierre(new Date());

                servicioOrdenSinCotizar.modificar(valor);

                buscarListaPorCliente();
                Clients.showNotification("Elimnado con éxito",
                        "Info", null, "end_center", 3000, true);

            } else {
            }
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();

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
    @NotifyChange({"listaOrdenSinCotizacionModel"})
    public void buscarListaPorFechas() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            buscarForFecha();
            getOrdeneSinCotizarCerradas();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacionModel"})
    public void buscarListaPorNombreFechas() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            buscarForNombreFecha();
            getOrdeneSinCotizarCerradas();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void buscarForFecha() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindFechasCerrada(fechaInicio, fechaFin);
    }

    private void buscarForNombreFecha() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.findAllNombreFechasFacturadaCerrada(fechaInicio, fechaFin, busCliente);
    }

    //cotizadas
    @Command
    @NotifyChange({"listaOrdenSinCotizacionModel"})
    public void ordenarForNombre() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Collections.sort(listaOrdenSinCotizacionModel, new Comparator() {
            public int compare(Object o1, Object o2) {
                OrdenSinCotizacion p1 = (OrdenSinCotizacion) o1;
                OrdenSinCotizacion p2 = (OrdenSinCotizacion) o2;
                return new String(p1.getCodCliente().getRazonSocial()).compareTo(new String(p2.getCodCliente().getRazonSocial()));
            }
        });
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacionModel"})
    public void ordenarForNumero() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Collections.sort(listaOrdenSinCotizacionModel, new Comparator() {
            public int compare(Object o1, Object o2) {
                OrdenSinCotizacion p1 = (OrdenSinCotizacion) o1;
                OrdenSinCotizacion p2 = (OrdenSinCotizacion) o2;
                return new Integer(p1.getSinNumero()).compareTo(new Integer(p2.getSinNumero()));
            }
        });
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarPorNumeroFactura() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            findBySinNumeroFacturaFinalizada();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void findBySinNumeroFacturaFinalizada() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.findBySinNumeroFacturaFinalizada(numeroFactura);
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion", "listaOrdenSinCotizacionModel"})
    public void buscarListaPorNombreComercial() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            findAllCerradanombreComercial();
            getOrdeneSinCotizarCerradas();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void findAllCerradanombreComercial() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.findAllCerradanombreComercial(nombreComercial);
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    /*ver la informacion del cliente*/
    @Command
    public void verCliente(@BindingParam("valor") OrdenSinCotizacion cliente) {

        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
        map.put("cliente", cliente.getCodCliente());
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/verCliente.zul", null, map);
        window.doModal();
    }

    @Command
    public void historial(@BindingParam("valor") OrdenSinCotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        final HashMap<String, OrdenSinCotizacion> map = new HashMap<String, OrdenSinCotizacion>();

        map.put("parametro", valor);

        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/histordesincot.zul", null, map);
        window.doModal();

    }

}
