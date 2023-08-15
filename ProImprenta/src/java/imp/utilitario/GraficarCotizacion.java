/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

/**
 *
 * @author gato
 */
public class GraficarCotizacion {
    private String vendedor;
    private Long valor;

    public GraficarCotizacion(String vendedor, Long valor) {
        this.vendedor = vendedor;
        this.valor = valor;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }
    
    
}
