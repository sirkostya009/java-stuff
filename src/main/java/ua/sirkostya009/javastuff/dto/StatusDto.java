package ua.sirkostya009.javastuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.sirkostya009.javastuff.dao.MailStatus;

@Data
@AllArgsConstructor
public class StatusDto {
    private MailStatus status;
    private String message;
}
