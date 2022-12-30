package ua.sirkostya009.javastuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Jacksonized
@AllArgsConstructor
public @Data class GenreInfo {
    private Long id;

    @NotBlank @NotNull
    private String name;
}
