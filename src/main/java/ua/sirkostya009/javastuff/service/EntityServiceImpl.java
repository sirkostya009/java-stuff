package ua.sirkostya009.javastuff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.sirkostya009.javastuff.exception.NotFoundException;
import ua.sirkostya009.javastuff.model.NamedEntity;
import ua.sirkostya009.javastuff.repository.EntityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {
    private final EntityRepository repository;

    @Override
    public List<NamedEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public NamedEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> entityNotFound(id));
    }

    @Override
    public NamedEntity persist(NamedEntity entity) {
        return repository.save(entity);
    }

    @Override
    public NamedEntity update(Long id, NamedEntity entity) {
        if (!repository.existsById(id)) throw entityNotFound(id);

        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private NotFoundException entityNotFound(Long id) {
        return new NotFoundException("entity with id " + id + " not found");
    }
}
