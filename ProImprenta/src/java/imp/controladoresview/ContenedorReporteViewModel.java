/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class ContenedorReporteViewModel {

     AMedia fileContent = null;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("pdf") AMedia recibido, @ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException {
        Selectors.wireComponents(view, this, false);
     
        if (recibido!=null) {
            fileContent = new AMedia("report", "pdf", "application/pdf", recibido.getByteData());
        } else {
            Messagebox.show("No presenta un documento registrado", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }
    
     public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }
}
