package validator;
import answerRegister.AnswerCityRegister;
import answerRegister.AnswerCityRegisterItem;
import answerRegister.CityRegisterResponse;
import exception.CityRegisterException;
import register.CityRegisterChecker;
import register.RealCityRegisterChecker;
import wedding.Person;
import wedding.StudentOrder;

public class CityRegisterValidator 
{
	private static final String IN_CODE = "NO_GRN";
	private CityRegisterChecker personChecker;
	
	public CityRegisterValidator() {
		personChecker = new RealCityRegisterChecker();
	}



	public AnswerCityRegister checkCityRegister(StudentOrder so) {
		AnswerCityRegister ans = new AnswerCityRegister(); 

		ans.addItems(checkPerson(so.getHusband()));
		ans.addItems(checkPerson(so.getWife()));
		
		for(int i=0; i<so.getChildren().size();i++) {
			ans.addItems(checkPerson(so.getChildren().get(i)));
		}

	

		return ans;
	}
	
	private AnswerCityRegisterItem checkPerson(Person person) {
		AnswerCityRegisterItem.CityStatus status = null;
		AnswerCityRegisterItem.CityError error = null;
		try {
			CityRegisterResponse tmp = personChecker.checkPerson(person);
			status = tmp.isRegistered() ?
				AnswerCityRegisterItem.CityStatus.YES:
				AnswerCityRegisterItem.CityStatus.NO;

		} catch(CityRegisterException ex){
			
		ex.printStackTrace(System.out);
		status = AnswerCityRegisterItem.CityStatus.ERROR;
		error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
		
		} catch(Exception ex){
		
		ex.printStackTrace(System.out);
		status = AnswerCityRegisterItem.CityStatus.ERROR;
		error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
	}
		
		AnswerCityRegisterItem ans =  new AnswerCityRegisterItem(status, person, error);
		
		return ans;
	}
	
	
}
