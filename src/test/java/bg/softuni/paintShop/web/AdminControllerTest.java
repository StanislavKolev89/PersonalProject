package bg.softuni.paintShop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void adminPage() throws Exception {
        mockMvc.perform(get("/admin")).
                andExpect(status().isOk()).andExpect(view().name("admin-panel"));
    }

    @Test
    void CategoryEditPage() throws Exception {
        mockMvc.perform(get("/admin/categories/edit/{id}", 1)).
                andExpect(model().attributeExists("categoryData")).
                andExpect(status().isOk()).andExpect(view().name("change-category"));

    }

    @Test
    void CategoryEditPageConfirm() throws Exception {
        mockMvc.perform(post("/admin/categories/edit/{id}", 1).
                        param("name", "TOOLS").
                        param("imageUrl", "ASDASDASDASDASD").
                        with(csrf())).andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin"));

    }

    @Test
    void CategoryEditPageConfirmRedirect() throws Exception {
        mockMvc.perform(post("/admin/categories/edit/{id}", 1).
                        param("name", "T").
                        param("imageUrl", "ASDASDASDASDASD").
                        with(csrf())).andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/categories/edit/1"));
    }

    @Test
    void deleteCategory() throws Exception {
        mockMvc.perform(get("/admin/categories/delete/{id}", 1)).
                andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/admin/categories/all"));
    }

    @Test
    void categoryAddPage() throws Exception {
        mockMvc.perform(get("/admin/categories/add")).
                andExpect(status().isOk()).andExpect(view().name("add-category"));
    }

    @Test
    void allCategoriesPage() throws Exception {
        mockMvc.perform(get("/admin/categories/all")).
                andExpect(status().isOk()).andExpect(view().name("category-admin"));
    }

    @Test
    void categoryAddConfirm() throws Exception {
        mockMvc.perform(post("/admin/categories/add").
                        param("id", "6").
                        param("name", "TOOLS").
                        param("imageUrl", "ASDASDASDASDASD").
                        param("deleted", "false").
                        with(csrf())).andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/categories/all"));
    }

    @Test
    void categoryAddConfirmHasErrors() throws Exception {
        mockMvc.perform(post("/admin/categories/add").
                        param("id", "1").
                        param("name", "TO").
                        param("imageUrl", "ASDASDASDASDASD").
                        param("deleted", "false").
                        with(csrf())).andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/categories/add"));
    }


    @Test
    void makeUserNotActive() throws Exception {
        mockMvc.perform(get("/admin/users/deactivate/{id}", 1L)).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/users/all"));
    }

    @Test
    void makeUserActive() throws Exception {
        mockMvc.perform(get("/admin/users/activate/{id}", 1L)).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/users/all"));


    }

    @Test
    void giveUserAdminRights() throws Exception {
        mockMvc.perform(get("/admin/users/makeAdmin/{id}", 1L)).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/users/all"));
    }

    @Test
    void removeUserAdminRights() throws Exception {
        mockMvc.perform(get("/admin/users/removeAdmin/{id}", 1L)).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/users/all"));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(get("/admin/users/delete/{id}", 1L)).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/users/all"));

    }

    @Test
    void deleteOrder() throws Exception {

        mockMvc.perform(get("/admin/orders/delete/{id}", 1L)).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/orders/all"));
    }

    @Test
    void orderDetails() throws Exception {
        mockMvc.perform(get("/admin/orders/details/{id}", 1)).
                andExpect(model().attributeExists("allProductsInOrder")).
                andExpect(model().attributeExists("service")).
                andExpect(status().isOk()).andExpect(view().name("order-products-details"));
    }

    @Test
    void allProductsPage() throws Exception {
        mockMvc.perform(get("/admin/products/all", 1)).
                andExpect(model().attributeExists("products")).
                andExpect(model().attributeExists("count")).
                andExpect(status().isOk()).andExpect(view().name("products-admin"));
    }

    @Test
    void editProductPage() throws Exception {
        mockMvc.perform(get("/admin/products/edit/{id}", 1)).
                andExpect(model().attributeExists("productData")).
                andExpect(model().attributeExists("categories")).
                andExpect(status().isOk()).andExpect(view().name("edit-product"));


    }

    @Test
    void editProductConfirm() throws Exception {
        mockMvc.perform(post("/admin/products/edit/{id}", 1).with(csrf()).
                        param("title", "TITLE").
                        param("description", "DESCRIPTIONDESCRIPTION").
                        param("price", "20").
                        param("imageUrl", "idasdasdasdasd").
                        param("category", "TOOLS")).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/products/all"));

    }

    @Test
    void editProductConfirmRedirectError() throws Exception {
        mockMvc.perform(post("/admin/products/edit/{id}", 1).with(csrf()).
                        param("title", "TITLE").
                        param("description", "DESCRITION").
                        param("price", "20").
                        param("imageUrl", "idasdasdasdasd").
                        param("category", "CATEGORY")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/products/edit/1"));

    }


    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(get("/admin/products/delete/{id}", 1L)).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/products/all"));
    }

    @Test
    void addProductPage() throws Exception {
        mockMvc.perform(get("/admin/products/add")).
                andExpect(model().attributeExists("categories")).
                andExpect(status().isOk()).andExpect(view().name("add-new-product"));
    }

    @Test
    void addProductConfirm() throws Exception {
        mockMvc.perform(post("/admin/products/add").with(csrf()).
                        param("title", "TITLE").
                        param("description", "DESCRI").
                        param("price", "20").
                        param("imageUrl", "idasdasdasdasd").
                        param("category", "TOOLS")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/products/add"));
    }


    @Test
    void addProductConfirmRedirectError() throws Exception {
        mockMvc.perform(post("/admin/products/add").with(csrf()).
                        param("title", "TITLE").
                        param("description", "DESCRITIONasdasdasd").
                        param("price", "20").
                        param("imageUrl", "idasdasdasdasd").
                        param("category", "TOOLS")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/products/all"));
    }

    @Test
    void categoryDTO() {
    }

    @Test
    void productDTO() {
    }

    @Test
    void allUsersPage() throws Exception {
        mockMvc.perform(get("/admin/users/all")).
                andExpect(model().attributeExists("users")).
                andExpect(model().attributeExists("userService")).
                andExpect(model().attributeExists("count")).
                andExpect(model().attributeExists("loggedUserId")).
                andExpect(status().isOk()).andExpect(view().name("users-admin"));
    }


    @Test
    void allOrdersPage() throws Exception {
        mockMvc.perform(get("/admin/orders/all")).
                andExpect(model().attributeExists("allOrders")).
                andExpect(model().attributeExists("count")).
                andExpect(model().attributeExists("orderService")).
                andExpect(model().attributeExists("totalTurnover")).
                andExpect(status().isOk()).andExpect(view().name("orders-admin"));
    }
}

