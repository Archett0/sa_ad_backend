package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.CollectionPoint;
import sg.edu.nus.ad_backend.repository.CollectionPointRepository;
import sg.edu.nus.ad_backend.service.ICollectionPointService;

import java.util.List;

@Service
public class CollectionPointService implements ICollectionPointService {
    private final CollectionPointRepository repository;

    public CollectionPointService(CollectionPointRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CollectionPoint> getAllCollectionPoints() {
        return repository.findAll();
    }

    @Override
    public CollectionPoint getCollectionPointById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public CollectionPoint saveCollectionPoint(CollectionPoint collectionPoint) {
        return repository.save(collectionPoint);
    }

    @Override
    public Void deleteCollectionPointById(Long id) {
        repository.deleteById(id);
        return null;
    }
}
