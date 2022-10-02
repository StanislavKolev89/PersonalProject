package bg.softuni.paintShop.service;

import bg.softuni.paintShop.model.dto.OrderDTO;
import bg.softuni.paintShop.model.entity.CategoryEntity;
import bg.softuni.paintShop.model.entity.OrderEntity;
import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.model.entity.UserEntity;
import bg.softuni.paintShop.repository.OrderProductRepository;
import bg.softuni.paintShop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService mockService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private OrderProductService mockOrderProductService;

    @Mock
    private WarehouseService warehouseService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private WarehouseService productQuantityService;

    private OrderDTO orderDTOOne = new OrderDTO();

    private OrderDTO orderDTOTwo = new OrderDTO();

    private UserEntity userEntity = new UserEntity();

    private OrderEntity orderOne = new OrderEntity();

    private OrderEntity orderTwo = new OrderEntity();

    private Map<ProductEntity, Integer> cartItems = new HashMap<>();

    private ProductEntity productOne = new ProductEntity();

    private ProductEntity productTwo = new ProductEntity();

    @BeforeEach
    void SetUp() {
        orderOne.setId(1L);
        orderTwo.setId(2L);
        userEntity.setUsername("STANCHO");
        userEntity.setEmail("email@gmail.com");
        userEntity.setId(1L);
        productOne.setTitle("DRILL");
        productOne.setCategory(new CategoryEntity());
        productOne.setPrice(BigDecimal.TEN);
        productTwo.setTitle("CLEARCOAT");
        productTwo.setCategory(new CategoryEntity());
        cartItems.put(productOne, 10);
        cartItems.put(productTwo, 10);
    }

    @Test
    void createOrder() {
        mockService.createOrder(cartItems, userEntity);
        verify(orderRepository, times(1)).save(any());
    }


    @Test
    void getTotalPriceOfOrder() {

        BigDecimal totalPriceOfOrder = mockService.getTotalPriceOfOrder(1L);
        Assertions.assertThat(totalPriceOfOrder).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void deleteOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderOne));
        mockService.deleteOrder(1L);
    }

}