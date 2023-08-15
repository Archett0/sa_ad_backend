package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.ApplicationTimestamp;

import java.util.List;

public interface IApplicationTimestampService {
    List<ApplicationTimestamp> getAllApplicationTimestamps();
    ApplicationTimestamp getApplicationTimestampById(Long id);
    ApplicationTimestamp saveApplicationTimestamp(ApplicationTimestamp applicationTimestamp);
    Void deleteApplicationTimestampById(Long id);
}
