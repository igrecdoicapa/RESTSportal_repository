package com.igrecdoicapa.RESTSportal.DAO;

import java.util.List;

import com.igrecdoicapa.RESTSportal.Entity.User;

public interface UserDAO {

    public List<User> findAll();
    
    public User findById(int theId);
    
    public User findByUserName(String username);
    
    public void save(User theUser);
    
    public void deleteById(int theId);
    
}
