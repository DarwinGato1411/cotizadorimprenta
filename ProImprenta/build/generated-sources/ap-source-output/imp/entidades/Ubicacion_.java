package imp.entidades;

import imp.entidades.Operacion;
import imp.entidades.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(Ubicacion.class)
public class Ubicacion_ { 

    public static volatile CollectionAttribute<Ubicacion, Operacion> operacionCollection;
    public static volatile SingularAttribute<Ubicacion, String> ubiNombre;
    public static volatile SingularAttribute<Ubicacion, Integer> codUbicacion;
    public static volatile CollectionAttribute<Ubicacion, Producto> productoCollection;

}