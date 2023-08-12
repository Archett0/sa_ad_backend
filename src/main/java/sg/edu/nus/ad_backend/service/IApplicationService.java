package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.Application;

import java.util.List;

public interface IApplicationService {
    List<Application> getAllApplications();
    Application getApplicationById(Long id);
    Application saveApplication(Application application);
    Void deleteApplicationById(Long id);
    List<Application> getByMemberId(Long id);
}
