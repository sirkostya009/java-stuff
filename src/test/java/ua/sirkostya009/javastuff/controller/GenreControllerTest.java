package ua.sirkostya009.javastuff.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.GenreInfo;
import ua.sirkostya009.javastuff.repository.GenreRepository;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GenreControllerTest {

    private final static String BASE_URL = "/api/v1/genres";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GenreRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void all() throws Exception {
        var response = mvc
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(parseObject(response, GenreInfo[].class))
                .isEqualTo(repository.findAll().stream().map(GenreInfo::of).toArray());
    }

    @Test
    void byId() throws Exception {
        repository.save(new Genre(null, "sample", Set.of())); // for some reason we can't query by id without this.

        var lastId = repository.count();

        var response = mvc
                .perform(get(BASE_URL + "/" + lastId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(parseObject(response, GenreInfo.class).getId()).isEqualTo(lastId);
    }

    @Test
    void byIdFailed() throws Exception {
        mvc.perform(get(BASE_URL + "/010101")).andExpect(status().isNotFound());
    }

    @Test
    void post() throws Exception {
        var info = new GenreInfo(null, "new category");

        var request = MockMvcRequestBuilders
                .post(BASE_URL)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(info));

        var response = mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var returnedInfo = parseObject(response, GenreInfo.class);

        assertThat(repository.existsById(returnedInfo.getId())).isTrue();

        var persisted = GenreInfo.of(repository.findById(returnedInfo.getId()).orElseThrow());

        assertThat(returnedInfo).isEqualTo(persisted);
    }

    private <T> T parseObject(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

}
