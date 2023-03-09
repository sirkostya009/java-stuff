package ua.sirkostya009.javastuff.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.sirkostya009.javastuff.dao.Mail;
import ua.sirkostya009.javastuff.dao.MailStatus;
import ua.sirkostya009.javastuff.repositories.MailRepository;
import ua.sirkostya009.javastuff.service.MailService;

@Component
@RequiredArgsConstructor
public class InboundMailListener {

    private final MailService service;
    private final MailRepository repository;

    @Value("${kafka.processing.mail-per-page}")
    private int mailPerPage = 300;

    @KafkaListener(topics = "${kafka.topics.mail-topic}")
    public void listen(Mail mail) {
        service.send(mail);
    }

    @Scheduled(
            fixedRateString = "${kafka.processing.fixed-rate}",
            initialDelayString = "${kafka.processing.initial-delay}"
    )
    public void task() {
        var pageNo = 0;
        Page<Mail> page;

        do {
            page = repository.findByStatus(MailStatus.PENDING, pageRequest(pageNo++));

            page.forEach(service::send);
        } while (page.hasNext());
    }

    private PageRequest pageRequest(int page) {
        return PageRequest.of(page, mailPerPage);
    }

}
