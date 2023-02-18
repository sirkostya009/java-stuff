package ua.sirkostya009.javastuff.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ua.sirkostya009.javastuff.dto.PepDto;
import ua.sirkostya009.javastuff.dto.PepSearchDto;

import java.util.Map;

public interface PublicFigureService {
    void fillFromArchive(MultipartFile archive);

    Map<String, Integer> mostPopularNames();

    Page<PepDto> searchBySingleString(String query,
                                      String lang,
                                      int page);

    Page<PepDto> search(PepSearchDto searchDto);
}
