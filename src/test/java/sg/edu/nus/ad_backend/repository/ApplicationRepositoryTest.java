package sg.edu.nus.ad_backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sg.edu.nus.ad_backend.model.Application;
import sg.edu.nus.ad_backend.model.Book;
import sg.edu.nus.ad_backend.model.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private MemberRepository mRepository;

    @Autowired
    private BookRepository bRepository;

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
        Book book1 = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, 0, member2);
        Book book2 = new Book(456, "Book2", "Au2", "http:cover2", 1, "Desc2", "literature", "Press2", 10, 2, 0, member1);
        bRepository.save(book1);
        bRepository.save(book2);
        Application application1 = new Application(book1, member1, 0);
        Application application2 = new Application(book2, member2, 3);
        repository.save(application1);
        repository.save(application2);
        List<Application> applications = repository.findAll();
        assertEquals(2, applications.size());
        assertEquals("username2", applications.get(0).getBook().getDonor().getUsername());
        assertEquals("username1", applications.get(1).getBook().getDonor().getUsername());
    }

    @Test
    public void testFindById() {
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
        Book book = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, 0, member2);
        bRepository.save(book);
        Application application = new Application(book, member1, 0);
        repository.save(application);
        Optional<Application> result = repository.findById(application.getId());
        assertTrue(result.isPresent());
        assertEquals(application.getRecipient().getUsername(), result.get().getRecipient().getUsername());
    }

    @Test
    public void testSave() {
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
        Book book = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, 0, member2);
        bRepository.save(book);
        Application application = new Application(book, member1, 0);
        Application result = repository.save(application);
        assertEquals(application, result);
    }

    @Test
    public void testDelete() {
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
        Book book = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, 0,member2);
        bRepository.save(book);
        Application application = new Application(book, member1, 0);
        repository.save(application);
        repository.delete(application);
        assertEquals(0, repository.count());
    }
}