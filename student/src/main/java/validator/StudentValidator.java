package validator;
import domain.AnswerStudent;
import wedding.StudentOrder;

public class StudentValidator {
	
	String hostName;
	public String login;
	String password;
	
	public AnswerStudent checkStudent(StudentOrder so) {
		System.out.println("Check student: "+hostName+", "+login+", "+password);
		return new AnswerStudent();
	}
}
