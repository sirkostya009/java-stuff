package ua.sirkostya009.javastuff.repository;

import org.springframework.data.domain.Page;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PepSearchDto;

public interface CustomPublicFigureRepository {

    Page<PublicFigure> search(PepSearchDto searchDto);

}
