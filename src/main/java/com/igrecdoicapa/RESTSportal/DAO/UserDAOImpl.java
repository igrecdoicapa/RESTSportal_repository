package com.igrecdoicapa.RESTSportal.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.igrecdoicapa.RESTSportal.Entity.User;

@Repository
public class UserDAOImpl implements UserDAO {

	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<User> findAll(){
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<User> theQuery = currentSession.createQuery("from User", User.class);
		
		List<User> users = theQuery.getResultList();
		
		return users;
	}

	@Override
	public void save(User theUser) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(theUser);
	}
	
	@Override
	public User findById(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		User theUser = currentSession.get(User.class, theId);
		return theUser;
	}
	
	


	@Override
	public User findByUserName(String username) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<User> theQuery = currentSession.createQuery("from User where username=:userName");
		
		theQuery.setParameter("userName", username);
		
		List<User> theUser = theQuery.getResultList();
		return theUser.get(0);
	}

	@Override
	public void deleteById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery1 = currentSession.createQuery("delete from Reservation where id_user=:userId");
		theQuery1.setParameter("userId", theId);
		theQuery1.executeUpdate();
		
		Query theQuery2 = currentSession.createQuery("delete from User where id=:userId");
		theQuery2.setParameter("userId", theId);
		theQuery2.executeUpdate();
		
	}
}
