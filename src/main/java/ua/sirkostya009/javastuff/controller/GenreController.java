package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.GenreInfo;
import ua.sirkostya009.javastuff.service.GenreService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService service;

    @GetMapping
    public List<GenreInfo> all() {
        return toInfo(service.all());
    }

    @PostMapping
    public GenreInfo post(@Valid @RequestBody GenreInfo info) {
        return toInfo(service.add(info));
    }

    private @Valid GenreInfo toInfo(Genre genre) {
        return new GenreInfo(genre.getId(), genre.getName());
    }

    private List<@Valid GenreInfo> toInfo(Collection<Genre> genres) {
        return genres.stream().map(this::toInfo).toList();
    }
}
