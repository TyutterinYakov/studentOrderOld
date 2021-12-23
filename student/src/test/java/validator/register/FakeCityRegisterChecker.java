package validator.register;

import answerRegister.CityRegisterResponse;
import exception.CityRegisterException;
import register.CityRegisterChecker;
import wedding.Adult;
import wedding.Child;
import wedding.Person;

public class FakeCityRegisterChecker implements CityRegisterChecker
{

	private static final String GOOD_1="1000";
	private static final String GOOD_2="2000";
	private static final String BAD_1="1001";
	private static final String BAD_2="2001";
	private static final String ERROR_1="1002";
	private static final String ERROR_2="2002";
	private static final String ERROR_T_1="1003";
	private static final String ERROR_T_2="2003";
	
	public CityRegisterResponse checkPerson(Person person) 
			throws CityRegisterException{
		//System.out.println(person instanceof Adult);
		CityRegisterResponse res = new CityRegisterResponse();
		if(person instanceof Adult) {
			Adult t = (Adult) person;
			//System.out.println(t.getPassportSeria());
			String ps = t.getPassportSeria();
			if(ps.equals(GOOD_1)||(ps.equals(GOOD_2))){
				res.setRegistered(true);
				res.setTemporal(false);
			}
			if(ps.equals(BAD_1)||(ps.equals(BAD_2))){
				res.setRegistered(false);
			//	res.setTemporal(false);
			}
			if(ps.equals(ERROR_1)||(ps.equals(ERROR_2))){
				CityRegisterException cr = new CityRegisterException("1","GRN ERROR "+ps);
				throw cr;
			}
		}
	//	System.out.println("Child: "+(person instanceof Child));
		if (person instanceof Child) {
			res.setRegistered(true);
			res.setTemporal(true);
		}
		
		System.out.println(res);
		
		return res;
	}
}
