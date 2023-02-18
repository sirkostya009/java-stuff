package ua.sirkostya009.javastuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PepDto {
    private String id;
    private String firstName;
    private String lastName;
    private boolean dead;
    private int age;
}
