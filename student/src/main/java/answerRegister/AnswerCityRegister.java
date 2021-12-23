package answerRegister;

import java.util.ArrayList;
import java.util.List;

public class AnswerCityRegister {
	private List<AnswerCityRegisterItem> items;



	public void addItems(AnswerCityRegisterItem item) {
		if(items==null) {
			items = new ArrayList<>(10);		
		}
		items.add(item);
	}
	
	
	public List<AnswerCityRegisterItem> getItems() {
		return items;
	}
	
}
