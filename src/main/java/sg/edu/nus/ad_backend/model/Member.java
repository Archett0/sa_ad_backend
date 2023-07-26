package sg.edu.nus.ad_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Member extends BasicEntity {

    private String username;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String password;

    private LocalDate birthday;

    private Integer gender;

    private String bio;

    private String avatar;
}
