package imp.entidades;

import imp.entidades.Cotizacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(EstadoCotizacion.class)
public class EstadoCotizacion_ { 

    public static volatile CollectionAttribute<EstadoCotizacion, Cotizacion> cotizacionCollection;
    public static volatile SingularAttribute<EstadoCotizacion, Integer> codEstCotizacion;
    public static volatile SingularAttribute<EstadoCotizacion, String> estCotSigla;
    public static volatile SingularAttribute<EstadoCotizacion, String> estCotNombre;

}