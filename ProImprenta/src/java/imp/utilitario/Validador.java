/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

import imp.controladoresview.CotizacionViewModel;
import java.util.Map;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
/**
 *
 * @author gato
 */
public class Validador extends AbstractValidator {
    public void validate(ValidationContext ctx) {
        //all the bean properties
        Map<String,Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
         
        //first let's check the passwords match
        validateCedula(ctx, (String)beanProps.get("password").getValue());
        validateEmail(ctx, (String)beanProps.get("email").getValue());
       
    }
    private void validateEmail(ValidationContext ctx, String email) {
        if(email == null || !email.matches(".+@.+\\.[a-z]+")) {
            this.addInvalidMessage(ctx, "email", "Ingrese un correo valido!");            
        }
    }
     private void validateCedula(ValidationContext ctx, String cedula) {
        if(cedula == null || !cedula.matches("/^[0-9]+$/")) {
            this.addInvalidMessage(ctx, "email", "Ingrese una cedula correcta!");            
        }
    }
    
}
