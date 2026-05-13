package cl.edu.galaxy.training.apps.siac.api_gestion_academica;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/db_academica",
        "spring.datasource.username=postgres",
        "spring.datasource.password=postgres",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "keycloak.resource=api-gestion-academica",
        "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:6082/realms/REALM_DEMO_V1/protocol/openid-connect/certs"
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityIntegrationTests {

    private static final String PUBLIC_ENDPOINT = "/api/v1/test/libre/demo";
    private static final String PRIVATE_ENDPOINT_1 = "/api/v1/test/private/endpoint1";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn200WhenPublicEndpointIsRequestedWithoutToken() throws Exception {
        mockMvc.perform(get(PUBLIC_ENDPOINT))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401WhenPrivateEndpointIsRequestedWithoutToken() throws Exception {
        mockMvc.perform(get(PRIVATE_ENDPOINT_1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test-user", authorities = { "profesor" })
    void shouldReturn403WhenPrivateEndpointIsRequestedWithWrongRole() throws Exception {
        mockMvc.perform(get(PRIVATE_ENDPOINT_1))
                .andExpect(status().isForbidden());
    }
}
