package sg.edu.nus.ad_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.ad_backend.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
