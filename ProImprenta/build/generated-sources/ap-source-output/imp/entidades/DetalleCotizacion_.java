package imp.entidades;

import imp.entidades.Cabida;
import imp.entidades.Cotizacion;
import imp.entidades.ManoDeObra;
import imp.entidades.RecorteMaterial;
import imp.entidades.Terciarizado;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(DetalleCotizacion.class)
public class DetalleCotizacion_ { 

    public static volatile SingularAttribute<DetalleCotizacion, Terciarizado> codTercearizado;
    public static volatile SingularAttribute<DetalleCotizacion, Integer> detColorAnverso;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detDescuentoMano;
    public static volatile SingularAttribute<DetalleCotizacion, Integer> codDetalle;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detCantidadSolicitada;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detSubTotal;
    public static volatile SingularAttribute<DetalleCotizacion, Integer> detColorReverso;
    public static volatile SingularAttribute<DetalleCotizacion, String> detPliegos;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detAnchoSolicitado;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detAnchoSolicitadoCerrado;
    public static volatile SingularAttribute<DetalleCotizacion, Cabida> codCabida;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detAltoSolicitado;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detAltoSolicitadoCerrado;
    public static volatile SingularAttribute<DetalleCotizacion, String> detMotivo;
    public static volatile SingularAttribute<DetalleCotizacion, RecorteMaterial> recorteMaterial;
    public static volatile SingularAttribute<DetalleCotizacion, ManoDeObra> codManObra;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detTipoProducto;
    public static volatile SingularAttribute<DetalleCotizacion, String> detTipoTrabajo;
    public static volatile SingularAttribute<DetalleCotizacion, String> detCantHoraMillar;
    public static volatile SingularAttribute<DetalleCotizacion, String> detProductoSolicitado;
    public static volatile SingularAttribute<DetalleCotizacion, String> detTipoDetalle;
    public static volatile SingularAttribute<DetalleCotizacion, String> detTipoCobro;
    public static volatile SingularAttribute<DetalleCotizacion, String> detUnidadCantidad;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detValorUnitario;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detDescuentoMaterial;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detUxf;
    public static volatile SingularAttribute<DetalleCotizacion, BigDecimal> detCantidadPlacas;
    public static volatile SingularAttribute<DetalleCotizacion, Cotizacion> codCotizacion;

}