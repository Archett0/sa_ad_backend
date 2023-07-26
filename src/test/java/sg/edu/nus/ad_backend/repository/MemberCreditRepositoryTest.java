package sg.edu.nus.ad_backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sg.edu.nus.ad_backend.model.Member;
import sg.edu.nus.ad_backend.model.MemberCredit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberCreditRepositoryTest {
    @Autowired
    private MemberCreditRepository repository;
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
        MemberCredit mc1 = new MemberCredit(member1, 100);
        MemberCredit mc2 = new MemberCredit(member2, 80);
        repository.save(mc1);
        repository.save(mc2);
        List<MemberCredit> mcs = repository.findAll();
        assertEquals(2, mcs.size());
        assertEquals("username1", mcs.get(0).getMember().getUsername());
        assertEquals("username2", mcs.get(1).getMember().getUsername());
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
        MemberCredit mc = new MemberCredit(member, 100);
        repository.save(mc);
        Optional<MemberCredit> result = repository.findById(mc.getId());
        assertTrue(result.isPresent());
        assertEquals(mc.getCredit(), result.get().getCredit());
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
        MemberCredit mc = new MemberCredit(member, 100);
        MemberCredit result = repository.save(mc);
        assertEquals(mc, result);
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
        MemberCredit mc = new MemberCredit(member, 100);
        repository.save(mc);
        repository.delete(mc);
        assertEquals(0, repository.count());
    }
}
