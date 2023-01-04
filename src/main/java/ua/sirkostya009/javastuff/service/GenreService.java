package ua.sirkostya009.javastuff.service;

import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.GenreInfo;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    Genre add(GenreInfo info);

    Genre findBy(Long id);
}
