package sg.edu.nus.ad_backend.dto;

import sg.edu.nus.ad_backend.model.Member;
import sg.edu.nus.ad_backend.model.Staff;

public class EntityToDTO {
    public static MemberDTO memberToDto(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getUsername(),
                member.getDisplayName(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getBirthday(),
                member.getGender(),
                member.getBio(),
                member.getAvatar());
    }

    public static StaffDTO staffToDto(Staff staff) {
        return new StaffDTO(
                staff.getId(),
                staff.getUsername(),
                staff.getPhoneNumber(),
                staff.getEmail(),
                staff.getRole()
        );
    }

    public static MemberRatioDTO getRatioDto(Member member) {
        Double res = member.getDonationCount() * 1.0 / (member.getDonationCount() + member.getReceiveCount());
        String formattedRes = String.format("%.2f", res);
        return new MemberRatioDTO(
                member.getId(),
                member.getUsername(),
                member.getDisplayName(),
                member.getDonationCount(),
                member.getReceiveCount(),
                Double.parseDouble(formattedRes)
        );
    }
}
