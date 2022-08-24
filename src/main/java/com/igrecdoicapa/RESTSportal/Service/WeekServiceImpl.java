package com.igrecdoicapa.RESTSportal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrecdoicapa.RESTSportal.DAO.WeekDAO;
import com.igrecdoicapa.RESTSportal.Entity.Week;

@Service
public class WeekServiceImpl implements WeekService {
	
	@Autowired
	private WeekDAO weekDAO;
	
	@Override
	@Transactional
	public List<Week> findAll() {
		return weekDAO.findAll();
	}

	@Override
	@Transactional
	public Week findById(int theId) {
		return weekDAO.findById(theId);
	}

	@Override
	@Transactional
	public Week findByYearAndNumber(int year, int number) {
		return weekDAO.findByYearAndNumber(year, number);
	}

	@Override
	@Transactional
	public void save(Week theWeek) {
		weekDAO.save(theWeek);

	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		weekDAO.deleteById(theId);
	}
}
