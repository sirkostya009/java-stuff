package ua.sirkostya009.javastuff.repository;

import org.springframework.data.domain.Page;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PublicFigureSearchDto;

public interface CustomPublicFigureRepository {

    Page<PublicFigure> search(PublicFigureSearchDto searchDto);

}
