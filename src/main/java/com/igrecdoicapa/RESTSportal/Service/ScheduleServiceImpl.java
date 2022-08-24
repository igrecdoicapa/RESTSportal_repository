package com.igrecdoicapa.RESTSportal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrecdoicapa.RESTSportal.DAO.ScheduleDAO;
import com.igrecdoicapa.RESTSportal.Entity.Schedule;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	@Override
	@Transactional
	public List<Schedule> findAll() {
		return scheduleDAO.findAll();
	}

	@Override
	@Transactional
	public Schedule findById(int theId) {
		return scheduleDAO.findById(theId);
	}

	@Override
	@Transactional
	public void save(Schedule theSchedule) {
		scheduleDAO.save(theSchedule);

	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		scheduleDAO.deleteById(theId);

	}

	@Override
	@Transactional
	public List<Schedule> findByWeekId(int weekId) {
		// TODO Auto-generated method stub
		return scheduleDAO.findByWeekId(weekId);
	}
	
	
}
