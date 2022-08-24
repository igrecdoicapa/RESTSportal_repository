package com.igrecdoicapa.RESTSportal.DAO;

import java.util.List;

import com.igrecdoicapa.RESTSportal.Entity.Activity;

public interface ActivityDAO {
	
	public List<Activity> findAll();
	
	public Activity findById(int theId);
	
	public Activity findByName(String name);
	
	public void save(Activity theActivity);
	
	public void deleteById(int theId);

}
