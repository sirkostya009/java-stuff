package ua.sirkostya009.javastuff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.sirkostya009.javastuff.constraint.EmailAddress;
import ua.sirkostya009.javastuff.dao.Mail;
import ua.sirkostya009.javastuff.dao.MailStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String id;
    @NotEmpty
    @NotNull
    private List<@EmailAddress String> to;
    @NotBlank
    @NotNull
    private String subject;
    @NotBlank
    @NotNull
    private String content;
    private MailStatus status;

    public static MailDto of(Mail mail) {
        return new MailDto(
                mail.getId(),
                mail.getRecipients(),
                mail.getSubject(),
                mail.getContent(),
                mail.getStatus()
        );
    }
}
