package ua.sirkostya009.javastuff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.sirkostya009.javastuff.dao.Mail;
import ua.sirkostya009.javastuff.dao.MailStatus;
import ua.sirkostya009.javastuff.dto.MailDto;
import ua.sirkostya009.javastuff.dto.StatusDto;
import ua.sirkostya009.javastuff.exception.NotFoundException;
import ua.sirkostya009.javastuff.kafka.KafkaTopics;
import ua.sirkostya009.javastuff.repositories.MailRepository;

import javax.mail.Message;
import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailRepository repository;
    private final JavaMailSender sender;
    private final KafkaTopics topics;
    private final KafkaTemplate<String, Mail> template;

    @Value("${mail.sender-address}")
    private String senderAddress;

    @Override
    public void send(Mail mail) {
        var status = trySendMail(mail);
        mail.setStatus(status);
        mail.setErrorMessage(status.getMessage());
        repository.save(mail);
    }

    @Override
    public Mail post(MailDto dto) {
        var mail = repository.save(Mail.builder()
                .recipients(dto.getTo())
                .subject(dto.getSubject())
                .content(dto.getContent())
                .status(MailStatus.PENDING)
                .build());

        template.send(topics.getMailTopic(), mail);
        return mail;
    }

    @Override
    public Mail getWithId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mail with id " + id + " wasn't found"));
    }

    @Override
    public StatusDto getStatusWithId(String id) {
        var mail = getWithId(id);
        return new StatusDto(
                mail.getStatus(),
                mail.getErrorMessage()
        );
    }

    private MailStatus trySendMail(Mail mail) {
        var message = sender.createMimeMessage();

        try {
            message.setFrom(senderAddress);
            message.setRecipients(Message.RecipientType.TO, String.join(", ", mail.getRecipients()));
            message.setSubject(mail.getSubject());
            message.setContent(mail.getContent(), "text/html");

            sender.send(message);

            return MailStatus.SENT;
        } catch (MessagingException e) {
            var status = MailStatus.PENDING;
            status.setMessage(e.getMessage());
            return status;
        }
    }

}
