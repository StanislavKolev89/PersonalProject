package bg.softuni.paintShop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentNotFoundException extends RuntimeException {
    private final Long id;
}
