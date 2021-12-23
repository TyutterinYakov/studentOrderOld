package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import config.Config;
import exception.DaoException;
import wedding.Address;
import wedding.Adult;
import wedding.Child;
import wedding.PassportOffice;
import wedding.Person;
import wedding.RegisterOffice;
import wedding.Street;
import wedding.StudentOrder;
import wedding.StudentOrderStatus;
import wedding.University;

public class StudentOrderDaoImpl implements StudentOrderDao 
{
	
	private static final Logger logger = LoggerFactory.getLogger(StudentOrderDaoImpl.class);
	
	private static final String INSERT_ORDER = "INSERT INTO jc_student_order("
			+ " student_order_status, student_order_date, h_sur_name, h_given_name, h_patronymic, "
			+ "h_date_of_birth, h_passport_seria, h_passport_number, h_passport_date, h_passport_office_id,"
			+ " h_post_index, h_street_code, h_building, h_extension, h_apartment, h_university_id,"
			+ " h_student_number, w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_seria,"
			+ " w_passport_number, w_passport_date, w_passport_office_id, w_post_index, w_street_code,"
			+ " w_building, w_extension, w_apartment, w_university_id, w_student_number, certificate_id,"
			+ " register_office_id, marriage_date)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_GHILD = "INSERT INTO jc_student_child("
			+ " student_order_id, c_sur_name, c_given_name, c_patronymic, c_date_of_birth, "
			+ "c_certificate_number, c_certificate_date, c_register_office_id, c_post_index,"
			+ " c_street_code, c_building, c_extension, c_apartment)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECT_ORDERS = "SELECT ro.r_office_area_id, "
			+ "ro.r_office_name, so.*, po_h.p_office_area_id as h_p_office_area_id, "
			+ "po_h.p_office_name as h_p_office_name, "
			+ "po_w.p_office_area_id as w_p_office_area_id, po_w.p_office_name as w_p_office_name "
			+ "FROM jc_student_order so "
			+ "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id "
			+ "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id "
			+ "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id "
			+ "WHERE student_order_status = ? ORDER BY student_order_date LIMIT ?";
	private static final String SELECT_CHILDS = "SELECT soc.*, ro.r_office_area_id, ro.r_office_name "
			+ "FROM jc_student_child soc "
			+ "INNER JOIN jc_register_office ro ON ro.r_office_id = soc.c_register_office_id "
			+ "WHERE soc.student_order_id IN ";
	private static final String SELECT_ORDERS_FULL =  "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
            "po_h.p_office_area_id as h_p_office_area_id, " +
            "po_h.p_office_name as h_p_office_name, " +
            "po_w.p_office_area_id as w_p_office_area_id, " +
            "po_w.p_office_name as w_p_office_name, " +
            "soc.*, ro_c.r_office_area_id, ro_c.r_office_name " +
            "FROM jc_student_order so " +
            "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
            "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
            "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
            "INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id " +
            "INNER JOIN jc_register_office ro_c ON ro_c.r_office_id = soc.c_register_office_id " +
            "WHERE student_order_status = ? ORDER BY so.student_order_id LIMIT ?";

	
	

	private Connection getConnection() throws SQLException{
		return ConnectionBuilder.getConnection();
	}	
	@Override
	public Long saveStudentOrder(StudentOrder so) throws DaoException {
		Long result = -1L;
		logger.debug("SO:{}", so);
		try(Connection con = getConnection()) {
			PreparedStatement stmt = con.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"});
			con.setAutoCommit(false);
			try {
		//Header
			stmt.setInt(1, StudentOrderStatus.START.ordinal());
			stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
		//Husband and Wife
			Adult husband = so.getHusband();
			Adult wife = so.getWife();
			setParamsForAdult(stmt, 3, husband);
			setParamsForAdult(stmt, 18, wife);						
		//Marriage
			stmt.setString(33, so.getMarriageSertificateId());
			stmt.setLong(34, so.getMarriageOffice().getOfficeId());
			stmt.setDate(35, java.sql.Date.valueOf(so.getMarriageDate()));			
			stmt.executeUpdate(); //Обновление данных(модификация)Возвращение измененных данных			
			ResultSet gkRs =  stmt.getGeneratedKeys();
			if(gkRs.next()) {
				result = gkRs.getLong(1);
			}
			gkRs.close();			
		//Children
			saveChildren(con, so, result);			
			con.commit();						
			} catch (SQLException ex) {
			con.rollback();
			throw ex;
			}			
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
				throw new DaoException(ex);
		}
		return result;
	}
	//Цикл для передачи количества детей и самих детей в метод setParamsForChilds
	private void saveChildren(Connection con, StudentOrder so, Long soId) throws SQLException {
		try(PreparedStatement stmt = con.prepareStatement(INSERT_GHILD)){
		for(Child child: so.getChildren()) {
			stmt.setLong(1, soId);
			setParamsForChilds(stmt, child);
			stmt.addBatch();
		}
		stmt.executeBatch();
		}
		
	}		
	//Wife and Husband
	private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
		setParamsForPerson(stmt, start, adult);
		stmt.setString(start+4, adult.getPassportSeria());
		stmt.setString(start+5, adult.getPassportNumber());
		stmt.setDate(start+6, java.sql.Date.valueOf(adult.getIssueDate()));
		stmt.setLong(start+7, adult.getIssueDepartment().getOfficeId());
		stmt.setLong(start+13, adult.getUniversity().getUniversityId());
		stmt.setString(start+14,  adult.getStudentId());
		setParamsForAddress(stmt, start+8, adult);
	}
	//Childs
	private void setParamsForChilds(PreparedStatement stmt, Child child) throws SQLException{
		setParamsForPerson(stmt, 2, child);
		stmt.setString(6, child.getCertificateNumber());
		stmt.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
		stmt.setLong(8, child.getIssueDepartment().getOfficeId());
		setParamsForAddress(stmt, 9, child);
	}
	//Address all
	private void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException {
		Address adult_address = person.getAddress();
		stmt.setString(start, adult_address.getPostCode());
		stmt.setLong(start+1, adult_address.getStreet().getStreetCode());
		stmt.setString(start+2, adult_address.getBuilding());
		stmt.setString(start+3, adult_address.getExtension());
		stmt.setString(start+4, adult_address.getApartment());
	}
	//Params person all
	private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
		stmt.setString(start, person.getSurName());
		stmt.setString(start+1, person.getGivenName());
		stmt.setString(start+2, person.getPatronymic());
		stmt.setDate(start+3, java.sql.Date.valueOf(person.getDateOfBirth()));
	}
	
	
	
	
	@Override
	public List<StudentOrder> getStudentOrders() throws DaoException {
	//	return getStudentOrdersTwoSelect();
		return getStudentOrdersOneSelect();
	}
	
	private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
		List<StudentOrder> result = new LinkedList<>();
		try(Connection con = getConnection()) {
			PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS_FULL);
			Map<Long, StudentOrder> maps = new HashMap<>();
			stmt.setInt(1, StudentOrderStatus.START.ordinal());
			int limit =  Integer.parseInt(Config.getProperties(Config.DB_LIMIT));
			stmt.setInt(2, limit);
			ResultSet rs = stmt.executeQuery();
			int counter=0;
				while(rs.next()) {
					Long soId = rs.getLong("student_order_id");
					if(!maps.containsKey(soId)) {
					StudentOrder so = new StudentOrder();
					fillStudentOrder(rs, so);
					fillMarriage(rs, so);
						
					so.setHusband(fillAdult(rs, "h_"));
					so.setWife(fillAdult(rs, "w_"));
					result.add(so);
					maps.put(soId, so);
					}
					StudentOrder so = maps.get(soId);
					so.addChild(fillChild(rs));
					counter++;
				}
				if(counter>=limit) {
				result.remove(result.size()-1);
				}
			rs.close();
			} catch(SQLException ex) {
				logger.error(ex.getMessage(), ex);
				throw new DaoException(ex);
			}
		
		
		return result;
	}
	
	private List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
		List<StudentOrder> result = new LinkedList<>();
		try(Connection con = getConnection()) {
			PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS);	
			stmt.setInt(2, Integer.parseInt(Config.getProperties(Config.DB_LIMIT)));
			stmt.setInt(1, StudentOrderStatus.START.ordinal());
			ResultSet rs = stmt.executeQuery();
			
				while(rs.next()) {
					StudentOrder so = getFullStudentOrder(rs);
					result.add(so);

				}

				findChildren(con, result);
			rs.close();
			} catch(SQLException ex) {
				logger.error(ex.getMessage(), ex);
				throw new DaoException(ex);
			}
		
		
		return result;
	}
	
	//Optimization code methods
	private StudentOrder getFullStudentOrder(ResultSet rs) throws SQLException {
		StudentOrder so = new StudentOrder();
		fillStudentOrder(rs, so);
		fillMarriage(rs, so);
		
		so.setHusband(fillAdult(rs, "h_"));
		so.setWife(fillAdult(rs, "w_"));
		return so;
	}
	
	private void findChildren(Connection con, List<StudentOrder> result) throws SQLException
	{
		String cl = "("+result.stream().map(so->String.valueOf(so.getStudentOrderId()))
		.collect(Collectors.joining(","))+")";
		
		Map<Long, StudentOrder> maps= result.stream().collect(Collectors
				.toMap(so-> so.getStudentOrderId(), so-> so));
		try(PreparedStatement stmt = con.prepareStatement(SELECT_CHILDS+cl)){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Child cld = fillChild(rs);

				StudentOrder so = maps.get(rs.getLong("student_order_id"));
				so.addChild(cld);
			}
		}
		
		
	}
	//Childs
	private Child fillChild(ResultSet rs) throws SQLException {
		String surName = rs.getString("c_sur_name");
		String givenName = rs.getString("c_given_name");
		String patrName = rs.getString("c_patronymic");
		LocalDate dateBirth = rs.getDate("c_date_of_birth").toLocalDate();
		Child child = new Child(surName, givenName, patrName, dateBirth);
		child.setCertificateNumber(rs.getString("c_certificate_number"));
		child.setIssueDate(rs.getDate("c_certificate_date").toLocalDate());
		
		Long roId = rs.getLong("c_register_office_id");
		String roArea = rs.getString("r_office_area_id");
		String roName = rs.getString("r_office_name");
		RegisterOffice ro = new RegisterOffice(roId, roArea, roName);
		child.setIssueDepartment(ro);
		
		Address adr = new Address();
		Street str = new Street(rs.getLong("c_street_code"), "");
		adr.setStreet(str);
		adr.setPostCode(rs.getString("c_post_index"));
		adr.setBuilding(rs.getString("c_building"));
		adr.setExtension(rs.getString("c_extension"));
		adr.setApartment(rs.getString("c_apartment"));
		child.setAddress(adr);
		
		return child;
	}
	
	//Adult +prefix 
	private Adult fillAdult(ResultSet rs, String st) throws SQLException{
		
		//Person info
		Adult adult = new Adult();
		adult.setSurName(rs.getString(st + "sur_name"));
		adult.setGivenName(rs.getString(st + "given_name"));
		adult.setPatronymic(rs.getString(st+"patronymic"));
		adult.setDateOfBirth(rs.getDate(st+"date_of_birth").toLocalDate());
		
		//Passport Info
		adult.setPassportSeria(rs.getString(st+"passport_seria"));
		adult.setPassportNumber(rs.getString(st+"passport_number"));
		adult.setIssueDate(rs.getDate(st+"passport_date").toLocalDate());
		
		Long pId = rs.getLong(st+"passport_office_id");
		String areaId = rs.getString(st+"p_office_area_id");
		String name = rs.getString(st+"p_office_name");
		PassportOffice po = new PassportOffice(pId, areaId, name);
		adult.setIssueDepartment(po);
		
		//Address info
		Address adr = new Address();
		Street str = new Street(rs.getLong(st+"street_code"), "");
		adr.setStreet(str);
		adr.setPostCode(rs.getString(st+"post_index"));
		adr.setBuilding(rs.getString(st+"building"));
		adr.setExtension(rs.getString(st+"extension"));
		adr.setApartment(rs.getString(st+"apartment"));
		adult.setAddress(adr);
		
		//University info
		University uni = new University(rs.getLong(st+"university_id"), "");
		adult.setUniversity(uni);
		adult.setStudentId(rs.getString(st+"student_number"));

		return adult;
	}
	
	//Marriage
	private void fillMarriage(ResultSet rs, StudentOrder so) throws SQLException {
		so.setMarriageSertificateId(rs.getString("certificate_id"));
		Long roId = rs.getLong("register_office_id");
		String areaId = rs.getString("r_office_area_id");
		String name  = rs.getString("r_office_name");
		RegisterOffice ro = new RegisterOffice(roId, areaId, name);
		so.setMarriageDate(rs.getDate("marriage_date").toLocalDate());
		so.setMarriageOffice(ro);
		
	}
	//Student order
	private void fillStudentOrder(ResultSet rs, StudentOrder so) throws SQLException {
		so.setStudentOrderId(rs.getLong("student_order_id"));
		so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
		so.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));
		
		
	}	
}
