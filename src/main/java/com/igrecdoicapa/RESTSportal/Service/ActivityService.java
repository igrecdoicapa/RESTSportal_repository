package com.igrecdoicapa.RESTSportal.Service;

import java.util.List;

import com.igrecdoicapa.RESTSportal.Entity.Activity;

public interface ActivityService {
	
	public List<Activity> findAll();
	
	public Activity findById(int theId);
	
	public Activity findByName(String name);
	
	public void save(Activity theActivity);
	
	public void deleteById(int theId);
}
