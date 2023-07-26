package sg.edu.nus.ad_backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sg.edu.nus.ad_backend.model.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

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
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> members = memberRepository.findAll();
        assertEquals(2, members.size());
        assertEquals("username1", members.get(0).getUsername());
        assertEquals("username2", members.get(1).getUsername());
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
        memberRepository.save(member);
        Optional<Member> result = memberRepository.findById(member.getId());
        assertTrue(result.isPresent());
        assertEquals(member.getUsername(), result.get().getUsername());
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
        Member result = memberRepository.save(member);
        assertEquals(member, result);
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
        memberRepository.save(member);
        memberRepository.delete(member);
        assertEquals(0, memberRepository.count());
    }
}
