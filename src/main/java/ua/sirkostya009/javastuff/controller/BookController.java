package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dto.BookInfo;
import ua.sirkostya009.javastuff.service.BookService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public Page<BookInfo> books(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "author", required = false) String author,
                                @RequestParam(value = "title", required = false) String title) {
        var p = bookService.findBy(author, title, page);
        return new PageImpl<>(toInfo(p.getContent()), p.getPageable(), p.getTotalElements());
    }

    @GetMapping("/genre/{id}")
    public List<BookInfo> byGenre(@PathVariable Long id) {
        return toInfo(bookService.findByGenreId(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookInfo post(@RequestBody @Valid BookInfo info) {
        return BookInfo.of(bookService.add(info));
    }

    @GetMapping("/{id}")
    public BookInfo get(@PathVariable Long id) {
        return BookInfo.of(bookService.findBy(id));
    }

    @PutMapping("/{id}")
    public BookInfo update(@PathVariable Long id, @RequestBody @Valid BookInfo info) {
        Book persisted = bookService.update(id, info);
        return BookInfo.of(persisted);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    private List<BookInfo> toInfo(Collection<Book> books) {
        return books.stream().map(BookInfo::of).toList();
    }
}
