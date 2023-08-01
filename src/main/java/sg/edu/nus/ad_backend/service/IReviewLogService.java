package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.ReviewLog;

import java.util.List;

public interface IReviewLogService {
    List<ReviewLog> getAllReviewLogs();
    ReviewLog getReviewLogById(Long id);
    ReviewLog saveReviewLog(ReviewLog reviewLog);
    Void deleteReviewLogById(Long id);
}
