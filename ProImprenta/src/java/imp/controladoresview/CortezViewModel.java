/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.CortezPosibles;
import imp.entidades.Materiales;
import imp.entidades.RecorteMaterial;
import  imp.servicios.ServicioCorteMaterial;
import  imp.servicios.ServicioCortez;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class CortezViewModel {
    
    @Wire("#windowRecibido1")
    Window windowRecibido1;
    ServicioCortez servicioCortez = new ServicioCortez();
 
    private List<CortezPosibles> listaCortesPorMaterial = new ArrayList<CortezPosibles>();
    private Materiales materiales = new Materiales();
    private AImage foto;
    private AImage foto1;
    AMedia fileContent = null;
    private CortezPosibles cortezPosibles = new CortezPosibles();
    private List<RecorteMaterial> listaRecorteMaterial = new ArrayList<RecorteMaterial>();
     private List<RecorteMaterial> listaRecorteMaterialAux = new ArrayList<RecorteMaterial>();
    ServicioCorteMaterial servicioCorteMaterial = new ServicioCorteMaterial();
    
    @AfterCompose
    public void afterCompose(@ExecutionArgParam("material") Materiales material, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        
        this.materiales = material;
        System.out.println("Valor del material: ... " + material.getCodMaterial());
        consultarRecorteMaterial(material);
        
        
    }
    
 
    private void consultarRecorteMaterial(Materiales codMaterial) {
        //List<RecorteMaterial> auxiliar = new ArrayList<RecorteMaterial>();
        listaCortesPorMaterial = servicioCorteMaterial.FindALlRecorteMaterialPorMaterial(codMaterial);
        //setListaRecorteMaterial(auxiliar);
//        for (RecorteMaterial o:listaRecorteMaterial) {
//          System.out.println("ELEMENTO.... "+o.getCortezPosibles().getCortDescripcion());   
//        }
    }
    
    @Command
    @NotifyChange("listaCortesPorMaterial")
    public void visorCortes() {
        
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("prueba", "");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/visorCortes.zul", null, map);
        window.doModal();
    }
    
    @Command
    @NotifyChange({"listaRecorteMaterial", "cortezPosibles", "foto1"})
    public void guardar() {
        if (cortezPosibles.getCortDescripcion() != null) {
            //cortezPosibles.setCodMaterial(materiales);
            servicioCortez.modificar(cortezPosibles);
//            cortezPosibles.setCortImagen(null);
            foto1 = null;
            
            Messagebox.show("Registro guardado con exito.");
            consultarCortez();
            cortezPosibles = new CortezPosibles();
            
        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
        
        
    }
    
    @Command
    @NotifyChange({"listaRecorteMaterial", "foto"})
    public void verFoto(@BindingParam("material") CortezPosibles materiales) throws IOException {
        
        foto = new AImage("foto", materiales.getCortImagen());
    }
    
    @Command
    @NotifyChange("foto1")
    public void subirArchivo() throws InterruptedException, IOException {
        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 300 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 300KB.\n Por favor seleccione un archivo más pequeño.");
                return;
            }
            this.cortezPosibles.setCortImagen(media.getByteData());
            foto1 = new AImage("foto", this.cortezPosibles.getCortImagen());
            
            Messagebox.show("La fotografía fue grabada exitosamente.");
        } else {
            Messagebox.show("El arhivo seleccionado no es una imagen.\n Selecione un archivo con extensión .jpg, png o gif.");
        }
        
        
    }
    
    private void consultarCortez() {
        //   listaCortesPorMaterial = servicioCortez.FindALlCortezPosiblesLikeNombre(materiales);
    }
    
    public List<CortezPosibles> getListaCortesPorMaterial() {
        return listaCortesPorMaterial;
    }
    
    public void setListaCortesPorMaterial(List<CortezPosibles> listaCortesPorMaterial) {
        this.listaCortesPorMaterial = listaCortesPorMaterial;
    }
    
    public Materiales getMateriales() {
        return materiales;
    }
    
    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }
    
    public AImage getFoto() {
        return foto;
    }
    
    public void setFoto(AImage foto) {
        this.foto = foto;
    }
    
    public CortezPosibles getCortezPosibles() {
        return cortezPosibles;
    }
    
    public void setCortezPosibles(CortezPosibles cortezPosibles) {
        this.cortezPosibles = cortezPosibles;
    }
    
    public AImage getFoto1() {
        return foto1;
    }
    
    public void setFoto1(AImage foto1) {
        this.foto1 = foto1;
    }
    
    public List<RecorteMaterial> getListaRecorteMaterial() {
        return listaRecorteMaterial;
    }
    
    public void setListaRecorteMaterial(List<RecorteMaterial> listaRecorteMaterial) {
        this.listaRecorteMaterial = listaRecorteMaterial;
    }

    public List<RecorteMaterial> getListaRecorteMaterialAux() {
        return listaRecorteMaterialAux;
    }

    public void setListaRecorteMaterialAux(List<RecorteMaterial> listaRecorteMaterialAux) {
        this.listaRecorteMaterialAux = listaRecorteMaterialAux;
    }

}
