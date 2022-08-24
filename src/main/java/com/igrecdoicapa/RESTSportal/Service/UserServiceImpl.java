package com.igrecdoicapa.RESTSportal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrecdoicapa.RESTSportal.DAO.UserDAO;
import com.igrecdoicapa.RESTSportal.Entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	public List<User> findAll() {
		return userDAO.findAll();
	}
	
	@Override
	@Transactional
	public void save(User theUser) {
		userDAO.save(theUser);
	}
	
	@Override
	@Transactional
	public User findById(int theId) {
		return userDAO.findById(theId);
	}
	
	@Override
	@Transactional
	public User findByUserName(String username) {
		return userDAO.findByUserName(username);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		userDAO.deleteById(theId);
	}

}
