package ua.sirkostya009.javastuff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.sirkostya009.javastuff.dao.Book;
import ua.sirkostya009.javastuff.dto.BookInfo;
import ua.sirkostya009.javastuff.exception.NotFoundException;
import ua.sirkostya009.javastuff.mapper.BookMapper;
import ua.sirkostya009.javastuff.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final BookMapper mapper;

    private final static int BOOKS_PER_PAGE = 10;

    @Override
    public Page<Book> findBy(String author, String title, int page) {
        return repository.findByAuthorAndTitle(author, title, PageRequest.of(page, BOOKS_PER_PAGE));
    }

    @Override
    public List<Book> findByGenreId(Long id) {
        return repository.findByGenreId(id);
    }

    @Override
    public Book add(BookInfo info) {
        return repository.save(mapper.mapToBook(info));
    }

    @Override
    public Book findBy(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Book update(Long id, BookInfo info) {
        info.setId(id); // purposefully ignore info's provided id
        return repository.save(mapper.mapToBook(info));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
