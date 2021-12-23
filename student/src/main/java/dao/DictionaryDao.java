package dao;

import java.util.List;

import exception.DaoException;
import wedding.CountryArea;
import wedding.PassportOffice;
import wedding.RegisterOffice;
import wedding.Street;

public interface DictionaryDao {

	List<Street> findStreets(String pattern) throws DaoException;
	List<PassportOffice> findPassportOffices(String areaId) throws DaoException;
	List<RegisterOffice> findRegisterOffices(String pattern) throws DaoException;
	
	List<CountryArea> findAreas(String areaId) throws DaoException;
}
