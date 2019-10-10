package pl.com.tt.testgenerator.dto;

import lombok.Data;

@Data
public class FinishedTestDto {

    private long id;
    private long[] closedAnswersTable;
    private String[] openAnswersTable;
}