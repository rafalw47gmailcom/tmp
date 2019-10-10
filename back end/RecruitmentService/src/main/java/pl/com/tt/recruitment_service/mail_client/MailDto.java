package pl.com.tt.recruitment_service.mail_client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private List<String> receivers;
    private String subject;
    private String content;
}
