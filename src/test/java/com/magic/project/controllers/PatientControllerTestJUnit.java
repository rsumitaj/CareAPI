package com.magic.project.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.magic.project.dto.DoctorDto;
import com.magic.project.dto.PatientDto;
import com.magic.project.handler.DoctorNotFoundException;
import com.magic.project.models.Doctor;
import com.magic.project.models.Patient;
import com.magic.project.services.DoctorService;
import com.magic.project.services.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTestJUnit {
	private Patient buildTestingPatient() {
		Patient patient = new Patient();
		patient.setPatientId(1);
		patient.setPatientName("NAME");
		patient.setPatientCity("Delhi");
		patient.setPatientEmail("abc@gmail.com");
		patient.setPatientPhoneNo("1234567890");
		patient.setSymptom("Orthopedic");
		return patient;
	}

	private PatientDto buildTestingPatientDto() {
		PatientDto patientDto = new PatientDto();
		patientDto.setPatientDtoId(1);
		patientDto.setPatientDtoName("NAME");
		patientDto.setPatientDtoCity("Delhi");
		patientDto.setPatientDtoEmail("abc@gmail.com");
		patientDto.setPatientDtoPhoneNo("1234567890");
		patientDto.setSymptom("Orthopedic");
		return patientDto;
	}

	@InjectMocks
	private PatientController patientController;
	@Mock
	private DoctorService doctorService;
	@Mock
	private PatientService patientService;
	@Mock
	private ModelMapper mapper;

	@Test
	void test_patientInsert() throws Exception {
		Patient patient = buildTestingPatient();
		PatientDto patientDto = buildTestingPatientDto();
		when(mapper.map(patientDto, Patient.class)).thenReturn(patient);
		doNothing().when(patientService).savePatient(ArgumentMatchers.any(Patient.class));
		when(mapper.map(patient, PatientDto.class)).thenReturn(patientDto);
		// Act
		ResponseEntity<PatientDto> response = patientController.addPatient(patientDto);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isEqualTo(patientDto);
	}

	@Test
	void test_getAllPatients() throws DoctorNotFoundException {
		// Arrange
		List<Patient> patients = new ArrayList<>();
		patients.add(buildTestingPatient());

		List<PatientDto> patientDtos = new ArrayList<>();
		patientDtos.add(buildTestingPatientDto());

		Mockito.when(patientService.getAllPatients()).thenReturn(patients);
		Mockito.when(mapper.map(patients.get(0), PatientDto.class)).thenReturn(patientDtos.get(0));

		// Act
		ResponseEntity<List<PatientDto>> response = patientController.getAllPatients();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(patientDtos.size(), response.getBody().size());
		assertEquals(patientDtos.get(0).getPatientDtoId(), response.getBody().get(0).getPatientDtoId());

	}

	@Test
	void test_deletePatient() throws DoctorNotFoundException {
		// Act
		ResponseEntity<String> response = patientController.deletePatient(1);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("Deleted");
	}
}
