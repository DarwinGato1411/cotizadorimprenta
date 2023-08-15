/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.ManoDeObra;
import  imp.servicios.ServicioManoObra;
import java.math.BigDecimal;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;

/**
 *
 * @author gato
 */
public class ManoObraViewModel {

    private ManoDeObra manoDeObra = new ManoDeObra();
    ServicioManoObra servicioManoObra = new ServicioManoObra();
    private String validar = "";
    @Wire("#txtPlancha")
    Decimalbox txtPlancha;
    @Wire("#txtTorres")
    Decimalbox txtTorres;
    @Wire("#manoObra")
    Radiogroup manoObra;
    @Wire("#txtLargoMax")
    Decimalbox txtLargoMax;
    @Wire("#txtAnchoMaximo")
    Decimalbox txtAnchoMaximo;
    @Wire("#txtLargoMinino")
    Decimalbox txtLargoMinino;
    @Wire("#txtAnchoMinimo")
    Decimalbox txtAnchoMinimo;
    @Wire("#txtCosto")
    Decimalbox txtCosto;
    @Wire("#txtHoraGigan")
    Decimalbox txtHoraGigan;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("manoObra") ManoDeObra manoDeObra, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        try {
            if (!manoDeObra.getManTipoProducto().equals("")) {
                this.manoDeObra = manoDeObra;
            }
            mostrarCampos();
            mostrarCamposGiganto();
        } catch (Exception e) {
            System.out.println("cayo en el error " + e.toString());
        }
    }

    @Command
    @NotifyChange({"listaClientesAll", "manoDeObra"})
    public void guardar() {
        if (manoDeObra != null) {
//            manoDeObra.setEstadoCliente(1);
            servicioManoObra.modificar(manoDeObra);
            Messagebox.show("Guardado con exito");
            manoDeObra = new ManoDeObra();
            //windowCliente.detach();
        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"listaClientesAll", "manoDeObra"})
    public void guardarGiganto() {
        if (manoDeObra != null) {
//            manoDeObra.setEstadoCliente(1);
            servicioManoObra.modificar(manoDeObra);
            Messagebox.show("Guardado con exito");
            manoDeObra = new ManoDeObra();
            //windowCliente.detach();
        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    private void mostrarCamposGiganto() {
        if (!manoDeObra.getManTipoProducto().equals("GIGANTOGRAFIA")) {
            txtCosto.setDisabled(Boolean.TRUE);
            manoDeObra.setManCostoOpcional(BigDecimal.ZERO);
            System.out.println("bloquea campos gigan");
        } else {
            txtCosto.setDisabled(Boolean.FALSE);
            txtHoraGigan.setDisabled(Boolean.FALSE);
            System.out.println("desbloquea campos gigan");

        }

    }

    private void mostrarCampos() {
        if (!manoDeObra.getManTipoProducto().equals("IMPRESION")) {
            txtPlancha.setDisabled(Boolean.TRUE);
            txtAnchoMaximo.setDisabled(Boolean.TRUE);
            txtLargoMax.setDisabled(Boolean.TRUE);
            txtLargoMinino.setDisabled(Boolean.TRUE);
            txtAnchoMinimo.setDisabled(Boolean.TRUE);
            txtTorres.setDisabled(Boolean.TRUE);
            manoObra.setVisible(Boolean.FALSE);

            manoDeObra.setManAlto(BigDecimal.ZERO);
            manoDeObra.setManAltoMin(BigDecimal.ZERO);
            manoDeObra.setManAncho(BigDecimal.ZERO);
            manoDeObra.setManAnchoMin(BigDecimal.ZERO);
            manoDeObra.setManCostoPlancha(Float.valueOf("0"));
            manoDeObra.setManNumeroTorresImpresion(0);
            manoDeObra.setManPermiteVolteo("N/A");
        } else {
            txtPlancha.setDisabled(Boolean.FALSE);
            txtAnchoMaximo.setDisabled(Boolean.FALSE);
            txtLargoMax.setDisabled(Boolean.FALSE);
            txtLargoMinino.setDisabled(Boolean.FALSE);
            txtAnchoMinimo.setDisabled(Boolean.FALSE);
            txtTorres.setDisabled(Boolean.FALSE);
            manoObra.setVisible(Boolean.TRUE);
        }

    }

    @Command
    @NotifyChange({"manoDeObra"})
    public void visualizarCampos() {
        mostrarCampos();
        // validar=manoDeObra.getManTipoProducto().toUpperCase().trim();

    }

    @Command
    @NotifyChange("manoDeObra")
    public void visualizarCamposGiganto() {
        mostrarCamposGiganto();
        // validar=manoDeObra.getManTipoProducto().toUpperCase().trim();

    }

    @Command
    @NotifyChange("manoDeObra")
    public void seleccionVolteo() {
    }

    public ManoDeObra getManoDeObra() {
        return manoDeObra;
    }

    public void setManoDeObra(ManoDeObra manoDeObra) {
        this.manoDeObra = manoDeObra;
    }

    public String getValidar() {
        return validar;
    }

    public void setValidar(String validar) {
        this.validar = validar;
    }
}
