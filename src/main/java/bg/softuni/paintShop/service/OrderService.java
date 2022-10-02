package bg.softuni.paintShop.service;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.dto.OrderDTO;
import bg.softuni.paintShop.model.entity.OrderEntity;
import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.model.entity.UserEntity;
import bg.softuni.paintShop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final WarehouseService productQuantityService;
    private final OrderProductService orderProductService;
    private final WarehouseService warehouseService;

    public void createOrder(Map<ProductEntity, Integer> cartItems, UserEntity buyer) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        OrderEntity order = new OrderEntity();
        order.setUser(buyer);
        order.setCreatedAt(now.truncatedTo(ChronoUnit.SECONDS));
        orderRepository.save(order);
        cartItems.forEach((product, orderedItemsCount) -> {
            orderProductService.addOrderAndProduct(order, product, orderedItemsCount);
            productQuantityService.decreaseStock(product.getId(), orderedItemsCount);

        });
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().filter(orderEntity -> !orderEntity.isDeleted()).map(orderEntity -> {
            OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);
            orderDTO.setUser(orderEntity.getUser().getEmail());
            return orderDTO;
        }).collect(Collectors.toList());
    }

    public BigDecimal getTotalPriceOfOrder(Long orderId) {
        return orderProductService.findAllOrderProducts(orderId).stream().
                map(order -> order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

    }

    public void deleteOrder(Long id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        order.setDeleted(true);
        orderProductService.findAllOrderProducts(id).stream().forEach(orderProductEntity -> {
            ProductEntity product = orderProductEntity.getProduct();
            int quantity = orderProductEntity.getQuantity();
            warehouseService.restoreQuantityOfProduct(product, quantity);
        });
        orderProductService.removeAllProductOfOrderById(id);
        orderRepository.save(order);

    }

    public boolean buyerIsAdmin(String username) {
        return userService.findByName(username).getId() == 1;
    }
}
