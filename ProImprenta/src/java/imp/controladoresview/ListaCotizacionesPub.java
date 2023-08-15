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
import imp.entidades.SolicitarMaterial;
import imp.entidades.UsuarioSistema;
import  imp.servicios.HelperPersistencia;
import  imp.servicios.ServicioCotizacion;
import  imp.servicios.ServicioEstadoCotizacion;
import  imp.servicios.ServicioEstadoOrden;
import  imp.servicios.ServicioOrden;
import  imp.servicios.ServicioOrdenSinCotizar;
import  imp.servicios.ServicioUsuario;
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
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
public class ListaCotizacionesPub {

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
    ServicioUsuario modelo = new ServicioUsuario();
    private Integer numOrdSinCot = 0;
    private Integer numOrdSinCotAux = 0;

    public ListaCotizacionesPub() {
        consultarListaOrdenPendientes();
        findAllOrdenSinCotizar();
        numOrdSinCot = listaOrdenSinCotizacion.size();
        numOrdSinCotAux = numOrdSinCot;
    }

    @Command
    public void reporteGeneral(@BindingParam("valor") OrdenDeProduccion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

        EntityManager emf = HelperPersistencia.getEMF();
        emf.getTransaction().begin();
        try {

            con = emf.unwrap(Connection.class);
            //con = conexionReportes.conexion();


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
            //  con.close();

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
                System.out.println("cerro entity");
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
    public void reporteGeneralCotizacion(@BindingParam("valor") Cotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {

        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            if (valor.getCotTipoCotizacion().equals("CIGI")) {
                // con = ConexionReportes.Conexion.conexion();
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
                // con.close();

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
            System.out.println("Error del reporte " + e.getMessage());
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
    @NotifyChange({"listaOrdenProduccion"})
    public void buscarListaPorCliente() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaCotizacionPorCliente();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenProduccion", "listaOrdenSinCotizacion"})
    public void buscarListaOrdenesAll() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaOrdenPendientes();
            findAllOrdenSinCotizar();
            numOrdSinCot = listaOrdenSinCotizacion.size();

//            if (numOrdSinCot > numOrdSinCotAux) {
//                numOrdSinCotAux = numOrdSinCot;
//                //reproduce el sonido
//                String reportFile = Executions.getCurrent().getDesktop().getWebApp()
//                        .getRealPath("/sonido");
//                String reportPath = "";
//
//                reportPath = reportFile + "/cartoon012.wav";
//
//                Clip sonido = AudioSystem.getClip();
//                File a = new File(reportPath);
//                if (a.exists()) {
//                    System.out.println("existe");
//                }
//                sonido.open(AudioSystem.getAudioInputStream(a));
//                sonido.start();
//                System.out.println("Reproduciendo 10s. de sonido.");
//                Thread.sleep(2000); // 1000 milisegundos (10 segundos)
//                sonido.close();
//
//
//            }

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
        listaOrdenProduccion = servicioOrden.listaOrdenProduccionPendientes();
    }

    private void consultarListaCotizacion() {
        listaOrdenProduccion = servicioOrden.listaOrdenPendientesNumero(busNumero);
    }

    private void consultarListaCotizacionPorCliente() {
        listaOrdenProduccion = servicioOrden.listaOrdenProduccionPendientesCliente(busCliente);
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
            UsuarioSistema dato = (UsuarioSistema) modelo.FindALlUsuarioPorUsuario(valor.getUsuario());
            boolean validar = true;


            if (dato.getUsuUsuario().equals("")) {
                validar = false;
            }
            if (!dato.getUsuUsuario().equals(valor.getUsuario()) || !dato.getUsuContrasena().equals(valor.getContrasena())) {
                validar = false;
            }

            if (validar) {
                valor.setOrdResponsable(dato.getUsuNombreUsuario());

                if (valor.getOrdResponsable() != null) {

//                        valor.setOrdResponsable(dato.getUsuNombreUsuario());

//                valor.setCodEstCotizacion(servicioEstadoCotizacion.FindALlEstadoSigla(busNumero));
//                servicioCotizacion.modificar(valor);
                    OrdenDeProduccion ord = servicioOrden.listaCotizacionNumeroCot(valor.getCodCotizacion().getCotNumero());
                    System.out.println("ORDEN RECUPEERADA " + ord.getOrdNumero());
                    EstadoOrdenProduccion estOrd = servicioEstadoOrden.FindALlEstadoLikeSigla("F");
                    EstadoCotizacion estCot = servicioEstadoCotizacion.FindALlEstadoSigla("T");
                    System.out.println("estdo RECUPEERADA " + estOrd.getEstOrdNombre());
                    valor.setCodEstadoOrden(estOrd);
                    valor.setOrdFechaCierre(new Date());
                    valor.setOrdHoraCerre(new Date());
                    //ver el estado de cierre de la orden de produccion
                    if (valor.getFechaDespacho().before(valor.getOrdFechaCierre())) {
                        valor.setOrdEstadoCierre("RETRASADA");
                    } else if (valor.getFechaDespacho().after(valor.getOrdFechaCierre())) {
                        valor.setOrdEstadoCierre("CORRECTA");
                    } else {
                        if (valor.getOrdHoraCerre().before(valor.getOrdHoraDespacho())) {
                            valor.setOrdEstadoCierre("CORRECTA");
                        } else {
                            valor.setOrdEstadoCierre("RETRASADA");
                        }
                    }

                    servicioOrden.modificar(valor);
                    Cotizacion cotizacion = valor.getCodCotizacion();
                    cotizacion.setCodEstCotizacion(estCot);
                    servicioCotizacion.modificar(cotizacion);
                    consultarListaOrdenPendientes();
                    Messagebox.show("orden finalizada con éxito");

                } else {
                    Messagebox.show("Debe ingresar un Responsable", "Atención", Messagebox.OK, Messagebox.ERROR);
                }

            } else {
                Messagebox.show("Verifique su usuario y contraseña", "Atención", Messagebox.OK, Messagebox.ERROR);

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
    //ordenes sin cotizar
    List<OrdenSinCotizacion> listaOrdenSinCotizacion = new ArrayList<OrdenSinCotizacion>();
    ServicioOrdenSinCotizar servicioOrdenSinCotizar = new ServicioOrdenSinCotizar();
    private String busClienteSinCotizar = "";
    private Integer busNumeroSinCotizar = 0;

    private void findAllOrdenSinCotizar() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllPub();
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

    //reporte de orden sin cotizar
    @Command
    public void reporteOrdenSinCotizar(@BindingParam("valor") OrdenSinCotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {


        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);


            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";

            reportPath = reportFile + "/ordensincotprod.jasper";


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

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }
        }
    }

    //lista de ordenes sin cotizar
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

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarListaPorClienteSinCotizar() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaCotizacionPorClienteSinCotizar();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarListaPorNumeroSinCotizar() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaCotizacionPorNumeroSinCotizar();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarListaOrdenesAllSinCotizar() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            findAllOrdenSinCotizar();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void consultarListaCotizacionPorClienteSinCotizar() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllPendienteCliente(busClienteSinCotizar);
    }

    private void consultarListaCotizacionPorNumeroSinCotizar() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllNumero(busNumeroSinCotizar);
    }

    public String getBusClienteSinCotizar() {
        return busClienteSinCotizar;
    }

    public void setBusClienteSinCotizar(String busClienteSinCotizar) {
        this.busClienteSinCotizar = busClienteSinCotizar;
    }

    public Integer getBusNumeroSinCotizar() {
        return busNumeroSinCotizar;
    }

    public void setBusNumeroSinCotizar(Integer busNumeroSinCotizar) {
        this.busNumeroSinCotizar = busNumeroSinCotizar;
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void finalizarOrdenSinCotizar(@BindingParam("valor") OrdenSinCotizacion valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            UsuarioSistema dato = (UsuarioSistema) modelo.FindALlUsuarioPorUsuario(valor.getUsuario());
            boolean validar = true;


            if (dato.getUsuUsuario().equals("")) {
                validar = false;
            }
            if (!dato.getUsuUsuario().equals(valor.getUsuario()) || !dato.getUsuContrasena().equals(valor.getContrasena())) {
                validar = false;
            }

            if (validar) {
                valor.setSinResponsable(dato.getUsuNombreUsuario());

                if (valor.getSinResponsable() != null) {

                    valor.setSinEstado("FINALIZADA");
                    valor.setSinFechaCierre(new Date());
                    valor.setSinHoraCierre(new Date());


                    servicioOrdenSinCotizar.modificar(valor);

                    findAllOrdenSinCotizar();
                    Clients.showNotification("Orden finalizada con éxito",
                    "Info", null, "end_center", 3000, true);

                } else {
                    Messagebox.show("Debe ingresar un Responsable", "Atención", Messagebox.OK, Messagebox.ERROR);
                }

            } else {
                Messagebox.show("Verifique su usuario y contraseña", "Atención", Messagebox.OK, Messagebox.ERROR);

            }
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    //nueva solicitud de material
    @Command
    public void nuevoSolicitarMaterial(@BindingParam("valor") SolicitarMaterial valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
//            final HashMap<String, SolicitarMaterial> map = new HashMap<String, SolicitarMaterial>();
//
//            map.put("solicitar", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/nuevoSolicitarMaterial.zul", null, null);
            window.doModal();
            window.detach();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    public void modificarSolicitarMaterial(@BindingParam("valor") SolicitarMaterial valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            final HashMap<String, SolicitarMaterial> map = new HashMap<String, SolicitarMaterial>();

            map.put("solicitar", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/nuevoSolicitarMaterial.zul", null, map);
            window.doModal();
            window.detach();
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
    @NotifyChange({"listaOrdenProduccion"})
    public void buscarListaFehas() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaOrdenPendientesFechas();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenProduccion"})
    public void buscarListaNombreFehas() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaOrdenPendientesNombreFechas();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void consultarListaOrdenPendientesNombreFechas() {
        listaOrdenProduccion = servicioOrden.listaOrdenProduccionPendientesNombreFechas(fechaInicio, fechaFin, busCliente);
    }

    private void consultarListaOrdenPendientesFechas() {
        listaOrdenProduccion = servicioOrden.listaOrdenProduccionPendientesFechas(fechaInicio, fechaFin);
    }
    private Date fechaInicioSin = new Date();
    private Date fechaFinSin = new Date();

    public Date getFechaInicioSin() {
        return fechaInicioSin;
    }

    public void setFechaInicioSin(Date fechaInicioSin) {
        this.fechaInicioSin = fechaInicioSin;
    }

    public Date getFechaFinSin() {
        return fechaFinSin;
    }

    public void setFechaFinSin(Date fechaFinSin) {
        this.fechaFinSin = fechaFinSin;
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarListaFehasSin() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaCotizacionPorClienteSinCotizarFehcha();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void buscarListaNombreFehasSin() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            consultarListaCotizacionPorClienteSinCotizarNombreFecha();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void consultarListaCotizacionPorClienteSinCotizarFehcha() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllPendienteFecha(fechaInicioSin, fechaFinSin);
    }

    private void consultarListaCotizacionPorClienteSinCotizarNombreFecha() {
        listaOrdenSinCotizacion = servicioOrdenSinCotizar.listaOrdenFindAllPendienteNombreFecha(fechaInicioSin, fechaFinSin, busClienteSinCotizar);
    }
//sin cotizar

    @Command
    @NotifyChange({"listaOrdenSinCotizacion"})
    public void ordenarForNommbre() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

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

    //cotizadas
    @Command
    @NotifyChange({"listaOrdenProduccion"})
    public void ordenarForNommbreCot() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Collections.sort(listaOrdenProduccion, new Comparator() {
            public int compare(Object o1, Object o2) {
                OrdenDeProduccion p1 = (OrdenDeProduccion) o1;
                OrdenDeProduccion p2 = (OrdenDeProduccion) o2;
                return new String(p1.getCodCotizacion().getCodCliente().getRazonSocial()).compareTo(new String(p2.getCodCotizacion().getCodCliente().getRazonSocial()));
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
