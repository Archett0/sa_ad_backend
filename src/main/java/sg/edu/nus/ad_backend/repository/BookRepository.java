package sg.edu.nus.ad_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.edu.nus.ad_backend.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.donor.id = :memberId")
    List<Book> getBooksByMemberId(Long memberId);
}
