package bg.softuni.paintShop.service;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.dto.ProductDTO;
import bg.softuni.paintShop.model.entity.CategoryEntity;
import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService mockService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private WarehouseService warehouseService;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    private ProductEntity productOne;

    private ProductEntity productTwo;

    private CategoryEntity category;

    @BeforeEach
    void setUp() {
        category = new CategoryEntity();
        category.setName("TOOLS");
        productOne = new ProductEntity();
        productOne.setCategory(category);
        productTwo = new ProductEntity();
        productTwo.setCategory(category);

    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(productOne, productTwo));
        List<ProductDTO> allProducts = mockService.getAllProducts();
        Assertions.assertThat(allProducts.size()).isEqualTo(2);
    }

    @Test
    void findProductById() {

        when(productRepository.findById(1L)).thenReturn(Optional.of(productOne));
        ProductDTO productById = mockService.findProductById(1L);
        Assertions.assertThat(productById).isNotNull();
    }

    @Test
    void getFilteredProducts() {
        when(productRepository.findAll()).thenReturn(List.of(productOne, productTwo));
        List<ProductDTO> filteredProducts = mockService.getFilteredProducts(category.getName());
        Assertions.assertThat(filteredProducts.size()).isEqualTo(2);
    }

    @Test
    void getViewModel() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productOne));
        ProductDTO viewModel = mockService.getViewModel(1L);
        Assertions.assertThat(viewModel).isNotNull();
    }

    @Test
    void getViewModelThrowsException() {
        org.junit.jupiter.api.Assertions.assertThrows(ObjectNotFoundException.class, () -> mockService.getViewModel(1L));
    }

    @Test
    void deleteProductThrowsException() {
        org.junit.jupiter.api.Assertions.assertThrows(ObjectNotFoundException.class, () ->
                mockService.deleteProduct(1L));
    }

    @Test
    void deleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productOne));
        mockService.deleteProduct(1L);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void returnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productOne));
        ProductEntity productEntity = mockService.returnProduct(1L);
        Assertions.assertThat(productEntity).isNotNull();
    }

    @Test
    void addNewProduct() {
        ProductDTO productDTO = new ProductDTO();
        mockService.addNewProduct(productDTO);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void editProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("TITLE");
        productDTO.setDescription("DESCRIPTION");
        productDTO.setCategory("CATEGORY");
        productDTO.setPrice(BigDecimal.TEN);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productOne));
        mockService.editProduct(productDTO, 1L);
        verify(productRepository, times(1)).save(any());

    }
}