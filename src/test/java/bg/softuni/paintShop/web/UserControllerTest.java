package bg.softuni.paintShop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegistrationPage() throws Exception {
        mockMvc.perform(get("/users/register")).
                andExpect(status().isOk()).andExpect(view().name("register"));
    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/users/login")).
                andExpect(status().isOk()).andExpect(view().name("login"));
    }

    @Test
    void testLoginPageConfirmRedirect() throws Exception {
        mockMvc.perform(get("/users/login-error").with(csrf()).
                        param("username", "a").
                        param("password", "1")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("http://localhost/users/login"));
    }


    @Test
    void testUserRegistration() throws Exception {
        mockMvc.perform(post("/users/register").
                        param("email", "admain@gmail.com").
                        param("firstName", "Admiddn").
                        param("lastName", "Adminddsov").
                        param("username", "Adminscdho").
                        param("address", "ADMIN ADMIN STR 1").
                        param("password", "12345").
                        param("confirmPassword", "12345").
                        with(csrf())).andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/"));
    }

    @Test
    @WithAnonymousUser
    void testProfilePage_withAnonymousUser_Forbidden() throws Exception {
        mockMvc.perform(get("/users/profile")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com")
    void testUserEditProfile() throws Exception {
        mockMvc.perform(get("/users/profile")).
                andExpect(status().isOk()).
                andExpect(view().name("profile"));

    }

    @Test
    @WithMockUser(username = "admin@gmail.com",roles = "ADMIN")
    void profileChangeConfirm() throws Exception {
        mockMvc.perform(post("/users/profile").with(csrf()).
                        param("firstName", "Admin").
                        param("lastName", "Adminsov").
                        param("username", "Adminscho").
                        param("address", "ADMIN ADMIN STR 1")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/profile"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com",roles = "ADMIN")
    void profileChangeConfirmRedirect() throws Exception {
        mockMvc.perform(post("/users/profile").with(csrf()).
                        param("firstName", "Ad").
                        param("lastName", "Admv").
                        param("username", "Adminscho").
                        param("address", "ADMIN ADMIN STR 1")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/profile"));
    }

}
