package ua.sirkostya009.javastuff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor // I refrain from using @Builder here
                    // because I do not want my project to compile
                    // whenever I add a new field to the dto and
                    // forget to modify mapper accordingly
public class PublicFigureDto {
    private String id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String fullName;
    private boolean dead;
    private boolean isPep;
    private Integer age;
}
