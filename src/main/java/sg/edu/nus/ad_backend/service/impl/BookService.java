package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.Book;
import sg.edu.nus.ad_backend.repository.BookRepository;
import sg.edu.nus.ad_backend.service.IBookService;

import java.util.List;

@Service
public class BookService implements IBookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Override
    public Void deleteBookById(Long id) {
        repository.deleteById(id);
        return null;
    }

    @Override
    public List<Book> getBooksByMemberId(Long id) {
        return repository.getBooksByMemberId(id);
    }

    @Override
    public List<Book> getRandomBooks() {
        return repository.getRandomBooks();
    }
}
