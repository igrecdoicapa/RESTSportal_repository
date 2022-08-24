package com.igrecdoicapa.RESTSportal.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igrecdoicapa.RESTSportal.Entity.Reservation;
import com.igrecdoicapa.RESTSportal.Entity.Schedule;
import com.igrecdoicapa.RESTSportal.Service.ReservationService;
import com.igrecdoicapa.RESTSportal.Service.ScheduleService;

@RestController
@RequestMapping("/api")
public class ReservationController {
	
	private ReservationService reservationService;
	private ScheduleService scheduleService;
	
	@Autowired
	public ReservationController(ReservationService theReservationService, ScheduleService theScheduleService) {
		this.reservationService = theReservationService;
		this.scheduleService = theScheduleService;
	}
	
	@GetMapping("/reservations")
	public List<Reservation> findAll(){
		return reservationService.findAll();
	}
	
	@GetMapping("/reservations/{ReservationId}")
	public Reservation getReservation(@PathVariable int ReservationId) {
		
		Reservation theReservation = reservationService.findById(ReservationId);
		if(theReservation == null) {
			throw new RuntimeException("Reservation id not found - " + ReservationId);
		}
		return theReservation;
	}
	
	@PostMapping("/reservations")
	public Reservation addReservation(@RequestBody Reservation theReservation) {
		theReservation.setId((int) 0);
		
		Schedule theSchedule = new Schedule();
		
		theSchedule = scheduleService.findById(theReservation.getSchedule().getId());
		
		if(theSchedule.getAvailableReservations() > 0) {
			reservationService.save(theReservation);
			theSchedule.setAvailableReservations(theSchedule.getAvailableReservations()-1);
			scheduleService.save(theSchedule);
			
			return theReservation;
		}
		
		
		return null;
		
	}
	
	@DeleteMapping("/reservations/{ReservationId}")
	public String deleteReservation(@PathVariable int ReservationId) {
		Reservation theReservation = reservationService.findById(ReservationId);
		
		if(theReservation == null) {
			throw new RuntimeException("Reservation not found - " + ReservationId);
		}
		
		reservationService.deleteById(ReservationId);
		
		return "Deleted Reservation id - " + ReservationId;
	}
}
