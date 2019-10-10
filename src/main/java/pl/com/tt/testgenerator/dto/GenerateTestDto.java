package pl.com.tt.testgenerator.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GenerateTestDto {

    private long id;

    private Date validFrom;

    private Date validUntil;
}