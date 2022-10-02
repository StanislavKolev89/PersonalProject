package bg.softuni.paintShop.web;

import bg.softuni.paintShop.model.dto.QuantityHolderDTO;
import bg.softuni.paintShop.service.ProductService;
import bg.softuni.paintShop.service.ShoppingCartService;
import bg.softuni.paintShop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final UserService userService;

    @PostMapping("/products/addToCart/{id}")
    public String cartItemAdd(@PathVariable Long id, QuantityHolderDTO quantityHolderDTO) {
        shoppingCartService.addToCart(id, quantityHolderDTO);
        return "redirect:/products/all";
    }

    @GetMapping("/products/fromCart/{id}/{quantity}")
    public String productDetails(Principal principal, @PathVariable Long id, @PathVariable int quantity, Model model, QuantityHolderDTO quantityHolderDTO) {
        model.addAttribute("productViewModel", productService.findProductById(id));
        model.addAttribute("active", userService.findByName(principal.getName()).isActive());
        quantityHolderDTO.setQuantity(quantity);
        return "product-details";
    }

    @GetMapping("/shoppingCart/details")
    public String orderDetails(Model model) {
        model.addAttribute("cartItems", shoppingCartService.getAllProducts());
        model.addAttribute("totalPrice", shoppingCartService.findTotalSum());
        model.addAttribute("service", shoppingCartService);
        return "cart-details";
    }

    @GetMapping("/shoppingCart/finishOrder")
    public String finishOrders(Principal principal) {
        shoppingCartService.finishOrder(principal.getName());
        return "redirect:/";
    }


    @GetMapping("/shoppingCart/removeProduct/{id}")
    public String removeProductFromCart(@PathVariable("id") Long productId) {
        shoppingCartService.removeProduct(productId);
        return "redirect:/shoppingCart/details";
    }


}
