package bg.softuni.paintShop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(get("/")).
                andExpect(status().isOk()).andExpect(view().name("index")).
                andExpect(model().attributeExists("firstCategoryItem"));

    }

    @Test
    void testContactsPage() throws Exception {
        mockMvc.perform(get("/contacts")).
                andExpect(status().isOk()).andExpect(view().name("contacts-page"));
    }

}