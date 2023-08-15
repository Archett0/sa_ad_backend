package sg.edu.nus.ad_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_timestamp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookTimestamp extends BasicEntity {
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "status_prev")
    private Integer statusPrev;

    @Column(name = "status_now")
    private Integer statusNow;

    @Column(name = "action_time")
    private LocalDateTime actionTime;

    @Column(name = "role_type")
    private Integer roleType;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_username")
    private String roleUsername;
}
