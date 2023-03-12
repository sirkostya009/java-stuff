package ua.sirkostya009.javastuff;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.sirkostya009.javastuff.dao.Mail;
import ua.sirkostya009.javastuff.dao.MailStatus;
import ua.sirkostya009.javastuff.dto.MailDto;
import ua.sirkostya009.javastuff.repositories.MailRepository;
import ua.sirkostya009.javastuff.task.Scheduler;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MailTests {

    @Value("${mail-tests.base-url}")
    private String baseUrl;
    @Value("${mail.tests.kafka-delay}")
    private int delay; // amount of time in milliseconds it takes kafka to process a message

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MailRepository repository;

    @Autowired
    private Scheduler scheduler;

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void postSuccessful() throws Exception {
        var mailDto = new MailDto(
                null,
                "from@example.com",
                List.of("to@example.com"),
                "test subject",
                "test content",
                null
        );

        var request = post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mailDto));

        var response = mvc.perform(request)
                .andReturn()
                .getResponse()
                .getContentAsString();

        var resultId = mapper.readValue(response, MailDto.class).getId();

        Thread.sleep(delay);

        checkIfSent(resultId);
    }

    @Test
    void kafkaScheduledJob() throws Exception {
        var mail = repository.save(Mail.builder()
                        .recipients(List.of("young@thug.org"))
                        .sender("mr@sandman.com")
                        .subject("subject")
                        .content("content")
                        .status(MailStatus.PENDING)
                        .build());

        scheduler.tryResendMail();

        checkIfSent(mail.getId());
    }

    void checkIfSent(String resultId) throws Exception {
        mvc.perform(get(baseUrl + "/check-status/" + resultId))
                .andExpect(jsonPath("$.status").value("SENT"));
    }

}