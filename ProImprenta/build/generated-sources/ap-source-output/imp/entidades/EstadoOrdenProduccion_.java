package imp.entidades;

import imp.entidades.OrdenDeProduccion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(EstadoOrdenProduccion.class)
public class EstadoOrdenProduccion_ { 

    public static volatile SingularAttribute<EstadoOrdenProduccion, String> estOrdNombre;
    public static volatile SingularAttribute<EstadoOrdenProduccion, Integer> codEstadoOrden;
    public static volatile CollectionAttribute<EstadoOrdenProduccion, OrdenDeProduccion> ordenDeProduccionCollection;
    public static volatile SingularAttribute<EstadoOrdenProduccion, String> estOrdSigla;

}