package ua.sirkostya009.javastuff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dto.BookInfo;
import ua.sirkostya009.javastuff.exception.NotFoundException;
import ua.sirkostya009.javastuff.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final GenreService genreService;

    private final static int BOOKS_PER_PAGE = 10;

    @Override
    public Page<Book> findBy(String author, String title, int page) {
        return repository.findByAuthorAndTitle(author, title, PageRequest.of(page, BOOKS_PER_PAGE));
    }

    @Override
    public List<Book> byGenre(Long id) {
        return repository.findByGenre(id);
    }

    @Override
    public Book add(BookInfo info) {
        return repository.save(new Book(
                null,
                info.getTitle(),
                info.getAuthor(),
                info.getGenreId() != null ? genreService.findBy(info.getGenreId()) : null
        ));
    }

    @Override
    public Book findBy(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Book update(Long id, BookInfo info) {
        var book = findBy(id);

        if (info.getGenreId() != null)
            repository.updateBookGenre(id, genreService.findBy(id));

        if (info.getAuthor() != null && !info.getAuthor().isBlank())
            repository.updateBookAuthor(id, info.getAuthor());

        if (info.getTitle() != null && !info.getTitle().isBlank())
            repository.updateBookTitle(id, info.getTitle());

        return book;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
