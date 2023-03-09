package ua.sirkostya009.javastuff.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ua.sirkostya009.javastuff.dao.Mail;
import ua.sirkostya009.javastuff.service.MailService;

@Component
@RequiredArgsConstructor
public class InboundMailListener {

    private final MailService service;

    @KafkaListener(topics = "${kafka.topics.mail-topic}")
    public void listen(Mail mail) {
        service.send(mail);
    }

}
