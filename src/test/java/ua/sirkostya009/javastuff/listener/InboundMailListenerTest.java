package ua.sirkostya009.javastuff.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.sirkostya009.javastuff.dao.Mail;
import ua.sirkostya009.javastuff.dao.MailStatus;
import ua.sirkostya009.javastuff.dto.MailDto;
import ua.sirkostya009.javastuff.repositories.MailRepository;
import ua.sirkostya009.javastuff.service.MailService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" }
)
class InboundMailListenerTest {

    @Value("${tests.mail.base-url}")
    private String baseUrl;
    @Value("${tests.mail.kafka-delay}")
    private int delay; // amount of time in milliseconds it takes kafka to process a message

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @SpyBean
    private InboundMailListener listener;
    @SpyBean
    private MailService service;
    @MockBean
    private MailRepository repository;

    @Test
    void listenTest() throws Exception {
        var dto = new MailDto(
                null,
                List.of("to@example.com"),
                "subject",
                "content",
                null
        );

        var mail = Mail.builder()
                .recipients(dto.getTo())
                .subject(dto.getSubject())
                .content(dto.getContent())
                .status(MailStatus.PENDING)
                .build();

        when(repository.save(any())).thenReturn(mail);

        mvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)));

        Thread.sleep(delay);

        var captor = ArgumentCaptor.forClass(Mail.class);
        verify(listener).listen(captor.capture());
        verify(service).send(mail);

        assertThat(mail).isEqualTo(captor.getValue());
    }

}
