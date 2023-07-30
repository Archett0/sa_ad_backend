package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.Staff;

import java.util.List;

public interface IStaffService {
    List<Staff> getAllStaffs();
    Staff getStaffById(Long id);
    Staff saveStaff(Staff staff);
    Void deleteStaffById(Long id);
}
