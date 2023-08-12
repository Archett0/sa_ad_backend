package sg.edu.nus.ad_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BasicEntity {

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "donor_id")
    private Member donor;

    @OneToOne
    @JoinColumn(name = "recipient_id")
    private Member recipient;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    private Integer status;
}
