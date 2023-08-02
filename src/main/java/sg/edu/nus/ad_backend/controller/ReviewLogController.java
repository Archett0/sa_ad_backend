package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.model.ReviewLog;
import sg.edu.nus.ad_backend.service.IReviewLogService;

import java.util.List;

@RestController
@RequestMapping("/api/reviewLog")
public class ReviewLogController {
    private final IReviewLogService reviewLogService;

    public ReviewLogController(IReviewLogService reviewLogService) {
        this.reviewLogService = reviewLogService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewLog>> getAll() {
        return ResponseEntity.ok(reviewLogService.getAllReviewLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewLog> getOne(@PathVariable("id") Long id) {
        ReviewLog reviewLog = reviewLogService.getReviewLogById(id);
        if (reviewLog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviewLog);
    }

    @PostMapping
    public ResponseEntity<ReviewLog> create(@RequestBody ReviewLog reviewLog) {
        return ResponseEntity.ok(reviewLogService.saveReviewLog(reviewLog));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewLog> update(@PathVariable("id") Long id, @RequestBody ReviewLog reviewLog) {
        ReviewLog existing = reviewLogService.getReviewLogById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        reviewLog.setId(id);
        return ResponseEntity.ok(reviewLogService.saveReviewLog(reviewLog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        ReviewLog existing = reviewLogService.getReviewLogById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviewLogService.deleteReviewLogById(id));
    }
}
