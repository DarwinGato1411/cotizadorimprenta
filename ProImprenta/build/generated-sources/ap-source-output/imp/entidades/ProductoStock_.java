package imp.entidades;

import imp.entidades.CatalogoProdCompra;
import imp.entidades.EstadoProStock;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(ProductoStock.class)
public class ProductoStock_ { 

    public static volatile SingularAttribute<ProductoStock, Float> prodstValorCompra;
    public static volatile SingularAttribute<ProductoStock, Integer> codProdStock;
    public static volatile SingularAttribute<ProductoStock, EstadoProStock> codEstStock;
    public static volatile SingularAttribute<ProductoStock, String> prodstNombre;
    public static volatile SingularAttribute<ProductoStock, Date> prodstFechaCompra;
    public static volatile SingularAttribute<ProductoStock, Integer> prodstCantidad;
    public static volatile SingularAttribute<ProductoStock, String> prodstNumeroFactura;
    public static volatile SingularAttribute<ProductoStock, Date> prodstFechaPagoFactura;
    public static volatile SingularAttribute<ProductoStock, CatalogoProdCompra> codCatProdCompra;

}