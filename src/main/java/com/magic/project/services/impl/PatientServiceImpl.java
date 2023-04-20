package com.magic.project.services.impl;

import com.magic.project.handler.PatientNotFoundException;
import com.magic.project.models.Patient;
import com.magic.project.repository.PatientRepository;
import com.magic.project.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public void savePatient(Patient patient) {
		patientRepository.save(patient);
	}

	public List<Patient> getAllPatients() {
		List<Patient> list = patientRepository.findAll();
		if (list.isEmpty()) {
			throw new PatientNotFoundException("No Patients Found.");
		}
		return list;
	}

	public void deletePatient(int id) {
		Patient patient = patientRepository.findById(id).orElse(null);
		if (patient == null) {
			throw new PatientNotFoundException("No Patients Found.");
		}
		patientRepository.deleteById(id);
	}
}
