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

import com.igrecdoicapa.RESTSportal.Entity.Activity;
import com.igrecdoicapa.RESTSportal.Service.ActivityService;

@RestController
@RequestMapping("/api")
public class ActivityController {
	
	private ActivityService activityService;
	
	@Autowired
	public ActivityController(ActivityService theActivityService) {
		activityService = theActivityService;
	}
	
	@GetMapping("/activities")
	public List<Activity> findAll(){
		return activityService.findAll();
	}
	
	@GetMapping("/activities/{ActivityId}")
	public Activity getActivity(@PathVariable int ActivityId) {
		
		Activity theActivity = activityService.findById(ActivityId);
		if(theActivity == null) {
			throw new RuntimeException("Activity id not found - " + ActivityId);
		}
		return theActivity;
	}
	
	@PostMapping("/activities")
	public Activity addActivity(@RequestBody Activity theActivity) {
		theActivity.setId(0);
		
		activityService.save(theActivity);
		
		return theActivity;
		
	}
	
	@PutMapping("/activities")
	public Activity updateActivity(@RequestBody Activity theActivity) {
		
		activityService.save(theActivity);
		
		return theActivity;
	}
	
	@DeleteMapping("/activities/{ActivityId}")
	public String deleteActivity(@PathVariable int ActivityId) {
		Activity theActivity = activityService.findById(ActivityId);
		
		if(theActivity == null) {
			throw new RuntimeException("Activity not found - " + ActivityId);
		}
		
		activityService.deleteById(ActivityId);
		
		return "Deleted Activity id - " + ActivityId;
	}
}
