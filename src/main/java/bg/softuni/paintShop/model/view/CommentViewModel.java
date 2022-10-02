package bg.softuni.paintShop.model.view;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentViewModel {

    private Long id;

    private String commentCreator;

    private String content;
}
