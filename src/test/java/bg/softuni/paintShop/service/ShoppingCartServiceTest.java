package bg.softuni.paintShop.service;

import static org.mockito.Mockito.when;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.dto.QuantityHolderDTO;
import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartService mockService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    private Map<ProductEntity, Integer> cartProducts = new HashMap<>();

    private QuantityHolderDTO quantityHolder = new QuantityHolderDTO();

    private ProductEntity product = new ProductEntity();

    @BeforeEach
    void setUp() {
        quantityHolder.setQuantity(10);
        product.setPrice(BigDecimal.TEN);
    }

    @Test
    void addToCart() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        mockService.addToCart(1L, quantityHolder);

    }

    @Test
    void finishOrder() {
        mockService.finishOrder("GOSHO");
    }

    @Test
    void pricePerProduct() {
        cartProducts.put(product, quantityHolder.getQuantity());
        Map.Entry<ProductEntity, Integer> entry = cartProducts.entrySet().iterator().next();
        BigDecimal bigDecimal = mockService.pricePerProduct(entry);
        Assertions.assertThat(bigDecimal).isEqualTo(BigDecimal.valueOf(100));
    }

    @Test
    void findTotalSum() {
        cartProducts.put(product, quantityHolder.getQuantity());
        BigDecimal totalSum = mockService.findTotalSum();
        Assertions.assertThat(totalSum).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    void getAllProducts() {
        Map<ProductEntity, Integer> allProducts = mockService.getAllProducts();
    }

    @Test
    void removeProduct() {

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        cartProducts.put(product, quantityHolder.getQuantity());
        mockService.removeProduct(1L);
    }

    @Test
    void removeProductThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(ObjectNotFoundException.class, () ->
                mockService.removeProduct(1L));
    }
}