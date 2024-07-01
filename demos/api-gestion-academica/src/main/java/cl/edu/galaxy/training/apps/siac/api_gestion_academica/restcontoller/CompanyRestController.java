package cl.edu.galaxy.training.apps.siac.api_gestion_academica.restcontoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import cl.edu.galaxy.training.apps.siac.api_gestion_academica.entity.CompanyEntity;
import cl.edu.galaxy.training.apps.siac.api_gestion_academica.service.CompanyService;

@Slf4j
@RestController
@RequestMapping("/api/v1/companies") // plural
public class CompanyRestController {

	@Autowired
	private CompanyService companyService; // DI - Property - 1ro

	@GetMapping
	public ResponseEntity<?> findAll() {
		try {
			List<CompanyEntity> companies = companyService.findAll();
			return ResponseEntity.ok(companies);

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		try {
			Optional<CompanyEntity> company = companyService.findById(id);
			return ResponseEntity.ok(company);

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@GetMapping("/by-company")
	public ResponseEntity<?> findByLikeCompany(@RequestParam("company") String company) {

		try {
			List<CompanyEntity> companies = companyService.findByLikeCompany(company);
			return ResponseEntity.ok(companies);

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}
	
	@GetMapping("/private/by-company")
	public ResponseEntity<?> findByLikeCompanyPrivate(@RequestParam("company") String company) {

		try {
			List<CompanyEntity> companies = companyService.findByLikeCompanyPrivate(company);
			return ResponseEntity.ok(companies);

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@GetMapping("/by-contact")
	public ResponseEntity<?> findByLikeContact(@RequestParam("contact") String contact) {

		try {
			List<CompanyEntity> companies = companyService.findByLikeContact(contact);
			return ResponseEntity.ok(companies);

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@GetMapping("/by-country")
	public ResponseEntity<?> findByLikeCountry(@RequestParam("country") String country) {

		try {
			List<CompanyEntity> companies = companyService.findByLikeCountry(country);
			return ResponseEntity.ok(companies);

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody CompanyEntity company) {

		try {
			CompanyEntity tpmCompanyEntity = companyService.save(company);
			return new ResponseEntity<>(tpmCompanyEntity, HttpStatus.CREATED);

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CompanyEntity company) {
		try {
			company.setId(id);
			CompanyEntity tpmCompanyEntity = companyService.update(company);
			return new ResponseEntity<>(tpmCompanyEntity, HttpStatus.OK);

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@PreAuthorize("hasAnyAuthority('admin') or hasAnyAuthority('director')")
	@PatchMapping("/update-situacion/{id}")
	public ResponseEntity<?> updatePartial(@PathVariable("id") Long id, @RequestBody CompanyEntity company) {

		Map<String, Object> message = new HashMap<>();

		try {
			company.setId(id);
			Boolean sw = companyService.updateState(company);

			if (sw) {

				message.put("message", "La situacion ha sido actualizada exitosamente");
				return new ResponseEntity<>(message, HttpStatus.OK);
			} else {
				message.put("message", "Error al actualziar la situacion");
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@PreAuthorize("hasAnyAuthority('admin')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Map<String, Object> message = new HashMap<>();
		try {
			Boolean sw = companyService.delete(id);

			if (sw) {

				message.put("message", "La compañia ha sido eliminada exitosamente");
				return new ResponseEntity<>(message, HttpStatus.OK);
			} else {
				message.put("message", "Error al eliminar la compañia");
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

}
