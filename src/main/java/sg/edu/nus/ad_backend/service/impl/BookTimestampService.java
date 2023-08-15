package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.BookTimestamp;
import sg.edu.nus.ad_backend.repository.BookTimestampRepository;
import sg.edu.nus.ad_backend.service.IBookTimestampService;

import java.util.List;

@Service
public class BookTimestampService implements IBookTimestampService {
    private final BookTimestampRepository repository;

    public BookTimestampService(BookTimestampRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BookTimestamp> getAllBookTimestamps() {
        return repository.findAll();
    }

    @Override
    public BookTimestamp getBookTimestampById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public BookTimestamp saveBookTimestamp(BookTimestamp bookTimestamp) {
        return repository.save(bookTimestamp);
    }

    @Override
    public Void deleteBookTimestampById(Long id) {
        repository.deleteById(id);
        return null;
    }
}
