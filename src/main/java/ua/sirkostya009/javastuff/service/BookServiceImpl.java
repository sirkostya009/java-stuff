package ua.sirkostya009.javastuff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dao.Genre;
import ua.sirkostya009.javastuff.dto.BookInfo;
import ua.sirkostya009.javastuff.exception.NotFoundException;
import ua.sirkostya009.javastuff.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreService genreService;

    @Override
    public List<Book> all() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> byCategory(Genre genre) {
        return bookRepository.findByCategory(genre);
    }

    @Override
    public Book add(BookInfo info) {
        return bookRepository.save(new Book(
                null,
                info.getTitle(),
                info.getAuthor(),
                info.getCategoryId() != null ? genreService.get(info.getCategoryId()) : null
        ));
    }

    @Override
    public Book get(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
    }

    @Override
    public Book update(Long id, BookInfo info) {
        var found = get(id);
        found.setAuthor(info.getAuthor());
        found.setGenre(genreService.get(info.getCategoryId()));
        return found;
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(get(id));
    }
}
