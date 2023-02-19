package ua.sirkostya009.javastuff.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PepSearchDto;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomPublicFigureRepositoryImpl implements CustomPublicFigureRepository {

    private final static String COUNT = "count";

    private final MongoTemplate template;

    @Override
    public Map<String, Integer> findTop10Names() {
        var groupOperation = group(PublicFigure.Fields.firstName).count().as(COUNT);
        var filterOperation = match(where(PublicFigure.Fields.isPep).is(true));
        var sortOperation = sort(Sort.Direction.DESC, COUNT);
        var aggregation = newAggregation(filterOperation, groupOperation, sortOperation, limit(10));

        try(var stream = template.aggregateStream(aggregation, PublicFigure.class, Document.class)) {
            // we're using LinkedHashMap here because documents
            // are already sorted in descending order, and we need
            // to preserve that order
            return stream.stream().reduce(new LinkedHashMap<>(), (linkedMap, document) -> {
                linkedMap.put((String) document.get("_id"), (Integer) document.get(COUNT));
                return linkedMap;
            }, (map, ignored) -> map);
        }
    }

    @Override
    public Page<PublicFigure> search(PepSearchDto searchDto) {
        var pageRequest = PageRequest.of(
                searchDto.getPage(),
                searchDto.getSize()
        );
        var query = new Query().with(pageRequest);

        if (StringUtils.isNotBlank(searchDto.getFirstName()))
            query.addCriteria(where(PublicFigure.Fields.firstName).is(searchDto.getFirstName()));
        if (StringUtils.isNotBlank(searchDto.getLastName()))
            query.addCriteria(where(PublicFigure.Fields.lastName).is(searchDto.getLastName()));
        if (StringUtils.isNotBlank(searchDto.getPatronymic()))
            query.addCriteria(where(PublicFigure.Fields.patronymic).is(searchDto.getPatronymic()));

        var figures = template.find(query, PublicFigure.class);

        return PageableExecutionUtils.getPage(
                figures,
                pageRequest,
                () -> template.count((Query.of(query).limit(-1).skip(-1)), PublicFigure.class)
        );
    }

}
