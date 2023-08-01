package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.MemberCredit;

import java.util.List;

public interface IMemberCreditService {
    List<MemberCredit> getAllMemberCredits();
    MemberCredit getMemberCreditById(Long id);
    MemberCredit saveMemberCredit(MemberCredit memberCredit);
    Void deleteMemberCreditById(Long id);
}
