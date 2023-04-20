package com.magic.project.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.magic.project.models.Doctor;

public class DoctorDto {
	private int doctorDtoId;
	@Pattern(regexp = Doctor.RULE, message = "Only alphabets")
	@Length(min = 3, message = "Invalid Length")
	private String doctorDtoName;
	@Length(min = 3, message = "Invalid Length")
	@Pattern(regexp = "^(Delhi|Noida|Faridabad|)$", message = "City must be Delhi, Noida or Faridabad")
	private String doctorDtoCity;
	@Email(message = "Invalid email address")
	private String doctorDtoEmail;
	@Pattern(regexp = "\\d{10}", message = "Invalid phone number")
	private String doctorDtoPhoneNo;
	@Pattern(regexp = "^(Orthopedic|Gynecology|Dermatology|ENT)$", message = "Speciality must be Orthopedic, Gynecology, Dermatology or ENT specialist")
	private String speciality;
	public int getDoctorDtoId() {
		return doctorDtoId;
	}
	public void setDoctorDtoId(int doctorDtoId) {
		this.doctorDtoId = doctorDtoId;
	}
	public String getDoctorDtoName() {
		return doctorDtoName;
	}
	public void setDoctorDtoName(String doctorDtoName) {
		this.doctorDtoName = doctorDtoName;
	}
	public String getDoctorDtoCity() {
		return doctorDtoCity;
	}
	public void setDoctorDtoCity(String doctorDtoCity) {
		this.doctorDtoCity = doctorDtoCity;
	}
	public String getDoctorDtoEmail() {
		return doctorDtoEmail;
	}
	public void setDoctorDtoEmail(String doctorDtoEmail) {
		this.doctorDtoEmail = doctorDtoEmail;
	}
	public String getDoctorDtoPhoneNo() {
		return doctorDtoPhoneNo;
	}
	public void setDoctorDtoPhoneNo(String doctorDtoPhoneNo) {
		this.doctorDtoPhoneNo = doctorDtoPhoneNo;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
}