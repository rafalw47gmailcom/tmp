package pl.com.tt.recruitment_service.mail_client;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Auth extends Authenticator {

    public Auth() {
        super();
    }

    private String username;
    private String password;

    public PasswordAuthentication getPasswordAuthentication() {

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new PasswordAuthentication(username, password);
    }
}