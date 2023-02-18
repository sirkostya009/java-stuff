package ua.sirkostya009.javastuff.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ua.sirkostya009.javastuff.dao.PublicFigure;

public interface PublicFigureRepository extends MongoRepository<PublicFigure, String>, CustomPublicFigureRepository {

    Page<PublicFigure> findPublicFigureByNamesContains(String names, Pageable pageable);

}
