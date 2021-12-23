package edu.javacourse.studentorder;

import java.util.List;

import answerRegister.AnswerCityRegister;
import dao.StudentOrderDaoImpl;
import domain.AnswerChildren;
import domain.AnswerStudent;
import domain.AnswerWedding;
import exception.DaoException;
import mail.MailSender;
import validator.ChildrenValidator;
import validator.CityRegisterValidator;
import validator.StudentValidator;
import validator.WeddingValidator;
import wedding.StudentOrder;

public class StudentOrderValidator {

	    private CityRegisterValidator cityRegisterVal;
	    private WeddingValidator weddingVal;
	    private ChildrenValidator childrenVal;
	    private StudentValidator studentVal;
	    private MailSender mailSender;

	    public StudentOrderValidator() {
	        cityRegisterVal = new CityRegisterValidator();
	        weddingVal = new WeddingValidator();
	        childrenVal = new ChildrenValidator();
	        studentVal = new StudentValidator();
	        mailSender = new MailSender();
	    }

	    public static void main(String[] args) {
	        StudentOrderValidator sov = new StudentOrderValidator();
	        sov.checkAll();
	    }

	    public void checkAll() {
	        try {
	            List<StudentOrder> soList = readStudentOrders();

	            for (StudentOrder so : soList) {
	                checkOneOrder(so);
	            }
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	    public List<StudentOrder> readStudentOrders() throws DaoException {
	        return new StudentOrderDaoImpl().getStudentOrders();
	    }

	    public void checkOneOrder(StudentOrder so) {
	        AnswerCityRegister cityAnswer = checkCityRegister(so);
	        
//	        AnswerWedding wedAnswer = checkWedding(so);
//	        AnswerChildren childAnswer = checkChildren(so);
//	        AnswerStudent studentAnswer = checkStudent(so);

//        sendMail(so);
	    }

	    public AnswerCityRegister checkCityRegister(StudentOrder so) {
	        return cityRegisterVal.checkCityRegister(so);
	    }

	    public AnswerWedding checkWedding(StudentOrder so) {
	        return weddingVal.checkWedding(so);
	    }

	    public AnswerChildren checkChildren(StudentOrder so) {
	        return childrenVal.checkChildren(so);
	    }

	    public AnswerStudent checkStudent(StudentOrder so) {
	        return studentVal.checkStudent(so);
	    }

	    public void sendMail(StudentOrder so) {
	        mailSender.sendMail(so);
	    }
}
