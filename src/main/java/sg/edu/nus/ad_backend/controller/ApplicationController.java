package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.model.Application;
import sg.edu.nus.ad_backend.service.IApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    private final IApplicationService applicationService;

    public ApplicationController(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAll() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getOne(@PathVariable("id") Long id) {
        Application application = applicationService.getApplicationById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PostMapping
    public ResponseEntity<Application> create(@RequestBody Application application) {
        return ResponseEntity.ok(applicationService.saveApplication(application));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> update(@PathVariable("id") Long id, @RequestBody Application application) {
        Application existing = applicationService.getApplicationById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        application.setId(id);
        return ResponseEntity.ok(applicationService.saveApplication(application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Application existing = applicationService.getApplicationById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationService.deleteApplicationById(id));
    }
}
