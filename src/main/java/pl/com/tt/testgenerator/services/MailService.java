package pl.com.tt.testgenerator.services;

public interface MailService {

    void sendMail(String to, String subject, String content);
}
