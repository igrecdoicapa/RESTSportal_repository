package com.igrecdoicapa.RESTSportal.Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name="schedule")
@JsonIgnoreProperties(value = {"tempHour","tempActivity","tempDate","tempTotalReservations"})
public class Schedule {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	@Column(name="date_schedule")
	private LocalDate dateSchedule;
	
	
	@Column(name="hour_schedule")
	private LocalTime hourSchedule;
	
	@Column(name="total_reservations")
	private int totalReservations;
	
	
	@Column(name="available_reservations")
	private int availableReservations;
	
	
	@OneToOne
	@JoinColumn(name="id_activity")
	@JsonIgnoreProperties({"bundles","user"})
	private Activity activity;
	
	@OneToMany(mappedBy="schedule")
	@JsonIgnoreProperties({"schedule"})
	private List<Reservation> reservations;
	
	@CsvBindByName(column = "Hour")
	@Transient
	private String tempHour;
	
	@CsvBindByName(column = "Activity")
	@Transient
	private String tempActivity;
	
	@CsvBindByName(column = "Date")
	@Transient
	private String tempDate;
	
	@CsvBindByName(column = "Total reservations")
	@Transient
	private String tempTotalReservations;

	public Schedule() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDateSchedule() {
		return dateSchedule;
	}

	public void setDateSchedule(LocalDate dateSchedule) {
		this.dateSchedule = dateSchedule;
	}

	public LocalTime getHourSchedule() {
		return hourSchedule;
	}

	public void setHourSchedule(LocalTime hourSchedule) {
		this.hourSchedule = hourSchedule;
	}

	public int getTotalReservations() {
		return totalReservations;
	}

	public void setTotalReservations(int totalReservations) {
		this.totalReservations = totalReservations;
	}

	public int getAvailableReservations() {
		return availableReservations;
	}

	public void setAvailableReservations(int availableReservations) {
		this.availableReservations = availableReservations;
	}


	
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getTempHour() {
		return tempHour;
	}

	public void setTempHour(String tempHour) {
		this.tempHour = tempHour;
	}

	public String getTempActivity() {
		return tempActivity;
	}

	public void setTempActivity(String tempActivity) {
		this.tempActivity = tempActivity;
	}

	public String getTempDate() {
		return tempDate;
	}

	public void setTempDate(String tempDate) {
		this.tempDate = tempDate;
	}

	public String getTempTotalReservations() {
		return tempTotalReservations;
	}

	public void setTempTotalReservations(String tempTotalReservations) {
		this.tempTotalReservations = tempTotalReservations;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	
	
}
