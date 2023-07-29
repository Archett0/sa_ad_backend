package sg.edu.nus.ad_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Staff extends BasicEntity {
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String password;

    private Integer role;
}
