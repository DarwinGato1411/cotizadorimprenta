package imp.entidades;

import imp.entidades.DetalleCotizacion;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(ManoDeObra.class)
public class ManoDeObra_ { 

    public static volatile SingularAttribute<ManoDeObra, Float> manCostoMillar;
    public static volatile SingularAttribute<ManoDeObra, Float> manCostoTiempo;
    public static volatile SingularAttribute<ManoDeObra, String> manTipoProducto;
    public static volatile SingularAttribute<ManoDeObra, Integer> codManObra;
    public static volatile SingularAttribute<ManoDeObra, Float> manCostoMinMillar;
    public static volatile SingularAttribute<ManoDeObra, Integer> manNumeroTorresImpresion;
    public static volatile SingularAttribute<ManoDeObra, Float> manCostoMinHora;
    public static volatile SingularAttribute<ManoDeObra, Integer> manImpresionHora;
    public static volatile SingularAttribute<ManoDeObra, String> manNombreProducto;
    public static volatile CollectionAttribute<ManoDeObra, DetalleCotizacion> detalleCotizacionCollection;
    public static volatile SingularAttribute<ManoDeObra, String> manPermiteVolteo;
    public static volatile SingularAttribute<ManoDeObra, BigDecimal> manCostoOpcional;
    public static volatile SingularAttribute<ManoDeObra, BigDecimal> manAltoMin;
    public static volatile SingularAttribute<ManoDeObra, BigDecimal> manAlto;
    public static volatile SingularAttribute<ManoDeObra, BigDecimal> manAncho;
    public static volatile SingularAttribute<ManoDeObra, BigDecimal> manAnchoMin;
    public static volatile SingularAttribute<ManoDeObra, Float> manCostoPlancha;

}