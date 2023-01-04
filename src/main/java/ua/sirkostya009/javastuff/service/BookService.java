package ua.sirkostya009.javastuff.service;

import org.springframework.data.domain.Page;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dto.BookInfo;

import java.util.List;

public interface BookService {
    Page<Book> findBy(String author, String title, int page);

    List<Book> byGenre(Long id);

    Book add(BookInfo info);

    Book findBy(Long id);

    Book update(Long id, BookInfo info);

    void delete(Long id);
}
