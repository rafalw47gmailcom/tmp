package pl.com.tt.testgenerator.dto;

import lombok.Data;

@Data
public class OneQuestionDto {

    private long time;
    private String answer;
    private boolean answered = false;

    public OneQuestionDto() {
    }

    public OneQuestionDto(long time) {
        this.time = time;
    }
}
