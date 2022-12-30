package ua.sirkostya009.javastuff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final static int BOOKS_PER_PAGE = 10;

    @Override
    public Page<Book> find(String author, String title, int page) {
        return bookRepository.findByAuthorOrTitle(author, title, PageRequest.of(page, BOOKS_PER_PAGE));
    }

    @Override
    public List<Book> byGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    @Transactional
    public Book add(BookInfo info) {
        var book = bookRepository.save(new Book(
                null,
                info.getTitle(),
                info.getAuthor(),
                info.getGenreId() != null ? genreService.get(info.getGenreId()) : null
        ));

        if (info.getGenreId() != null)
            genreService.get(info.getGenreId()).getBooks().add(book);

        return book;
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
        found.setGenre(genreService.get(info.getGenreId()));
        return found;
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(get(id));
    }
}
