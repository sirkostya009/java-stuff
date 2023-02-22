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
    Page<PublicFigure> findByNameContains(String string, Pageable pageable);

    @Aggregation(pipeline = {
            "{$match: {'isPep': true}}",
            "{$group: {'_id': '$firstName', count: {$sum: 1}}}",
            "{$sort: {count: -1}}",
            "{$limit: 10}",
    })
    Stream<NameCount> findTop10PepNames();

}
