/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cotizacion;
import imp.entidades.EstadoCotizacion;
import imp.entidades.EstadoOrdenProduccion;
import imp.entidades.OrdenDeProduccion;
import  imp.servicios.HelperPersistencia;
import  imp.servicios.ServicioCotizacion;
import  imp.servicios.ServicioEstadoCotizacion;
import  imp.servicios.ServicioEstadoOrden;
import  imp.servicios.ServicioOrden;
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
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class ListaOrdenesCerradas {

    private String busCliente = "";
    private String busNumero = "";
    ServicioCotizacion servicioCotizacion = new ServicioCotizacion();
    List<Cotizacion> listaCotizaciones = new ArrayList<Cotizacion>();
    List<OrdenDeProduccion> listaOrdenProduccion = new ArrayList<OrdenDeProduccion>();
    ServicioEstadoOrden servicioEstadoOrden = new ServicioEstadoOrden();
    ServicioOrden servicioOrden = new ServicioOrden();
    ServicioEstadoCotizacion servicioEstadoCotizacion = new ServicioEstadoCotizacion();
    AMedia fileContent = null;
    Connection con = null;

    public ListaOrdenesCerradas() {
        consultarListaOrdenPendientes();
    }

    @Command
    public void reporteGeneral(@BindingParam("valor") OrdenDeProduccion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

        EntityManager emf = HelperPersistencia.getEMF();
        emf.getTransaction().begin();
        try {

            con = emf.unwrap(Connection.class);


            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            if (valor.getCodCotizacion().getCotTipoCotizacion().equals("CIGI")) {
                reportPath = reportFile + "/ordenGiganto.jasper";
            } else if (valor.getCodCotizacion().getCotTipoCotizacion().equals("CIDIG")) {
                reportPath = reportFile + "/ordenDig.jasper";
            } else if (valor.getCodCotizacion().getCotTipoCotizacion().equals("CIDIC")) {
                reportPath = reportFile + "/ordenDigCom.jasper";
            } else {
                reportPath = reportFile + "/ordenpro.jasper";
            }




            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numorden", valor.getCodCotizacion().getCotNumero());


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
            ////con.close();
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
            ////con.close();
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
                //con = ConexionReportes.Conexion.conexion();
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
                //con.close();
            } else {
                //  con = ConexionReportes.Conexion.conexion();
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
            Clients.showNotification("Eliminado con éxito",
                    "Info", null, "end_center", 3000, true);
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
    @NotifyChange({"listaOrdenProduccion"})
    public void buscarListaPorCliente() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaCotizacionPorCliente();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenProduccion"})
    public void buscarListaOrdenesAll() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaOrdenPendientes();

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenProduccion"})
    public void buscarListaPorNumero() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaCotizacion();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void consultarListaOrdenPendientes() {
        listaOrdenProduccion = servicioOrden.listaOrdenProduccionCerradas();
    }

    private void consultarListaCotizacion() {
        listaOrdenProduccion = servicioOrden.listaOrdenCerradasNumero(busNumero);
    }

    private void consultarListaCotizacionPorCliente() {
        listaOrdenProduccion = servicioOrden.listaOrdenProduccionCerradasCliente(busCliente);
    }

    public List<Cotizacion> getListaCotizaciones() {
        return listaCotizaciones;
    }

    public void setListaCotizaciones(List<Cotizacion> listaCotizaciones) {
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
    public void verAdjunto(@BindingParam("valor") OrdenDeProduccion valor) {
        if (valor.getOrdPdf() != null) {
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
    public void VerImagen(@BindingParam("valor") OrdenDeProduccion valor) {
        final HashMap<String, OrdenDeProduccion> map = new HashMap<String, OrdenDeProduccion>();
        //para pasar al visor
        map.put("img", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/visorImagen.zul", null, map);
        window.doModal();
    }

    //cotizadas
    @Command
    @NotifyChange({"listaOrdenProduccion"})
    public void ordenarForNombreCot() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Collections.sort(listaOrdenProduccion, new Comparator() {
            public int compare(Object o1, Object o2) {
                OrdenDeProduccion p1 = (OrdenDeProduccion) o1;
                OrdenDeProduccion p2 = (OrdenDeProduccion) o2;
                return new String(p1.getCodCotizacion().getCodCliente().getNombreComercial()).compareTo(new String(p2.getCodCotizacion().getCodCliente().getNombreComercial()));
            }
        });
    }

    @Command
    @NotifyChange({"listaOrdenProduccion"})
    public void ordenarForNumeroCot() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Collections.sort(listaOrdenProduccion, new Comparator() {
            public int compare(Object o1, Object o2) {
                OrdenDeProduccion p1 = (OrdenDeProduccion) o1;
                OrdenDeProduccion p2 = (OrdenDeProduccion) o2;
                return new Integer(p1.getOrdSecuencial()).compareTo(new Integer(p2.getOrdSecuencial()));
            }
        });
    }
}
