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
public class UsedProductDTO {

    private Long id;

    @NotBlank

    @Size(min = 5, max = 20, message = "Title must be between 5 and 20 characters.")
    private String title;

    @NotBlank(message = "You must enter some description.")
    @Size(min = 10, message = "Description too short!")
    private String description;

    @NotNull(message = "You must enter some price")
    @Positive
    private BigDecimal price;

    @NotNull(message = "You should give number for contact!")
    @Size(min = 10, max = 15, message = "Enter valid phone number!")
    private String phoneNumber;

    private String imageUrl;

    @NotBlank(message = "You have to choose category!")
    private String category;

    private String user;
}
