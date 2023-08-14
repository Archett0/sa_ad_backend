package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.dto.EntityToDTO;
import sg.edu.nus.ad_backend.dto.StaffDTO;
import sg.edu.nus.ad_backend.dto.UpdatePasswordDTO;
import sg.edu.nus.ad_backend.model.Staff;
import sg.edu.nus.ad_backend.service.IStaffService;
import sg.edu.nus.ad_backend.util.EncryptPassword;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final IStaffService staffService;

    public StaffController(IStaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<List<StaffDTO>> getAll() {
        return ResponseEntity.ok(staffService.getAllStaffs().stream().map(EntityToDTO::staffToDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getOne(@PathVariable("id") Long id) {
        Staff staff = staffService.getStaffById(id);
        if (staff == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EntityToDTO.staffToDto(staff));
    }

    @PostMapping
    public ResponseEntity<StaffDTO> create(@RequestBody Staff staff) {
        staff.setPassword(EncryptPassword.encodePassword(staff.getPassword()));
        return ResponseEntity.ok(EntityToDTO.staffToDto(staffService.saveStaff(staff)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> update(@PathVariable("id") Long id, @RequestBody Staff staff) {
        Staff existingStaff = staffService.getStaffById(id);
        if (existingStaff == null) {
            return ResponseEntity.notFound().build();
        }
        staff.setId(id);
        staff.setPassword(existingStaff.getPassword());
        return ResponseEntity.ok(EntityToDTO.staffToDto(staffService.saveStaff(staff)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Staff existingStaff = staffService.getStaffById(id);
        if (existingStaff == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(staffService.deleteStaffById(id));
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordDTO dto) {
        Staff existingStaff = staffService.getStaffById(dto.getId());
        if (existingStaff == null) {
            return ResponseEntity.notFound().build();
        }
        existingStaff.setPassword(EncryptPassword.encodePassword(dto.getPassword()));
        staffService.saveStaff(existingStaff);
        return ResponseEntity.ok().build();
    }
}
