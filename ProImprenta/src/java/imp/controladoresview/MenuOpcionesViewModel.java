/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.seguridad.EnumSesion;
import imp.seguridad.UserCredential;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;

import org.zkoss.zul.Menuitem;

/**
 *
 * @author gato
 */
public class MenuOpcionesViewModel extends SelectorComposer<Component> {

    @Wire("#btnAdministrar")
    Menuitem btnAdministrar;
    @Wire("#btnCotizar")
    Menuitem btnCotizar;
    @Wire("#btnCompaginado")
    Menuitem btnCompaginado;
    @Wire("#btnGiganto")
    Menuitem btnGiganto;
    @Wire("#btnDigital")
    Menuitem btnDigital;
    @Wire("#btnUsuarios")
    Menuitem btnUsuarios;
    @Wire("#btnOrdenFinalizada")
    Menuitem btnOrdenFinalizada;
//    @Wire("#btnReportesGraficas")
//    Menuitem btnReportesGraficas;
    @Wire("#btnCotizacionCerrada")
    Menuitem btnCotizacionCerrada;
    @Wire("#btnManual")
    Menuitem btnManual;
    @Wire("#btnCalculadora")
    Menuitem btnCalculadora;
    @Wire("#btnListaUsuarios")
    Menuitem btnListaUsuarios;
    @Wire("#btnListaOrdenSinCotizarCerradas")
    Menuitem btnListaOrdenSinCotizarCerradas;
    @Wire("#btnMailMasivo")
    Menuitem btnMailMasivo;
    UserCredential credential = new UserCredential();
    private String acceso = "";

    /*MENU DEL KARDEX*/
    @Wire("#btnIngreso")
    Menuitem btnIngreso;
    @Wire("#btnSalida")
    Menuitem btnSalida;
    @Wire("#btnKardex")
    Menuitem btnKardex;
    @Wire("#btnMaterialTrabajo")
    Menuitem btnMaterialTrabajo;

    @Wire("#btnSolicitado")
    Menuitem btnSolicitado;

    public MenuOpcionesViewModel() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (credential.getUsuarioSistema() != null) {

            if (credential.getUsuarioSistema() != null) {
                if (credential.getUsuarioSistema().getUsuNivelAcceso() == 1) {
                    //administrador tiene todos los permisos
                    btnAdministrar.setVisible(Boolean.TRUE);
                    btnCotizar.setVisible(Boolean.TRUE);
                    btnCompaginado.setVisible(Boolean.TRUE);
                    btnGiganto.setVisible(Boolean.TRUE);
                    btnDigital.setVisible(Boolean.TRUE);
//                    btnUsuarios.setVisible(Boolean.TRUE);
                    btnOrdenFinalizada.setVisible(Boolean.TRUE);
                    btnAdministrar.setVisible(Boolean.TRUE);

                    /*inventario*/
                    btnIngreso.setVisible(Boolean.TRUE);
                    btnSalida.setVisible(Boolean.TRUE);
                    btnKardex.setVisible(Boolean.TRUE);
                    btnMaterialTrabajo.setVisible(Boolean.TRUE);

                } else if (credential.getUsuarioSistema().getUsuNivelAcceso() == 2) {
                    //cotizador
                    //                btnReportesGraficas.setVisible(Boolean.FALSE);
//                    btnUsuarios.setVisible(Boolean.FALSE);
                    btnAdministrar.setVisible(Boolean.FALSE);
                    btnAdministrar.setVisible(Boolean.TRUE);
                    btnIngreso.setVisible(Boolean.TRUE);
                    btnSalida.setVisible(Boolean.TRUE);
                    btnKardex.setVisible(Boolean.TRUE);
                    btnMaterialTrabajo.setVisible(Boolean.FALSE);

                } else if (credential.getUsuarioSistema().getUsuNivelAcceso() == 3) {
                    //operador
                    btnAdministrar.setVisible(Boolean.FALSE);
                    //                btnCotizar.setVisible(Boolean.FALSE);
                    //                btnCompaginado.setVisible(Boolean.FALSE);
                    //                btnGiganto.setVisible(Boolean.FALSE);
                    //                btnDigital.setVisible(Boolean.FALSE);
//                    btnUsuarios.setVisible(Boolean.FALSE);
                    btnOrdenFinalizada.setVisible(Boolean.FALSE);
                    //                btnReportesGraficas.setVisible(Boolean.FALSE);
                    btnCotizacionCerrada.setVisible(Boolean.FALSE);
//                    btnManual.setVisible(Boolean.FALSE);
//                    btnCalculadora.setVisible(Boolean.FALSE);
                    btnListaUsuarios.setVisible(Boolean.FALSE);

                    btnAdministrar.setVisible(Boolean.FALSE);
                    btnIngreso.setVisible(Boolean.FALSE);
                    btnSalida.setVisible(Boolean.TRUE);
                    btnKardex.setVisible(Boolean.FALSE);
                    btnMaterialTrabajo.setVisible(Boolean.FALSE);

                } else if (credential.getUsuarioSistema().getUsuNivelAcceso() == 4) {
                    //operador
                    btnAdministrar.setVisible(Boolean.FALSE);
                    btnCotizar.setVisible(Boolean.FALSE);
                    btnCompaginado.setVisible(Boolean.FALSE);
                    btnGiganto.setVisible(Boolean.FALSE);
                    btnDigital.setVisible(Boolean.FALSE);
//                    btnUsuarios.setVisible(Boolean.FALSE);
                    btnOrdenFinalizada.setVisible(Boolean.FALSE);
                    //                btnReportesGraficas.setVisible(Boolean.FALSE);
                    //                btnCotizacionCerrada.setVisible(Boolean.FALSE);
//                    btnManual.setVisible(Boolean.FALSE);
                    //                btnCalculadora.setVisible(Boolean.FALSE);
                    btnListaUsuarios.setVisible(Boolean.FALSE);
                } else if (credential.getUsuarioSistema().getUsuNivelAcceso() == 5) {
                    //operador

                    btnCotizar.setVisible(Boolean.FALSE);

                    btnCompaginado.setVisible(Boolean.FALSE);

                    btnGiganto.setVisible(Boolean.FALSE);

                    btnDigital.setVisible(Boolean.FALSE);

//                    btnUsuarios.setVisible(Boolean.FALSE);

                    btnOrdenFinalizada.setVisible(Boolean.FALSE);

                    btnCotizacionCerrada.setVisible(Boolean.FALSE);

//                    btnManual.setVisible(Boolean.FALSE);

//                    btnCalculadora.setVisible(Boolean.FALSE);

                    btnListaUsuarios.setVisible(Boolean.FALSE);

                    btnListaOrdenSinCotizarCerradas.setVisible(Boolean.FALSE);
                    btnAdministrar.setVisible(Boolean.FALSE);
                    //                btnCotizar.setVisible(Boolean.FALSE);
                    //                btnCompaginado.setVisible(Boolean.FALSE);
                    //                btnGiganto.setVisible(Boolean.FALSE);
                    //                btnDigital.setVisible(Boolean.FALSE);
                    //                btnUsuarios.setVisible(Boolean.FALSE);
                    //                btnOrdenFinalizada.setVisible(Boolean.FALSE);
                    ////                btnReportesGraficas.setVisible(Boolean.FALSE);
                    //                btnCotizacionCerrada.setVisible(Boolean.FALSE);
                    ////                btnManual.setVisible(Boolean.FALSE);
                    ////                btnCalculadora.setVisible(Boolean.FALSE);
                    //                btnListaUsuarios.setVisible(Boolean.FALSE);
                    //                btnListaUsuarios.setVisible(Boolean.FALSE);
                }
            }

        }

    }

    @Listen("onClick = #btnAdministrar")
    public void doadministrarDocumentos() {
        Executions.sendRedirect("/cotizacion/administrarRegistros.zul");
    }

    @Listen("onClick = #btnCotizar")
    public void doCotizar() {
        Executions.sendRedirect("/cotizacion/cotizacion.zul");
    }

    @Listen("onClick = #btnCompaginado")
    public void doCotizarCompaginado() {
        Executions.sendRedirect("/cotizacion/cotizacionCompaginado.zul");
    }

    @Listen("onClick = #btnListaUsuarios")
    public void doListaUsuarios() {
        Executions.sendRedirect("/cotizacion/listaCotizaciones.zul");

    }

    @Listen("onClick = #btnGiganto")
    public void doCotizarGiganto() {
        Executions.sendRedirect("/cotizacion/cotizacionGiganto.zul");

    }

    @Listen("onClick = #btnDigital")
    public void doCptizarDigital() {
        Executions.sendRedirect("/cotizacion/cotizacionDigital.zul");

    }

    @Listen("onClick = #btnCalculadora")
    public void abrirOpcionUtilitario() {
        Executions.sendRedirect("/cotizacion/opcionutilitarios.zul");

    }

    @Listen("onClick = #btnPrincipal")
    public void abrirPrincipal() {
        Executions.sendRedirect("/cotizacion/principal.zul");

    }

    @Listen("onClick = #btnReportesGraficas")
    public void graficas() {
        Executions.sendRedirect("/cotizacion/administrarReportes.zul");

    }

    @Listen("onClick = #btnOrdenSinCotizar")
    public void ordenSinCotizar() {
        Executions.sendRedirect("/cotizacion/ordenSinCotizar.zul");

    }

    @Listen("onClick = #btnListaOrdenSinCotizar")
    public void listaOrdenSinCotizar() {
        Executions.sendRedirect("/cotizacion/listaOrdenSinCotizar.zul");

    }

    @Listen("onClick = #btnManual")
    public void abrirManual() {
//        Executions.sendRedirect("/cotizacion/ContenedorManual.zul");
        final HashMap<String, String> map = new HashMap<String, String>();
//        map.put("pdf", "");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/cotizacion/ContenedorManual.zul", null, map);
        window.doModal();
    }

    @Listen("onClick = #btnUsuarios")
    public void abrirUsuarios() {
        Executions.sendRedirect("/cotizacion/administrarUsuariosViewModel.zul");

    }

    @Listen("onClick = #btnOrdenFinalizada")
    public void ordenCerrada() {
        Executions.sendRedirect("/cotizacion/listaOrdenesProduccion.zul");

    }

    @Listen("onClick = #btnCotizacionCerrada")
    public void cotizacionesCerrada() {
        Executions.sendRedirect("/cotizacion/listaCotizacionesCerradas.zul");

    }

    @Listen("onClick = #btnCotizacionFacturadas")
    public void cotizacionesFacturadas() {
        Executions.sendRedirect("/cotizacion/listaCotizacionesFacturadas.zul");

    }

    @Listen("onClick = #btnListaOrdenSinCotizarCerradas")
    public void ordenSinCotizarCerrada() {
        Executions.sendRedirect("/cotizacion/listaOrdenSinCotizarCerradas.zul");

    }

    @Listen("onClick = #btnMaterialSolicitado")
    public void materialSolicitado() {
        Executions.sendRedirect("/cotizacion/listaSolicitudMateriales.zul");

    }

    @Listen("onClick = #btnListaOrdenSinCotizarFacturadas")
    public void listaOrdenSinCotFacturado() {
        Executions.sendRedirect("/cotizacion/listaOrdenSinCotizarFacturado.zul");

    }

    @Listen("onClick = #btnMailMasivo")
    public void mailMasivo() {
        Executions.sendRedirect("/cotizacion/mailMasivo.zul");

    }

//    @Listen("onClick = #btnCalculadora")
//    public void abrirCalculadora() {
//
//        final HashMap<String, String> map = new HashMap<String, String>();
////        map.put("detalle", "");
//        org.zkoss.zul.Window window = (Window) Executions.createComponents(
//                "/cotizacion/calculadoracabidas.zul", null, map);
//        window.doModal();
//
//    }

    /*INVENTARIO*/
    @Listen("onClick = #btnAdministrarCatalogo")
    public void btnAdministrarCatalogo() {
        Executions.sendRedirect("/inventario/administrar.zul");
    }

    @Listen("onClick = #btnIngreso")
    public void doIngreso() {
        Executions.sendRedirect("/inventario/ingresoMaterial.zul");
    }

    @Listen("onClick = #btnSalida")
    public void doSalida() {
        Executions.sendRedirect("/inventario/salidaMaterial.zul");
    }

    @Listen("onClick = #btnKardex")
    public void doKardex() {
        Executions.sendRedirect("/inventario/reportes.zul");
    }

    @Listen("onClick = #btnReporteInventario")
    public void doReporteInventario() {
        Executions.sendRedirect("/inventario/reportesinventario.zul");
    }

    @Listen("onClick = #btnMaterialTrabajo")
    public void doMaterialTrabajo() {
        Executions.sendRedirect("/inventario/materalesTrabajo.zul");
    }

//    @Listen("onClick = #btnUsuarios")
//    public void doUsuarios() {
//        Executions.sendRedirect("/inventario/administrarusuarios.zul");
//    }
    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }
}
