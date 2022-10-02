package bg.softuni.paintShop.web;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
class UsedProductControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    void confirmEditRedirect() throws Exception {
        mockMvc.perform(post("/used/products/details/{id}", 1).with(csrf()).
                        param("title", "TITLE").
                        param("description", "description desc").
                        param("price", "222.23").
                        param("phoneNumber", "1233231").
                        param("imageUrl", "image").
                        param("category", "TOOLS")).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/used/products/details/1"));
    }

    @Test
    void secondHandProductPage() throws Exception {
        mockMvc.perform(get("/used/products/forSale")).
                andExpect(model().attributeExists("usedProducts")).
                andExpect(model().attributeExists("allCategories")).
                andExpect(status().isOk()).andExpect(view().name("second-hand-products"));

    }



    @Test
    void usedProductDTO() {
    }

    @Test
    void editProductConfirm() throws Exception {
        mockMvc.perform(post("/used/products/add").with(csrf()).
                        param("title", "TITLE").
                        param("description", "description desc").
                        param("price", "222.23").
                        param("phoneNumber", "123546123231").
                        param("imageUrl", "mageUrasdasdasdasdle").
                        param("category", "TOOLS")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/used/products/forSale"));
    }

    @Test
    void editProductConfirmRedirect() throws Exception {
        mockMvc.perform(post("/used/products/add").with(csrf()).
                        param("title", "TITLE").
                        param("description", "description desc").
                        param("price", "222.23").
                        param("phoneNumber", "12331").
                        param("imageUrl", "mageUr").
                        param("category", "TOOLS")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/used/products/add"));
    }

    @Test
    void secondHandProductDeletePage() throws Exception {
        mockMvc.perform(get("/used/products/delete/{id}",1)).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/used/products/forSale"));
    }

    @Test
    void secondHandProductAddPage() throws Exception {
        mockMvc.perform(get("/used/products/add")).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("allCategories")).
                andExpect(view().name("add-used-product"));
    }
}