package ua.sirkostya009.javastuff.service;

import org.springframework.data.domain.Page;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.BookInfo;

import java.util.List;

public interface BookService {
    Page<Book> find(String author, String title, int page);

    List<Book> byGenre(Genre genre);

    Book add(BookInfo info);

    Book get(Long id);

    Book update(Long id, BookInfo info);

    void delete(Long id);
}
