package imp.entidades;

import imp.entidades.Cliente;
import imp.entidades.DetalleOrdenSinCotizar;
import imp.entidades.SegOrdSinCot;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(OrdenSinCotizacion.class)
public class OrdenSinCotizacion_ { 

    public static volatile SingularAttribute<OrdenSinCotizacion, byte[]> sinPdf;
    public static volatile SingularAttribute<OrdenSinCotizacion, String> sinNumeroFactura;
    public static volatile SingularAttribute<OrdenSinCotizacion, String> sinObservacion;
    public static volatile SingularAttribute<OrdenSinCotizacion, Date> sinFechaEmision;
    public static volatile SingularAttribute<OrdenSinCotizacion, Date> sinHoraCierre;
    public static volatile SingularAttribute<OrdenSinCotizacion, String> sinQuienCrea;
    public static volatile SingularAttribute<OrdenSinCotizacion, String> sinQuienModifica;
    public static volatile SingularAttribute<OrdenSinCotizacion, Integer> sinNumero;
    public static volatile SingularAttribute<OrdenSinCotizacion, String> sinEstado;
    public static volatile SingularAttribute<OrdenSinCotizacion, BigDecimal> sinUtilidad;
    public static volatile SingularAttribute<OrdenSinCotizacion, Date> sinHoraInicio;
    public static volatile SingularAttribute<OrdenSinCotizacion, Date> sinFechaCierre;
    public static volatile SingularAttribute<OrdenSinCotizacion, String> sinDetalleTrabajo;
    public static volatile SingularAttribute<OrdenSinCotizacion, BigDecimal> sinCosto;
    public static volatile SingularAttribute<OrdenSinCotizacion, Boolean> sinAdjPdf;
    public static volatile CollectionAttribute<OrdenSinCotizacion, DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection;
    public static volatile SingularAttribute<OrdenSinCotizacion, byte[]> sinImagen;
    public static volatile SingularAttribute<OrdenSinCotizacion, Cliente> codCliente;
    public static volatile SingularAttribute<OrdenSinCotizacion, Boolean> sinDdjImg;
    public static volatile CollectionAttribute<OrdenSinCotizacion, SegOrdSinCot> detalleSegOrdSinCotCollection;
    public static volatile SingularAttribute<OrdenSinCotizacion, Date> sinFechaModifica;
    public static volatile SingularAttribute<OrdenSinCotizacion, String> sinTipoCotizacion;
    public static volatile SingularAttribute<OrdenSinCotizacion, Date> sinFechaFacturacion;
    public static volatile SingularAttribute<OrdenSinCotizacion, Integer> codOrdenSinCotizacion;
    public static volatile SingularAttribute<OrdenSinCotizacion, String> sinResponsable;
    public static volatile SingularAttribute<OrdenSinCotizacion, BigDecimal> sinTotalUtilidad;
    public static volatile SingularAttribute<OrdenSinCotizacion, String> sinDescripcion;
    public static volatile SingularAttribute<OrdenSinCotizacion, Date> sinFechaInicio;

}