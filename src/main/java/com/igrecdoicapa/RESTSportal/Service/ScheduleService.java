package com.igrecdoicapa.RESTSportal.Service;

import java.util.List;

import com.igrecdoicapa.RESTSportal.Entity.Schedule;

public interface ScheduleService {

	public List<Schedule> findAll();
	
	public Schedule findById(int theId);
	
	public void save(Schedule theSchedule);
	
	public void deleteById(int theId);
	
	public List<Schedule> findByWeekId(int weekId);
}
