package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.CollectionPoint;

import java.util.List;

public interface ICollectionPointService {
    List<CollectionPoint> getAllCollectionPoints();
    CollectionPoint getCollectionPointById(Long id);
    CollectionPoint saveCollectionPoint(CollectionPoint collectionPoint);
    Void deleteCollectionPointById(Long id);
}
