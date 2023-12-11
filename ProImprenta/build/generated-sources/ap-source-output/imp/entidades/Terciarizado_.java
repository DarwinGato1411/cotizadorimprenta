package imp.entidades;

import imp.entidades.DetalleCotizacion;
import imp.entidades.DetalleOrdenSinCotizar;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(Terciarizado.class)
public class Terciarizado_ { 

    public static volatile SingularAttribute<Terciarizado, Integer> codTercearizado;
    public static volatile SingularAttribute<Terciarizado, Float> terCostoUnitario;
    public static volatile SingularAttribute<Terciarizado, Float> terCostoOpcional;
    public static volatile CollectionAttribute<Terciarizado, DetalleCotizacion> detalleCotizacionCollection;
    public static volatile SingularAttribute<Terciarizado, Float> terCostoMillar;
    public static volatile SingularAttribute<Terciarizado, String> terProveedor;
    public static volatile CollectionAttribute<Terciarizado, DetalleOrdenSinCotizar> detalleOrdenSinCotizarCollection;
    public static volatile SingularAttribute<Terciarizado, String> terNombreProducto;

}