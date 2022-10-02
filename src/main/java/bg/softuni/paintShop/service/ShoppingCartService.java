package bg.softuni.paintShop.service;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.dto.QuantityHolderDTO;
import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.model.entity.UserEntity;
import bg.softuni.paintShop.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@RequiredArgsConstructor
@Service
@SessionScope
public class ShoppingCartService {
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final UserService userService;
    private final Map<ProductEntity, Integer> cartProducts = new HashMap<>();

    public void addToCart(Long productById, QuantityHolderDTO quantityHolderDTO) {
        productRepository.findById(productById).ifPresent(productEntity ->
              cartProducts.put(productEntity, quantityHolderDTO.getQuantity()));

    }

    public void finishOrder(String principalName) {
        UserEntity buyer = userService.findByName(principalName);
        orderService.createOrder(cartProducts, buyer);
        cartProducts.clear();
    }

    //Usage in template
    public BigDecimal pricePerProduct(Map.Entry<ProductEntity, Integer> singleProduct) {
        return singleProduct.getKey().getPrice().multiply(BigDecimal.valueOf(singleProduct.getValue()));
    }

    public BigDecimal findTotalSum() {
        return cartProducts.entrySet().stream()
              .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
              .reduce(BigDecimal::add)
              .orElse(BigDecimal.ZERO);

    }

    public Map<ProductEntity, Integer> getAllProducts() {
        return Collections.unmodifiableMap(cartProducts);
    }

    public void removeProduct(Long productById) {
        productRepository.findById(productById)
              .ifPresentOrElse(cartProducts::remove, () -> {
                  throw new ObjectNotFoundException();
              });
    }
}
