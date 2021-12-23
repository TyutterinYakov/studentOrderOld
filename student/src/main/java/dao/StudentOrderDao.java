package dao;

import java.util.List;

import exception.DaoException;
import wedding.StudentOrder;

public interface StudentOrderDao 
{

	Long saveStudentOrder(StudentOrder so) throws DaoException;
	
	
	List<StudentOrder> getStudentOrders() throws DaoException;
}
