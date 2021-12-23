package register;

import answerRegister.CityRegisterResponse;
import exception.CityRegisterException;
import wedding.Person;

public interface CityRegisterChecker {

		CityRegisterResponse checkPerson(Person person) throws CityRegisterException;
		
		
}
