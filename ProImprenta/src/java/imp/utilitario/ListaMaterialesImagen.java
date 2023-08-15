/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

import imp.entidades.Materiales;
import java.io.Serializable;
import org.zkoss.image.AImage;

/**
 *
 * @author gato
 */
public class ListaMaterialesImagen implements Serializable{

    private Materiales materiales;
    private AImage fotoGeneral;

    public ListaMaterialesImagen() {
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public AImage getFotoGeneral() {
        return fotoGeneral;
    }

    public void setFotoGeneral(AImage fotoGeneral) {
        this.fotoGeneral = fotoGeneral;
    }
}
