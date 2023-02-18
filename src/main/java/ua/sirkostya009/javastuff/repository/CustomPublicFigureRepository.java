package ua.sirkostya009.javastuff.repository;

import org.springframework.data.domain.Page;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PepSearchDto;

import java.util.Map;

public interface CustomPublicFigureRepository {

    Map<String, Integer> findTop10Names();

    Page<PublicFigure> search(PepSearchDto searchDto);

}
