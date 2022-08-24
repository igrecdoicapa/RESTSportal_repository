package com.igrecdoicapa.RESTSportal.Entity;

import java.util.Date;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="activity")
//@JsonIgnoreProperties(value = {"validUntil"})
public class Activity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
	@JoinTable(
			name = "bundle_activity",
			joinColumns = @JoinColumn(name = "id_activity"),
			inverseJoinColumns = @JoinColumn(name = "id_bundle")
			)
	@JsonIgnoreProperties({"activities","users"})
	private List<Bundle> bundles;

	@Transient
	private boolean containedByBundle;
	
	@Transient
	private Date validUntil;

	public Activity() {
	}

	public Activity(String name) {
		super();
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

	public List<Bundle> getBundles() {
		return bundles;
	}

	public void setBundles(List<Bundle> bundles) {
		this.bundles = bundles;
	}

	
	public boolean isContainedByBundle() {
		return containedByBundle;
	}

	public void setContainedByBundle(boolean containedByBundle) {
		this.containedByBundle = containedByBundle;
	}

	
	
	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}


	
}
