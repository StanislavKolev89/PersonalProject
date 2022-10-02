package bg.softuni.paintShop.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "LONGTEXT", name = "image_url")
    private String imageUrl;


    @Column(nullable = false)
    private boolean deleted = false;


}