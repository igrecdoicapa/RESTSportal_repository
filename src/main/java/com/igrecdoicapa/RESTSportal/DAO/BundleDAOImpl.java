package com.igrecdoicapa.RESTSportal.DAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.igrecdoicapa.RESTSportal.Entity.Bundle;
import com.igrecdoicapa.RESTSportal.Entity.BundleActivity;

@Repository
public class BundleDAOImpl implements BundleDAO {
	
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Bundle> findAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Bundle> theQuery = currentSession.createQuery("from Bundle", Bundle.class);
		
		List<Bundle> bundles = theQuery.getResultList();
		
		return bundles;
	}

	@Override
	public void save(Bundle theBundle) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(theBundle);
	}

	@Override
	public Bundle findById(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		return currentSession.get(Bundle.class, theId);
	}

	@Override
	public void deleteById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("delete from Bundle where id = :bundleId");
		theQuery.setParameter("bundleId", theId);
		
		theQuery.executeUpdate();
	}

	@Override
	public HashMap getValidUntil(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<BundleActivity> theQuery = currentSession.createQuery("from BundleActivity where id_bundle = :bundleId", BundleActivity.class);
		theQuery.setParameter("bundleId", theId);
		
		List<BundleActivity> bundleActivities = theQuery.getResultList();
		
		HashMap activityValidUntil = new HashMap();
		
		for (BundleActivity bundleActivity : bundleActivities) {
			activityValidUntil.put(
					"b" + bundleActivity.getBundleId() + "a" + bundleActivity.getActivityId()
					,bundleActivity.getValidUntil());
		}
		
		return activityValidUntil;
	}

	@Override
	public void updateValidUntil(int bundleId, int activityId, String validUntil) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<BundleActivity> theQuery = currentSession.createQuery("update BundleActivity set validUntil = '" + validUntil+ "' where id_bundle = :bundleId and id_activity = :activityId");
		theQuery.setParameter("bundleId", bundleId);
		theQuery.setParameter("activityId", activityId);
		
		theQuery.executeUpdate();
		
	}
	
	
}
