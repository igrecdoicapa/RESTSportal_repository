package com.igrecdoicapa.RESTSportal.DAO;

import java.util.List;

import com.igrecdoicapa.RESTSportal.Entity.Reservation;
import com.igrecdoicapa.RESTSportal.Entity.Schedule;

public interface ReservationDAO {
	
	public List<Reservation> findAll();
	
	public Reservation findById(int theId);
	
	public void save(Reservation theReservation);
	
	public void deleteById(int theId);
	
	public List<Reservation> findByScheduleId(int scheduleId);
}
