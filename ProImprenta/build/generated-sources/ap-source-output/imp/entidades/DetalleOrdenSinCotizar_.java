package imp.entidades;

import imp.entidades.OrdenSinCotizacion;
import imp.entidades.Producto;
import imp.entidades.Terciarizado;
import imp.entidades.TipoTrabajo;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(DetalleOrdenSinCotizar.class)
public class DetalleOrdenSinCotizar_ { 

    public static volatile SingularAttribute<DetalleOrdenSinCotizar, Terciarizado> codTercearizado;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, TipoTrabajo> idTipoTrabajo;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, BigDecimal> detsinCatidadCantida;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, Producto> codProducto;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, String> tipoItem;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, String> sinTipo;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, String> detsinDescripcion;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, Integer> codDetSinCotizar;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, BigDecimal> detsinSubtotal;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, BigDecimal> detsinValor;
    public static volatile SingularAttribute<DetalleOrdenSinCotizar, OrdenSinCotizacion> codOrdenSinCotizacion;

}