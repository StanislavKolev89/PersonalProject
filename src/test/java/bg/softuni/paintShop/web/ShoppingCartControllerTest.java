package bg.softuni.paintShop.web;

import bg.softuni.paintShop.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.annotation.RequestScope;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Test
    void cartItemAdd() throws Exception {
        mockMvc.perform(post("/products/addToCart/{id}", 1).with(csrf())).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/products/all"));
    }

    @Test
    void productDetails() throws Exception {
        mockMvc.perform(get("/products/fromCart/{id}/{quantity}", 1, 2)).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("productViewModel")).
                andExpect(model().attributeExists("active")).
                andExpect(view().name("product-details"));
    }

    @RequestScope
    @Test
    void orderDetails() throws Exception {
        mockMvc.perform(get("/shoppingCart/details")).
                andExpect(status().isOk()).
                andExpect(view().name("cart-details"));
    }

    @Test
    void finishOrders() throws Exception {
        mockMvc.perform(get("/shoppingCart/finishOrder")).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"));
    }

    @Test
    void removeProductFromCart() throws Exception {
        mockMvc.perform(get("/shoppingCart/removeProduct/{id}",1)).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/shoppingCart/details"));
    }
}