package ua.sirkostya009.javastuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public @Data class BookInfo {
    private Long id;

    @NotBlank @NotNull
    private String title;

    @NotBlank @NotNull
    private String author;

    @NotNull
    private Long categoryId;
}
