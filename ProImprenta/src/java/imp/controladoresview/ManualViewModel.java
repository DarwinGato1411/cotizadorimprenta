/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;

/**
 *
 * @author gato
 */
public class ManualViewModel {

    AMedia fileContent = null;
    byte[] buffer = new byte[10*1024 * 1024];

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws FileNotFoundException, IOException {
        Selectors.wireComponents(view, this, false);

        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");
        String pathSalida = directorioReportes + File.separator + "Manual_imprenta.pdf";
        System.out.println("Path  "+pathSalida);
        File f = new File(pathSalida);
        FileInputStream fs = new FileInputStream(f);
        fs.read(buffer);
        fs.close();

        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        fileContent = new AMedia("report", "pdf", "application/pdf", is);


    }

    public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
    
    
}
