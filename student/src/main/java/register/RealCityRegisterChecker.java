package register;

import answerRegister.CityRegisterRequest;
import answerRegister.CityRegisterResponse;
import config.Config;
import exception.CityRegisterException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import wedding.Adult;
import wedding.Person;

public class RealCityRegisterChecker implements CityRegisterChecker {

	public CityRegisterResponse checkPerson(Person person) 
			throws CityRegisterException
	{
		try {
		CityRegisterRequest request = new CityRegisterRequest(person);
		Client client = ClientBuilder.newClient();
		CityRegisterResponse response = client.target(Config.getProperties(Config.CR_URL))
			.request(MediaType.APPLICATION_JSON)
			.post(Entity.entity(request, MediaType.APPLICATION_JSON))
			.readEntity(CityRegisterResponse.class);
		
		return response;
		} catch(Exception ex) {
			throw new CityRegisterException("1", ex.getMessage(), ex);
		}
	}
	
}
