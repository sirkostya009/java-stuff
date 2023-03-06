package ua.sirkostya009.javastuff.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.sirkostya009.javastuff.dao.Mail;
import ua.sirkostya009.javastuff.dao.MailStatus;

public interface MailRepository extends ElasticsearchRepository<Mail, String> {
    Page<Mail> findByStatus(MailStatus status, Pageable pageable);
}
