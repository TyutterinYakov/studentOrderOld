package validator;
import domain.AnswerWedding;
import wedding.StudentOrder;

public class WeddingValidator {
	String hostName;
	String login;
	String password;
	public AnswerWedding checkWedding(StudentOrder so) {
		
		System.out.println("checkWedding: "+hostName+", "+login+", "+password);
		
		return new AnswerWedding();
	}
}
