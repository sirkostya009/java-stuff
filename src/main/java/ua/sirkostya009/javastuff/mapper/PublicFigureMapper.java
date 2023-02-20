package ua.sirkostya009.javastuff.mapper;

import org.springframework.stereotype.Component;
import ua.sirkostya009.javastuff.dao.PublicFigure;
import ua.sirkostya009.javastuff.dto.PublicFigureDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Function;

@Component
public class PublicFigureMapper {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Function<PublicFigure, PublicFigureDto> mapLambda(boolean inEnglish) {
        return figure -> convert(figure, inEnglish);
    }

    public PublicFigureDto convert(PublicFigure figure, boolean inEnglish) {
        return new PublicFigureDto(
                figure.getId(),
                inEnglish ? figure.getFirstNameEn() : figure.getFirstName(),
                inEnglish ? figure.getLastNameEn() : figure.getLastName(),
                inEnglish ? figure.getPatronymicEn() : figure.getPatronymic(),
                inEnglish ? figure.getFullNameEn() : figure.getFullName(),
                figure.isDied(),
                figure.isPep(),
                parseAge(figure.getDateOfBirth())
        );
    }

    private Integer parseAge(String birthDate) {
        try {
            var date = LocalDate.parse(birthDate, FORMATTER);
            return LocalDate.now()
                    .minusYears(date.getYear())
                    .minusMonths(date.getMonthValue())
                    .minusDays(date.getDayOfMonth())
                    .getYear();
        } catch (DateTimeParseException ignored) {
            return null;
        }
    }

}
