package ua.sirkostya009.javastuff.mapper;

import org.springframework.stereotype.Service;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dto.BookInfo;

@Service
public class BookMapper {

    public Book mapToBook(BookInfo bookInfo) {
        Book book = new Book();
        book.setId(bookInfo.getId());
        book.setAuthor(bookInfo.getAuthor());
//        book.setGenre(bookInfo.getGenreId()); - this is another question:)
        bookInfo.setTitle(bookInfo.getTitle());
        return book;
    }

}
