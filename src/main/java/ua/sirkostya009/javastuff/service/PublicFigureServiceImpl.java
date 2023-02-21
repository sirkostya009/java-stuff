package ua.sirkostya009.javastuff.service;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PublicFigureSearchDto;
import ua.sirkostya009.javastuff.dto.PublicFigureDto;
import ua.sirkostya009.javastuff.exception.CouldNotBeParsed;
import ua.sirkostya009.javastuff.mapper.PublicFigureMapper;
import ua.sirkostya009.javastuff.repository.PublicFigureRepository;

import java.io.BufferedInputStream;
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
    public void fillFromArchive(MultipartFile archive) {
        repository.deleteAll();

        try(var zip = new ZipInputStream(new BufferedInputStream(archive.getInputStream()))) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if(!entry.isDirectory() && entry.getName().endsWith(".json")) {
                    var figures = parseJson(zip);

                    repository.saveAll(figures);
                }

                zip.closeEntry();
            }
        } catch (IOException e) {
            // an exception is thrown whenever try block tries to close the zip stream
            // that's why we need this crutch here. could closeEntry() be closing stream too?
            if (!e.getMessage().contains("Stream closed"))
                throw new CouldNotBeParsed("Exception thrown while parsing archive");
        }
    }

    @Override
    public Map<String, Integer> top10PepNames() {
        return repository.findTop10PepNames()
                .collect(LinkedHashMap::new,
                        (map, nameCount) -> map.put(nameCount.get_id(), nameCount.getCount()),
                        (map1, map2) -> {});
    }

    @Override
    public Page<PublicFigureDto> search(String query, String lang, int page) {
        var inEnglish = shouldBeInEnglish(lang);
        return repository
                .findPublicFigureByNameContains(query, PageRequest.of(page, PUBLIC_FIGURES_PER_PAGE))
                .map(publicFigureMapper.mapLambda(inEnglish));
    }

    @Override
    public Page<PublicFigureDto> search(PublicFigureSearchDto searchDto) {
        var inEnglish = shouldBeInEnglish(searchDto.getLang());
        return repository.search(searchDto).map(publicFigureMapper.mapLambda(inEnglish));
    }

    private List<PublicFigure> parseJson(InputStream in) {
        try(var jsonParser = objectMapper.getFactory().createParser(in)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY)
                throw new CouldNotBeParsed("json file must begin with an array");

            var figures = new ArrayList<PublicFigure>();

            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                var figure = objectMapper.readValue(jsonParser, PublicFigure.class);

                figures.add(figure); // accumulating all instances into a List cn be two times
                                     // faster than saving figures one-by-one, however,
                                     // it consumes a hundred times more memory, that doesn't get cleaned up.
                                     // additionally, cpu usage seem to spike more frequently,
                                     // and is just used more of.
            }

            return figures;
        } catch (IOException e) {
            throw new CouldNotBeParsed(e.getMessage(), e);
        }
    }

    private boolean shouldBeInEnglish(String lang) {
        return "en".equalsIgnoreCase(lang);
    }

}
