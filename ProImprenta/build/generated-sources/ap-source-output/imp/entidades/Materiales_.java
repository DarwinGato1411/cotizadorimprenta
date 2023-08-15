package imp.entidades;

import imp.entidades.RecorteMaterial;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(Materiales.class)
public class Materiales_ { 

    public static volatile SingularAttribute<Materiales, Float> matCosto;
    public static volatile SingularAttribute<Materiales, String> matTipo;
    public static volatile SingularAttribute<Materiales, byte[]> matImagen;
    public static volatile SingularAttribute<Materiales, String> matNombre;
    public static volatile SingularAttribute<Materiales, BigDecimal> matAlto;
    public static volatile CollectionAttribute<Materiales, RecorteMaterial> recorteMaterialCollection;
    public static volatile SingularAttribute<Materiales, BigDecimal> matAncho;
    public static volatile SingularAttribute<Materiales, Integer> codMaterial;

}