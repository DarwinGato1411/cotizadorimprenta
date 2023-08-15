package imp.entidades;

import imp.entidades.OrdenDeProduccion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(Responsable.class)
public class Responsable_ { 

    public static volatile SingularAttribute<Responsable, String> resTelefono;
    public static volatile SingularAttribute<Responsable, String> resCorreo;
    public static volatile SingularAttribute<Responsable, Integer> codResponsable;
    public static volatile SingularAttribute<Responsable, String> resCedula;
    public static volatile SingularAttribute<Responsable, String> resWeb;
    public static volatile SingularAttribute<Responsable, String> resCargo;
    public static volatile SingularAttribute<Responsable, String> resNombre;
    public static volatile CollectionAttribute<Responsable, OrdenDeProduccion> ordenDeProduccionCollection;
    public static volatile SingularAttribute<Responsable, String> resApellido;
    public static volatile SingularAttribute<Responsable, String> resCelular;
    public static volatile SingularAttribute<Responsable, String> resDireccion;
    public static volatile SingularAttribute<Responsable, String> resContrasena;

}