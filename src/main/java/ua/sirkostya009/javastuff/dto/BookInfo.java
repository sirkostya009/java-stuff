package ua.sirkostya009.javastuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.sirkostya009.javastuff.dao.Book;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public @Data class BookInfo {
    private Long id;

    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    private String author;

    @NotNull
    private Long genreId;

    @Valid
    public static BookInfo of(Book book) {
        return new BookInfo(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre().getId()
        );
    }

}
