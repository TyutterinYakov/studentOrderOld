package wedding;

public class Street {
	
	private Long streetCode;
	private String streetName;
	
	
	public Street() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Street(Long streetCode, String streetName) {
		super();
		this.streetCode = streetCode;
		this.streetName = streetName;
	}


	public Long getStreetCode() {
		return streetCode;
	}
	public void setStreetCode(Long streetCode) {
		this.streetCode = streetCode;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	@Override
	public String toString() {
		return "STREET: "+streetCode+" "+streetName;
	}
	
}
