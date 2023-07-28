package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.Member;
import sg.edu.nus.ad_backend.repository.MemberRepository;
import sg.edu.nus.ad_backend.service.IMemberService;

import java.util.List;

@Service
public class MemberService implements IMemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Member> getAllMembers() {
        return repository.findAll();
    }

    @Override
    public Member getMemberById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Member saveMember(Member member) {
        return repository.save(member);
    }

    @Override
    public Void deleteMemberById(Long id) {
        repository.deleteById(id);
        return null;
    }
}
