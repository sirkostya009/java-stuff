package ua.sirkostya009.javastuff.service;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PublicFigureDto;
import ua.sirkostya009.javastuff.dto.PepSearchDto;
import ua.sirkostya009.javastuff.exception.CouldNotBeParsed;
import ua.sirkostya009.javastuff.mapper.PublicFigureMapper;
import ua.sirkostya009.javastuff.repository.PublicFigureRepository;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
public class PublicFigureServiceImpl implements PublicFigureService {

    private final static int PUBLIC_FIGURES_PER_PAGE = 10;

    private final PublicFigureRepository repository;
    private final PublicFigureMapper publicFigureMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void fillFromArchive(MultipartFile archive) {
        repository.deleteAll();

        try(var zip = new ZipInputStream(new BufferedInputStream(archive.getInputStream()))) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if(!entry.isDirectory() && entry.getName().endsWith(".json"))
                    parseJson(zip);

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
    public Page<PublicFigureDto> searchBySingleString(String query, String lang, int page) {
        var inEnglish = shouldBeInEnglish(lang);
        return repository
                .findPublicFigureByNameContains(query, PageRequest.of(page, PUBLIC_FIGURES_PER_PAGE))
                .map(figure -> publicFigureMapper.convert(figure, inEnglish));
    }

    @Override
    public Page<PublicFigureDto> search(PepSearchDto searchDto) {
        var inEnglish = shouldBeInEnglish(searchDto.getLang());
        return repository.search(searchDto).map(figure -> publicFigureMapper.convert(figure, inEnglish));
    }

    private void parseJson(InputStream in) {
        try(var jsonParser = objectMapper.getFactory().createParser(in)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY)
                throw new CouldNotBeParsed("json file must begin with an array");

            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                var figure = objectMapper.readValue(jsonParser, PublicFigure.class);

                repository.save(figure); // saving an instance per parse takes on avg 75% more time to complete,
                                         // however it is far more memory efficient compared to saving into a list,
                                         // for some reason, don't clean up leaving about 600mb leaked.
            }
        } catch (IOException e) {
            throw new CouldNotBeParsed(e.getMessage(), e);
        }
    }

    private boolean shouldBeInEnglish(String lang) {
        return "en".equalsIgnoreCase(lang);
    }

}
