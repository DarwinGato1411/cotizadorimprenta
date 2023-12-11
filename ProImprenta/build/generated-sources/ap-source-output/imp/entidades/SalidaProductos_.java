package imp.entidades;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(SalidaProductos.class)
public class SalidaProductos_ { 

    public static volatile SingularAttribute<SalidaProductos, Integer> codigo;
    public static volatile SingularAttribute<SalidaProductos, String> ubiNombre;
    public static volatile SingularAttribute<SalidaProductos, String> prodNombre;
    public static volatile SingularAttribute<SalidaProductos, String> catDescripcion;
    public static volatile SingularAttribute<SalidaProductos, Long> id;
    public static volatile SingularAttribute<SalidaProductos, BigDecimal> cantidad;
    public static volatile SingularAttribute<SalidaProductos, String> tipOperacion;

}