package sg.edu.nus.ad_backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sg.edu.nus.ad_backend.model.Staff;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StaffRepositoryTest {

    @Autowired
    private StaffRepository repository;

    @Test
    public void testFindAll() {
        Staff staff1 = new Staff("staff1", "123", "123@123.com", "ps1", 0);
        Staff staff2 = new Staff("staff2", "234", "234@123.com", "ps2", 1);
        repository.save(staff1);
        repository.save(staff2);
        List<Staff> staffs = repository.findAll();
        assertEquals(2, staffs.size());
        assertEquals("staff1", staffs.get(0).getUsername());
        assertEquals("staff2", staffs.get(1).getUsername());
    }

    @Test
    public void testFindById() {
        Staff staff = new Staff("staff1", "123", "123@123.com", "ps1", 0);
        repository.save(staff);
        Optional<Staff> result = repository.findById(staff.getId());
        assertTrue(result.isPresent());
        assertEquals(staff.getUsername(), result.get().getUsername());
    }

    @Test
    public void testSave() {
        Staff staff = new Staff("staff1", "123", "123@123.com", "ps1", 0);
        Staff result = repository.save(staff);
        assertEquals(staff, result);
    }

    @Test
    public void testDelete() {
        Staff staff = new Staff("staff1", "123", "123@123.com", "ps1", 0);
        repository.save(staff);
        repository.delete(staff);
        assertEquals(0, repository.count());
    }
}
