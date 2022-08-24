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

import com.igrecdoicapa.RESTSportal.Entity.Schedule;
import com.igrecdoicapa.RESTSportal.Service.ScheduleService;

@RestController
@RequestMapping("/api")
public class ScheduleController {
	private ScheduleService ScheduleService;
	
	@Autowired
	public ScheduleController(ScheduleService theScheduleService) {
		this.ScheduleService = theScheduleService;
	}
	
	@GetMapping("/Schedules")
	public List<Schedule> findAll(){
		return ScheduleService.findAll();
	}
	
	@GetMapping("/Schedules/{ScheduleId}")
	public Schedule getSchedule(@PathVariable int ScheduleId) {
		
		Schedule theSchedule = ScheduleService.findById(ScheduleId);
		if(theSchedule == null) {
			throw new RuntimeException("Schedule id not found - " + ScheduleId);
		}
		return theSchedule;
	}
	
	@PostMapping("/Schedules")
	public Schedule addSchedule(@RequestBody Schedule theSchedule) {
		theSchedule.setId(0);
		
		ScheduleService.save(theSchedule);
		
		return theSchedule;
		
	}
	
	@PutMapping("/Schedules")
	public Schedule updateSchedule(@RequestBody Schedule theSchedule) {
		
		ScheduleService.save(theSchedule);
		
		return theSchedule;
	}
	
	@DeleteMapping("/Schedules/{ScheduleId}")
	public String deleteSchedule(@PathVariable int ScheduleId) {
		Schedule theSchedule = ScheduleService.findById(ScheduleId);
		
		if(theSchedule == null) {
			throw new RuntimeException("Schedule not found - " + ScheduleId);
		}
		
		ScheduleService.deleteById(ScheduleId);
		
		return "Deleted Schedule id - " + ScheduleId;
	}
}
