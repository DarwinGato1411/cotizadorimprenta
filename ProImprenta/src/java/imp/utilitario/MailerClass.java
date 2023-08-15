/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;

import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;
import javax.mail.Transport;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;

/**
 * Clase que permite el envio de e-mails utilizando el API javamail.
 *
 */
public class MailerClass {

    /**
     * Recupera el nombre del catálogo descrito en la enumeración
     *
     * @param categoria nombre del parametroa a buscar
     * @return
     */
    public String getConfiguracionCorreo(String categoria) {
//        Set<BeCatalogo> dato = ofertaServicio.getCatalogo1(categoria);
//        if (dato.iterator().hasNext()) {
//            return dato.iterator().next().getNbCatalogo();
//        }
        return null;
    }

    /**
     * Método que envía al mail las credenciales de acceso al sistema
     *
     * @param address Dirección de correo electronico
     * @param mensaje Contenido del mensaje
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean sendMail(List<String> address, String mensaje,
            String pathAdjunto, String asuntoInf,
            String nombreArchivoMail, String rutaFTP)
            throws java.rmi.RemoteException {

        try {
            System.out.println("INGRESA AL ENVIO");
            String asunto = asuntoInf;
            String host = "smtp.gmail.com";
            String port = "587";
            String from = "imdiquito@gmail.com";
            String protocol = "smtp";
            String usuarioSmpt = "imdiquito@gmail.com";
            String password = "mspmsp506";

            // Propiedades de la conexión
            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.user", usuarioSmpt);
            properties.setProperty("mail.smtp.password", password);
            properties.setProperty("mail.smtp.port", port);
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.debug", "false");
            // Setup Port
            properties.put("mail.smtp.ssl.trust", host);
            SmtpAuthenticator auth = new SmtpAuthenticator();
            // Get the default Session object.
            Session session = Session.getDefaultInstance(properties, auth);
            MimeMessage m = new MimeMessage(session);
            Address addressfrom = new InternetAddress();
            InternetAddress[] recipientAddress = new InternetAddress[address.size()];
            int count = 0;
            for (String item : address) {
                recipientAddress[count] = new InternetAddress(item.trim());
                count++;
            }

            Address[] addresTto = recipientAddress;



            m.setFrom(addressfrom);

            BodyPart texto = new MimeBodyPart();
//            texto.setText("Informacion que  se desee enviar");
            String linkFacebook = "https://www.facebook.com/Imagen.Digital.Impresiones/timeline";
            String linkPagina = "http://www.imagenec.com/";

            texto.setContent("<h4>" + mensaje + "</h4><br>"
                    + "<IMG SRC='" + rutaFTP + "'>"
                    + "<table>\n"
                    + "	<tr>\n"
                    + "	<td>Visitanos en nuestra página oficial:\n"
                    + "	</td>\n"
                    + "	<td>\n"
                    + "	<a href=" + linkPagina + ">  " + linkPagina + " </a>\n"
                    + "	</td>\n"
                    + "	</tr>\n"
                    + "	\n"
                    + "    <tr>\n"
                    + "	<td>Visitanos en facebook:</td>\n"
                    + "	<td>\n"
                    + "	<a href=" + linkFacebook + "> " + linkFacebook + "</a>\n"
                    + "	</td></tr>\n"
                    + " <tr>\n"
                    + " <td>Contactenos:</td>\n"
                    + "	<td>Pontevedra N24-275 entre Guipuzcoa y Vizcaya (A una cuadra del Cine Ocho y Medio, sector Floresta)\n"
                    + "Quito</td>"
                    + "</tr>\n"
                    + " <tr>\n"
                    + "	<td>Telefax:</td><td> (593 2) 2 904 639</td>\n"
                    + "</tr>\n"
                    + " <tr>\n"
                    + "	<td>Movil:</td><td> (593 2) 9982 37 099 / 098 3515 718</td>\n"
                    + "</tr>\n"
                    + " <tr>\n"
                    + "	<td>Ventas: </td><td>ventas@imagenec.com</td>\n"
                    + "</tr>\n"
                    + " <tr>\n"
                    + "	<td>Gerencia:</td><td> barlin@imagenec.com</td>\n"
                    + "</tr>\n"
                    + "</table>", "text/html");

            MimeMultipart multiParte = new MimeMultipart();
            // inicio adjunto

            if (pathAdjunto.equals("")) {
            } else {
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(new DataHandler(new FileDataSource(
                        pathAdjunto)));
                adjunto.setFileName(nombreArchivoMail);
                multiParte.addBodyPart(adjunto);
            }


            multiParte.addBodyPart(texto);

            m.setRecipients(Message.RecipientType.TO, addresTto);
//            m.setRecipients(Message.RecipientType.BCC, from);
            m.setSubject(asunto);
            m.setSentDate(new java.util.Date());
//             m.setContent(dirDatos, "text/plain");
            m.setContent(multiParte);

            Transport t = session.getTransport(protocol);
//             t.connect();
            t.connect(host, usuarioSmpt, password);
            t.send(m);
            t.close();
            return true;
        } catch (javax.mail.MessagingException e) {
            System.out.println("error" + e);
            e.printStackTrace();

            return false;
        }
    }

    class SmtpAuthenticator extends Authenticator {

        public SmtpAuthenticator() {

            super();
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "darwin.morocho@bestsystemec.com";
            String password = "***2018*+best";

            return new PasswordAuthentication(username, password);


        }
    }

    //envio de mail simple
//    
//      m.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(address));
    public boolean sendMailSimple(String address, String mensaje,
            String pathAdjunto, String asuntoInf,
            String nombreArchivoMail, String rutaFTP)
            throws java.rmi.RemoteException {

        try {

            System.out.println("INGRESA AL ENVIO");
            String asunto = asuntoInf;
            String host = "mail.bestsystemec.com";
            String port = "587";
            String from = "darwin.morocho@bestsystemec.com";
            String protocol = "smtp";
            String usuarioSmpt = "darwin.morocho@bestsystemec.com";
            String password = "***2018*+best";

            // Propiedades de la conexión
            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.user", usuarioSmpt);
            properties.setProperty("mail.smtp.password", password);
            properties.setProperty("mail.smtp.port", port);
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.debug", "false");
            // Setup Port
            properties.put("mail.smtp.ssl.trust", host);
            SmtpAuthenticator auth = new SmtpAuthenticator();
            // Get the default Session object.
            Session session = Session.getDefaultInstance(properties, auth);
            MimeMessage m = new MimeMessage(session);
            Address addressfrom = new InternetAddress();



            m.setFrom(addressfrom);

            BodyPart texto = new MimeBodyPart();
//            texto.setText("Informacion que  se desee enviar");
            String linkFacebook = "https://www.facebook.com/Imagen.Digital.Impresiones/";
            String linkPagina = "https://bestsystemec.com/";

            texto.setContent("<h4>" + mensaje + "</h4><br>"
                    + "<IMG SRC='" + rutaFTP + "'>"
                    + "<table>\n"
                    //                    + "	<tr>\n"
                    //                    + "	<td>Visitanos en nuestra página oficial:\n"
                    //                    + "	</td>\n"
                    //                    + "	<td>\n"
                    //                    + "	<a href=" + linkPagina + ">  " + linkPagina + " </a>\n"
                    //                    + "	</td>\n"
                    //                    + "	</tr>\n"
                    + "	\n"
                    + "    <tr>\n"
                    + "	<td>Visitanos en facebook:</td>\n"
                    + "	<td>\n"
                    + "	<a href=" + linkFacebook + "> " + linkFacebook + "</a>\n"
                    + "	</td></tr>\n"
                    + " <tr>\n"
                    + " <td>Contactenos:</td>\n"
                    + "	<td>Pontevedra N24-275 entre Guipuzcoa y Vizcaya (A una cuadra del Cine Ocho y Medio, sector Floresta)\n"
                    + "593 Quito \n"
                    + "</td>"
                    + "</tr>\n"
                    + " <tr>\n"
                    + "	<td>Telefax:</td><td> (02) 290-4639</td>\n"
                    + "</tr>\n"
                    + " <tr>\n"
                    + "	<a href= www.imagenec.com >  www.imagenec.com </a>\n"
                    + "	<td>Pagina web:</td><td> www.imagenec.com</td>\n"
                    + "</tr>\n"
                    //                    + " <tr>\n"
                    //                    + "	<td>Ventas: </td><td>jsaltos@imagenec.com / info@imagenec.com</td>\n"
                    //                    + "</tr>\n"
                    //                    + " <tr>\n"
                    //                    + "	<td>Gerencia:</td><td> barlin@imagenec.com</td>\n"
                    //                    + "</tr>\n"
                    + "</table>", "text/html");

            MimeMultipart multiParte = new MimeMultipart();
            // inicio adjunto

            m.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            multiParte.addBodyPart(texto);

//            m.setRecipients(Message.RecipientType.TO, addresTto);
//            m.setRecipients(Message.RecipientType.BCC, from);
            m.setSubject(asunto);
            m.setSentDate(new java.util.Date());
//             m.setContent(dirDatos, "text/plain");
            m.setContent(multiParte);

            Transport t = session.getTransport(protocol);
//             t.connect();
            t.connect(host, usuarioSmpt, password);
            t.send(m);
            t.close();
            return true;
        } catch (javax.mail.MessagingException e) {
            System.out.println("error" + e);
            e.printStackTrace();

            return false;
        }
    }

    public boolean sendMailSimplePedido(String address, String mensaje,
            String asuntoInf,
            String solicitante)
            throws java.rmi.RemoteException {

        try {
//            System.out.println("INGRESA AL ENVIO");
//            String asunto = asuntoInf;
//            String host = "smtp.gmail.com";
//            String port = "587";
//            String from = "supapeleria2016@gmail.com";
//            String protocol = "smtp";
//            String usuarioSmpt = "supapeleria2016@gmail.com";
//            String password = "Dereckandre02";

            System.out.println("INGRESA AL ENVIO");
            String asunto = asuntoInf;
            String host = "smtp.gmail.com";
            String port = "587";
            String from = "imdiquito@gmail.com";
            String protocol = "smtp";
            String usuarioSmpt = "imdiquito@gmail.com";
            String password = "mspmsp506";

            // Propiedades de la conexión
            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.user", usuarioSmpt);
            properties.setProperty("mail.smtp.password", password);
            properties.setProperty("mail.smtp.port", port);
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.debug", "false");
            // Setup Port
            properties.put("mail.smtp.ssl.trust", host);
            SmtpAuthenticator auth = new SmtpAuthenticator();
            // Get the default Session object.
            Session session = Session.getDefaultInstance(properties, auth);
            MimeMessage m = new MimeMessage(session);
            Address addressfrom = new InternetAddress();



            m.setFrom(addressfrom);

            BodyPart texto = new MimeBodyPart();
            texto.setContent("<h4>" + mensaje + "</h4><br>"
                    + "<table>\n"
                    + "	<td>SOLICITADO POR: " + solicitante.toUpperCase() + " </td>"
                    + "</tr>\n"
                    + " <tr>\n"
                    + "</table>", "text/html");

            MimeMultipart multiParte = new MimeMultipart();
            // inicio adjunto

            m.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            multiParte.addBodyPart(texto);

//            m.setRecipients(Message.RecipientType.TO, addresTto);
//            m.setRecipients(Message.RecipientType.BCC, from);
            m.setSubject(asunto);
            m.setSentDate(new java.util.Date());
//             m.setContent(dirDatos, "text/plain");
            m.setContent(multiParte);

            Transport t = session.getTransport(protocol);
//             t.connect();
            t.connect(host, usuarioSmpt, password);
            t.send(m);
            t.close();
            return true;
        } catch (javax.mail.MessagingException e) {
            System.out.println("error" + e);
            e.printStackTrace();

            return false;
        }
    }
}