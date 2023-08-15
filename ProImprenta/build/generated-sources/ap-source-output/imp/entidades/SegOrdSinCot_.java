package imp.entidades;

import imp.entidades.OrdenSinCotizacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(SegOrdSinCot.class)
public class SegOrdSinCot_ { 

    public static volatile SingularAttribute<SegOrdSinCot, String> segObservacion;
    public static volatile SingularAttribute<SegOrdSinCot, OrdenSinCotizacion> ordCodOrdenSinCotizacion;
    public static volatile SingularAttribute<SegOrdSinCot, Integer> segSinNumero;
    public static volatile SingularAttribute<SegOrdSinCot, Integer> codSegSinCotizar;
    public static volatile SingularAttribute<SegOrdSinCot, String> segResponsable;
    public static volatile SingularAttribute<SegOrdSinCot, Date> segFechaModifica;
    public static volatile SingularAttribute<SegOrdSinCot, String> segSinDescripcion;

}