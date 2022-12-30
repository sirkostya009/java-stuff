package ua.sirkostya009.javastuff.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dao.Genre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b where b.genre = :genre")
    List<Book> findByGenre(Genre genre);

    @Query("""
            select b from Book b
            where (:author is not null and upper(b.author) like upper(concat('%', :author, '%')))
            or (:title is not null and upper(b.title) like upper(concat('%', :title, '%')))""")
    Page<Book> findByAuthorOrTitle(String author, String title, Pageable pageable);
}
