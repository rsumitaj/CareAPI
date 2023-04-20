package com.magic.project.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.magic.project.handler.DoctorNotFoundException;
import com.magic.project.handler.PatientNotFoundException;
import com.magic.project.models.Doctor;
import com.magic.project.models.Patient;
import com.magic.project.repository.PatientRepository;
import com.magic.project.services.impl.PatientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTestJUnit {
	private Patient buildTestingPatient() {
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("NAME");
		patient.setPatientCity("Delhi");
		patient.setPatientEmail("abc@gmail.com");
		patient.setPatientPhoneNo("1234567890");
		patient.setSymptom("Backpain");
		return patient;
	}

	@InjectMocks
	private PatientServiceImpl patientService;
	@Mock
	private PatientRepository patientRepository;

	@Test
	void test_savePatient() {
		Patient patient = this.buildTestingPatient();
		patientService.savePatient(patient);
		verify(patientRepository).save(patient);
	}

	@Test
	void test_getAllPatients() {
		// Given
		Patient patient = this.buildTestingPatient();
		// When
		when(patientRepository.findAll()).thenReturn(List.of(patient));
		List<Patient> patients = patientService.getAllPatients();
		// Then
		assertEquals(1, patients.size());
		verify(patientRepository).findAll();
		// When
		when(patientRepository.findAll()).thenReturn(List.of());
		// Then
		Throwable exception = assertThrows(PatientNotFoundException.class, () -> {
			patientService.getAllPatients();
		});
		assertEquals("No Patients Found.", exception.getMessage());
	}

	@Test
	void test_deletePatient() {
		// Given
		Patient patient = this.buildTestingPatient();
		// When
		when(patientRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(patient));
		patientService.deletePatient(patient.getPatientId());
		verify(patientRepository).deleteById(patient.getPatientId());
		// for error
		when(patientRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Throwable exception = assertThrows(PatientNotFoundException.class, () -> {
			patientService.deletePatient(0);
		});
		assertEquals("No Patients Found.", exception.getMessage());

	}
}
