package pl.com.tt.testgenerator.services.servicesimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.com.tt.testgenerator.services.MailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    @Value("${mailConfig.host}")
    private String SMTP_SERVER;
    @Value("${mailConfig.name}")
    private String USERNAME;
    @Value("${mailConfig.password}")
    private String PASSWORD;
    @Value(("${mailConfig.port}"))
    private int PORT;
    @Value("${mailConfig.fromEmail}")
    private String FROM;

    @Override
    public void sendMail(String To, String subject, String content) {

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", PORT);
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To)
            );
            message.setSubject(subject);

            message.setContent(content, "text/html");
            Transport.send(message);

            System.out.println("Sent email !");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
