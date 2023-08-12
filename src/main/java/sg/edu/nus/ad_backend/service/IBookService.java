package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.Book;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book saveBook(Book book);
    Void deleteBookById(Long id);
    List<Book> getBooksByMemberId(Long id);
    List<Book> getRandomBooks();
}
