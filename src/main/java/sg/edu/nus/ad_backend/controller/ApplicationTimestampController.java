package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.model.ApplicationTimestamp;
import sg.edu.nus.ad_backend.service.IApplicationTimestampService;

import java.util.List;

@RestController
@RequestMapping("/api/applicationTimestamp")
public class ApplicationTimestampController {
    private final IApplicationTimestampService applicationTimestampService;

    public ApplicationTimestampController(IApplicationTimestampService applicationTimestampService) {
        this.applicationTimestampService = applicationTimestampService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationTimestamp>> getAll() {
        return ResponseEntity.ok(applicationTimestampService.getAllApplicationTimestamps());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationTimestamp> getOne(@PathVariable("id") Long id) {
        ApplicationTimestamp applicationTimestamp = applicationTimestampService.getApplicationTimestampById(id);
        if (applicationTimestamp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationTimestamp);
    }

    @PostMapping
    public ResponseEntity<ApplicationTimestamp> create(@RequestBody ApplicationTimestamp applicationTimestamp) {
        return ResponseEntity.ok(applicationTimestampService.saveApplicationTimestamp(applicationTimestamp));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationTimestamp> update(@PathVariable("id") Long id, @RequestBody ApplicationTimestamp applicationTimestamp) {
        ApplicationTimestamp existing = applicationTimestampService.getApplicationTimestampById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        applicationTimestamp.setId(id);
        return ResponseEntity.ok(applicationTimestampService.saveApplicationTimestamp(applicationTimestamp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        ApplicationTimestamp existing = applicationTimestampService.getApplicationTimestampById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationTimestampService.deleteApplicationTimestampById(id));
    }
}
