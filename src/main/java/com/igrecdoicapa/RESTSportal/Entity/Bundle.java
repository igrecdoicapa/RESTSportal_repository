package com.igrecdoicapa.RESTSportal.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="bundle")
public class Bundle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="name")
	private String name;

	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
	@JoinTable(
			name = "bundle_activity",
			joinColumns = @JoinColumn(name = "id_bundle"),
			inverseJoinColumns = @JoinColumn(name = "id_activity")
			)
	@JsonIgnoreProperties("bundles")
	private List<Activity> activities;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
	@JoinTable(
			name = "bundle_user",
			joinColumns = @JoinColumn(name = "id_bundle"),
			inverseJoinColumns = @JoinColumn(name = "id_user")
			)
	@JsonIgnoreProperties({"bundles","reservations"})
	private List<User> users;
	
	
	public Bundle() {
	}

	public Bundle( String name) {
		
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addActivity(Activity theActivity) {
		if(this.activities == null) {
			activities = new ArrayList<Activity>();
		}
		activities.add(theActivity);
	}
}
