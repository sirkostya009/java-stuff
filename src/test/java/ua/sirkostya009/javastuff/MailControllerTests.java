package ua.sirkostya009.javastuff;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.sirkostya009.javastuff.dao.MailStatus;
import ua.sirkostya009.javastuff.dto.MailDto;
import ua.sirkostya009.javastuff.dto.StatusDto;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MailControllerTests {

    private final static String BASE_URL = "/api/mail";
    private final static int KAFKA_PROCESSING_DELAY = 500; // amount of time in milliseconds it takes kafka to process a message

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void postSuccessful() throws Exception {
        genericTest(
                mailDto("from@example.com", "from@example.com"),
                new StatusDto(MailStatus.SENT, null)
        );
    }

    @Test
    void postUnsuccessful() throws Exception {
        genericTest(
                mailDto("from", ".com"),
                new StatusDto(MailStatus.PENDING, "Local address starts with dot")
        );
    }

    void genericTest(MailDto mailDto, StatusDto statusDto) throws Exception {
        var request = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mailDto));

        var response = mvc.perform(request)
                .andReturn()
                .getResponse()
                .getContentAsString();

        var resultId = mapper.readValue(response, MailDto.class).getId();

        Thread.sleep(KAFKA_PROCESSING_DELAY);

        response = mvc.perform(get(BASE_URL + "/check-status/" + resultId))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var status = mapper.readValue(response, StatusDto.class);

        assertThat(status).isEqualTo(statusDto);
    }

    MailDto mailDto(String from, String to) {
        return new MailDto(
                null,
                from,
                Collections.singletonList(to),
                "test subject",
                "test content",
                null
        );
    }

}
