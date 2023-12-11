package imp.entidades;

import imp.entidades.DetalleOrdenSinCotizar;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(TipoTrabajo.class)
public class TipoTrabajo_ { 

    public static volatile SingularAttribute<TipoTrabajo, Integer> idTipoTrabajo;
    public static volatile CollectionAttribute<TipoTrabajo, DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection;
    public static volatile SingularAttribute<TipoTrabajo, String> tipoComentario;
    public static volatile SingularAttribute<TipoTrabajo, String> tipoDescripcion;

}