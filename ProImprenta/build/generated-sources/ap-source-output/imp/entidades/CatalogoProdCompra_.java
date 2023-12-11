package imp.entidades;

import imp.entidades.ProductoStock;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(CatalogoProdCompra.class)
public class CatalogoProdCompra_ { 

    public static volatile SingularAttribute<CatalogoProdCompra, Float> catComValorMillar;
    public static volatile SingularAttribute<CatalogoProdCompra, String> catComNombreProducto;
    public static volatile SingularAttribute<CatalogoProdCompra, Float> catComValorUnidad;
    public static volatile CollectionAttribute<CatalogoProdCompra, ProductoStock> productoStockCollection;
    public static volatile SingularAttribute<CatalogoProdCompra, String> catComNombreProveedor;
    public static volatile SingularAttribute<CatalogoProdCompra, Integer> codCatProdCompra;

}