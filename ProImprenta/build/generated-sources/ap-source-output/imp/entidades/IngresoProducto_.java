package imp.entidades;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(IngresoProducto.class)
public class IngresoProducto_ { 

    public static volatile SingularAttribute<IngresoProducto, Integer> codigo;
    public static volatile SingularAttribute<IngresoProducto, String> ubiNombre;
    public static volatile SingularAttribute<IngresoProducto, String> prodNombre;
    public static volatile SingularAttribute<IngresoProducto, String> catDescripcion;
    public static volatile SingularAttribute<IngresoProducto, Long> id;
    public static volatile SingularAttribute<IngresoProducto, BigDecimal> cantidad;
    public static volatile SingularAttribute<IngresoProducto, String> tipOperacion;

}