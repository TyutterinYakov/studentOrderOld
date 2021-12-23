package wedding;

public class University {
	private Long universityId;
	private String universityName;
	
	
	
	
	public University() {
		super();
	}
	
	public University(Long universityId, String universityName) {
		super();
		this.universityId = universityId;
		this.universityName = universityName;
	}
	
	public Long getUniversityId() {
		return universityId;
	}
	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	
	@Override
	public String toString() {
		return "University: "+universityId+" "+universityName; 
	}
	
	
}
