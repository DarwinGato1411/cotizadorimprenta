/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cotizacion;
import imp.entidades.EstadoCotizacion;
import imp.entidades.EstadoOrdenProduccion;
import imp.entidades.OrdenDeProduccion;
import  imp.servicios.ServicioCotizacion;
import  imp.servicios.ServicioEstadoCotizacion;
import  imp.servicios.ServicioEstadoOrden;
import  imp.servicios.ServicioOrden;
import imp.utilitario.Utilitario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class CambiarEstadoCotizacion {

    @Wire
    Window idEstCot;
    private Cotizacion cotizacion = new Cotizacion();
    private List<EstadoCotizacion> listaEstados = new ArrayList<EstadoCotizacion>();
    private EstadoCotizacion selEstado = new EstadoCotizacion();
    ServicioEstadoCotizacion servicioEstadoCotizacion = new ServicioEstadoCotizacion();
    ServicioCotizacion servicioCotizacion = new ServicioCotizacion();
    ServicioOrden servicioOrden = new ServicioOrden();
    ServicioEstadoOrden servicioEstadoOrden = new ServicioEstadoOrden();
    private String numeroOrden = "";
    private OrdenDeProduccion ordenEdit = new OrdenDeProduccion();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("cotizacion") Cotizacion cot, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        System.out.println("codigo cotizacion " + cot.getCodCotizacion());
        System.out.println("numero cotizacion " + cot.getCotNumero());
        cotizacion = cot;

        if (servicioOrden.listaCotizacionNumeroCot(cot.getCotNumero()) != null) {
            ordenEdit = servicioOrden.listaCotizacionNumeroCot(cot.getCotNumero());
        } else {
            ordenEdit = new OrdenDeProduccion();

        }

        obtenerEstados();
        numeroCotizacion();
    }

    private void numeroCotizacion() {
        OrdenDeProduccion ord = servicioOrden.ultimaOrden();

        if (ord != null) {
            System.out.println("oreden recuperada secuencial " + ord.getOrdSecuencial() + "   numero de orden" + ord.getOrdNumero());
            numeroOrden = Utilitario.NumeroFactura(ord.getOrdSecuencial());
            System.out.println("NUEVO Numero de orden de produccion " + numeroOrden);
        } else {
            numeroOrden = Utilitario.NumeroFactura("0");
        }
    }

    @Command
    public void guardar() {


        try {
            System.out.println("codigo cotizacion " + cotizacion.getCodCotizacion());
            System.out.println("numero cotizacion " + cotizacion.getCotNumero());
            System.out.println("secuencial " + numeroOrden);

            int horas = 5;
            if (cotizacion.getCodEstCotizacion().getEstCotSigla().equals("A")) {
                System.out.println("ENTRA A CREAR LA ORDEN");
                servicioCotizacion.modificar(cotizacion);
                EstadoOrdenProduccion estOrd = servicioEstadoOrden.FindALlEstadoLikeSigla("G");
                System.out.println("estado de la orden " + estOrd.getEstOrdNombre());
                ordenEdit.setCodEstadoOrden(estOrd);
                ordenEdit.setFechaCreacion(new Date());
                ordenEdit.setFechaDespacho(new Date());
                ordenEdit.setOrdHoraInicio(new Date());
                Date horaCierre = new Date();
                horaCierre.setHours(horas);
                ordenEdit.setOrdHoraDespacho(horaCierre);



                ordenEdit.setCodCotizacion(cotizacion);
                ordenEdit.setOrdNumero(cotizacion.getCotNumero());
                ordenEdit.setOrdSecuencial(numeroOrden);
                servicioOrden.modificar(ordenEdit);

                idEstCot.detach();

            } else {
                System.out.println("OTRO ESTADO DE LA ORDEN");

                servicioCotizacion.modificar(cotizacion);
                EstadoOrdenProduccion estOrd = servicioEstadoOrden.FindALlEstadoLikeSigla("P");
                ordenEdit.setCodEstadoOrden(estOrd);
                idEstCot.detach();
            }
            Messagebox.show("Modificado con exito");
        } catch (Exception e) {
            System.out.println("ERROR ANTES DE INSERTAR " + e);
        }

    }

    private void obtenerEstados() {
        listaEstados = servicioEstadoCotizacion.FindALlEstado();
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public List<EstadoCotizacion> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<EstadoCotizacion> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public EstadoCotizacion getSelEstado() {
        return selEstado;
    }

    public void setSelEstado(EstadoCotizacion selEstado) {
        this.selEstado = selEstado;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public OrdenDeProduccion getOrdenEdit() {
        return ordenEdit;
    }

    public void setOrdenEdit(OrdenDeProduccion ordenEdit) {
        this.ordenEdit = ordenEdit;
    }
}
