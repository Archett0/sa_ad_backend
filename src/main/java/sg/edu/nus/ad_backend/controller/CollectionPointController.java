package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.common.BookConstants;
import sg.edu.nus.ad_backend.dto.CPCountDTO;
import sg.edu.nus.ad_backend.model.Book;
import sg.edu.nus.ad_backend.model.CollectionPoint;
import sg.edu.nus.ad_backend.service.IBookService;
import sg.edu.nus.ad_backend.service.ICollectionPointService;

import java.util.*;

@RestController
@RequestMapping("/api/collectionPoint")
public class CollectionPointController {
    private final ICollectionPointService collectionPointService;
    private final IBookService bookService;

    public CollectionPointController(ICollectionPointService collectionPointService, IBookService bookService) {
        this.collectionPointService = collectionPointService;
        this.bookService = bookService;
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

    @GetMapping("/count")
    public ResponseEntity<List<CPCountDTO>> count() {
        List<CollectionPoint> cps = collectionPointService.getAllCollectionPoints();
        Map<Long, CPCountDTO> dtoMap = new HashMap<>();
        for (CollectionPoint cp : cps) {
            if (!dtoMap.containsKey(cp.getId())) {
                dtoMap.put(cp.getId(), new CPCountDTO(cp.getId(), 0, 0));
            }
        }
        List<Book> books = bookService.getAllBooks();
        for (Book book: books) {
            CPCountDTO cur = dtoMap.get(book.getCollectionPoint().getId());
            if (Objects.equals(book.getStatus(), BookConstants.BOOK_PENDING)) {
                cur.setPendingCount(cur.getPendingCount() + 1);
            }
            else if(Objects.equals(book.getStatus(), BookConstants.BOOK_DEPOSITED)) {
                cur.setDepositedCount(cur.getDepositedCount() + 1);
            }
        }
        return ResponseEntity.ok(dtoMap.values().stream().toList());
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<CPCountDTO> countById(@PathVariable("id") Long id) {
        CollectionPoint cp = collectionPointService.getCollectionPointById(id);
        if (cp == null) {
            return ResponseEntity.notFound().build();
        }
        CPCountDTO dto = new CPCountDTO(id, 0, 0);
        List<Book> books = bookService.getAllBooks();
        for (Book book: books) {
            if (!Objects.equals(book.getCollectionPoint().getId(), id)) {
                continue;
            }
            if (Objects.equals(book.getStatus(), BookConstants.BOOK_PENDING)) {
                dto.setPendingCount(dto.getPendingCount() + 1);
            }
            else if(Objects.equals(book.getStatus(), BookConstants.BOOK_DEPOSITED)) {
                dto.setDepositedCount(dto.getDepositedCount() + 1);
            }
        }
        return ResponseEntity.ok(dto);
    }
}
