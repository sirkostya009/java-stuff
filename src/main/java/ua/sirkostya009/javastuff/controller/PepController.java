package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.sirkostya009.javastuff.dto.PublicFigureDto;
import ua.sirkostya009.javastuff.dto.PepSearchDto;
import ua.sirkostya009.javastuff.service.PublicFigureService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pep")
public class PepController {

    private final PublicFigureService service;

    @PostMapping("/upload-archive")
    public void uploadArchive(@RequestParam("archive") MultipartFile archive) {
        service.fillFromArchive(archive);
    }

    @GetMapping("/top-10-pep-names")
    public Map<String, Integer> top10PepNames() {
        return service.top10PepNames();
    }

    @GetMapping("/search")
    public Page<PublicFigureDto> search(@RequestParam(name = "query") String query,
                                        @RequestParam(name = "lang", defaultValue = "uk") String lang,
                                        @RequestParam(name = "page", defaultValue = "0") int page) {
        return service.searchBySingleString(query, lang, page);
    }

    @PostMapping("/search")
    public Page<PublicFigureDto> search(@RequestBody @Valid PepSearchDto searchDto) {
        return service.search(searchDto);
    }

}
