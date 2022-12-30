package ua.sirkostya009.javastuff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.GenreInfo;
import ua.sirkostya009.javastuff.exception.NotFoundException;
import ua.sirkostya009.javastuff.repository.GenreRepository;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    @Override
    public List<Genre> all() {
        return repository.findAll();
    }

    @Override
    public Genre add(GenreInfo info) {
        return repository.save(new Genre(null, info.getName(), new HashSet<>()));
    }

    @Override
    public Genre get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre with id " + id + " not found"));
    }
}
