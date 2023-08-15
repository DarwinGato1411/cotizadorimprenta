package imp.entidades;

import imp.entidades.RecorteMaterial;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(CortezPosibles.class)
public class CortezPosibles_ { 

    public static volatile SingularAttribute<CortezPosibles, BigDecimal> cortAlto;
    public static volatile SingularAttribute<CortezPosibles, BigDecimal> cortAncho;
    public static volatile CollectionAttribute<CortezPosibles, RecorteMaterial> recorteMaterialCollection;
    public static volatile SingularAttribute<CortezPosibles, byte[]> cortImagen;
    public static volatile SingularAttribute<CortezPosibles, String> cortDescripcion;
    public static volatile SingularAttribute<CortezPosibles, Integer> codFxp;
    public static volatile SingularAttribute<CortezPosibles, Integer> codCorte;

}