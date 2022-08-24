package com.igrecdoicapa.RESTSportal.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.igrecdoicapa.RESTSportal.Entity.Reservation;
import com.igrecdoicapa.RESTSportal.Entity.Schedule;
import com.igrecdoicapa.RESTSportal.Entity.Week;

@Repository
public class ReservationDAOImpl implements ReservationDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Reservation> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Reservation> theQuery = currentSession.createQuery("from Reservation", Reservation.class);
		
		List<Reservation> reservations = theQuery.getResultList();
		
		return reservations;
	}

	@Override
	public Reservation findById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Reservation theReservation = currentSession.get(Reservation.class, theId);
		return theReservation;
	}

	@Override
	public void save(Reservation theReservation) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(theReservation);
		
	}

	@Override
	public void deleteById(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("delete from Reservation where id=:reservationId");
		theQuery.setParameter("reservationId", theId);
		theQuery.executeUpdate();
	}

	@Override
	public List<Reservation> findByScheduleId(int scheduleId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Reservation> theQuery = currentSession.createQuery("from Reservation where id_schedule=:scheduleId");
		
		theQuery.setParameter("scheduleId", scheduleId);
		
		List<Reservation> reservations = theQuery.getResultList();
		return reservations;
	}
	
	
}
