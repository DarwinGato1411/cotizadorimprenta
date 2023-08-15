/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

import imp.entidades.Materiales;

/**
 *
 * @author gato
 */
public class MaterialIsertarModificar {
    private Materiales materiales= new Materiales();
    private String tpoOperacion="";

    public MaterialIsertarModificar() {
    }

    public MaterialIsertarModificar(Materiales mate,String tipo) {
        this.materiales=mate;
        this.tpoOperacion=tipo;
    }

  

    
    
    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public String getTpoOperacion() {
        return tpoOperacion;
    }

    public void setTpoOperacion(String tpoOperacion) {
        this.tpoOperacion = tpoOperacion;
    }
    
    
   
}
