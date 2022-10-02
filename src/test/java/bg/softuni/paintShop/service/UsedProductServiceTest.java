package bg.softuni.paintShop.service;

import bg.softuni.paintShop.model.dto.UsedProductDTO;
import bg.softuni.paintShop.model.entity.CategoryEntity;
import bg.softuni.paintShop.model.entity.UsedProductEntity;
import bg.softuni.paintShop.model.entity.UserEntity;
import bg.softuni.paintShop.repository.UsedProductRepository;
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
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsedProductServiceTest {

    @InjectMocks
    private UsedProductService mockedService;

    @Mock
    private UsedProductRepository usedProductRepository;

    @Mock
    private Principal principal;

    @Mock
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private CategoryService categoryService;

    private UsedProductEntity productOne = new UsedProductEntity();

    private UsedProductEntity productTwo = new UsedProductEntity();

    private CategoryEntity category = new CategoryEntity();

    private UserEntity user = new UserEntity();

    @BeforeEach
    void setUp() {

        user.setUsername("Username");

        category.setName("Category");

        productOne.setCategory(category);
        productOne.setUser(user);
        productOne.setDescription("Description one");
        productOne.setTitle("TITLE");
        productOne.setId(1L);
        productOne.setImageUrl("Image Url");

        productTwo.setCategory(category);
        productTwo.setUser(user);
        productTwo.setDescription("Description two");
        productTwo.setTitle("TITLE");
        productTwo.setId(2L);
        productTwo.setImageUrl("Image Url");
    }

    @Test
    void getAllProducts() {

        when(usedProductRepository.findAll()).thenReturn(List.of(productOne, productTwo));
        List<UsedProductDTO> allProducts = mockedService.getAllProducts();
        Assertions.assertThat(allProducts.size()).isEqualTo(2);

    }

    @Test
    void getSellerName() {
        when(usedProductRepository.findById(1L)).thenReturn(Optional.of(productOne));
        String sellerName = mockedService.getSellerName(1L);
        Assertions.assertThat(sellerName).isEqualTo("Username");
    }

    @Test
    void getProductById() {
        when(usedProductRepository.findById(1L)).thenReturn(Optional.of(productOne));
        UsedProductDTO productById = mockedService.getProductById(1L);
        Assertions.assertThat(productById).isNotNull();
    }

    @Test
    void addNewProduct() {
        UsedProductDTO usedProductDTO = new UsedProductDTO();
        usedProductDTO.setId(1L);
        usedProductDTO.setCategory(category.getName());
        usedProductDTO.setUser(user.getUsername());
        mockedService.addNewProduct(usedProductDTO, principal);

    }

    @Test
    void editProducts() {
        UsedProductDTO usedProductDTO = new UsedProductDTO();
        usedProductDTO.setId(1L);
        usedProductDTO.setPrice(BigDecimal.TEN);
        usedProductDTO.setCategory(category.getName());
        usedProductDTO.setUser(user.getUsername());
        usedProductDTO.setImageUrl("IMAGE URL");
        when(usedProductRepository.findById(1L)).thenReturn(Optional.of(productOne));
        mockedService.editProducts(usedProductDTO, 1L);
    }

    @Test
    void deleteProduct(){
        mockedService.deleteProduct(1L);
        verify(usedProductRepository, times(1)).deleteById(1L);
    }
}