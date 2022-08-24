package com.igrecdoicapa.RESTSportal.Service;

import java.util.List;

import com.igrecdoicapa.RESTSportal.Entity.Week;

public interface WeekService {

	public List<Week> findAll();
	
	public Week findById(int theId);
	
	public Week findByYearAndNumber(int year, int number);
	
	public void save(Week theWeek);
	
	public void deleteById(int theId);
}
