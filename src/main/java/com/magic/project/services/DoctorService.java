package com.magic.project.services;

import com.magic.project.models.Doctor;

import java.util.List;

import javax.validation.Valid;

public interface DoctorService {
	void saveDoctor(Doctor doctor);

	List<Doctor> getAllDoctors();

	List<Doctor> getDoctorByCity(String city);

	List<Doctor> getDoctorByCityAndSpeciality(String city, String speciality);

	void deleteDoctor(int id);

	List<Doctor> getDoctorsByPatientSymptoms(int patientId);

	Doctor getDoctor(int id);

	Doctor updateNumber(Doctor doc, @Valid Doctor newDoc);
}
