package ua.sirkostya009.javastuff.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dto.BookInfo;
import ua.sirkostya009.javastuff.repository.BookRepository;
import ua.sirkostya009.javastuff.repository.GenreRepository;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private static final String BASE_URL = "/api/v1/books";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        var uncategorized = genreRepository.findById(1L).orElseThrow();
        var sciFi = genreRepository.findById(2L).orElseThrow();
        bookRepository.save(new Book(null, "No category book", "Author 1", uncategorized));
        bookRepository.save(new Book(null, "Science fiction book", "Author 2", sciFi));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        var response = mvc
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var returnedInfos = parseObject(
                response.substring(response.indexOf('['), response.indexOf(']') + 1),
                BookInfo[].class
        );
        var persistedInfos = bookRepository.findAll().stream().map(BookInfo::of).toList();

        assertThat(Arrays.asList(returnedInfos)).isEqualTo(persistedInfos);
    }

    // this is more of a unit test but done through controller and service layers
    @Test
    public void testGetBookWithAuthor() throws Exception {
        var author = "Author 2";

        var response = mvc
                .perform(get(BASE_URL)
                        .param("author", author))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var returnedInfos = parseObject(
                response.substring(response.indexOf('['), response.indexOf(']') + 1),
                BookInfo[].class
        );
        var persistedInfos = bookRepository.findByAuthorAndTitle(author, null, PageRequest.of(0, 10))
                .stream()
                .map(BookInfo::of)
                .toList();

        assertThat(Arrays.asList(returnedInfos)).isEqualTo(persistedInfos);
    }

    @Test
    public void testAddBook() throws Exception {
        var info = new BookInfo(null, "Another sci-fi book", "Author 3", 2L);

        var request = MockMvcRequestBuilders
                .post(BASE_URL)
                .content(mapper.writeValueAsBytes(info))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        var response = mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var returned = parseObject(response, BookInfo.class);
        assertEquality(
                returned,
                bookRepository.findById(returned.getId()).orElseThrow()
        );
    }

    @Test
    public void testGetBookById() throws Exception {
        var response = mvc
                .perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquality(
                parseObject(response, BookInfo.class),
                bookRepository.findById(1L).orElseThrow()
        );
    }

    @Test
    public void testUpdateBook() throws Exception {
        var info = new BookInfo(null, "Updated Title", "Updated Author", 2L);

        var request = MockMvcRequestBuilders
                .put(BASE_URL + "/1")
                .accept(APPLICATION_JSON)
                .content(mapper.writeValueAsString(info))
                .contentType(APPLICATION_JSON);

        var response = mvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquality(
                parseObject(response, BookInfo.class),
                bookRepository.findById(1L).orElseThrow()
        );
    }

    @Test
    public void testBookValidation() throws Exception {
        var request = MockMvcRequestBuilders
//                .post(BASE_URL)
                .put(BASE_URL + "/1")
                .accept(APPLICATION_JSON)
                .content(mapper.writeValueAsString("{}"))
                .contentType(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteBook() throws Exception {
        mvc.perform(delete(BASE_URL + "/1")).andExpect(status().isNoContent());

        assertThat(bookRepository.existsById(1L)).isFalse();
    }

    private <T> T parseObject(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    private void assertEquality(BookInfo info, Book book) {
        assertThat(info).isEqualTo(BookInfo.of(book));
    }

}
