package imp.entidades;

import imp.entidades.Cotizacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2023-08-16T16:20:34")
@StaticMetamodel(DocumentoGenerado.class)
public class DocumentoGenerado_ { 

    public static volatile SingularAttribute<DocumentoGenerado, String> docGenResponsable;
    public static volatile SingularAttribute<DocumentoGenerado, Integer> codDocGenerado;
    public static volatile SingularAttribute<DocumentoGenerado, Date> docGenFecha;
    public static volatile SingularAttribute<DocumentoGenerado, Cotizacion> codCotizacion;

}