package imp.entidades;

import imp.entidades.UsuarioSistema;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(SolicitarMaterial.class)
public class SolicitarMaterial_ { 

    public static volatile SingularAttribute<SolicitarMaterial, String> solNumFacCompra;
    public static volatile SingularAttribute<SolicitarMaterial, BigDecimal> solValorCompra;
    public static volatile SingularAttribute<SolicitarMaterial, BigDecimal> solCantidad;
    public static volatile SingularAttribute<SolicitarMaterial, Integer> idSolicita;
    public static volatile SingularAttribute<SolicitarMaterial, String> solDescripcion;
    public static volatile SingularAttribute<SolicitarMaterial, String> solProveedor;
    public static volatile SingularAttribute<SolicitarMaterial, String> solEstado;
    public static volatile SingularAttribute<SolicitarMaterial, Date> solHora;
    public static volatile SingularAttribute<SolicitarMaterial, Date> solFecha;
    public static volatile SingularAttribute<SolicitarMaterial, UsuarioSistema> codUsuarioSistema;

}