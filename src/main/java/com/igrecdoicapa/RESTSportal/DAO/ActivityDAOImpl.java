package com.igrecdoicapa.RESTSportal.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.igrecdoicapa.RESTSportal.Entity.Activity;
import com.igrecdoicapa.RESTSportal.Entity.BundleActivity;

@Repository
public class ActivityDAOImpl implements ActivityDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Activity> findAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Activity> theQuery = currentSession.createQuery("from Activity", Activity.class);
		
		List<Activity> activities = theQuery.getResultList();
		
		return activities;
	}

	@Override
	public void deleteById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("delete from Activity where id = :activityId");
		theQuery.setParameter("activityId", theId);
		
		theQuery.executeUpdate();

	}
	
	@Override
	public void save(Activity theActivity) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(theActivity);

	}

	@Override
	public Activity findById(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		return currentSession.get(Activity.class, theId);
	}

	@Override
	public Activity findByName(String name) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Activity> theQuery = currentSession.createQuery("from Activity where name = :activityName", Activity.class);
		theQuery.setParameter("activityName", name);
		
		List<Activity> activities = theQuery.getResultList();
		if(activities.size() == 0) {
			return null;
		}
		return activities.get(0);
	}

}
