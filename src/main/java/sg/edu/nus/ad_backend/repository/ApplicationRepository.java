package sg.edu.nus.ad_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.edu.nus.ad_backend.model.Application;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT a FROM Application a WHERE a.recipient.id=:memberId")
    List<Application> getApplicationsByMemberId(Long memberId);

    @Query("SELECT a FROM Application a WHERE a.status=3 AND a.recipient.id=:recipientId AND a.book.id=:bookId")
    Application getByRecipientIdAndBookId(Long recipientId, Long bookId);
}
