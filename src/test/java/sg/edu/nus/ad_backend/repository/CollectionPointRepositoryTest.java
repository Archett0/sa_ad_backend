package sg.edu.nus.ad_backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sg.edu.nus.ad_backend.model.CollectionPoint;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CollectionPointRepositoryTest {
    @Autowired
    private CollectionPointRepository cpRepository;

    @Test
    public void testFindAll() {
        CollectionPoint cp1 = new CollectionPoint("cp01", "Singapore, Singapore, 187352", 0, "https://demo_qr.server/1");
        CollectionPoint cp2 = new CollectionPoint("cp02", "Singapore, Singapore, 196237", 0, "https://demo_qr.server/2");
        cpRepository.save(cp1);
        cpRepository.save(cp2);
        List<CollectionPoint> collectionPoints = cpRepository.findAll();
        assertEquals(2, collectionPoints.size());
        assertEquals("cp01", collectionPoints.get(0).getName());
        assertEquals("cp02", collectionPoints.get(1).getName());
    }

    @Test
    public void testFindById() {
        CollectionPoint cp = new CollectionPoint("cp01", "Singapore, Singapore, 187352", 0, "https://demo_qr.server/1");
        cpRepository.save(cp);
        Long generatedId = cp.getId();
        Optional<CollectionPoint> result = cpRepository.findById(generatedId);
        assertTrue(result.isPresent());
        assertEquals(cp.getName(), result.get().getName());
    }

    @Test
    public void testSave() {
        CollectionPoint cp = new CollectionPoint("cp01", "Singapore, Singapore, 187352", 0, "https://demo_qr.server/1");
        CollectionPoint result = cpRepository.save(cp);
        assertEquals(cp, result);
    }

    @Test
    public void testDelete() {
        CollectionPoint cp = new CollectionPoint("cp01", "Singapore, Singapore, 187352", 0, "https://demo_qr.server/1");
        cpRepository.save(cp);
        cpRepository.delete(cp);
        assertEquals(0, cpRepository.count());
    }
}
