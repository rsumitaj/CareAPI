package com.magic.project.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "doctors")
public class Doctor {
	public static final String RULE ="[A-Za-z' ']*";
	public static final String ORTHO= "Orthopedic";
	@Id
	@GeneratedValue
	private int doctorId;
	@Pattern(regexp = RULE, message = "Only alphabets")
	@Length(min = 3, message = "Invalid Length")
	private String doctorName;
	@Length(min = 3, message = "Invalid Length")
	@Pattern(regexp = "^(Delhi|Noida|Faridabad|)$", message = "City must be Delhi, Noida or Faridabad")
	private String doctorCity;
	@Email(message = "Invalid email address")
	private String doctorEmail;
	@Pattern(regexp = "\\d{10}", message = "Invalid phone number")
	private String doctorPhoneNo;
	@Pattern(regexp = "^(Orthopedic|Gynecology|Dermatology|ENT)$", message = "Speciality must be Orthopedic, Gynecology, Dermatology or ENT specialist")
	private String speciality;
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorCity() {
		return doctorCity;
	}
	public void setDoctorCity(String doctorCity) {
		this.doctorCity = doctorCity;
	}
	public String getDoctorEmail() {
		return doctorEmail;
	}
	public void setDoctorEmail(String doctorEmail) {
		this.doctorEmail = doctorEmail;
	}
	public String getDoctorPhoneNo() {
		return doctorPhoneNo;
	}
	public void setDoctorPhoneNo(String doctorPhoneNo) {
		this.doctorPhoneNo = doctorPhoneNo;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	}
