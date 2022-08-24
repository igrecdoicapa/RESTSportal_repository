package com.igrecdoicapa.RESTSportal.DAO;

import java.util.List;

import com.igrecdoicapa.RESTSportal.Entity.Schedule;
import com.igrecdoicapa.RESTSportal.Entity.Week;

public interface ScheduleDAO {
	
	public List<Schedule> findAll();
	
	public Schedule findById(int theId);
	
	public void save(Schedule theSchedule);
	
	public void deleteById(int theId);
	
	public List<Schedule> findByWeekId(int weekId);
}
