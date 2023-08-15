/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cliente;
import  imp.servicios.ServicioCliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gato
 */
public class ClienteViewModel {
    ServicioCliente servicioCliente= new ServicioCliente();
    List<Cliente> listaClientesAll= new ArrayList<Cliente>();

    public ClienteViewModel() {
        
        cosultarClientesALl();
    }

    
    private void cosultarClientesALl(){
    listaClientesAll=servicioCliente.FindALlCliente();
    }
    public List<Cliente> getListaClientesAll() {
        return listaClientesAll;
    }

    public void setListaClientesAll(List<Cliente> listaClientesAll) {
        this.listaClientesAll = listaClientesAll;
    }
    
    
    
}
