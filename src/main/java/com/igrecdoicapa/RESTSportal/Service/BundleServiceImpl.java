package com.igrecdoicapa.RESTSportal.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrecdoicapa.RESTSportal.DAO.BundleDAO;
import com.igrecdoicapa.RESTSportal.Entity.Bundle;

@Service
public class BundleServiceImpl implements BundleService {
	
	@Autowired
	private BundleDAO bundleDAO;

	@Override
	@Transactional
	public List<Bundle> findAll() {
		return bundleDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Bundle theBundle) {
		bundleDAO.save(theBundle);
	}

	@Override
	@Transactional
	public Bundle findById(int theId) {
		return bundleDAO.findById(theId);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		bundleDAO.deleteById(theId);
	}

	@Override
	@Transactional
	public HashMap getValidUntil(int theId) {
		return bundleDAO.getValidUntil(theId);
	}

	@Override
	@Transactional
	public void updateValidUntil(int bundleId, int activityId, String validUntil) {
		bundleDAO.updateValidUntil(bundleId, activityId, validUntil);
		
	}
	
	
	
}
