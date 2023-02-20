package ua.sirkostya009.javastuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicFigureDto {
    private String id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private boolean dead;
    private boolean isPep;
    private Integer age;
}
