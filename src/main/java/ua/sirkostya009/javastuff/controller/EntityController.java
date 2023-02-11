package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.javastuff.model.NamedEntity;
import ua.sirkostya009.javastuff.service.EntityService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/entities")
@RequiredArgsConstructor
public class EntityController {
    private final EntityService service;

    @GetMapping
    public List<NamedEntity> get() {
        log.info("get all");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public NamedEntity get(@PathVariable Long id) {
        log.info("get with id " + id);
        return service.findById(id);
    }

    @PostMapping
    public NamedEntity post(@RequestBody @Valid NamedEntity entity) {
        log.info("posting " + entity);
        return service.persist(entity);
    }

    @PutMapping("/{id}")
    public NamedEntity update(@PathVariable Long id,
                              @RequestBody @Valid NamedEntity entity) {
        log.info("updating " + id + " with " + entity);
        return service.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("deleting entity " + id);
        service.deleteById(id);
    }
}
