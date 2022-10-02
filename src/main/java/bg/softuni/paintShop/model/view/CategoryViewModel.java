package bg.softuni.paintShop.model.view;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryViewModel {

    private Long id;

    private String name;

    private String imageUrl;

    private boolean deleted;
}
