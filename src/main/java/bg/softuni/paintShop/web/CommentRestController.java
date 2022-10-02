package bg.softuni.paintShop.web;

import bg.softuni.paintShop.exception.CommentNotFoundException;
import bg.softuni.paintShop.model.dto.ApiErrorDTO;
import bg.softuni.paintShop.model.dto.CommentCreationDTO;
import bg.softuni.paintShop.model.dto.CommentDTO;
import bg.softuni.paintShop.model.dto.ContentConsumerDto;
import bg.softuni.paintShop.model.view.CommentViewModel;
import bg.softuni.paintShop.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentRestController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @GetMapping("/{productId}/comments")
    public List<CommentViewModel> getComments(@PathVariable("productId") Long productId) {
        List<CommentDTO> allCommentsOfCurrentProduct = commentService.getAllCommentsOfCurrentProduct(productId);
        if (allCommentsOfCurrentProduct.isEmpty()) {
            throw new CommentNotFoundException(productId);
        }
        return allCommentsOfCurrentProduct.stream()
                .map(commentDTO -> modelMapper.map(commentDTO, CommentViewModel.class))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/{productId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentViewModel> createNewComment(@PathVariable("productId") Long productId,
                                                             @AuthenticationPrincipal UserDetails principal,
                                                             @RequestBody ContentConsumerDto contentConsumerDto) {


        CommentCreationDTO commentCreationDTO = new CommentCreationDTO();
        commentCreationDTO.setUsername(principal.getUsername());
        commentCreationDTO.setContent(contentConsumerDto.getContent());
        commentCreationDTO.setProductId(productId);

        CommentViewModel comment = modelMapper.map(commentService.createComment(commentCreationDTO), CommentViewModel.class);

        return ResponseEntity.created(URI.create(String.format("/api/%d/comments/%d", productId, comment.getId())))
                .body(comment);

    }

    @ExceptionHandler({CommentNotFoundException.class})
    public ResponseEntity<ApiErrorDTO> handleProductNotFound(CommentNotFoundException cmne) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorDTO(cmne.getId(), "No comments found for this product!"));
    }
}
