package ua.sirkostya009.javastuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dao.Genre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b where b.genre = :genre")
    List<Book> findByCategory(Genre genre);
}
