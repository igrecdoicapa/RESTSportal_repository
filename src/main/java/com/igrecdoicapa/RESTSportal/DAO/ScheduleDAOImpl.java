package com.igrecdoicapa.RESTSportal.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.igrecdoicapa.RESTSportal.Entity.Schedule;
import com.igrecdoicapa.RESTSportal.Entity.User;

@Repository
public class ScheduleDAOImpl implements ScheduleDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Schedule> findAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Schedule> theQuery = currentSession.createQuery("from Schedule", Schedule.class);
		
		List<Schedule> schedules = theQuery.getResultList();
		
		return schedules;
	}

	@Override
	public void deleteById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("delete from Schedule where id = :scheduleId");
		theQuery.setParameter("scheduleId", theId);
		
		theQuery.executeUpdate();

	}
	
	@Override
	public void save(Schedule theSchedule) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(theSchedule);

	}

	@Override
	public Schedule findById(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		return currentSession.get(Schedule.class, theId);
	}

	@Override
	public List<Schedule> findByWeekId(int weekId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Schedule> theQuery = currentSession.createQuery("from Schedule where id_week=:weekId");
		
		theQuery.setParameter("weekId", weekId);
		
		List<Schedule> schedules = theQuery.getResultList();
		
		if(schedules.size() == 0) {
			return null;
		}
		return schedules;
	}
	
	
}
