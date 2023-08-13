package sg.edu.nus.ad_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String username;
    private String displayName;
    private String phoneNumber;
    private String email;
    private LocalDate birthday;
    private Integer gender;
    private String bio;
    private String avatar;
}
