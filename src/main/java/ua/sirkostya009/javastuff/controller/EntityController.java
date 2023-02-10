package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.javastuff.model.NamedEntity;
import ua.sirkostya009.javastuff.service.EntityService;

import java.util.List;

@RestController
@RequestMapping("/api/entities")
@RequiredArgsConstructor
public class EntityController {
    private final EntityService service;

    @GetMapping
    public List<NamedEntity> get() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public NamedEntity get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public NamedEntity post(@RequestBody NamedEntity entity) {
        return service.persist(entity);
    }

    @PutMapping("/{id}")
    public NamedEntity update(@PathVariable Long id,
                              @RequestBody NamedEntity entity) {
        return service.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
