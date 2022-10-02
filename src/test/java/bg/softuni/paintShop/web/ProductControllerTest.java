package bg.softuni.paintShop.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void oneCategoryPage() throws Exception {
        mockMvc.perform(get("/products/{category}", "TOOLS")).
                andExpect(model().attributeExists("chosenCategoryProducts")).
                andExpect(model().attributeExists("categoryName")).
                andExpect(model().attributeExists("itemCount")).
                andExpect(status().isOk()).andExpect(view().name("one-category-products"));
    }

    @Test
    void productsPage() throws Exception {
        mockMvc.perform(get("/products/all")).
                andExpect(model().attributeExists("products")).
                andExpect(model().attributeExists("allCategories")).
                andExpect(status().isOk()).andExpect(view().name("products"));
    }


    @Test
    void quantityHolderDTO() {
    }
}