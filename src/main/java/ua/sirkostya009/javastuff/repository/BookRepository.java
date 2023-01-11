package ua.sirkostya009.javastuff.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.sirkostya009.javastuff.dao.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b where b.genre.id = :id")
    List<Book> findByGenreId(Long id);

    @Query("""
        select b from Book b
        where (:author is null or upper(b.author) like upper(concat('%', :author, '%')))
        and (:title is null or upper(b.title) like upper(concat('%', :title, '%')))
    """)
    Page<Book> findByAuthorAndTitle(String author, String title, Pageable pageable);
}
