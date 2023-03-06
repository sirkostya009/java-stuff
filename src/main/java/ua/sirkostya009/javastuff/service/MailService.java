package ua.sirkostya009.javastuff.service;

import ua.sirkostya009.javastuff.dao.Mail;
import ua.sirkostya009.javastuff.dto.MailDto;
import ua.sirkostya009.javastuff.dto.StatusDto;

public interface MailService {
    void send(Mail dto);

    Mail post(MailDto dto);

    Mail getWithId(String id);

    StatusDto getStatusWithId(String id);
}
