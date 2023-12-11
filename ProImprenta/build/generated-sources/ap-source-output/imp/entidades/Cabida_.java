package imp.entidades;

import imp.entidades.DetalleCotizacion;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(Cabida.class)
public class Cabida_ { 

    public static volatile SingularAttribute<Cabida, byte[]> cabGrafico;
    public static volatile SingularAttribute<Cabida, Integer> cabCategoria;
    public static volatile CollectionAttribute<Cabida, DetalleCotizacion> detalleCotizacionCollection;
    public static volatile SingularAttribute<Cabida, Integer> codCabida;
    public static volatile SingularAttribute<Cabida, BigDecimal> cabLargo;
    public static volatile SingularAttribute<Cabida, String> cabDescripcion;
    public static volatile SingularAttribute<Cabida, BigDecimal> cabAncho;
    public static volatile SingularAttribute<Cabida, BigDecimal> cabUxf;

}