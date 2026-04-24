package cl.edu.galaxy.training.apps.siac.api_gestion_academica;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpointShouldReturn200WithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/test/libre/demo"))
                .andExpect(status().isOk());
    }

    @Test
    void privateEndpointShouldReturn401WithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/test/private/endpoint1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test-user", authorities = { "profesor" })
    void privateEndpointShouldReturn403WithWrongRole() throws Exception {
        mockMvc.perform(get("/api/v1/test/private/endpoint1"))
                .andExpect(status().isForbidden());
    }
}
