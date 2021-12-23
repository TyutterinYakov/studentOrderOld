package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

	private static final String FORMDATE = "dd.MM.yyyy";

	@Override
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v, DateTimeFormatter.ofPattern(FORMDATE));
	}

	@Override
	public String marshal(LocalDate v) throws Exception {
		return v.format(DateTimeFormatter.ofPattern(FORMDATE));
	}

}
