package tests;



import org.junit.BeforeClass;
import org.junit.Test;

import dao.StudentOrderDaoImpl;
import exception.DaoException;
import wedding.Address;
import wedding.Adult;
import wedding.Child;
import wedding.PassportOffice;
import wedding.RegisterOffice;
import wedding.Street;
import wedding.StudentOrder;
import wedding.University;

import java.time.LocalDate;
import java.util.List;


public class StudentOrderDaoImplTest
{
    @BeforeClass
    public static void startUp() throws Exception {
        DBInit.startUp();
    }

    @Test
    public void saveStudentOrder() throws DaoException {
        StudentOrder so = buildStudentOrder(10);
        Long id = new StudentOrderDaoImpl().saveStudentOrder(so);
    }

    @Test(expected = DaoException.class)
    public void saveStudentOrderError() throws DaoException {
        StudentOrder so = buildStudentOrder(10);
        so.getHusband().setSurName(null);
        Long id = new StudentOrderDaoImpl().saveStudentOrder(so);
    }

    @Test
    public void getStudentOrders() throws DaoException {
        List<StudentOrder> list = new StudentOrderDaoImpl().getStudentOrders();
    }

    public StudentOrder buildStudentOrder(long id) {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderId(id);
        so.setMarriageSertificateId("" + (123456000 + id));
        so.setMarriageDate(LocalDate.of(2016, 7, 4));

        RegisterOffice ro = new RegisterOffice(1L, "", "");
        so.setMarriageOffice(ro);

        Street street = new Street(1L, "First street");

        Address address = new Address("195000", street, "10", "2", "222");

        // Муж
        Adult husband = new Adult("Кротов", "Александр", "Витальевич", LocalDate.of(2001, 6, 8));
        husband.setPassportSeria("" + (4115));
        husband.setPassportNumber("" + (680124));
        husband.setIssueDate(LocalDate.of(2021, 7, 6));
        PassportOffice po1 = new PassportOffice(1L, "", "");
        husband.setIssueDepartment(po1);
        husband.setStudentId("" + (100000 + id));
        husband.setAddress(address);
        husband.setUniversity(new University(2L, ""));
        husband.setStudentId("HH12345");

        // Жена
        Adult wife = new Adult("Кротова", "Василиса", "Николаевна", LocalDate.of(2000, 1, 3));
        wife.setPassportSeria("" + (4122));
        wife.setPassportNumber("" + (682222));
        wife.setIssueDate(LocalDate.of(2020, 1, 26));
        PassportOffice po2 = new PassportOffice(2L, "", "");
        wife.setIssueDepartment(po2);
        wife.setStudentId("" + (200000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(1L, ""));
        wife.setStudentId("WW12345");

        // Ребенок
        Child child1 = new Child("Кротов", "Николай", "Александрович", LocalDate.of(2020, 01, 05));
        child1.setCertificateNumber("" + (43435465));
        child1.setIssueDate(LocalDate.of(2020, 1, 5));
        RegisterOffice ro2 = new RegisterOffice(2L, "", "");
        child1.setIssueDepartment(ro2);
        child1.setAddress(address);
        // Ребенок
        Child child2 = new Child("Кротова", "Мария", "Александровна", LocalDate.of(2019, 03, 05));
        child2.setCertificateNumber("" + (45412223));
        child2.setIssueDate(LocalDate.of(2019, 03, 05));
        RegisterOffice ro3 = new RegisterOffice(3L, "", "");
        child2.setIssueDepartment(ro3);
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChild(child1);
        so.addChild(child2);

        return so;
    }

}