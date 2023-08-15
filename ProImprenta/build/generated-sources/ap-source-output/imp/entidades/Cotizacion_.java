package imp.entidades;

import imp.entidades.Cliente;
import imp.entidades.DetalleCotizacion;
import imp.entidades.DocumentoGenerado;
import imp.entidades.EstadoCotizacion;
import imp.entidades.OrdenDeProduccion;
import imp.entidades.ParametrizacionPorcentajes;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(Cotizacion.class)
public class Cotizacion_ { 

    public static volatile SingularAttribute<Cotizacion, Date> cotFechaEmision;
    public static volatile SingularAttribute<Cotizacion, BigDecimal> cotPorcentajeGanancia;
    public static volatile SingularAttribute<Cotizacion, BigDecimal> cotDescuento;
    public static volatile SingularAttribute<Cotizacion, String> cotTotalLetras;
    public static volatile SingularAttribute<Cotizacion, String> cotNumeroFacturado;
    public static volatile SingularAttribute<Cotizacion, EstadoCotizacion> codEstCotizacion;
    public static volatile SingularAttribute<Cotizacion, Date> cotFechaFacturacion;
    public static volatile CollectionAttribute<Cotizacion, OrdenDeProduccion> ordenDeProduccionCollection;
    public static volatile SingularAttribute<Cotizacion, String> cotNumero;
    public static volatile SingularAttribute<Cotizacion, Cliente> codCliente;
    public static volatile CollectionAttribute<Cotizacion, DocumentoGenerado> documentoGeneradoCollection;
    public static volatile SingularAttribute<Cotizacion, String> cotUnificar;
    public static volatile SingularAttribute<Cotizacion, BigDecimal> cotTotal;
    public static volatile CollectionAttribute<Cotizacion, DetalleCotizacion> detalleCotizacionCollection;
    public static volatile SingularAttribute<Cotizacion, ParametrizacionPorcentajes> codParametrizacion;
    public static volatile SingularAttribute<Cotizacion, String> cotObservacion;
    public static volatile SingularAttribute<Cotizacion, BigDecimal> cotValorAdicional;
    public static volatile SingularAttribute<Cotizacion, String> cotTipoCotizacion;
    public static volatile SingularAttribute<Cotizacion, BigDecimal> cotSubtotal;
    public static volatile SingularAttribute<Cotizacion, Integer> codCotizacion;

}