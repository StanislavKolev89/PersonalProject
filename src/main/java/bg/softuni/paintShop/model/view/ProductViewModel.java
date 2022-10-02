package bg.softuni.paintShop.model.view;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewModel {

    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private String phoneNumber;

    private String imageUrl;

    private String category;

}
