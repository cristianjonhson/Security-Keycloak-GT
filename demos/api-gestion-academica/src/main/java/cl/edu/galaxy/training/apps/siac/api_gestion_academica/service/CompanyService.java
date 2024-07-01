package cl.edu.galaxy.training.apps.siac.api_gestion_academica.service;

import java.util.List;
import java.util.Optional;
import cl.edu.galaxy.training.apps.siac.api_gestion_academica.entity.CompanyEntity;

public interface CompanyService {

	Optional<CompanyEntity> findById(long id) throws ServiceException;
	
	List<CompanyEntity> findAll() throws ServiceException;
	
	List<CompanyEntity> findByLikeCompany(String company) throws ServiceException;
	
	List<CompanyEntity> findByLikeCompanyPrivate(String company) throws ServiceException;
	
	List<CompanyEntity> findByLikeContact(String contact) throws ServiceException;

	List<CompanyEntity> findByLikeCountry(String country) throws ServiceException;

	CompanyEntity save(CompanyEntity companyEntity) throws ServiceException;
	
	CompanyEntity update(CompanyEntity companyEntity) throws ServiceException;
	
	Boolean updateState(CompanyEntity companyEntity) throws ServiceException;
	
	Boolean delete(Long id) throws ServiceException;

}
