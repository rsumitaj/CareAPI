package com.magic.project.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "patients")
public class Patient {

	@Id
	@GeneratedValue
	private int patientId;
	@NotNull
	@Pattern(regexp = "[A-Za-z' ']*", message = "Only alphabets")
	@Length(min = 3, message = "Invalid Length")
	private String patientName;
	@NotNull
	@Pattern(regexp = "[A-Za-z' ']*", message = "Only alphabets")
	@Length(max = 20, message = "Invalid Length")
	private String patientCity;
	@Email(message = "Invalid email address")
	private String patientEmail;
	@NotNull
	@Pattern(regexp = "\\d{10}", message = "Invalid phone number")
	private String patientPhoneNo;
	@NotNull
	@Pattern(regexp = "^(Arthritis|Backpain|Tissue injuries|Dysmenorrhea|Skin infection|Skin burn|Ear pain)$", message = "Invalid symptom")
	private String symptom;

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientCity() {
		return patientCity;
	}

	public void setPatientCity(String patientCity) {
		this.patientCity = patientCity;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getPatientPhoneNo() {
		return patientPhoneNo;
	}

	public void setPatientPhoneNo(String patientPhoneNo) {
		this.patientPhoneNo = patientPhoneNo;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

}