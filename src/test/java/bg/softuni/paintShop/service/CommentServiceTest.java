package bg.softuni.paintShop.service;

import bg.softuni.paintShop.model.dto.CommentCreationDTO;
import bg.softuni.paintShop.model.dto.CommentDTO;
import bg.softuni.paintShop.model.entity.CommentEntity;
import bg.softuni.paintShop.model.entity.UserEntity;
import bg.softuni.paintShop.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService mockedService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Spy
    private ModelMapper modelMapper;

    private CommentEntity commentEntityOne = new CommentEntity();

    private CommentEntity commentEntityTwo = new CommentEntity();

    private UserEntity userEntity = new UserEntity();

    private CommentCreationDTO commentCreationDTO = new CommentCreationDTO();

    private CommentDTO commentDTOOne = new CommentDTO();

    private CommentDTO commentDTOTwo = new CommentDTO();

    @BeforeEach
    void setUp() {
        userEntity.setUsername("USER");
        commentCreationDTO.setUsername(userEntity.getUsername());
        commentEntityOne.setAuthor(userEntity);
        commentDTOOne.setId(1L);
        commentDTOOne.setContent("CONTENT");
        commentDTOTwo.setId(2L);
        commentDTOTwo.setContent("CONTENT");
        commentEntityOne.setAuthor(userEntity);
        commentEntityTwo.setAuthor(userEntity);

    }

    @Test
    void createComment() {
        Mockito.when(commentRepository.save(commentEntityOne)).thenReturn(commentEntityOne);
        CommentDTO comment = mockedService.createComment(commentCreationDTO);
        Assertions.assertThat(comment).isNotNull();
    }

    @Test
    void getAllCommentsOfCurrentProduct() {
        Mockito.when(commentRepository.findAllByProductId(1L)).thenReturn(List.of(commentEntityOne, commentEntityTwo));
        mockedService.getAllCommentsOfCurrentProduct(1L);
    }
}