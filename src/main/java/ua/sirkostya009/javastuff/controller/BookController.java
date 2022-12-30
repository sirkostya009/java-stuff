package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dto.BookInfo;
import ua.sirkostya009.javastuff.service.BookService;
import ua.sirkostya009.javastuff.service.GenreService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;

    @GetMapping
    public List<BookInfo> books() {
        return toInfo(bookService.all());
    }

    @GetMapping("/category/{id}")
    public List<BookInfo> byCategory(@PathVariable Long id) {
        return toInfo(bookService.byCategory(genreService.get(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookInfo post(@RequestBody @Valid BookInfo info) {
        return toInfo(bookService.add(info));
    }

    @GetMapping("/{id}")
    public BookInfo get(@PathVariable Long id) {
        return toInfo(bookService.get(id));
    }

    @PutMapping("/{id}")
    public BookInfo update(@PathVariable Long id, @Valid @RequestBody BookInfo info) {
        return toInfo(bookService.update(id, info));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("")
    public BookInfo search(@PathParam("author") String author,
                           @PathParam("title") String title) {
        return toInfo(bookService.get(1L));
    }

    private @Valid BookInfo toInfo(Book book) {
        return new BookInfo(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre().getId());
    }

    private List<@Valid BookInfo> toInfo(Collection<Book> books) {
        return books.stream().map(this::toInfo).toList();
    }
}
