package sg.edu.nus.ad_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "moderator_review_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewLog extends BasicEntity {
    private Long targetId;

    private Integer targetType;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private Staff moderator;

    @Column(name = "review_conclusion")
    private Integer conclusion;

    @Column(name = "review_comment")
    private String comment;
}
