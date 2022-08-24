package com.igrecdoicapa.RESTSportal.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.igrecdoicapa.RESTSportal.Entity.Bundle;
import com.igrecdoicapa.RESTSportal.Service.ActivityService;
import com.igrecdoicapa.RESTSportal.Service.BundleService;

@RestController
@RequestMapping("/api")
public class BundleController {
	
	private BundleService bundleService;
	private ActivityService activityService;
	
	@Autowired
	public BundleController(BundleService theBundleService, ActivityService theActivityService) {
		bundleService = theBundleService;
		activityService = theActivityService;
	}
	
	@GetMapping("/bundles")
	public List<Bundle> findAll(){

		List<Bundle> bundles = bundleService.findAll();
		List<Bundle> newBundles = new ArrayList();
		for(Bundle bundle : bundles) {
			Bundle newBundle = new Bundle();
			newBundle.setId(bundle.getId());
			newBundle.setName(bundle.getName());
			newBundle.setUsers(bundle.getUsers());
			HashMap activityValidUntil = bundleService.getValidUntil(bundle.getId());
			//Reason newBundles and newActivities lists and newBundle and newActivity objects exist:
			//ValidUntil dates gets overwritten on older Bundle/Activity association by the last association
			//because Activity object had the same memory number in all bundles containing the same Activity
			//ex:	Association1 between bundle1 and activity1 is created and a validUntil value is assigned. 
			//		Association2 between bundle2 and and activity1 will not overwrite association1.
			List<Activity> newActivities = new ArrayList();
			for(Activity activity : bundle.getActivities()) {
				Activity newActivity = new Activity();
				newActivity.setId(activity.getId());
				newActivity.setName(activity.getName());
				newActivity.setContainedByBundle(true);
				newActivity.setValidUntil((Date) activityValidUntil.get("b" + bundle.getId() + "a" + newActivity.getId()));
				newActivities.add(newActivity);
			}
			newBundles.add(newBundle);
			newBundle.setActivities(newActivities);
		}

		return newBundles;
	}
	
	@GetMapping("/bundles/{BundleId}")
	public Bundle getBundle(@PathVariable int BundleId) {
		
		Bundle theBundle = bundleService.findById(BundleId);
		HashMap activityValidUntil = bundleService.getValidUntil(BundleId);

		if(theBundle == null) {
			throw new RuntimeException("Bundle id not found - " + BundleId);
		}
		
		for(Activity activity : theBundle.getActivities()) {
			activity.setContainedByBundle(true);
			activity.setValidUntil((Date) activityValidUntil.get("b" + theBundle.getId() + "a" + activity.getId()));
		}
		
		List<Activity> activities = activityService.findAll();
		
		
		for(Activity activity: activities) {
			if(theBundle.getActivities().contains(activity)) {
				continue;
			}
			theBundle.getActivities().add(activity);
		}
		
		return theBundle;
	}
	
	@PostMapping("/bundles")
	public Bundle addBundle(@RequestBody Bundle theBundle) {
		theBundle.setId(0);
		
		List<Activity> activities = new ArrayList<>();
		
		if (theBundle.getActivities() != null) {
			for(Activity activity : theBundle.getActivities()) {
				if(activity.isContainedByBundle()) {
					activities.add(activity);
				}
			}
		}
		
		
		theBundle.setActivities(activities);
		bundleService.save(theBundle);
		for (Activity activity : theBundle.getActivities()) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			bundleService.updateValidUntil(theBundle.getId(), activity.getId(), simpleDateFormat.format(activity.getValidUntil()));
		}
		return theBundle; 
		
	}
	
	@PutMapping("/bundles")
	public Bundle updateBundle(@RequestBody Bundle theBundle) {
		
		List<Activity> activities = new ArrayList<>();
		
		for(Activity activity : theBundle.getActivities()) {
			if(activity.isContainedByBundle()) {
				activities.add(activity);
			}
		}
		
		theBundle.setActivities(activities);
		
		bundleService.save(theBundle);
		for (Activity activity : theBundle.getActivities()) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			bundleService.updateValidUntil(theBundle.getId(), activity.getId(), simpleDateFormat.format(activity.getValidUntil()));
		}
		return theBundle;
	}
	
	@DeleteMapping("/bundles/{BundleId}")
	public String deleteBundle(@PathVariable int BundleId) {
		Bundle theBundle = bundleService.findById(BundleId);
		
		if(theBundle == null) {
			throw new RuntimeException("Bundle not found - " + BundleId);
		}
		
		bundleService.deleteById(BundleId);
		
		return "Deleted Bundle id - " + BundleId;
	}
}
