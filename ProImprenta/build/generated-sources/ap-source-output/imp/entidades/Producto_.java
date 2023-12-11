package imp.entidades;

import imp.entidades.Categoria;
import imp.entidades.DetalleOrdenSinCotizar;
import imp.entidades.Operacion;
import imp.entidades.Ubicacion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, Categoria> codCategoria;
    public static volatile CollectionAttribute<Producto, DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection;
    public static volatile SingularAttribute<Producto, BigDecimal> prodCostoCompra;
    public static volatile SingularAttribute<Producto, BigDecimal> prodCostoVenta;
    public static volatile SingularAttribute<Producto, Integer> codProducto;
    public static volatile CollectionAttribute<Producto, Operacion> operacionCollection;
    public static volatile SingularAttribute<Producto, BigDecimal> prodCantidadMinima;
    public static volatile SingularAttribute<Producto, String> prodSerie;
    public static volatile SingularAttribute<Producto, String> prodNombre;
    public static volatile SingularAttribute<Producto, BigDecimal> prodValorIncial;
    public static volatile SingularAttribute<Producto, String> prodUnidadMedida;
    public static volatile SingularAttribute<Producto, Date> prodFechaRegistro;
    public static volatile SingularAttribute<Producto, Ubicacion> codUbicacion;
    public static volatile SingularAttribute<Producto, String> prodMarca;

}