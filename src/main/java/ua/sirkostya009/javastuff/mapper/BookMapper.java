package ua.sirkostya009.javastuff.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dto.BookInfo;
import ua.sirkostya009.javastuff.service.GenreService;

@Service
@RequiredArgsConstructor
public class BookMapper {

    private final GenreService service;

    public Book mapToBook(BookInfo info) {
        return new Book(
                info.getId(),
                info.getAuthor().trim(),
                info.getTitle().trim(),
                service.findBy(info.getGenreId())
        );
    }

}
