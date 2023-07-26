package sg.edu.nus.ad_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collection_point")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CollectionPoint extends BasicEntity {

    private String name;

    private String address;

    private Integer status;

    @Column(name = "qr_code")
    private String qrCode;
}
