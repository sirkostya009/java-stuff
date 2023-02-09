package ua.sirkostya009.javastuff.service;

import ua.sirkostya009.javastuff.model.NamedEntity;

import java.util.List;

public interface EntityService {
    List<NamedEntity> findAll();

    NamedEntity findById(Long id);

    NamedEntity persist(NamedEntity entity);

    NamedEntity update(Long id, NamedEntity entity);

    void deleteById(Long id);
}
