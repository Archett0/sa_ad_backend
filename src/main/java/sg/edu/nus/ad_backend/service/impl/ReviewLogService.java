package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.ReviewLog;
import sg.edu.nus.ad_backend.repository.ReviewLogRepository;
import sg.edu.nus.ad_backend.service.IReviewLogService;

import java.util.List;

@Service
public class ReviewLogService implements IReviewLogService {
    private final ReviewLogRepository repository;

    public ReviewLogService(ReviewLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ReviewLog> getAllReviewLogs() {
        return repository.findAll();
    }

    @Override
    public ReviewLog getReviewLogById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ReviewLog saveReviewLog(ReviewLog reviewLog) {
        return repository.save(reviewLog);
    }

    @Override
    public Void deleteReviewLogById(Long id) {
        repository.deleteById(id);
        return null;
    }
}
