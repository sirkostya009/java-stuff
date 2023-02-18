package ua.sirkostya009.javastuff.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PepSearchDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    @Min(value = 0,  message = "page number cannot be lower than 0")
    private int page;
    @Min(value = 1, message = "size cannot be lower than 1")
    @Max(value = 25, message = "size cannot be higher than 25")
    private int size;
    private String lang;
}
