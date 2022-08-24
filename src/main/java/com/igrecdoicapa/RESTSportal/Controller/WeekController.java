package com.igrecdoicapa.RESTSportal.Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igrecdoicapa.RESTSportal.Entity.Reservation;
import com.igrecdoicapa.RESTSportal.Entity.Schedule;
import com.igrecdoicapa.RESTSportal.Entity.Week;
import com.igrecdoicapa.RESTSportal.Service.ActivityService;
import com.igrecdoicapa.RESTSportal.Service.ReservationService;
import com.igrecdoicapa.RESTSportal.Service.ScheduleService;
import com.igrecdoicapa.RESTSportal.Service.WeekService;
import com.opencsv.bean.CsvToBeanBuilder;

@RestController
@RequestMapping("/api")
public class WeekController {
	
	private WeekService weekService;
	private ScheduleService scheduleService;
	private ActivityService activityService;
	private ReservationService reservationService;
	
	@Autowired
	public WeekController(WeekService theWeekService, ScheduleService theScheduleService, ActivityService theActivityService, ReservationService theReservationService) {
		this.weekService = theWeekService;
		this.scheduleService = theScheduleService;
		this.activityService = theActivityService;
		this.reservationService = theReservationService;
	}
	
	@GetMapping("/week/{year}/{week}")
	public Week getWeek(@PathVariable int year, @PathVariable int week)  {
		
		Week theWeek = weekService.findByYearAndNumber(year, week);
		
		if(theWeek == null) {
			throw new RuntimeException("Week " + week + " from year " + year + " not found");
		}

		return theWeek;
	}
	
	@PostMapping("/week")
	public String manageWeek(@RequestBody Week theWeek) throws IllegalStateException, FileNotFoundException {
		
		Week week = weekService.findByYearAndNumber(theWeek.getYearOfWeek(), theWeek.getWeekNumber());
		
		if(week != null) {
			
			List<Schedule> affectedSchedules = scheduleService.findByWeekId(week.getId());
			
			List<Reservation> affectedReservations = new ArrayList();
			
			if(affectedSchedules != null) {
				
				for(Schedule schedule : affectedSchedules) {
					
					affectedReservations.addAll(reservationService.findByScheduleId(schedule.getId()));
					for (Reservation reservation : affectedReservations) {
						
						reservationService.deleteById(reservation.getId());
					}
					
					scheduleService.deleteById(schedule.getId());
				}
			}
			
			weekService.deleteById(week.getId());
			
			insertNewSchedule(theWeek);
			
			List<Schedule> newSchedules = scheduleService.findByWeekId(theWeek.getId());
			
			for(Reservation reservation : affectedReservations) {

				for(Schedule schedule : newSchedules) {
					if(reservation.getSchedule().getActivity().getId() == schedule.getActivity().getId()) {
						if(reservation.getSchedule().getHourSchedule() == schedule.getHourSchedule()) {
							if(reservation.getSchedule().getDateSchedule().isEqual(schedule.getDateSchedule())) {
								Reservation newReservation = new Reservation();
								newReservation.setSchedule(schedule);
								newReservation.setUser(reservation.getUser());
								reservationService.save(newReservation);
								schedule.setAvailableReservations(schedule.getAvailableReservations()-1);
								scheduleService.save(schedule);
							}
						}
					}
				}
			}
			
		} else {
			insertNewSchedule(theWeek);
		}
        
		return "Schedule updated"; 
	}
	
	@DeleteMapping("/week/{weekId}")
	public String deleteMapping(@PathVariable int weekId) {
		Week theWeek = weekService.findById(weekId);
		
		if(theWeek == null) {
			throw new RuntimeException("Week not found - " + weekId);
		}
		
		List<Schedule> affectedSchedules = scheduleService.findByWeekId(theWeek.getId());
		
		List<Reservation> affectedReservations = new ArrayList();
		
		if(affectedSchedules != null) {
			
			for(Schedule schedule : affectedSchedules) {
				
				affectedReservations.addAll(reservationService.findByScheduleId(schedule.getId()));
				for (Reservation reservation : affectedReservations) {
					
					reservationService.deleteById(reservation.getId());
				}
				
				scheduleService.deleteById(schedule.getId());
			}
		}
		
		weekService.deleteById(weekId);
		
		return "Deleted week id - " + weekId;
	}
	
	public void insertNewSchedule (@RequestBody Week theWeek) throws IllegalStateException, FileNotFoundException {

		List<Schedule> schedules = new CsvToBeanBuilder(new FileReader(theWeek.getFilePath()))
                .withType(Schedule.class)
                .build()
                .parse();

        for(Schedule schedule : schedules) {

        	schedule.setDateSchedule(LocalDate.parse(schedule.getTempDate()));
        	schedule.setHourSchedule(LocalTime.parse(schedule.getTempHour()));
        	schedule.setTotalReservations(Integer.parseInt(schedule.getTempTotalReservations()));
        	schedule.setAvailableReservations(Integer.parseInt(schedule.getTempTotalReservations()));
        	schedule.setActivity(activityService.findByName(schedule.getTempActivity()));
        }

        theWeek.setSchedules(schedules);

        weekService.save(theWeek);
	}
}
