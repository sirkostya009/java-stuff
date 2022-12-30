package ua.sirkostya009.javastuff.service;

import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.BookInfo;

import java.util.List;

public interface BookService {
    List<Book> all();

    List<Book> byCategory(Genre genre);

    Book add(BookInfo info);

    Book get(Long id);

    Book update(Long id, BookInfo info);

    void delete(Long id);
}
