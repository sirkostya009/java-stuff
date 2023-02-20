package ua.sirkostya009.javastuff.repository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PepSearchDto;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class CustomPublicFigureRepositoryImpl implements CustomPublicFigureRepository {

    private final MongoTemplate template;

    @Override
    public Page<PublicFigure> search(PepSearchDto searchDto) {
        var pageRequest = PageRequest.of(
                searchDto.getPage(),
                searchDto.getSize()
        );
        var query = new Query().with(pageRequest);

        if (StringUtils.isNotBlank(searchDto.getFirstName()))
            query.addCriteria(where(PublicFigure.Fields.firstName).regex(searchDto.getFirstName(), "i"));
        if (StringUtils.isNotBlank(searchDto.getLastName()))
            query.addCriteria(where(PublicFigure.Fields.lastName).regex(searchDto.getLastName(), "i"));
        if (StringUtils.isNotBlank(searchDto.getPatronymic()))
            query.addCriteria(where(PublicFigure.Fields.patronymic).regex(searchDto.getPatronymic(), "i"));

        var figures = template.find(query, PublicFigure.class);

        return PageableExecutionUtils.getPage(
                figures,
                pageRequest,
                () -> template.count((Query.of(query).limit(-1).skip(-1)), PublicFigure.class)
        );
    }

}
