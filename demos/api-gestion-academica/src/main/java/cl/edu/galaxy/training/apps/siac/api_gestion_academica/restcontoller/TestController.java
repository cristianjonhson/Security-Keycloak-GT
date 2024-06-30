package cl.edu.galaxy.training.apps.siac.api_gestion_academica.restcontoller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")  
public class TestController {
	
    @GetMapping("/libre/demo")
    public String allAccess() {
        return "Public content";
    }

    @GetMapping("/private/endpoint1")
    @PreAuthorize("hasAnyAuthority('invitado')")
    public String endpoint1() {
        return "User board";
    }
    
    @GetMapping("/private/endpoint3")
    @PreAuthorize("hasAnyAuthority('super')")
    public String endpoint3() {
        return "User board";
    }
    
    @GetMapping("/private/endpoint4")
    @PreAuthorize("hasAnyAuthority('user')")
    public String endpoint4() {
        return "User board";
    }
    
    @GetMapping("/private/endpoint5")
    @PreAuthorize("hasRole('DEMO')") //ROLE_DEMO -> Keycloak
    public String endpoint5() {
        return "User board";
    }

    @GetMapping("/private/endpoint2")
    @PreAuthorize("hasRole('administrator')")
    public String endpoint2() {
        return "Administrator board";
    }
}