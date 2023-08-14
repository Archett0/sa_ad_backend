package sg.edu.nus.ad_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Book extends BasicEntity {

    private String isbn;

    private String title;

    private String author;

    private String cover;

    @Column(name = "book_condition")
    private Integer bookCondition;

    private String description;

    private String genre;

    private String press;

    private Integer language;

    private Integer status;

    @Column(name = "like_count")
    private Integer likeCount;

    @OneToOne
    @JoinColumn(name = "donor_id")
    private Member donor;

    @ManyToOne
    @JoinColumn(name = "collection_point_id")
    private CollectionPoint collectionPoint;
}
