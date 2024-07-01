package cl.edu.galaxy.training.apps.siac.api_gestion_academica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/
public class ApiGestionAcademicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGestionAcademicaApplication.class, args);
	}

}
