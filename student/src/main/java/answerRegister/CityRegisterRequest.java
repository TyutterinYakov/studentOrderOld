package answerRegister;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.LocalDateAdapter;
import wedding.Address;
import wedding.Person;

public class CityRegisterRequest {
	
	
	private String surName;
	private String givenName;
	private String patronymic;
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	private LocalDate dateOfBirth;
	private Long streetCode;
	private String building;
	private String extension;
	private String apartment;
	
	
	public CityRegisterRequest(Person person) {
		surName = person.getSurName();
		givenName = person.getGivenName();
		patronymic = person.getPatronymic();
		dateOfBirth = person.getDateOfBirth();
		Address add = person.getAddress();
		streetCode = add.getStreet().getStreetCode();
		building = add.getBuilding();
		extension = add.getExtension();
		apartment = add.getApartment();
		
	}
	
	public CityRegisterRequest() {
		super();
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getPatronymic() {
		return patronymic;
	}
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getApartment() {
		return apartment;
	}
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
	public Long getStreetCode() {
		return streetCode;
	}
	public void setStreetCode(Long streetCode) {
		this.streetCode = streetCode;
	}
	
	@Override
	public String toString() {
		return surName+" "+givenName+" "+patronymic+" "+dateOfBirth+" "+streetCode+" "+
				building+" "+extension+" "+apartment;
	}
	
	
	
}

