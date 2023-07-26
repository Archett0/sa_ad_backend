package sg.edu.nus.ad_backend.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.ad_backend.model.BasicEntity;

import java.time.LocalDateTime;

public class BasicEntityListener {
    @PrePersist
    public void prePersist(BasicEntity basicEntity) {
        basicEntity.setGmtCreated(LocalDateTime.now());
        basicEntity.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(BasicEntity basicEntity) {
        basicEntity.setGmtModified(LocalDateTime.now());
    }
}
