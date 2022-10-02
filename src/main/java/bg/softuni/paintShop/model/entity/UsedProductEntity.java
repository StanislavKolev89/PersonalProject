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
@Table(name = "used_products")

public class UsedProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,columnDefinition = "LONGTEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(columnDefinition = "LONGTEXT", name = "image_url")
    private String imageUrl;

    @ManyToOne
    private CategoryEntity category;

    @ManyToOne
    private UserEntity user;

}
