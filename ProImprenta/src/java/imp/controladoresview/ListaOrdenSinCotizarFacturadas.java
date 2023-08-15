/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cotizacion;
import imp.entidades.EstadoCotizacion;
import imp.entidades.EstadoOrdenProduccion;
import imp.entidades.OrdenDeProduccion;
import imp.entidades.OrdenSinCotizacion;
import  imp.servicios.HelperPersistencia;
import  imp.servicios.ServicioCotizacion;
import  imp.servicios.ServicioEstadoCotizacion;
import  imp.servicios.ServicioEstadoOrden;
import  imp.servicios.ServicioOrden;
import  imp.servicios.ServicioOrdenSinCotizar;
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
public class ListaOrdenSinCotizarFacturadas {

    private String busCliente = "";
    private String numeroFactura = "";
    private String nombreComercial = "";
    private Integer busNumero = 0;
    ServicioCotizacion servicioCotizacion = new ServicioCotizacion();
    List<OrdenDeProduccion> listaOrdenProduccion = new ArrayList<OrdenDeProduccion>();
    ServicioEstadoOrden servicioEstadoOrden = new ServicioEstadoOrden();
    ServicioOrden servicioOrden = new ServicioOrden();
    ServicioEstadoCotizacion servicioEstadoCotizacion = new ServicioEstadoCotizacion();
    AMedia fileContent = null;
    Connection con = null;
    //ordenes sin cotizar
    List<OrdenSinCotizacion> listaOrdenSinCotizacion = new ArrayList<OrdenSinCotizacion>();
    ServicioOrdenSinCotizar servicioOrdenSinCotizar = new ServicioOrdenSinCotizar();
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();

    public ListaOrdenSinCotizarFacturadas() {
        findAllOrdenSinCotizar();
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
                //con.close();
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
    public void modificarCotizacion(@BindingParam("valor") OrdenSinCotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        final HashMap<String, OrdenSinCotizacion> map = new HashMap<String, OrdenSinCotizacion>();

        map.put("ordenSinCotizacion", valor);

        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/ordenSinCotizarEdit.zul", null, map);
        window.doModal();

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
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarListaPorCliente() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            buscarForCliente();
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
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarListaPorNumero() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            buscarForNumero();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarPorNumeroFactura() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            findBySinNumeroFacturaFacturada();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarListaPorFechas() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            buscarForFecha();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarListaPorNombreFechas() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            buscarForNombreFecha();
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

    private void findAllOrdenSinCotizar() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllfacturadas();
    }

    private void findBySinNumeroFacturaFacturada() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.findBySinNumeroFacturaFacturada(numeroFactura);
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

    private void buscarForCliente() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllPendienteClienteFacturadas(busCliente);
    }

    private void buscarForNumero() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllNumeroFacturadas(busNumero);
    }

    private void buscarForFecha() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindFechas(fechaInicio, fechaFin);
    }

    private void buscarForNombreFecha() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.findAllNombreFechasFacturada(fechaInicio, fechaFin, busCliente);
    }

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
    //cotizadas

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void ordenarForNombre() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Collections.sort(listaOrdenSinCotizacion, new Comparator() {
            public int compare(Object o1, Object o2) {
                OrdenSinCotizacion p1 = (OrdenSinCotizacion) o1;
                OrdenSinCotizacion p2 = (OrdenSinCotizacion) o2;
                return new String(p1.getCodCliente().getRazonSocial()).compareTo(new String(p2.getCodCliente().getRazonSocial()));
            }
        });
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void ordenarForNumero() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Collections.sort(listaOrdenSinCotizacion, new Comparator() {
            public int compare(Object o1, Object o2) {
                OrdenSinCotizacion p1 = (OrdenSinCotizacion) o1;
                OrdenSinCotizacion p2 = (OrdenSinCotizacion) o2;
                return new Integer(p1.getSinNumero()).compareTo(new Integer(p2.getSinNumero()));
            }
        });
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
            findAllFacturadanombreComercial();
            
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void findAllFacturadanombreComercial() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.findAllFacturadanombreComercial(nombreComercial);
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    
}
