package sg.edu.nus.ad_backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sg.edu.nus.ad_backend.model.ReviewLog;
import sg.edu.nus.ad_backend.model.Staff;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewLogRepositoryTest {

    @Autowired
    private ReviewLogRepository repository;

    @Autowired
    private StaffRepository sRepository;

    @Test
    public void testFindAll() {
        Staff staff = new Staff("staff1", "123", "123@123.com", "ps1", 0);
        sRepository.save(staff);
        ReviewLog reviewLog1 = new ReviewLog(1L, 0, staff, 0, "comment1");
        ReviewLog reviewLog2 = new ReviewLog(2L, 0, staff, 1, "comment2");
        repository.save(reviewLog1);
        repository.save(reviewLog2);
        List<ReviewLog> reviewLogs = repository.findAll();
        assertEquals(2, reviewLogs.size());
        assertEquals("comment1", reviewLogs.get(0).getComment());
        assertEquals("comment2", reviewLogs.get(1).getComment());
    }

    @Test
    public void testFindById() {
        Staff staff = new Staff("staff1", "123", "123@123.com", "ps1", 0);
        sRepository.save(staff);
        ReviewLog reviewLog = new ReviewLog(1L, 0, staff, 0, "comment1");
        repository.save(reviewLog);
        Optional<ReviewLog> result = repository.findById(reviewLog.getId());
        assertTrue(result.isPresent());
        assertEquals(reviewLog.getComment(), result.get().getComment());
    }

    @Test
    public void testSave() {
        Staff staff = new Staff("staff1", "123", "123@123.com", "ps1", 0);
        sRepository.save(staff);
        ReviewLog reviewLog = new ReviewLog(1L, 0, staff, 0, "comment1");
        ReviewLog result = repository.save(reviewLog);
        assertEquals(reviewLog, result);
    }

    @Test
    public void testDelete() {
        Staff staff = new Staff("staff1", "123", "123@123.com", "ps1", 0);
        sRepository.save(staff);
        ReviewLog reviewLog = new ReviewLog(1L, 0, staff, 0, "comment1");
        repository.save(reviewLog);
        repository.delete(reviewLog);
        assertEquals(0, repository.count());
    }
}
