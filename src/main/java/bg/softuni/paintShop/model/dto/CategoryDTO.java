package bg.softuni.paintShop.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;
    @NotNull
    @Size(min = 5, message = "Category name too short. Has to be at least 5 letters long!")
    private String name;
    @NotNull
    @Size(min = 10, message = "Image url too short!")
    private String imageUrl;

    private boolean deleted;
}
