package bg.softuni.paintShop.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Must enter Title for this product")
    @Size(min = 5, message = "Title is too short!")
    private String title;

    @NotBlank(message = "Must enter some description")
    @Size(min = 15, message = "Description is too short!")
    private String description;

    @NotNull(message = "Enter valid price")
    @Positive(message = "Value must be positive number!")
    private BigDecimal price;

    @NotBlank(message = "It's not possible to create product without image url")
    private String imageUrl;

    @NotNull(message = "Must choose category!")
    private String category;

}
