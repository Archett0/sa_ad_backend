package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.model.CollectionPoint;
import sg.edu.nus.ad_backend.service.ICollectionPointService;

import java.util.List;

@RestController
@RequestMapping("/api/collectionPoint")
public class CollectionPointController {
    private final ICollectionPointService collectionPointService;

    public CollectionPointController(ICollectionPointService collectionPointService) {
        this.collectionPointService = collectionPointService;
    }

    @GetMapping
    public ResponseEntity<List<CollectionPoint>> getAll() {
        return ResponseEntity.ok(collectionPointService.getAllCollectionPoints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionPoint> getOne(@PathVariable("id") Long id) {
        CollectionPoint collectionPoint = collectionPointService.getCollectionPointById(id);
        if (collectionPoint == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collectionPoint);
    }

    @PostMapping
    public ResponseEntity<CollectionPoint> create(@RequestBody CollectionPoint collectionPoint) {
        return ResponseEntity.ok(collectionPointService.saveCollectionPoint(collectionPoint));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionPoint> update(@PathVariable("id") Long id, @RequestBody CollectionPoint collectionPoint) {
        CollectionPoint existing = collectionPointService.getCollectionPointById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        collectionPoint.setId(id);
        return ResponseEntity.ok(collectionPointService.saveCollectionPoint(collectionPoint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        CollectionPoint existing = collectionPointService.getCollectionPointById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collectionPointService.deleteCollectionPointById(id));
    }
}
