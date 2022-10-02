package bg.softuni.paintShop.service;

import bg.softuni.paintShop.model.dto.CommentCreationDTO;
import bg.softuni.paintShop.model.dto.CommentDTO;
import bg.softuni.paintShop.model.entity.CommentEntity;
import bg.softuni.paintShop.model.entity.UserEntity;
import bg.softuni.paintShop.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;


    public CommentDTO createComment(CommentCreationDTO commentCreationDTO) {
        UserEntity author = userService.findByName(commentCreationDTO.getUsername());
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCreatedAt(LocalDateTime.now());
        commentEntity.setAuthor(author);
        commentEntity.setProduct(productService.returnProduct(commentCreationDTO.getProductId()));
        commentEntity.setContent(commentCreationDTO.getContent());

        CommentEntity newAddedComment = commentRepository.save(commentEntity);
        CommentDTO commentDTO = modelMapper.map(newAddedComment, CommentDTO.class);
        commentDTO.setCommentCreator(newAddedComment.getAuthor().getUsername());
        return commentDTO;
    }

    public List<CommentDTO> getAllCommentsOfCurrentProduct(Long productId) {
        return commentRepository.findAllByProductId(productId).stream()
                .map(commentEntity -> {
                    CommentDTO commentDTO = modelMapper.map(commentEntity, CommentDTO.class);
                    commentDTO.setCommentCreator(commentEntity.getAuthor().getUsername());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }
}
