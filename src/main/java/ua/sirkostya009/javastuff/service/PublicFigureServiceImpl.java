package ua.sirkostya009.javastuff.service;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PepDto;
import ua.sirkostya009.javastuff.dto.PepSearchDto;
import ua.sirkostya009.javastuff.exception.CouldNotBeParsed;
import ua.sirkostya009.javastuff.repository.PublicFigureRepository;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicFigureServiceImpl implements PublicFigureService {

    private final static int PUBLIC_FIGURES_PER_PAGE = 10;

    private final PublicFigureRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new JavaTimeModule());
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
            // an exception is thrown whenever try block tries to close the zip stream,
            // that's why we need this crutch here
            if (!e.getMessage().contains("Stream closed"))
                throw new CouldNotBeParsed("Exception thrown while parsing archive");
        }
    }

    @Override
    public Map<String, Integer> mostPopularNames() {
        return repository.findTop10Names();
    }

    @Override
    public Page<PepDto> searchBySingleString(String query, String lang, int page) {
        var inEnglish = shouldBeInEnglish(lang);
        return repository
                .findPublicFigureByNamesContains(query, PageRequest.of(page, PUBLIC_FIGURES_PER_PAGE))
                .map(figure -> convert(figure, inEnglish));
    }

    @Override
    public Page<PepDto> search(PepSearchDto searchDto) {
        var inEnglish = shouldBeInEnglish(searchDto.getLang());
        return repository.search(searchDto).map(figure -> convert(figure, inEnglish));
    }

    private void parseJson(InputStream in) {
        try (var jsonParser = mapper.getFactory().createParser(in)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY)
                throw new CouldNotBeParsed("json file must be one big array");

            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                var figure = mapper.readValue(jsonParser, PublicFigure.class);

                repository.save(figure); // saving an instance per parse takes slightly more time
                                         // but is far more memory efficient unlike bulk ops
                                         // that, for some reason, don't clean up leaving about 600mb leaked.
            }
        } catch (IOException e) {
            throw new CouldNotBeParsed(e.getMessage(), e);
        }
    }

    private boolean shouldBeInEnglish(String lang) {
        return "en".equalsIgnoreCase(lang);
    }

    private PepDto convert(PublicFigure figure, boolean inEnglish) {
        return new PepDto(
                figure.getId(),
                inEnglish ? figure.getFirstNameEn() : figure.getFirstName(),
                inEnglish ? figure.getLastNameEn() : figure.getLastName(),
                inEnglish ? figure.getPatronymicEn() : figure.getPatronymic(),
                figure.isDied(),
                parseAge(figure)
        );
    }

    private int parseAge(PublicFigure figure) {
        var birthDate = figure.getDateOfBirth();
        try {
            var year = LocalDate.parse(birthDate).getYear();
            return LocalDate.now().getYear() - year;
        } catch (DateTimeParseException ignored) {
            return -1;
        }
    }

}
