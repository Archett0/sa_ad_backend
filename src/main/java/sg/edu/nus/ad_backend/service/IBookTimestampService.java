package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.BookTimestamp;

import java.util.List;

public interface IBookTimestampService {
    List<BookTimestamp> getAllBookTimestamps();
    BookTimestamp getBookTimestampById(Long id);
    BookTimestamp saveBookTimestamp(BookTimestamp bookTimestamp);
    Void deleteBookTimestampById(Long id);
}
