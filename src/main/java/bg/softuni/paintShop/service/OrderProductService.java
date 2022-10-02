package bg.softuni.paintShop.service;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.entity.OrderEntity;
import bg.softuni.paintShop.model.entity.OrderProductEntity;
import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    public void addOrderAndProduct(OrderEntity order, ProductEntity product, Integer quantity) {

        OrderProductEntity orderProductEntity = new OrderProductEntity();
        orderProductEntity.setOrder(order);
        orderProductEntity.setProduct(product);
        orderProductEntity.setQuantity(quantity);
        orderProductRepository.save(orderProductEntity);
    }


    public List<OrderProductEntity> findAllUsersProducts(Long id) {
        return orderProductRepository.findAllOrdersByUserId(id).orElseThrow(ObjectNotFoundException::new);
    }

    public List<OrderProductEntity> findAll() {
        return orderProductRepository.findAll();
    }

    public List<OrderProductEntity> findAllOrderProducts(Long orderId) {
        return orderProductRepository.findOrderProductEntitiesByOrder_Id(orderId).orElseThrow(ObjectNotFoundException::new);
    }

    //Usage in template
    public BigDecimal pricePerProduct(OrderProductEntity orderProductEntity) {
        return orderProductEntity.getProduct().getPrice().multiply(BigDecimal.valueOf(orderProductEntity.getQuantity()));
    }

    public BigDecimal findTurnover() {
        return orderProductRepository.findAll().stream().filter(orderProductEntity -> orderProductEntity.getOrder().getUser().getId() != 1).
                filter(orderProductEntity -> !orderProductEntity.getOrder().isDeleted()).
                map(orderProductEntity -> orderProductEntity.getProduct().getPrice().multiply(BigDecimal.valueOf(orderProductEntity.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void removeAllProductOfOrderById(Long id) {
        orderProductRepository.deleteAllOrdersProductEntitiesByOrderId(id);
    }
}
