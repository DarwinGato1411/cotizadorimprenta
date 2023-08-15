package imp.entidades;

import imp.entidades.Cotizacion;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(ParametrizacionPorcentajes.class)
public class ParametrizacionPorcentajes_ { 

    public static volatile SingularAttribute<ParametrizacionPorcentajes, String> porDescripcion;
    public static volatile SingularAttribute<ParametrizacionPorcentajes, BigDecimal> porGanancia;
    public static volatile CollectionAttribute<ParametrizacionPorcentajes, Cotizacion> cotizacionCollection;
    public static volatile SingularAttribute<ParametrizacionPorcentajes, Integer> codParametrizacion;
    public static volatile SingularAttribute<ParametrizacionPorcentajes, BigDecimal> porPerdida;
    public static volatile SingularAttribute<ParametrizacionPorcentajes, BigDecimal> porExtra;

}