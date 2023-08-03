package sg.edu.nus.ad_backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sg.edu.nus.ad_backend.model.Book;
import sg.edu.nus.ad_backend.model.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    @Autowired
    private MemberRepository mRepository;

    @Test
    public void testFindAll() {
        Member member1 = new Member(
                "username1",
                "displayname1",
                "+65 1237-2131",
                "adqeqwe@gamil.com",
                "12123sgda8asdgayug8eg2eyguygagsd",
                LocalDate.now(),
                0,
                "bio",
                "https://avatar.com"
        );
        Member member2 = new Member(
                "username2",
                "displayname2",
                "+65 1412-9878",
                "adqeqwe@gamil.com",
                "654456sgda8asdgayug8eg2eyguygagsd",
                LocalDate.now(),
                1,
                "bio",
                "https://avatar.com"
        );
        mRepository.save(member1);
        mRepository.save(member2);
        Book book1 = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, 0, member1);
        Book book2 = new Book(456, "Book2", "Au2", "http:cover2", 1, "Desc2", "literature", "Press2", 10, 2, 0, member2);
        repository.save(book1);
        repository.save(book2);
        List<Book> books = repository.findAll();
        assertEquals(2, books.size());
        assertEquals("username1", books.get(0).getDonor().getUsername());
        assertEquals("username2", books.get(1).getDonor().getUsername());
    }

    @Test
    public void testFindById() {
        Member member = new Member(
                "username1",
                "displayname1",
                "+65 1237-2131",
                "adqeqwe@gamil.com",
                "12123sgda8asdgayug8eg2eyguygagsd",
                LocalDate.now(),
                0,
                "bio",
                "https://avatar.com"
        );
        mRepository.save(member);
        Book book = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, 0, member);
        repository.save(book);
        Optional<Book> result = repository.findById(book.getId());
        assertTrue(result.isPresent());
        assertEquals(book.getIsbn(), result.get().getIsbn());
    }

    @Test
    public void testSave() {
        Member member = new Member(
                "username1",
                "displayname1",
                "+65 1237-2131",
                "adqeqwe@gamil.com",
                "12123sgda8asdgayug8eg2eyguygagsd",
                LocalDate.now(),
                0,
                "bio",
                "https://avatar.com"
        );
        mRepository.save(member);
        Book book = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, 0, member);
        Book result = repository.save(book);
        assertEquals(book, result);
    }

    @Test
    public void testDelete() {
        Member member = new Member(
                "username1",
                "displayname1",
                "+65 1237-2131",
                "adqeqwe@gamil.com",
                "12123sgda8asdgayug8eg2eyguygagsd",
                LocalDate.now(),
                0,
                "bio",
                "https://avatar.com"
        );
        mRepository.save(member);
        Book book = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, 0, member);
        repository.save(book);
        repository.delete(book);
        assertEquals(0, repository.count());
    }
}
