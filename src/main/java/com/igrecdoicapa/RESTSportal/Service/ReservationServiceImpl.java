package com.igrecdoicapa.RESTSportal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrecdoicapa.RESTSportal.DAO.ReservationDAO;
import com.igrecdoicapa.RESTSportal.Entity.Reservation;
import com.igrecdoicapa.RESTSportal.Entity.Schedule;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationDAO reservationDAO;
	
	@Override
	@Transactional
	public List<Reservation> findAll() {
		return reservationDAO.findAll();
	}

	@Override
	@Transactional
	public Reservation findById(int theId) {
		return reservationDAO.findById(theId);
	}

	@Override
	@Transactional
	public void save(Reservation theReservation) {
		reservationDAO.save(theReservation);
		
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		reservationDAO.deleteById(theId);
	}

	@Override
	@Transactional
	public List<Reservation> findByScheduleId(int scheduleId) {
		return reservationDAO.findByScheduleId(scheduleId);
	}

	
}
