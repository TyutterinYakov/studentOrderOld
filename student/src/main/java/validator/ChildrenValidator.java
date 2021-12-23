package validator;
import domain.AnswerChildren;
import wedding.StudentOrder;

public class ChildrenValidator {
	String hostName;
	String login;
	String password;
	public AnswerChildren checkChildren(StudentOrder so) {
		System.out.println("Check children: "+hostName+", "+login+", "+password);
		return new AnswerChildren();
	}
}
