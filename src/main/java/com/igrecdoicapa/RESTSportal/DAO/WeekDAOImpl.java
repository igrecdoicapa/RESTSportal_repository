package com.igrecdoicapa.RESTSportal.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.igrecdoicapa.RESTSportal.Entity.Schedule;
import com.igrecdoicapa.RESTSportal.Entity.Week;
import com.igrecdoicapa.RESTSportal.Service.ScheduleService;

@Repository
public class WeekDAOImpl implements WeekDAO {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ScheduleService scheduleService;

	@Override
	public List<Week> findAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Week> theQuery = currentSession.createQuery("from Week", Week.class);
		
		List<Week> weeks = theQuery.getResultList();
		
		return weeks;
	}
	
	@Override
	public Week findByYearAndNumber(int year, int number) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Week> theQuery = currentSession.createQuery("from Week where yearOfWeek = :yearOfWeek and weekNumber=:weekNumber"); 
		
		theQuery.setParameter("yearOfWeek", year);
		theQuery.setParameter("weekNumber", number);
		
		List<Week> theWeek = theQuery.getResultList();
		if(theWeek.size() == 0) {
			return null;
		}
		return theWeek.get(0);
	}

	@Override
	public void deleteById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery1 = currentSession.createQuery("delete from Schedule where id_week = :weekId");
		theQuery1.setParameter("weekId", theId);
		
		Query theQuery2 = currentSession.createQuery("delete from Week where id = :weekId");
		theQuery2.setParameter("weekId", theId);
		
		theQuery1.executeUpdate();
		theQuery2.executeUpdate();

	}
	
	@Override
	public void save(Week theWeek) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(theWeek);

	}

	@Override
	public Week findById(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		return currentSession.get(Week.class, theId);
	}


	
	
}
