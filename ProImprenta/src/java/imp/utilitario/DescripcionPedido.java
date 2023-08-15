/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

import imp.entidades.Cabida;
import imp.entidades.CortezPosibles;
import imp.entidades.DetalleCotizacion;
import imp.entidades.ManoDeObra;
import imp.entidades.Materiales;
import imp.entidades.Terciarizado;
import imp.entidades.TipoTrabajo;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.image.AImage;

/**
 *
 * @author gato
 */
public class DescripcionPedido implements Serializable {

    private Integer idDetalle = 0;
    private Integer idDetCotizacion;
    private BigDecimal cantidad;
    private BigDecimal pliegos;
    private String detalleTrabajo = "";
    private TipoTrabajo tipoTrabajo;
    private String descripcionTrabajo;
    private BigDecimal largoAbierto;
    private BigDecimal anchoAbierto;
    private BigDecimal largoCerrado;
    private BigDecimal anchoCerrado;
    private Integer colorAnverso;
    private Integer colorReverso;
    private BigDecimal motivo;
    private Materiales materiales;
    private CortezPosibles cortezPosibles;
    private AImage fotoGeneral = null;
    private BigDecimal uxf=BigDecimal.ZERO;;
    private Cabida cabida;
    private String matTipoCobro = "";
    private AImage fotoCabida;
    private BigDecimal maculatura;
    private BigDecimal placas;
    private BigDecimal placasPorRegistrO;
    private List<Cabida> listaCabidas = new ArrayList<Cabida>();
    private ManoDeObra manoObra = new ManoDeObra();
    private String tipoCobroManoObra = "";
    private String tipoCobroTercerizado = "";
    private String tipoCobroAdicional = "";
    private ManoDeObra manoObraAdicional = new ManoDeObra();
    //para el calculo de los costos
    private BigDecimal valorTotalMaterial;
    private BigDecimal valorTotalManoObra;
    private BigDecimal valorTotalImpresion;
    private BigDecimal numeroHoras = BigDecimal.ZERO;
    //mapa materiales vaerios
    private BigDecimal catidadVarios = BigDecimal.ZERO;
    private Materiales materialesVarios;
    private BigDecimal valorVariosTotal = BigDecimal.ZERO;
    //pre post
    private BigDecimal catidadPrePost = BigDecimal.ZERO;
    private ManoDeObra manoObraPrePost = new ManoDeObra();
    private BigDecimal valorTotalPrePost = BigDecimal.ZERO;
    //tercerizado
    private List<Terciarizado> listaTercerizados = new ArrayList<Terciarizado>();
    private Terciarizado terciarizado = new Terciarizado();
    private String tipoCobroManoObraPrePost = "";
    private BigDecimal cantidadTercerizado = BigDecimal.ZERO;
    private BigDecimal valorUnitarioTercerizado = BigDecimal.ZERO;
    private BigDecimal totalTercerizado = BigDecimal.ZERO;
    private String detalleTercerizado = "";
    private BigDecimal costoTotalPlacas = BigDecimal.ZERO;
    private BigDecimal cantidadHoraMillar = BigDecimal.ZERO;
    private BigDecimal descuentoMano = BigDecimal.ZERO;
    //gisganto
    private BigDecimal metros = BigDecimal.ZERO;
    private BigDecimal costoMetro = BigDecimal.ZERO;
    private BigDecimal costoTinta = BigDecimal.ZERO;
    private BigDecimal costoOperador = BigDecimal.ZERO;
    private BigDecimal valotM2 = BigDecimal.ZERO;
    private BigDecimal valorUnitario = BigDecimal.ZERO;
    private BigDecimal valorTotalGiganto = BigDecimal.ZERO;
    
    private BigDecimal valorM2xCosto = BigDecimal.ZERO;
    private BigDecimal valorM2xOperador = BigDecimal.ZERO;
    private BigDecimal valorM2xTinta= BigDecimal.ZERO;
    private BigDecimal pliegosImpresionCompaginado;
    private String detTipoTrabajo;
    
    //decuento material
      private BigDecimal porcentajeDescuentoMaterial=BigDecimal.ZERO;

    public DescripcionPedido() {
    }

    public DescripcionPedido(BigDecimal cantidad1, BigDecimal pliego1, BigDecimal largoAbierto,
            BigDecimal anchoAbierto, BigDecimal largoCerrado, BigDecimal anchoCerrado,
            Integer colorAnverso, Integer colorReverso, BigDecimal motivo, Materiales materiales1,
            CortezPosibles cortezPosibles1, TipoTrabajo tipoTrabajo1, String descripcionTrabajo1,
            String matTipoCobro1, List<Cabida> lstCabida) {
        this.cantidad = cantidad1;
        this.largoAbierto = largoAbierto;
        this.pliegos = pliego1;
        this.anchoAbierto = anchoAbierto;
        this.largoCerrado = largoCerrado;
        this.anchoCerrado = anchoCerrado;
        this.colorAnverso = colorAnverso;
        this.colorReverso = colorReverso;
        this.motivo = motivo;
        this.materiales = materiales1;
        this.cortezPosibles = cortezPosibles1;
        this.tipoTrabajo = tipoTrabajo1;
        this.descripcionTrabajo = descripcionTrabajo1;
        this.matTipoCobro = matTipoCobro1;
        this.listaCabidas = lstCabida;
    }

    public DescripcionPedido(Materiales materialesVarios) {
        this.materialesVarios = materialesVarios;
    }

    public DescripcionPedido(ManoDeObra manoObraPrePost) {
        this.manoObraPrePost = manoObraPrePost;
    }

    public DescripcionPedido(List<Terciarizado> listaTercerizado) {
        this.listaTercerizados = listaTercerizado;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getLargoAbierto() {
        return largoAbierto;
    }

    public void setLargoAbierto(BigDecimal largoAbierto) {
        this.largoAbierto = largoAbierto;
    }

    public BigDecimal getAnchoAbierto() {
        return anchoAbierto;
    }

    public void setAnchoAbierto(BigDecimal anchoAbierto) {
        this.anchoAbierto = anchoAbierto;
    }

    public BigDecimal getLargoCerrado() {
        return largoCerrado;
    }

    public void setLargoCerrado(BigDecimal largoCerrado) {
        this.largoCerrado = largoCerrado;
    }

    public BigDecimal getAnchoCerrado() {
        return anchoCerrado;
    }

    public void setAnchoCerrado(BigDecimal anchoCerrado) {
        this.anchoCerrado = anchoCerrado;
    }

    public Integer getColorAnverso() {
        return colorAnverso;
    }

    public void setColorAnverso(Integer colorAnverso) {
        this.colorAnverso = colorAnverso;
    }

    public Integer getColorReverso() {
        return colorReverso;
    }

    public void setColorReverso(Integer colorReverso) {
        this.colorReverso = colorReverso;
    }

    public BigDecimal getMotivo() {
        return motivo;
    }

    public void setMotivo(BigDecimal motivo) {
        this.motivo = motivo;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public CortezPosibles getCortezPosibles() {
        return cortezPosibles;
    }

    public void setCortezPosibles(CortezPosibles cortezPosibles) throws IOException {
        if (cortezPosibles != null) {
            fotoGeneral = new AImage("fotoGeneral", cortezPosibles.getCortImagen());
        }
        this.cortezPosibles = cortezPosibles;
    }

    public AImage getFotoGeneral() {
        return fotoGeneral;
    }

    public void setFotoGeneral(AImage fotoGeneral) {
        this.fotoGeneral = fotoGeneral;
    }

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public BigDecimal getPliegos() {
        return pliegos;
    }

    public void setPliegos(BigDecimal pliegos) {
        this.pliegos = pliegos;
    }

    public BigDecimal getUxf() {
        return uxf;
    }

    public void setUxf(BigDecimal uxf) {
        this.uxf = uxf;
    }

    public Cabida getCabida() {
        return cabida;
    }

    public void setCabida(Cabida cabida) {
        this.cabida = cabida;
    }

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    public String getMatTipoCobro() {
        return matTipoCobro;
    }

    public void setMatTipoCobro(String matTipoCobro) {
        this.matTipoCobro = matTipoCobro;
    }

    public AImage getFotoCabida() {
        return fotoCabida;
    }

    public void setFotoCabida(AImage fotoCabida) {
        this.fotoCabida = fotoCabida;
    }

    public BigDecimal getMaculatura() {
        return maculatura;
    }

    public void setMaculatura(BigDecimal maculatura) {
        this.maculatura = maculatura;
    }

    public BigDecimal getPlacas() {
        return placas;
    }

    public void setPlacas(BigDecimal placas) {
        this.placas = placas;
    }

    public List<Cabida> getListaCabidas() {
        return listaCabidas;
    }

    public void setListaCabidas(List<Cabida> listaCabidas) {
        this.listaCabidas = listaCabidas;
    }

    public ManoDeObra getManoObra() {
        return manoObra;
    }

    public void setManoObra(ManoDeObra manoObra) {
        this.manoObra = manoObra;
    }

    public String getTipoCobroManoObra() {
        return tipoCobroManoObra;
    }

    public void setTipoCobroManoObra(String tipoCobroManoObra) {
        this.tipoCobroManoObra = tipoCobroManoObra;
    }

    public String getTipoCobroTercerizado() {
        return tipoCobroTercerizado;
    }

    public void setTipoCobroTercerizado(String tipoCobroTercerizado) {
        this.tipoCobroTercerizado = tipoCobroTercerizado;
    }

    public String getTipoCobroAdicional() {
        return tipoCobroAdicional;
    }

    public void setTipoCobroAdicional(String tipoCobroAdicional) {
        this.tipoCobroAdicional = tipoCobroAdicional;
    }

    public ManoDeObra getManoObraAdicional() {
        return manoObraAdicional;
    }

    public void setManoObraAdicional(ManoDeObra manoObraAdicional) {
        this.manoObraAdicional = manoObraAdicional;
    }

    public BigDecimal getValorTotalMaterial() {
        return valorTotalMaterial;
    }

    public void setValorTotalMaterial(BigDecimal valorTotalMaterial) {
        this.valorTotalMaterial = valorTotalMaterial;
    }

    public BigDecimal getValorTotalManoObra() {
        return valorTotalManoObra;
    }

    public void setValorTotalManoObra(BigDecimal valorTotalManoObra) {
        this.valorTotalManoObra = valorTotalManoObra;
    }

    public BigDecimal getValorTotalImpresion() {
        return valorTotalImpresion;
    }

    public void setValorTotalImpresion(BigDecimal valorTotalImpresion) {
        this.valorTotalImpresion = valorTotalImpresion;
    }

    public BigDecimal getNumeroHoras() {
        return numeroHoras;
    }

    public void setNumeroHoras(BigDecimal numeroHoras) {
        this.numeroHoras = numeroHoras;
    }

    public BigDecimal getCatidadVarios() {
        return catidadVarios;
    }

    public void setCatidadVarios(BigDecimal catidadVarios) {
        this.catidadVarios = catidadVarios;
    }

    public Materiales getMaterialesVarios() {
        return materialesVarios;
    }

    public void setMaterialesVarios(Materiales materialesVarios) {
        this.materialesVarios = materialesVarios;
    }

    public BigDecimal getValorVariosTotal() {
        return valorVariosTotal;
    }

    public void setValorVariosTotal(BigDecimal valorVariosTotal) {
        this.valorVariosTotal = valorVariosTotal;
    }

    public BigDecimal getCatidadPrePost() {
        return catidadPrePost;
    }

    public void setCatidadPrePost(BigDecimal catidadPrePost) {
        this.catidadPrePost = catidadPrePost;
    }

    public ManoDeObra getManoObraPrePost() {
        return manoObraPrePost;
    }

    public void setManoObraPrePost(ManoDeObra manoObraPrePost) {
        this.manoObraPrePost = manoObraPrePost;
    }

    public BigDecimal getValorTotalPrePost() {
        return valorTotalPrePost;
    }

    public void setValorTotalPrePost(BigDecimal valorTotalPrePost) {
        this.valorTotalPrePost = valorTotalPrePost;
    }

    public String getTipoCobroManoObraPrePost() {
        return tipoCobroManoObraPrePost;
    }

    public void setTipoCobroManoObraPrePost(String tipoCobroManoObraPrePost) {
        this.tipoCobroManoObraPrePost = tipoCobroManoObraPrePost;
    }

    public List<Terciarizado> getListaTercerizados() {
        return listaTercerizados;
    }

    public void setListaTercerizados(List<Terciarizado> listaTercerizados) {
        this.listaTercerizados = listaTercerizados;
    }

    public Terciarizado getTerciarizado() {
        return terciarizado;
    }

    public void setTerciarizado(Terciarizado terciarizado) {
        this.terciarizado = terciarizado;
    }

    public BigDecimal getCantidadTercerizado() {
        return cantidadTercerizado;
    }

    public void setCantidadTercerizado(BigDecimal cantidadTercerizado) {
        this.cantidadTercerizado = cantidadTercerizado;
    }

    public BigDecimal getTotalTercerizado() {
        return totalTercerizado;
    }

    public void setTotalTercerizado(BigDecimal totalTercerizado) {
        this.totalTercerizado = totalTercerizado;
    }

    public BigDecimal getValorUnitarioTercerizado() {
        return valorUnitarioTercerizado;
    }

    public void setValorUnitarioTercerizado(BigDecimal valorUnitarioTercerizado) {
        this.valorUnitarioTercerizado = valorUnitarioTercerizado;
    }

    public String getDetalleTercerizado() {
        return detalleTercerizado;
    }

    public void setDetalleTercerizado(String detalleTercerizado) {
        this.detalleTercerizado = detalleTercerizado;
    }

    public BigDecimal getCostoTotalPlacas() {
        return costoTotalPlacas;
    }

    public void setCostoTotalPlacas(BigDecimal costoTotalPlacas) {
        this.costoTotalPlacas = costoTotalPlacas;
    }

    public BigDecimal getCantidadHoraMillar() {
        return cantidadHoraMillar;
    }

    public void setCantidadHoraMillar(BigDecimal cantidadHoraMillar) {
        this.cantidadHoraMillar = cantidadHoraMillar;
    }

    public Integer getIdDetCotizacion() {
        return idDetCotizacion;
    }

    public void setIdDetCotizacion(Integer idDetCotizacion) {
        this.idDetCotizacion = idDetCotizacion;
    }

    public BigDecimal getPlacasPorRegistrO() {
        return placasPorRegistrO;
    }

    public void setPlacasPorRegistrO(BigDecimal placasPorRegistrO) {
        this.placasPorRegistrO = placasPorRegistrO;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getDetalleTrabajo() {
        return detalleTrabajo;
    }

    public void setDetalleTrabajo(String detalleTrabajo) {
        this.detalleTrabajo = detalleTrabajo;
    }

    public BigDecimal getDescuentoMano() {
        return descuentoMano;
    }

    public void setDescuentoMano(BigDecimal descuentoMano) {
        this.descuentoMano = descuentoMano;
    }

    public BigDecimal getMetros() {
        return metros;
    }

    public void setMetros(BigDecimal metros) {
        this.metros = metros;
    }

    public BigDecimal getCostoMetro() {
        return costoMetro;
    }

    public void setCostoMetro(BigDecimal costoMetro) {
        this.costoMetro = costoMetro;
    }

    public BigDecimal getCostoTinta() {
        return costoTinta;
    }

    public void setCostoTinta(BigDecimal costoTinta) {
        this.costoTinta = costoTinta;
    }

    public BigDecimal getCostoOperador() {
        return costoOperador;
    }

    public void setCostoOperador(BigDecimal costoOperador) {
        this.costoOperador = costoOperador;
    }

    public BigDecimal getValotM2() {
        return valotM2;
    }

    public void setValotM2(BigDecimal valotM2) {
        this.valotM2 = valotM2;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotalGiganto() {
        return valorTotalGiganto;
    }

    public void setValorTotalGiganto(BigDecimal valorTotalGiganto) {
        this.valorTotalGiganto = valorTotalGiganto;
    }

    public BigDecimal getValorM2xCosto() {
        return valorM2xCosto;
    }

    public void setValorM2xCosto(BigDecimal valorM2xCosto) {
        this.valorM2xCosto = valorM2xCosto;
    }

    public BigDecimal getValorM2xOperador() {
        return valorM2xOperador;
    }

    public void setValorM2xOperador(BigDecimal valorM2xOperador) {
        this.valorM2xOperador = valorM2xOperador;
    }

    public BigDecimal getValorM2xTinta() {
        return valorM2xTinta;
    }

    public void setValorM2xTinta(BigDecimal valorM2xTinta) {
        this.valorM2xTinta = valorM2xTinta;
    }

    public BigDecimal getPliegosImpresionCompaginado() {
        return pliegosImpresionCompaginado;
    }

    public void setPliegosImpresionCompaginado(BigDecimal pliegosImpresionCompaginado) {
        this.pliegosImpresionCompaginado = pliegosImpresionCompaginado;
    }

    public String getDetTipoTrabajo() {
        return detTipoTrabajo;
    }

    public void setDetTipoTrabajo(String detTipoTrabajo) {
        this.detTipoTrabajo = detTipoTrabajo;
    }

    public BigDecimal getPorcentajeDescuentoMaterial() {
        return porcentajeDescuentoMaterial;
    }

    public void setPorcentajeDescuentoMaterial(BigDecimal porcentajeDescuentoMaterial) {
        this.porcentajeDescuentoMaterial = porcentajeDescuentoMaterial;
    }

   
    
    
}
