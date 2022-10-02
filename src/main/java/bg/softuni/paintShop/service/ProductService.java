package bg.softuni.paintShop.service;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.dto.ProductDTO;
import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final WarehouseService warehouseService;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().
                filter(productEntity -> !productEntity.getCategory().isDeleted()).
                filter(productEntity -> !productEntity.isDeleted())
                .map(productEntity -> {
                    ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
                    productDTO.setCategory(productEntity.getCategory().getName());
                    return productDTO;
                }).collect(Collectors.toList());
    }


    public ProductDTO findProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
        productDTO.setCategory(productEntity.getCategory().getName());
        return productDTO;

    }

    public List<ProductDTO> getFilteredProducts(String category) {
        return productRepository.findAll().stream().
                filter(productEntity -> productEntity.getCategory().getName().equals(category))
                .filter(productEntity -> !productEntity.isDeleted()).
                map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO getViewModel(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        return modelMapper.map(productEntity, ProductDTO.class);
    }

    public void deleteProduct(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        productEntity.setDeleted(true);
        warehouseService.deleteProduct(id);
        productRepository.save(productEntity);
    }

    public ProductEntity returnProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(ObjectNotFoundException::new);
    }

    public void addNewProduct(ProductDTO productDTO) {
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        productEntity.setDeleted(false);
        productEntity.setCategory(categoryService.findCategoryByName(productDTO.getCategory()));
        productRepository.save(productEntity);
        warehouseService.saveNewProduct(productEntity);
    }

    public void editProduct(ProductDTO productDTO, Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        productEntity.setTitle(productDTO.getTitle());
        productEntity.setCategory(categoryService.findCategoryByName(productDTO.getCategory()));
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setDescription(productDTO.getDescription());
        productRepository.save(productEntity);

    }


}
