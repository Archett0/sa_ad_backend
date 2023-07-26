package sg.edu.nus.ad_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.ad_backend.listener.BasicEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(BasicEntityListener.class)
public abstract class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gmt_created", updatable = false)
    private LocalDateTime gmtCreated;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;
}
