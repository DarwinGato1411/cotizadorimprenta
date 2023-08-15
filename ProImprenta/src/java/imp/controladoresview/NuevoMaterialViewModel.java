/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Materiales;
import  imp.servicios.ServicioCorteMaterial;
import  imp.servicios.ServicioCortez;
import  imp.servicios.ServicioMateriales;
import imp.utilitario.MaterialIsertarModificar;
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
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevoMaterialViewModel {

    ServicioMateriales servicioMateriales = new ServicioMateriales();
    ServicioCortez servicioCortez = new ServicioCortez();
    ServicioCorteMaterial servicioCorteMaterial = new ServicioCorteMaterial();
    private Materiales materiales = new Materiales();
    private String insertarModificar = "";
    @Wire("#txtAlto")
    Decimalbox txtAlto;
    @Wire("#txtAncho")
    Decimalbox txtAncho;
    @Wire("#windowMaterial")
    Window windowMaterial;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("material") MaterialIsertarModificar isertarModificar, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (isertarModificar.getMateriales() != null) {
            System.out.println("valor de materiales ingreso " + materiales.getMatTipo());
            this.materiales = isertarModificar.getMateriales();
            insertarModificar = isertarModificar.getTpoOperacion();
            System.out.println("valor de materiales luego de asignar... " + this.materiales.getMatTipo());
            // this.materiales.setMatTipo("IMPRESION");

        } else {
            this.materiales = new Materiales("OTROS");

            insertarModificar = isertarModificar.getTpoOperacion();;
        }
        tipoMaterialValido();

    }

    private void tipoMaterialValido() {
        if (materiales.getMatTipo().equals("IMPRESION")) {

            txtAlto.setVisible(Boolean.TRUE);
            txtAncho.setVisible(Boolean.TRUE);

        } else if (materiales.getMatTipo().equals("GIGANTOGRAFIA")) {

            txtAlto.setVisible(Boolean.FALSE);
            txtAncho.setVisible(Boolean.TRUE);

            materiales.setMatAlto(BigDecimal.ZERO);
            materiales.setMatAncho(BigDecimal.ZERO);

        } else {
            txtAlto.setVisible(Boolean.FALSE);
            txtAncho.setVisible(Boolean.FALSE);

            materiales.setMatAlto(BigDecimal.ZERO);
            materiales.setMatAncho(BigDecimal.ZERO);
        }
    }

    @Command
    @NotifyChange("materiales")
    public void visualizarDimension() {
        tipoMaterialValido();
    }

    @Command
    @NotifyChange({"materiales"})
    public void guardar() {
        if (materiales.getMatNombre() != null) {
            if (insertarModificar.equals("N")) {
                System.out.println("INGRESA A INSERTAR");
                servicioMateriales.crear(materiales);
//                Messagebox.show("Guardado con exito");
                materiales = new Materiales();
                //windowMaterial.detach();
            } else {
                servicioMateriales.modificar(materiales);
//                Messagebox.show("Guardado con exito");
                materiales = new Materiales();

            }
            windowMaterial.detach();

        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }
}
