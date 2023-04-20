package com.magic.project.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.magic.project.models.Doctor;

public class PatientDto {
	private int patientDtoId;
	@NotNull
	@Pattern(regexp = Doctor.RULE, message = "Only alphabets")
	@Length(min = 3, message = "Invalid Length")
	private String patientDtoName;
	@NotNull
	@Pattern(regexp = Doctor.RULE, message = "Only alphabets")
	@Length(max = 20, message = "Invalid Length")
	private String patientDtoCity;
	@Email(message = "Invalid email address")
	private String patientDtoEmail;
	@NotNull
	@Pattern(regexp = "\\d{10}", message = "Invalid phone number")
	private String patientDtoPhoneNo;
	@NotNull
	@Pattern(regexp = "^(Arthritis|Backpain|Tissue injuries|Dysmenorrhea|Skin infection|Skin burn|Ear pain)$", message = "Invalid symptom")
	private String symptom;
	public int getPatientDtoId() {
		return patientDtoId;
	}
	public void setPatientDtoId(int patientDtoId) {
		this.patientDtoId = patientDtoId;
	}
	public String getPatientDtoName() {
		return patientDtoName;
	}
	public void setPatientDtoName(String patientDtoName) {
		this.patientDtoName = patientDtoName;
	}
	public String getPatientDtoCity() {
		return patientDtoCity;
	}
	public void setPatientDtoCity(String patientDtoCity) {
		this.patientDtoCity = patientDtoCity;
	}
	public String getPatientDtoEmail() {
		return patientDtoEmail;
	}
	public void setPatientDtoEmail(String patientDtoEmail) {
		this.patientDtoEmail = patientDtoEmail;
	}
	public String getPatientDtoPhoneNo() {
		return patientDtoPhoneNo;
	}
	public void setPatientDtoPhoneNo(String patientDtoPhoneNo) {
		this.patientDtoPhoneNo = patientDtoPhoneNo;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

}
