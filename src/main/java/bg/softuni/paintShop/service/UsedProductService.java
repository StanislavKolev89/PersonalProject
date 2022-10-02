package bg.softuni.paintShop.service;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.dto.UsedProductDTO;
import bg.softuni.paintShop.model.entity.UsedProductEntity;
import bg.softuni.paintShop.repository.UsedProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsedProductService {

    private final UsedProductRepository usedProductRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public List<UsedProductDTO> getAllProducts() {
        return usedProductRepository.findAll().stream().map(usedProductEntity -> {
            UsedProductDTO usedProductDTO = modelMapper.map(usedProductEntity, UsedProductDTO.class);
            usedProductDTO.setUser(usedProductEntity.getUser().getUsername());
            usedProductDTO.setCategory(usedProductEntity.getCategory().getName());
            return usedProductDTO;
        }).collect(Collectors.toList());
    }


    public String getSellerName(Long id) {
        return usedProductRepository.findById(id).orElseThrow(ObjectNotFoundException::new).getUser().getUsername();

    }

    public UsedProductDTO getProductById(Long id) {
        UsedProductEntity usedProductEntity = usedProductRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        UsedProductDTO usedProductDTO = modelMapper.map(usedProductEntity, UsedProductDTO.class);
        usedProductDTO.setUser(usedProductEntity.getUser().getUsername());
        usedProductDTO.setCategory(usedProductEntity.getCategory().getName());
        return usedProductDTO;

    }

    public void addNewProduct(@Valid UsedProductDTO productDTO, Principal principal) {
        UsedProductEntity product = modelMapper.map(productDTO, UsedProductEntity.class);
        product.setUser(userService.findByName(principal.getName()));
        product.setCategory(categoryService.findCategoryByName(productDTO.getCategory()));
        usedProductRepository.save(product);
    }

    public void editProducts(UsedProductDTO usedProductDTO, Long id) {
        UsedProductEntity usedProductEntity = usedProductRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        usedProductEntity.setCategory(categoryService.findCategoryByName(usedProductDTO.getCategory()));
        usedProductEntity.setPrice(usedProductDTO.getPrice());
        usedProductEntity.setTitle(usedProductDTO.getTitle());
        usedProductEntity.setDescription(usedProductDTO.getDescription());
        if (usedProductDTO.getImageUrl() != null) {
            usedProductEntity.setImageUrl(usedProductDTO.getImageUrl());
        }
        usedProductEntity.setPhoneNumber(usedProductDTO.getPhoneNumber());
    }

    public void deleteProduct(Long id) {
        usedProductRepository.deleteById(id);
    }
}
