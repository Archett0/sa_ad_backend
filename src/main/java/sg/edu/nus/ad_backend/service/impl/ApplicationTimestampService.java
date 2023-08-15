package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.ApplicationTimestamp;
import sg.edu.nus.ad_backend.repository.ApplicationTimestampRepository;
import sg.edu.nus.ad_backend.service.IApplicationTimestampService;

import java.util.List;

@Service
public class ApplicationTimestampService implements IApplicationTimestampService {
    private final ApplicationTimestampRepository repository;

    public ApplicationTimestampService(ApplicationTimestampRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ApplicationTimestamp> getAllApplicationTimestamps() {
        return repository.findAll();
    }

    @Override
    public ApplicationTimestamp getApplicationTimestampById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ApplicationTimestamp saveApplicationTimestamp(ApplicationTimestamp applicationTimestamp) {
        return repository.save(applicationTimestamp);
    }

    @Override
    public Void deleteApplicationTimestampById(Long id) {
        repository.deleteById(id);
        return null;
    }
}
