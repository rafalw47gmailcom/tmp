package pl.com.tt.testgenerator.dto;

import lombok.Data;

@Data
public class CurrentQuestionDto {

    private String type;
    private Long thisQuestionId;
    private Long nextQuestionId;
    private String answer;
    private boolean markedAnswer;
}
