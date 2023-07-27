package sg.edu.nus.ad_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.ad_backend.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
