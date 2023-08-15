package imp.entidades;

import imp.entidades.Operacion;
import imp.entidades.SolicitarMaterial;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2021-03-26T15:24:59")
@StaticMetamodel(UsuarioSistema.class)
public class UsuarioSistema_ { 

    public static volatile SingularAttribute<UsuarioSistema, String> usuCorreo;
    public static volatile SingularAttribute<UsuarioSistema, String> usuRespuesta;
    public static volatile CollectionAttribute<UsuarioSistema, SolicitarMaterial> solicitarMaterialCollection;
    public static volatile SingularAttribute<UsuarioSistema, Date> usuFechaModificacion;
    public static volatile SingularAttribute<UsuarioSistema, String> usuNombreUsuario;
    public static volatile SingularAttribute<UsuarioSistema, String> usuPregunta;
    public static volatile SingularAttribute<UsuarioSistema, Integer> usuNivelAcceso;
    public static volatile SingularAttribute<UsuarioSistema, Integer> codUsuarioSistema;
    public static volatile SingularAttribute<UsuarioSistema, String> usuUsuario;
    public static volatile CollectionAttribute<UsuarioSistema, Operacion> operacionCollection;
    public static volatile SingularAttribute<UsuarioSistema, Boolean> usuActivo;
    public static volatile SingularAttribute<UsuarioSistema, Date> usuFechaCreacion;
    public static volatile SingularAttribute<UsuarioSistema, String> usuContrasena;

}