package wedding;

import java.time.LocalDate;

public class Person {

	private String surName;
	private String givenName;
	private String patronymic;
	private LocalDate DateOfBirth;
	private Address address;

	public Person(String surName, String givenName, String patronymic, LocalDate dateOfBirth) {
		this.surName = surName;
		this.givenName = givenName;
		this.patronymic = patronymic;
		DateOfBirth = dateOfBirth;
	}
	
	
	public Person() {
		// TODO Auto-generated constructor stub
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
		return DateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	
	
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    @Override
	public String toString() {
    	return "Person: "+surName+" "+givenName+" "+patronymic+" "+DateOfBirth+" "+address;
    	}
	
	
}
