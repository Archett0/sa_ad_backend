package sg.edu.nus.ad_backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sg.edu.nus.ad_backend.model.Book;
import sg.edu.nus.ad_backend.model.CollectionPoint;
import sg.edu.nus.ad_backend.model.Member;
import sg.edu.nus.ad_backend.model.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private MemberRepository mRepository;

    @Autowired
    private BookRepository bRepository;

    @Autowired
    private CollectionPointRepository cpRepository;

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
        Book book1 = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, member2);
        Book book2 = new Book(456, "Book2", "Au2", "http:cover2", 1, "Desc2", "literature", "Press2", 10, 2, member1);
        bRepository.save(book1);
        bRepository.save(book2);
        CollectionPoint cp1 = new CollectionPoint("cp01", "Singapore, Singapore, 187352", 0, "https://demo_qr.server/1");
        CollectionPoint cp2 = new CollectionPoint("cp02", "Singapore, Singapore, 196237", 0, "https://demo_qr.server/2");
        cpRepository.save(cp1);
        cpRepository.save(cp2);
        Transaction transaction1 = new Transaction(book1, member2, member1, LocalDateTime.now(), null, 0, cp1, null, null);
        Transaction transaction2 = new Transaction(book2, member1, member2, LocalDateTime.now(), LocalDateTime.now(), 2, cp2, 3.5, 4.2);
        repository.save(transaction1);
        repository.save(transaction2);
        List<Transaction> transactions = repository.findAll();
        assertEquals(2, transactions.size());
        assertEquals("cp01", transactions.get(0).getCollectionPoint().getName());
        assertEquals("cp02", transactions.get(1).getCollectionPoint().getName());
        assertEquals("username1", transactions.get(0).getRecipient().getUsername());
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
        Book book = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, member2);
        bRepository.save(book);
        CollectionPoint cp = new CollectionPoint("cp01", "Singapore, Singapore, 187352", 0, "https://demo_qr.server/1");
        cpRepository.save(cp);
        Transaction transaction = new Transaction(book, member2, member1, LocalDateTime.now(), null, 0, cp, null, null);
        repository.save(transaction);
        Optional<Transaction> result = repository.findById(transaction.getId());
        assertTrue(result.isPresent());
        assertEquals(cp.getName(), result.get().getCollectionPoint().getName());
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
        Book book = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, member2);
        bRepository.save(book);
        CollectionPoint cp = new CollectionPoint("cp01", "Singapore, Singapore, 187352", 0, "https://demo_qr.server/1");
        cpRepository.save(cp);
        Transaction transaction = new Transaction(book, member2, member1, LocalDateTime.now(), null, 0, cp, null, null);
        Transaction result = repository.save(transaction);
        assertEquals(transaction, result);
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
        Book book = new Book(123, "Book1", "Au1", "http:cover1", 1, "Desc1", "fiction", "Press1", 0, 1, member2);
        bRepository.save(book);
        CollectionPoint cp = new CollectionPoint("cp01", "Singapore, Singapore, 187352", 0, "https://demo_qr.server/1");
        cpRepository.save(cp);
        Transaction transaction = new Transaction(book, member2, member1, LocalDateTime.now(), null, 0, cp, null, null);
        repository.save(transaction);
        repository.delete(transaction);
        assertEquals(0, repository.count());
    }
}
