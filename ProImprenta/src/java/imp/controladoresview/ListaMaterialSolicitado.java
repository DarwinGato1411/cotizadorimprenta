/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.SolicitarMaterial;
import imp.servicios.HelperPersistencia;
import imp.servicios.ServicioSolicitarMaterial;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class ListaMaterialSolicitado {

    ServicioSolicitarMaterial servicioSolicitarMaterial = new ServicioSolicitarMaterial();
    private List<SolicitarMaterial> listaMaterialesSolicitados = new ArrayList<SolicitarMaterial>();
    private String estadoMaterialSol = "SOLICITADO";
    AMedia fileContent = null;
    Connection con = null;

    public ListaMaterialSolicitado() {
        findAll();
    }

    @Command
    public void reporteMaterialSolicitado(@BindingParam("valor") SolicitarMaterial valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            reportPath = reportFile + "/materialsolicitado.jasper";
            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("id_solicita", valor.getIdSolicita());
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
    @NotifyChange({"listaMaterialesSolicitados"})
    public void cambiarEstado(@BindingParam("valor") SolicitarMaterial valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        if (Messagebox.show("¿Desea despachar el material solicitado?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION) == Messagebox.YES) {
            valor.setSolEstado("DESPACHADO");
            servicioSolicitarMaterial.modificar(valor);
//            estadoMaterialSol = "SOLICITADO";
            findAll();
        }

    }

    @Command
    @NotifyChange({"listaMaterialesSolicitados"})
    public void cambiarEstadoeliminarMaterial(@BindingParam("valor") SolicitarMaterial valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        if (Messagebox.show("¿Desea despachar el material solicitado?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION) == Messagebox.YES) {
            valor.setSolEstado("ELIMINADO");
            servicioSolicitarMaterial.modificar(valor);
//            estadoMaterialSol = "SOLICITADO";
            findAll();
        }

    }

    @Command
    @NotifyChange({"listaMaterialesSolicitados"})
    public void buscarEstado() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        findAll();
    }

    private void findAll() {
        listaMaterialesSolicitados = servicioSolicitarMaterial.findAllSolicitarMaterialEstado(estadoMaterialSol);
    }

    public List<SolicitarMaterial> getListaMaterialesSolicitados() {
        return listaMaterialesSolicitados;
    }

    public void setListaMaterialesSolicitados(List<SolicitarMaterial> listaMaterialesSolicitados) {
        this.listaMaterialesSolicitados = listaMaterialesSolicitados;
    }

    public String getEstadoMaterialSol() {
        return estadoMaterialSol;
    }

    public void setEstadoMaterialSol(String estadoMaterialSol) {
        this.estadoMaterialSol = estadoMaterialSol;
    }

    public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }
    //nueva solicitud de material

    @Command
    @NotifyChange({"listaMaterialesSolicitados"})
    public void nuevoSolicitarMaterial(@BindingParam("valor") SolicitarMaterial valor) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
//            final HashMap<String, SolicitarMaterial> map = new HashMap<String, SolicitarMaterial>();
//
//            map.put("solicitar", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/cotizacion/nuevoSolicitarMaterial.zul", null, null);
            window.doModal();
            window.detach();
            findAll();

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }
}
