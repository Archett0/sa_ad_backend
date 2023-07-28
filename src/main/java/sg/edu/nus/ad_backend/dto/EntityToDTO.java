package sg.edu.nus.ad_backend.dto;

import sg.edu.nus.ad_backend.model.Member;

public class EntityToDTO {
    public static MemberDTO memberToDto(Member member) {
        return new MemberDTO(
                member.getUsername(),
                member.getDisplayName(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getBirthday(),
                member.getGender(),
                member.getBio(),
                member.getAvatar());
    }
}
