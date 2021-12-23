package wedding;

public class CountryArea {
	private String areaId;
	private String areaName;
	
	
	public CountryArea() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CountryArea(String areaId, String areaName) {
		super();
		this.areaId = areaId;
		this.areaName = areaName;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	@Override
	public String toString() {
		return "COUNTRYAREA: "+areaId+" "+areaName;
	}
	
	
}
