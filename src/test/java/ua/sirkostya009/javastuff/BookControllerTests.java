package ua.sirkostya009.javastuff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.BookInfo;
import ua.sirkostya009.javastuff.repository.BookRepository;
import ua.sirkostya009.javastuff.repository.GenreRepository;

import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class
)
@AutoConfigureMockMvc
public class BookControllerTests {

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
        var uncategorized = genreRepository.save(new Genre(null, "Uncategorized", Set.of()));
        var sciFi = genreRepository.save(new Genre(null, "Sci-Fi", Set.of()));
        bookRepository.save(new Book(null, "No category book", "Author 1", uncategorized));
        bookRepository.save(new Book(null, "Science fiction book", "Author 2", sciFi));
    }

    @AfterEach
    public void tearDown() {
        bookRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON);

        var response = mvc
                .perform(request)
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

    @Test
    public void testAddBook() throws Exception {
        var info = new BookInfo(null, "Another sci-fi book", "Author 3", 2L);

        var request = MockMvcRequestBuilders
                .post("/api/v1/books/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(info))
                .contentType(MediaType.APPLICATION_JSON);

        var response = mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var returnedInfo = parseObject(response, BookInfo.class);
        var persistedBook = bookRepository.findById(3L).get();

        assertThat(returnedInfo.getId()).isEqualTo(persistedBook.getId());
        assertThat(returnedInfo.getTitle()).isEqualTo(persistedBook.getTitle());
        assertThat(returnedInfo.getAuthor()).isEqualTo(persistedBook.getAuthor());
        assertThat(returnedInfo.getGenreId()).isEqualTo(persistedBook.getGenre().getId());
    }

    @Test
    public void testGetBookById() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/api/v1/books/1")
                .accept(MediaType.APPLICATION_JSON);

        System.out.println(bookRepository.findAll());
        var response = mvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var returnedInfo = parseObject(response, BookInfo.class);
        var persistedBook = bookRepository.findById(1L).get();

        assertThat(returnedInfo.getId()).isEqualTo(persistedBook.getId());
        assertThat(returnedInfo.getTitle()).isEqualTo(persistedBook.getTitle());
        assertThat(returnedInfo.getAuthor()).isEqualTo(persistedBook.getAuthor());
        assertThat(returnedInfo.getGenreId()).isEqualTo(persistedBook.getGenre().getId());
    }

    @Test
    public void testUpdateBook() throws Exception {
        var info = new BookInfo(null, "Updated Title", "Updated Author", 2L);

        var request = MockMvcRequestBuilders
                .put("/api/v1/books/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(info))
                .contentType(MediaType.APPLICATION_JSON);

        var response = mvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var returnedInfo = parseObject(response, BookInfo.class);
        var persistedBook = bookRepository.findById(1L).get();

        assertThat(returnedInfo).isEqualTo(persistedBook);
    }

    @Test
    public void testDeleteBook() throws Exception {
        var request = MockMvcRequestBuilders
                .delete("/api/v1/books/1")
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(bookRepository.existsById(1L)).isFalse();
    }

    private <T> T parseObject(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }
}
