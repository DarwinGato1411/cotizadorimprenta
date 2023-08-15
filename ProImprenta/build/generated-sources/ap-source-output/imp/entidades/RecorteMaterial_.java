package imp.entidades;

import imp.entidades.CortezPosibles;
import imp.entidades.DetalleCotizacion;
import imp.entidades.Materiales;
import imp.entidades.RecorteMaterialPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(RecorteMaterial.class)
public class RecorteMaterial_ { 

    public static volatile CollectionAttribute<RecorteMaterial, DetalleCotizacion> detalleCotizacionCollection;
    public static volatile SingularAttribute<RecorteMaterial, String> cmDescripcion;
    public static volatile SingularAttribute<RecorteMaterial, Materiales> materiales;
    public static volatile SingularAttribute<RecorteMaterial, CortezPosibles> cortezPosibles;
    public static volatile SingularAttribute<RecorteMaterial, RecorteMaterialPK> recorteMaterialPK;

}