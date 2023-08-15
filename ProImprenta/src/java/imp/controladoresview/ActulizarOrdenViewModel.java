/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cotizacion;
import imp.entidades.OrdenDeProduccion;
import  imp.servicios.ServicioOrden;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class ActulizarOrdenViewModel {

    @Wire
    Window idOrdenProduccion;
    private Cotizacion cotizacion = new Cotizacion();
    private OrdenDeProduccion ordenDeProduccion = new OrdenDeProduccion();
    private List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
    ServicioOrden servicioOrden = new ServicioOrden();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("cotizacion") Cotizacion cot, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        cotizacion = cot;
        ordenDeProduccion = servicioOrden.listaCotizacionNumeroCot(cot.getCotNumero());
//        recuperarOrden();
    }

    private void recuperarOrden() {
        for (OrdenDeProduccion item : listaOrden) {
            System.out.println("Orde " + item.getOrdNumero());
            ordenDeProduccion = item;

        }
    }

    @Command
    public void guardar() {
        try {


            servicioOrden.modificar(ordenDeProduccion);
             Clients.showNotification("Guardado con éxito",
                    "Info", null, "end_center", 3000, true);
            idOrdenProduccion.detach();

        } catch (Exception e) {
        }

    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public OrdenDeProduccion getOrdenDeProduccion() {
        return ordenDeProduccion;
    }

    public void setOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion) {
        this.ordenDeProduccion = ordenDeProduccion;
    }

    public List<OrdenDeProduccion> getListaOrden() {
        return listaOrden;
    }

    public void setListaOrden(List<OrdenDeProduccion> listaOrden) {
        this.listaOrden = listaOrden;
    }
    //subir pdf
    private String filePath;
    AMedia fileContent = null;
    byte[] buffer = new byte[1024 * 1024];

    @Command
    @NotifyChange({"fileContent", "ordenDeProduccion"})
    public void subirArchivo() throws InterruptedException, IOException {

        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.util.media.AMedia) {
            String nombre = media.getName().toString();
            if (media != null && nombre.contains("pdf")) {
                if (media.getByteData().length > 10 * 1024 * 1024) {
                    Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 300KB.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

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

                    ordenDeProduccion.setOrdPdf(bFile);

                    f.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Messagebox.show("El arhivo seleccionado no es un archivo valido.\n Seleccione un archivo pdf", "Atención", Messagebox.OK, Messagebox.ERROR);
            }
        }

    }

    public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

    @Command
    @NotifyChange("ordenDeProduccion")
    public void subirFotografia() throws InterruptedException, IOException {
        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 5*1024 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 5MB .\n Por favor seleccione un archivo más pequeño.");
                return;
            }
            this.ordenDeProduccion.setOrdImg(media.getByteData());
//            foto1 = new AImage("foto", this.cortezPosibles.getCortImagen());
//            consultarCortez("");
//            Messagebox.show("La fotografía fue grabada exitosamente.");
        } else {
            Messagebox.show("El arhivo seleccionado no es una imagen.\n Selecione un archivo con extensión .jpg, png o gif.");
        }


    }
}
