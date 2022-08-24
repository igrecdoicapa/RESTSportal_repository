package com.igrecdoicapa.RESTSportal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrecdoicapa.RESTSportal.DAO.ActivityDAO;
import com.igrecdoicapa.RESTSportal.Entity.Activity;

@Service
public class ActivitySeviceImpl implements ActivityService {
	
	@Autowired
	private ActivityDAO activityDAO;

	@Override
	@Transactional
	public List<Activity> findAll() {
		return activityDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Activity theActivity) {
		activityDAO.save(theActivity);
	}

	@Override
	@Transactional
	public Activity findById(int theId) {
		return activityDAO.findById(theId);
	}
	
	
	
	@Override
	@Transactional
	public Activity findByName(String name) {
		return activityDAO.findByName(name);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		activityDAO.deleteById(theId);
	}

}
