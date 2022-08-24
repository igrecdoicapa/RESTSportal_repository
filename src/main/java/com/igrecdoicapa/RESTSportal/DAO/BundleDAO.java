package com.igrecdoicapa.RESTSportal.DAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.igrecdoicapa.RESTSportal.Entity.Bundle;

public interface BundleDAO {
	
	public List<Bundle> findAll();
	
	public Bundle findById(int theId);
	
	public void save(Bundle theBundle);
	
	public void deleteById(int theId);
	
	public HashMap getValidUntil(int theId);
	
	public void updateValidUntil(int bundleId, int activityId, String validUntil);
}
