package imp.entidades;

import imp.entidades.ProductoStock;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(EstadoProStock.class)
public class EstadoProStock_ { 

    public static volatile SingularAttribute<EstadoProStock, String> estStockSigla;
    public static volatile SingularAttribute<EstadoProStock, Integer> codEstStock;
    public static volatile SingularAttribute<EstadoProStock, String> estStockNombre;
    public static volatile CollectionAttribute<EstadoProStock, ProductoStock> productoStockCollection;

}