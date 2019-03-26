package mail;

import server.Server;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 /**
 * Менеджер отправки почты через smtp.gmail.com
  *
  * Author: Belenov.D
 * */

public class MailManager {

    private static final String from = "<your_mail>@gmail.com"; // адрес почты на gmail
    private static final String pass = "<your_password>"; // пароль от почты на gmail

    private static Properties getProperties() {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.EnableSSL.enable","true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        return props;
    }

    public static boolean send (String to, String title, String msg) {
        boolean sent = false;
        Session session = Session.getDefaultInstance(getProperties(),
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                from, pass);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(title);
            message.setText(msg);

            Transport.send(message);
            Server.logger.log("Email \""+msg+"\" успешно отправлен по адресу "+to);
            sent = true;
        } catch (Exception e) {
            Server.logger.log(e);
        }
        return sent;
    }
}