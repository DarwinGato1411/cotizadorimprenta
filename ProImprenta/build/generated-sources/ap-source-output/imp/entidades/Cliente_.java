package imp.entidades;

import imp.entidades.Cotizacion;
import imp.entidades.OrdenSinCotizacion;
import imp.entidades.PersonaContacto;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, String> ruc;
    public static volatile SingularAttribute<Cliente, Integer> estadoCliente;
    public static volatile SingularAttribute<Cliente, String> direccion;
    public static volatile SingularAttribute<Cliente, Integer> codCliente;
    public static volatile SingularAttribute<Cliente, String> tipoCliente;
    public static volatile SingularAttribute<Cliente, String> razonSocial;
    public static volatile CollectionAttribute<Cliente, Cotizacion> cotizacionCollection;
    public static volatile SingularAttribute<Cliente, String> correo;
    public static volatile SingularAttribute<Cliente, String> nombreComercial;
    public static volatile SingularAttribute<Cliente, String> celular;
    public static volatile CollectionAttribute<Cliente, OrdenSinCotizacion> ordenSinCotizacionCollection;
    public static volatile SingularAttribute<Cliente, String> direccionWeb;
    public static volatile SingularAttribute<Cliente, String> telefono;
    public static volatile CollectionAttribute<Cliente, PersonaContacto> personaContactoCollection;
    public static volatile SingularAttribute<Cliente, String> vendedorAsignado;

}