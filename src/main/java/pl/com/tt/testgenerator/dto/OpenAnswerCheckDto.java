package pl.com.tt.testgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OpenAnswerCheckDto {
    List<String> questions;
    List<String> suggestedAnswers;
    String[] answers;

}
