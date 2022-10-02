package bg.softuni.paintShop.model.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")

public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,columnDefinition = "LONGTEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(columnDefinition = "LONGTEXT", name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne()
    private CategoryEntity category;

    @Column(nullable = false)
    private boolean deleted = false;

}
