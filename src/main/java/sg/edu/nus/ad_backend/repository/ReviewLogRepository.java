package sg.edu.nus.ad_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.ad_backend.model.ReviewLog;

public interface ReviewLogRepository extends JpaRepository<ReviewLog, Long> {
}
