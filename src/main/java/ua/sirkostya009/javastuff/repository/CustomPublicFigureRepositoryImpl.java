package ua.sirkostya009.javastuff.repository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dao.PublicFigure.Fields;
import ua.sirkostya009.javastuff.dto.PublicFigureSearchDto;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class CustomPublicFigureRepositoryImpl implements CustomPublicFigureRepository {

    private final MongoTemplate template;

    @Override
    public Page<PublicFigure> search(PublicFigureSearchDto searchDto) {
        var pageRequest = PageRequest.of(searchDto.getPage(), searchDto.getSize());
        var query = new Query().with(pageRequest);
        var options = "i";

        if (StringUtils.isNotBlank(searchDto.getFirstName()))
            query.addCriteria(where(Fields.firstName).regex(searchDto.getFirstName(), options));
        if (StringUtils.isNotBlank(searchDto.getLastName()))
            query.addCriteria(where(Fields.lastName).regex(searchDto.getLastName(), options));
        if (StringUtils.isNotBlank(searchDto.getPatronymic()))
            query.addCriteria(where(Fields.patronymic).regex(searchDto.getPatronymic(), options));

        var figures = template.find(query, PublicFigure.class);

        return new PageImpl<>(figures,
                              pageRequest,
                              template.count(query.limit(-1).skip(-1), PublicFigure.class));
    }

}
