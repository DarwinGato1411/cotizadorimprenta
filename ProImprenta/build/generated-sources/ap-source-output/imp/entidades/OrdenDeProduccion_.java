package imp.entidades;

import imp.entidades.Cotizacion;
import imp.entidades.EstadoOrdenProduccion;
import imp.entidades.Responsable;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(OrdenDeProduccion.class)
public class OrdenDeProduccion_ { 

    public static volatile SingularAttribute<OrdenDeProduccion, String> ordNumero;
    public static volatile SingularAttribute<OrdenDeProduccion, Responsable> codResponsable;
    public static volatile SingularAttribute<OrdenDeProduccion, Date> ordFechaCierre;
    public static volatile SingularAttribute<OrdenDeProduccion, Integer> codOrdenProduccion;
    public static volatile SingularAttribute<OrdenDeProduccion, String> ordSecuencial;
    public static volatile SingularAttribute<OrdenDeProduccion, Date> ordHoraDespacho;
    public static volatile SingularAttribute<OrdenDeProduccion, byte[]> ordPdf;
    public static volatile SingularAttribute<OrdenDeProduccion, Date> ordHoraCerre;
    public static volatile SingularAttribute<OrdenDeProduccion, String> ordEstadoCierre;
    public static volatile SingularAttribute<OrdenDeProduccion, String> ordResponsable;
    public static volatile SingularAttribute<OrdenDeProduccion, String> ordDescripcion;
    public static volatile SingularAttribute<OrdenDeProduccion, Date> ordHoraInicio;
    public static volatile SingularAttribute<OrdenDeProduccion, Date> fechaCreacion;
    public static volatile SingularAttribute<OrdenDeProduccion, EstadoOrdenProduccion> codEstadoOrden;
    public static volatile SingularAttribute<OrdenDeProduccion, byte[]> ordImg;
    public static volatile SingularAttribute<OrdenDeProduccion, Cotizacion> codCotizacion;
    public static volatile SingularAttribute<OrdenDeProduccion, Date> fechaDespacho;

}