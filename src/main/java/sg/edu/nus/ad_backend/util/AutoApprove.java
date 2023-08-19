package sg.edu.nus.ad_backend.util;

import sg.edu.nus.ad_backend.model.Member;

public class AutoApprove {
    public static boolean judge(Member member) {
        double ratio = member.getDonationCount() * 1.0 / (member.getDonationCount() + member.getReceiveCount());
        return ratio >= 0.4;
    }
}
