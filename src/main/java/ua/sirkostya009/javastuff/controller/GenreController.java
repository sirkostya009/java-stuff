package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return toInfo(service.findAll());
    }

    @GetMapping("{id}")
    public GenreInfo byId(@PathVariable Long id) {
        return GenreInfo.of(service.findBy(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GenreInfo post(@Valid @RequestBody GenreInfo info) {
        return GenreInfo.of(service.add(info));
    }

    private List<GenreInfo> toInfo(Collection<Genre> genres) {
        return genres.stream().map(GenreInfo::of).toList();
    }
}
