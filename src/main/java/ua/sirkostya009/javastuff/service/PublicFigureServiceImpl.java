package ua.sirkostya009.javastuff.service;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PublicFigureDto;
import ua.sirkostya009.javastuff.dto.PublicFigureSearchDto;
import ua.sirkostya009.javastuff.exception.CouldNotBeParsed;
import ua.sirkostya009.javastuff.mapper.PublicFigureMapper;
import ua.sirkostya009.javastuff.repository.PublicFigureRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
public class PublicFigureServiceImpl implements PublicFigureService {

    private final static int PUBLIC_FIGURES_PER_PAGE = 10;

    private final PublicFigureRepository repository;
    private final PublicFigureMapper publicFigureMapper;
    private final ObjectMapper objectMapper;

    @Override
    public void importArchive(MultipartFile archive) {
        try(var zip = new ZipInputStream(archive.getInputStream())) {
            var figures = new ArrayList<PublicFigure>();

            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null)
                if (!entry.isDirectory() && entry.getName().endsWith(".json"))
                    figures.addAll(parseJson(zip));

            repository.deleteAll();
            repository.saveAll(figures);
        } catch (IOException e) {
            throw new CouldNotBeParsed("Error occurred while reading zip: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Integer> top10PepNames() {
        return repository
                .findTop10PepNames()
                .collect(LinkedHashMap::new,
                        (map, nameCount) -> map.put(nameCount.get_id(), nameCount.getCount()),
                        (map1, map2) -> {});
    }

    @Override
    public Page<PublicFigureDto> search(String query, String lang, int page) {
        var inEnglish = shouldBeInEnglish(lang);
        return repository
                .findByNameContains(query, PageRequest.of(page, PUBLIC_FIGURES_PER_PAGE))
                .map(publicFigureMapper.dtoFunction(inEnglish));
    }

    @Override
    public Page<PublicFigureDto> search(PublicFigureSearchDto searchDto) {
        var inEnglish = shouldBeInEnglish(searchDto.getLang());
        return repository
                .search(searchDto)
                .map(publicFigureMapper.dtoFunction(inEnglish));
    }

    private List<PublicFigure> parseJson(InputStream in) throws IOException {
        var jsonParser = objectMapper.getFactory().createParser(in);

        if (jsonParser.nextToken() != JsonToken.START_ARRAY)
            throw new CouldNotBeParsed("json file must begin with an array");

        var figures = new ArrayList<PublicFigure>();

        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            var figure = objectMapper.readValue(jsonParser, PublicFigure.class);

            figures.add(figure); // accumulating all instances into a List cn be two times
                                 // faster than saving figures one-by-one, however,
                                 // it consumes ten times more memory that
                                 // doesn't get cleaned up, for some reason.
                                 // additionally, cpu usage seem to spike more frequently,
                                 // and is just higher on average.
        }

        return figures;
    }

    private boolean shouldBeInEnglish(String lang) {
        return "en".equalsIgnoreCase(lang);
    }

}
