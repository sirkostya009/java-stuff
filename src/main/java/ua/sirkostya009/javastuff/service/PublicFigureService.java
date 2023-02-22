package ua.sirkostya009.javastuff.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ua.sirkostya009.javastuff.dto.PublicFigureDto;
import ua.sirkostya009.javastuff.dto.PublicFigureSearchDto;

import java.util.Map;

public interface PublicFigureService {
    void importArchive(MultipartFile archive);

    Map<String, Integer> top10PepNames();

    Page<PublicFigureDto> search(String query,
                                 String lang,
                                 int page);

    Page<PublicFigureDto> search(PublicFigureSearchDto searchDto);
}
