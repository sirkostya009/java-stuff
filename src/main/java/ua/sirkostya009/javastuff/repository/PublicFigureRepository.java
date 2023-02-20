package ua.sirkostya009.javastuff.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ua.sirkostya009.javastuff.dao.NameCount;
import ua.sirkostya009.javastuff.dao.PublicFigure;

import java.util.stream.Stream;

public interface PublicFigureRepository extends MongoRepository<PublicFigure, String>, CustomPublicFigureRepository {

    @Query("{$or: [{'fullName': {$regex: ?0, $options: 'i'}}, {'fullNameEn': {$regex: ?0, $options: 'i'}}]}")
    Page<PublicFigure> findPublicFigureByNameContains(String names, Pageable pageable);

    @Aggregation(pipeline = {
            "{$match: {'isPep': true}}",
            "{$group: {'_id': '$firstName', count: {$sum: 1}}}",
            "{$sort: {count: -1}}",
            "{$limit: 10}",
    })
    Stream<NameCount> findTop10PepNames();

    @Query("""
        {$or: [
            {'firstName': {$regex: ?0, $options: 'i'}},
            {'lastName': {$regex: ?1, $options: 'i'}},
            {'patronymic': {$regex: ?2, $options: 'i'}},
            {'firstNameEn': {$regex: ?0, $options: 'i'}},
            {'lastNameEn': {$regex: ?1, $options: 'i'}},
            {'patronymicEn': {$regex: ?2, $options: 'i'}},
        ]}
        """)
    Page<PublicFigure> findByFirstNameOrLastNameOrPatronymic(String fullName,
                                                             String lastName,
                                                             String patronymic,
                                                             Pageable pageable);

}
