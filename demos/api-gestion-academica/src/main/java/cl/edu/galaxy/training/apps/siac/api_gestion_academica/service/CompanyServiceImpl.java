package cl.edu.galaxy.training.apps.siac.api_gestion_academica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.edu.galaxy.training.apps.siac.api_gestion_academica.entity.CompanyEntity;
import cl.edu.galaxy.training.apps.siac.api_gestion_academica.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository; // DI - Property - 1ro

	@Override
	public Optional<CompanyEntity> findById(long id) throws ServiceException {
		try {
			return companyRepository.findById(id);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<CompanyEntity> findAll() throws ServiceException {
		try {
			return companyRepository.findAll();

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<CompanyEntity> findByLikeCompany(String company) throws ServiceException {
		try {
			return companyRepository.findByLikeCompany("%"+company+"%");

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public List<CompanyEntity> findByLikeCompanyPrivate(String company) throws ServiceException {
		try {
			return companyRepository.findByLikeCompanyPrivate("%"+company+"%");

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<CompanyEntity> findByLikeContact(String contact) throws ServiceException {
		try {
			return companyRepository.findByLikeContact("%"+contact+"%");

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<CompanyEntity> findByLikeCountry(String country) throws ServiceException {
		try {
			return companyRepository.findByLikeCountry("%"+country+"%");

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public CompanyEntity save(CompanyEntity companyEntity) throws ServiceException { // Post
		try {
			return companyRepository.save(companyEntity);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public CompanyEntity update(CompanyEntity companyEntity) throws ServiceException { // Put
		try {

			// Idempotente

			Optional<CompanyEntity> optCompanyEntity = this.findById(companyEntity.getId());
			// Update
			if (optCompanyEntity.isPresent()) {

				CompanyEntity tmpCompanyEntity = optCompanyEntity.get();

				tmpCompanyEntity.setCompany(companyEntity.getCompany());
				tmpCompanyEntity.setContact(companyEntity.getContact());
				tmpCompanyEntity.setCountry(companyEntity.getCountry());

				return companyRepository.save(tmpCompanyEntity);
			}

			// Insert
			return companyRepository.save(companyEntity);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Boolean updateState(CompanyEntity companyEntity) throws ServiceException {
		try {
			CompanyEntity tmpCompanyEntity = this.findById(companyEntity.getId())
					.orElseThrow(() -> new ServiceException("Not found company by id " + companyEntity.getId()));
			tmpCompanyEntity.setState(companyEntity.getState());
			companyRepository.save(tmpCompanyEntity);
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Boolean delete(Long id) throws ServiceException {
		try {
			companyRepository.deleteById(id); // Borrado Fisico
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
