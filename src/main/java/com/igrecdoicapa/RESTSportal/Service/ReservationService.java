package com.igrecdoicapa.RESTSportal.Service;

import java.util.List;

import com.igrecdoicapa.RESTSportal.Entity.Reservation;
import com.igrecdoicapa.RESTSportal.Entity.Schedule;

public interface ReservationService {
	
	public List<Reservation> findAll();
	
	public Reservation findById(int reservationId);
	
	public void save(Reservation theReservation);
	
	public void deleteById(int i);
	
	public List<Reservation> findByScheduleId(int scheduleId);
}
