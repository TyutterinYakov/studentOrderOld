package wedding;

public class RegisterOffice {
	
	private Long officeId;
	private String officeAreaId;
	private String officeName;
	public RegisterOffice(Long officeId, String officeAreaId, String officeName) {
		super();
		this.officeId = officeId;
		this.officeAreaId = officeAreaId;
		this.officeName = officeName;
	}
	public RegisterOffice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}
	public String getOfficeAreaId() {
		return officeAreaId;
	}
	public void setOfficeAreaId(String officeAreaId) {
		this.officeAreaId = officeAreaId;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@Override
	public String toString() {
		return "RegisterOffice: "+officeId+" "+officeAreaId+" "+officeName; 
	}
	

}
