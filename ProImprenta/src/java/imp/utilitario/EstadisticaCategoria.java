/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

/**
 *
 * @author gato
 */
public class EstadisticaCategoria implements Comparable<Object>{

    private String categoria;
    private String ubicacion;
    private Long cantidad;

    public EstadisticaCategoria(String categoria, Long cantidad) {
        this.categoria = categoria;
        this.cantidad = cantidad;
    }
    public EstadisticaCategoria(String categoria, Long cantidad,String ubicacion) {
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.ubicacion=ubicacion;
    }

    public EstadisticaCategoria() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    

    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
