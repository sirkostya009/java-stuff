package ua.sirkostya009.javastuff.service;

import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.GenreInfo;

import java.util.List;

public interface GenreService {
    List<Genre> all();

    Genre add(GenreInfo info);

    Genre get(Long id);
}
