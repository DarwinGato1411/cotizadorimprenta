package imp.entidades;

import imp.entidades.Categoria;
import imp.entidades.Producto;
import imp.entidades.TipoOperacion;
import imp.entidades.Ubicacion;
import imp.entidades.UsuarioSistema;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(Operacion.class)
public class Operacion_ { 

    public static volatile SingularAttribute<Operacion, Date> opeFecha;
    public static volatile SingularAttribute<Operacion, UsuarioSistema> idUsuario;
    public static volatile SingularAttribute<Operacion, Categoria> codCategoria;
    public static volatile SingularAttribute<Operacion, String> opeAreaDespacho;
    public static volatile SingularAttribute<Operacion, BigDecimal> opeCatidad;
    public static volatile SingularAttribute<Operacion, Integer> codOperacion;
    public static volatile SingularAttribute<Operacion, Producto> codProducto;
    public static volatile SingularAttribute<Operacion, BigDecimal> opeCostoUltimaCompra;
    public static volatile SingularAttribute<Operacion, String> opeReferencia;
    public static volatile SingularAttribute<Operacion, String> opeFactura;
    public static volatile SingularAttribute<Operacion, String> opeConcepo;
    public static volatile SingularAttribute<Operacion, TipoOperacion> codTipoOperacion;
    public static volatile SingularAttribute<Operacion, Ubicacion> codUbicacion;
    public static volatile SingularAttribute<Operacion, String> opeNombreDespacho;
    public static volatile SingularAttribute<Operacion, String> opeEstadoProducto;

}