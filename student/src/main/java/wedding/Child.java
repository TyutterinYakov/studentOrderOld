package wedding;

import java.time.LocalDate;

public class Child extends Person {

	private String certificateNumber;
	private LocalDate issueDate;
	private RegisterOffice issueDepartment;
	private Address address;

	
	
	public Child(String surName, String givenName, String patronymic, LocalDate dateOfBirth) {
		super(surName, givenName, patronymic, dateOfBirth);

	}
	
	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}
	public RegisterOffice getIssueDepartment() {
		return issueDepartment;
	}
	public void setIssueDepartment(RegisterOffice issueDepartment) {
		this.issueDepartment = issueDepartment;
	}
	
	@Override
	public String toString() {
		return "Child: "+certificateNumber+" "+issueDate+" "+issueDepartment+" "+super.toString();
	}

}
