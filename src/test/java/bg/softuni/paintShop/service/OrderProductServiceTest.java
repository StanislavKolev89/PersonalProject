package bg.softuni.paintShop.service;

import bg.softuni.paintShop.model.entity.OrderEntity;
import bg.softuni.paintShop.model.entity.OrderProductEntity;
import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.repository.OrderProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProductServiceTest {

    @Mock
    private OrderProductRepository orderProductRepository;

    @InjectMocks
    private OrderProductService mockedService;

    private ProductEntity product;

    private OrderEntity order;

    private OrderProductEntity orderProductEntityOne;

    private OrderProductEntity orderProductEntityTwo;

    @BeforeEach
    void setUp() {
        product = new ProductEntity();
        product.setTitle("DRILL");
        product.setPrice(BigDecimal.TEN);
        order = new OrderEntity();
        order.setCreatedAt(LocalDateTime.now());
        orderProductEntityOne = new OrderProductEntity();
        orderProductEntityTwo = new OrderProductEntity();


    }

    @Test
    void addOrderAndProduct() {
        orderProductEntityOne.setProduct(product);
        orderProductEntityOne.setOrder(order);
        orderProductEntityOne.setQuantity(2);
        mockedService.addOrderAndProduct(order, product, 2);

        verify(orderProductRepository, times(1)).save(any());
    }

    @Test
    void findAllUsersProducts() {
        orderProductEntityOne.setProduct(product);
        orderProductEntityOne.setOrder(order);
        orderProductEntityOne.setQuantity(2);
        when(orderProductRepository.findAllOrdersByUserId(1L)).
                thenReturn(Optional.of(List.of(orderProductEntityOne, orderProductEntityTwo)));
        List<OrderProductEntity> allUsersProducts = mockedService.findAllUsersProducts(1L);
        Assertions.assertThat(allUsersProducts).size().isEqualTo(2);
    }

    @Test
    void findAll() {
        when(orderProductRepository.findAll()).thenReturn(List.of(orderProductEntityOne, orderProductEntityTwo));
        List<OrderProductEntity> allUsersProducts = mockedService.findAll();
        Assertions.assertThat(allUsersProducts).isNotEmpty();
        Assertions.assertThat(allUsersProducts).size().isEqualTo(2);

    }

    @Test
    void findAllOrderProducts() {
        when(orderProductRepository.findOrderProductEntitiesByOrder_Id(1L)).
                thenReturn(Optional.of(List.of(orderProductEntityOne, orderProductEntityTwo)));
        List<OrderProductEntity> allOrderProducts = mockedService.findAllOrderProducts(1L);
        Assertions.assertThat(allOrderProducts).isNotEmpty();
    }

    @Test
    void pricePerProduct() {
        orderProductEntityOne.setOrder(order);
        orderProductEntityOne.setProduct(product);
        orderProductEntityOne.setQuantity(1);
        BigDecimal bigDecimal = mockedService.pricePerProduct(orderProductEntityOne);
        Assertions.assertThat(bigDecimal).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void findTurnover() {
        BigDecimal bigDecimal = mockedService.findTurnover();
        Assertions.assertThat(bigDecimal).isEqualTo(BigDecimal.ZERO);
    }

}