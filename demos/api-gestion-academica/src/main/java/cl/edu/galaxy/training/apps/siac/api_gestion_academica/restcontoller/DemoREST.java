package cl.edu.galaxy.training.apps.siac.api_gestion_academica.restcontoller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import cl.edu.galaxy.training.apps.siac.api_gestion_academica.entity.CompanyEntity;


@Slf4j
@RestController
@RequestMapping("/api/v1/demos") // plural
public class DemoREST {

	// Default  - Preterminado
	@GetMapping
	public String demo() {
		return "Spring Boot Demo v1";

	}

	@GetMapping("/v2")
	@ResponseBody
	public Map<String, String> demov2() {

		Map<String, String> data = new HashMap<>();

		data.put("mensaje", "Spring Boot Demo v2");
		data.put("fecha", LocalDate.now().toString());

		return data;

	}

	@GetMapping("/v3")
	public ResponseEntity<Map<String, String>> demov3() {

		Map<String, String> data = new HashMap<>();

		data.put("mensaje", "Spring Boot Demo v2");
		data.put("fecha", LocalDate.now().toString());

		return ResponseEntity.ok(data);

	}

	@GetMapping("/v4")
	public ResponseEntity<List<CompanyEntity>> demov4() {
		List<CompanyEntity> companies = new ArrayList<>();

		CompanyEntity company = new CompanyEntity();
		company.setId(1L);
		company.setCompany("Alfreds Futterkiste");
		company.setContact("Maria Anders");
		company.setCountry("Germany");

		companies.add(company);

		CompanyEntity company2 = new CompanyEntity(1L, "Centro comercial Moctezuma", "Francisco Chang", "Mexico");

		companies.add(company2);

		return ResponseEntity.ok(companies);
	}
	
	@GetMapping("/v5/{id}")
	public ResponseEntity<Map<String, Object>> demov5(@PathVariable("id") Long id) {

		Map<String, Object> data = new HashMap<>();

		data.put("mensaje", "Spring Boot Demo v2");
		data.put("fecha", LocalDate.now().toString());
		
		data.put("id", id);

		return ResponseEntity.ok(data);

	}
	
	@GetMapping("/v6")
	public ResponseEntity<Map<String, Object>> demov6(@RequestParam("company") String company) {

		Map<String, Object> data = new HashMap<>();

		data.put("company", company);

		return ResponseEntity.ok(data);
	}
	
	@GetMapping("/v6.1")
	public ResponseEntity<Map<String, Object>> demov6_1(@RequestParam(value = "company",defaultValue = "") String company) {

		Map<String, Object> data = new HashMap<>();

		data.put("company", company);

		return ResponseEntity.ok(data);
	}
	
	@GetMapping("/v7")
	public ResponseEntity<Map<String, Object>> demov7(@RequestParam("company") String company, @RequestParam("contact") String contact) {

		Map<String, Object> data = new HashMap<>();

		data.put("company", company);
		
		data.put("contact", contact);

		return ResponseEntity.ok(data);

	}
	
	@GetMapping("/v8")
	public ResponseEntity<List<String>> demov8(@RequestParam("contries") List<String> contries) {

		return ResponseEntity.ok(contries);

	}
	
	@GetMapping("/v9")
	public ResponseEntity<String> demov9(@RequestHeader("token") String token) {

		return ResponseEntity.ok(token);

	}
	
	@PostMapping
	public ResponseEntity<CompanyEntity> save(@RequestBody CompanyEntity company)  {
		
		log.info("Company, {}",company);
		company.setCompany(company.getCompany().toUpperCase());
		company.setId(1L);
		return ResponseEntity.ok(company);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyEntity> update(@PathVariable("id") Long id, @RequestBody CompanyEntity company)  {
		log.info("id, {}",id);
		log.info("Company, {}",company);
		company.setCompany(company.getCompany().toUpperCase());
		company.setId(id);
		return ResponseEntity.ok(company);
	}
	
	@PutMapping("/{id}/{idPais}")
	public ResponseEntity<CompanyEntity> update(@PathVariable("id") Long id, @PathVariable("idPais") Long idPais, @RequestBody CompanyEntity company)  {
		log.info("id, {}",id);
		log.info("idPais,{}",idPais);
		log.info("Company, {}",company);
		company.setCompany(company.getCompany().toUpperCase());
		company.setId(id);
		return ResponseEntity.ok(company);
	}
	
	// Mala práctica
	@PutMapping("/{id}/{company}/{contact}/{country}")
	public ResponseEntity<CompanyEntity> update(@PathVariable("id") Long id, @PathVariable("company") String company,@PathVariable("contact") String contact, @PathVariable("country") String country)  {
		log.info("id, {}",id);
		log.info("company,{}",company);
		log.info("contact, {}",contact);
		log.info("country, {}",country);
		
		CompanyEntity oCompany = new CompanyEntity();
		oCompany.setId(id);
		oCompany.setCompany(company);
		oCompany.setContact(contact);
		oCompany.setCountry(country);
		
		return ResponseEntity.ok(oCompany);
		
		// Nota: 
		//1.- Si el recurso(entidad) existe -> actualizarlo
		//2.- No existe registrarlo
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<CompanyEntity> updatePartial(@PathVariable("id") Long id, @RequestBody CompanyEntity company)  {
		log.info("id, {}",id);
		log.info("Company, {}",company);
		company.setCompany(company.getCompany().toUpperCase());
		company.setId(id);
		return ResponseEntity.ok(company);
		
		// Nota: 
		//1.- Si el recurso(entidad) existe -> actualizarlo
		//2.- No existe generar error
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id)  {
		log.info("id, {}",id);
	}

	@DeleteMapping("/v2/{id}")
	public ResponseEntity<Void> deleteV2(@PathVariable("id") Long id)  {
		return ResponseEntity.ok(null);
	}
	


}
