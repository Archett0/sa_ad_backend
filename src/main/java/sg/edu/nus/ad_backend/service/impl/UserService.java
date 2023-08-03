package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.Member;
import sg.edu.nus.ad_backend.model.Staff;
import sg.edu.nus.ad_backend.security.model.LoginUser;
import sg.edu.nus.ad_backend.repository.MemberRepository;
import sg.edu.nus.ad_backend.repository.StaffRepository;
import sg.edu.nus.ad_backend.security.common.SecurityConstants;
import sg.edu.nus.ad_backend.service.IUserService;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final StaffRepository staffRepository;
    private final MemberRepository memberRepository;

    public UserService(StaffRepository staffRepository, MemberRepository memberRepository) {
        this.staffRepository = staffRepository;
        this.memberRepository = memberRepository;
    }

    public Optional<LoginUser> getUserByUsername(String username) {
        LoginUser loginUser = new LoginUser();
        Staff staff = staffRepository.getStaffByUsername(username);
        if (staff != null) {
            loginUser.setUserId(staff.getId());
            loginUser.setUsername(staff.getUsername());
            loginUser.setPassword(staff.getPassword());
            if (staff.getRole().equals(SecurityConstants.SYS_ROLE_ADMIN_KEY)) {
                loginUser.setRole(SecurityConstants.SYS_ROLE_ADMIN);
            } else if (staff.getRole().equals(SecurityConstants.SYS_ROLE_MODERATOR_KEY)) {
                loginUser.setRole(SecurityConstants.SYS_ROLE_MODERATOR);
            } else {
                return Optional.empty();
            }
            return Optional.of(loginUser);
        }
        Member member = memberRepository.getMemberByUsername(username);
        if (member != null) {
            loginUser.setUserId(member.getId());
            loginUser.setUsername(member.getUsername());
            loginUser.setPassword(member.getPassword());
            loginUser.setRole(SecurityConstants.SYS_ROLE_MEMBER);
            return Optional.of(loginUser);
        }
        return Optional.empty();
    }
}
