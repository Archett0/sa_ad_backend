package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.MemberCredit;
import sg.edu.nus.ad_backend.repository.MemberCreditRepository;
import sg.edu.nus.ad_backend.service.IMemberCreditService;

import java.util.List;

@Service
public class MemberCreditService implements IMemberCreditService {
    private final MemberCreditRepository repository;

    public MemberCreditService(MemberCreditRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MemberCredit> getAllMemberCredits() {
        return repository.findAll();
    }

    @Override
    public MemberCredit getMemberCreditById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public MemberCredit saveMemberCredit(MemberCredit memberCredit) {
        return repository.save(memberCredit);
    }

    @Override
    public Void deleteMemberCreditById(Long id) {
        repository.deleteById(id);
        return null;
    }
}
