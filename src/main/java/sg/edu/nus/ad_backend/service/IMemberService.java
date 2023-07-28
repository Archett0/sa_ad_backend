package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.Member;

import java.util.List;

public interface IMemberService {
    List<Member> getAllMembers();
    Member getMemberById(Long id);
    Member saveMember(Member member);
    Void deleteMemberById(Long id);
}
