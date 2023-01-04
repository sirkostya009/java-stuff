package ua.sirkostya009.javastuff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.GenreInfo;
import ua.sirkostya009.javastuff.exception.NotFoundException;
import ua.sirkostya009.javastuff.repository.GenreRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    @Override
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Override
    public Genre add(GenreInfo info) {
        return repository.save(new Genre(null, info.getName(), Set.of()));
    }

    @Override
    public Genre findBy(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre with id " + id + " not found"));
    }
}
