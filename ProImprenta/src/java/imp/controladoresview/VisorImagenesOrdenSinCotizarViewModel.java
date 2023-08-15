/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.OrdenSinCotizacion;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;

/**
 *
 * @author gato
 */
public class VisorImagenesOrdenSinCotizarViewModel {

    private AImage fotoGeneral = null;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("img") OrdenSinCotizacion ordenSinCotizacion, @ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException, IOException {
        Selectors.wireComponents(view, this, false);

        if (ordenSinCotizacion.getSinImagen() != null) {
            fotoGeneral = new AImage("fotoGeneral", ordenSinCotizacion.getSinImagen());
        } else {
            fotoGeneral = new AImage("fotoGeneral", Imagen_A_Bytes());
//            Messagebox.show("No presenta una imagen registrada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    public AImage getFotoGeneral() {
        return fotoGeneral;
    }

    public void setFotoGeneral(AImage fotoGeneral) {
        this.fotoGeneral = fotoGeneral;
    }

    public byte[] Imagen_A_Bytes() throws FileNotFoundException {
        String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/imagenes");
        String reportPath = "";
        reportPath = reportFile + File.separator + "ninguno.png";
        File file = new File(reportPath);

        FileInputStream fis = new FileInputStream(file);
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        }

        byte[] bytes = bos.toByteArray();
        return bytes;
    }
}
