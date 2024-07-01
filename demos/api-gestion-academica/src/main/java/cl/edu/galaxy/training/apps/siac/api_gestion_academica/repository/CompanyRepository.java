package cl.edu.galaxy.training.apps.siac.api_gestion_academica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.edu.galaxy.training.apps.siac.api_gestion_academica.entity.CompanyEntity;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>{

	//JPQL
	@Query("select c from Company c where upper(c.company) like upper(:company)")
	List<CompanyEntity> findByLikeCompany(@Param("company") String company);
	
	@Query("select c from Company c where upper(c.company) like upper(:company) and c.state='0'")
	List<CompanyEntity> findByLikeCompanyPrivate(@Param("company") String company);
	

	@Query("select c from Company c where upper(c.country) like upper(:country)")
	List<CompanyEntity> findByLikeCountry(@Param("country") String country);

	
	@Query("select c from Company c where upper(c.contact) like upper(:contact)")
	List<CompanyEntity> findByLikeContact(@Param("contact") String contact);

	
}
