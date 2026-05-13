package cl.edu.galaxy.training.apps.siac.api_gestion_academica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:postgresql://localhost:5432/db_academica",
		"spring.datasource.username=postgres",
		"spring.datasource.password=postgres",
		"spring.datasource.driver-class-name=org.postgresql.Driver",
		"keycloak.resource=api-gestion-academica",
		"spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:6082/realms/REALM_DEMO_V1/protocol/openid-connect/certs"
})
@ActiveProfiles("test")
class ApiGestionAcademicaApplicationTests {

	@Test
	void contextLoads() {
	}

}
