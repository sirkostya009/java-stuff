package ua.sirkostya009.javastuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.sirkostya009.javastuff.dao.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
