package cl.edu.galaxy.training.apps.siac.api_gestion_academica.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


//JPA
@Entity(name = "Company")	   // Java- Entity- JPQL	
@Table(name = "tbl_company")   // BD
public class CompanyEntity {   // Java-Beans(Clase) 
	
	@Id
	@Column(name = "company_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "company",length = 260)
	private String company;
	
	@Column(name = "contact",length = 160)
	private String contact;
	
	@Column(name = "country",length = 120)
	private String country;

	@Column(name = "state",length = 1)
	private String state;

	
	public CompanyEntity() {
		super();
	}

	public CompanyEntity(Long id, String company, String contact, String country) {
		super();
		this.id = id;
		this.company = company;
		this.contact = contact;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", company=" + company + ", contact=" + contact + ", country=" + country + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, contact, country, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyEntity other = (CompanyEntity) obj;
		return Objects.equals(company, other.company) && Objects.equals(contact, other.contact)
				&& Objects.equals(country, other.country) && Objects.equals(id, other.id);
	}
	
}
