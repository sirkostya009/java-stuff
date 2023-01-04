package ua.sirkostya009.javastuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.sirkostya009.javastuff.dao.Genre;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public @Data class GenreInfo {
    private Long id;

    @NotBlank @NotNull
    private String name;

    public static @Valid GenreInfo of(Genre genre) {
        return new GenreInfo(
                genre.getId(),
                genre.getName()
        );
    }
}
