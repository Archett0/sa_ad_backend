package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.Application;
import sg.edu.nus.ad_backend.repository.ApplicationRepository;
import sg.edu.nus.ad_backend.service.IApplicationService;

import java.util.List;

@Service
public class ApplicationService implements IApplicationService {
    private final ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Application> getAllApplications() {
        return repository.findAll();
    }

    @Override
    public Application getApplicationById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Application saveApplication(Application application) {
        return repository.save(application);
    }

    @Override
    public Void deleteApplicationById(Long id) {
        repository.deleteById(id);
        return null;
    }
}
