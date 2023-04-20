package com.magic.project.services;

import com.magic.project.models.Patient;

import java.util.List;

public interface PatientService {
	void savePatient(Patient patient);

	List<Patient> getAllPatients();

	void deletePatient(int id);
}
