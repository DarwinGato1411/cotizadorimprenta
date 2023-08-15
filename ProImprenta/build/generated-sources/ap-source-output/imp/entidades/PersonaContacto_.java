package imp.entidades;

import imp.entidades.Cliente;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(PersonaContacto.class)
public class PersonaContacto_ { 

    public static volatile SingularAttribute<PersonaContacto, String> perConDireccion;
    public static volatile SingularAttribute<PersonaContacto, String> perConNombre;
    public static volatile SingularAttribute<PersonaContacto, String> perConCelular;
    public static volatile SingularAttribute<PersonaContacto, Integer> codPerContacto;
    public static volatile SingularAttribute<PersonaContacto, String> perConTelefono;
    public static volatile SingularAttribute<PersonaContacto, String> perConCorreo;
    public static volatile SingularAttribute<PersonaContacto, Cliente> codCliente;

}