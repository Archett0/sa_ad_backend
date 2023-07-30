package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.Staff;
import sg.edu.nus.ad_backend.repository.StaffRepository;
import sg.edu.nus.ad_backend.service.IStaffService;

import java.util.List;

@Service
public class StaffService implements IStaffService {
    private final StaffRepository repository;

    public StaffService(StaffRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Staff> getAllStaffs() {
        return repository.findAll();
    }

    @Override
    public Staff getStaffById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Staff saveStaff(Staff staff) {
        return repository.save(staff);
    }

    @Override
    public Void deleteStaffById(Long id) {
        repository.deleteById(id);
        return null;
    }
}
