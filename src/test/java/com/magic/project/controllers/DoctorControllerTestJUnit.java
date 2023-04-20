package com.magic.project.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.magic.project.handler.DoctorNotFoundException;
import com.magic.project.models.Doctor;
import com.magic.project.services.DoctorService;
import com.magic.project.services.PatientService;

@ExtendWith(MockitoExtension.class)
public class DoctorControllerTestJUnit {
	private Doctor buildTestingDoctor() {
		Doctor doctor = new Doctor();
		doctor.setDoctorId(1);
		doctor.setDoctorName("NAME");
		doctor.setDoctorCity("Delhi");
		doctor.setDoctorEmail("abc@gmail.com");
		doctor.setDoctorPhoneNo("1234567890");
		doctor.setSpeciality("Orthopedic");
		return doctor;
	}

	private DoctorDto buildTestingDoctorDto() {
		DoctorDto doctorDto = new DoctorDto();
		doctorDto.setDoctorDtoId(1);
		doctorDto.setDoctorDtoName("NAME");
		doctorDto.setDoctorDtoCity("Delhi");
		doctorDto.setDoctorDtoEmail("abc@gmail.com");
		doctorDto.setDoctorDtoPhoneNo("1234567890");
		doctorDto.setSpeciality("Orthopedic");
		return doctorDto;
	}

	@InjectMocks
	private DoctorController doctorController;
	@Mock
	private DoctorService doctorService;
	@Mock
	private PatientService patientService;
	@Mock
	private ModelMapper mapper;

	@Test
	void should_add_new_employee() throws Exception {
		Doctor doctor = buildTestingDoctor();
		DoctorDto doctorDto = buildTestingDoctorDto();
		when(mapper.map(doctorDto, Doctor.class)).thenReturn(doctor);
		doNothing().when(doctorService).saveDoctor(ArgumentMatchers.any(Doctor.class));
		when(mapper.map(doctor, DoctorDto.class)).thenReturn(doctorDto);
		// Act
		ResponseEntity<DoctorDto> response = doctorController.addDoctor(doctorDto);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isEqualTo(doctorDto);
	}

	@Test
	void test_getDoctor() throws DoctorNotFoundException {
		Doctor doctor = buildTestingDoctor();
		DoctorDto doctorDto = buildTestingDoctorDto();
		when(doctorService.getDoctor(Mockito.anyInt())).thenReturn(doctor);
		when(mapper.map(doctor, DoctorDto.class)).thenReturn(doctorDto);
		// Act
		ResponseEntity<DoctorDto> response = doctorController.getDoctor(1);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(doctorDto);
	}

	@Test
	void test_getAllDoctors() throws DoctorNotFoundException {
		// Arrange
		List<Doctor> doctors = new ArrayList<>();
		doctors.add(buildTestingDoctor());

		List<DoctorDto> doctorDtos = new ArrayList<>();
		doctorDtos.add(buildTestingDoctorDto());

		Mockito.when(doctorService.getAllDoctors()).thenReturn(doctors);
		Mockito.when(mapper.map(doctors.get(0), DoctorDto.class)).thenReturn(doctorDtos.get(0));

		// Act
		ResponseEntity<List<DoctorDto>> response = doctorController.getAllDoctors();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(doctorDtos.size(), response.getBody().size());
		assertEquals(doctorDtos.get(0).getDoctorDtoId(), response.getBody().get(0).getDoctorDtoId());

	}

	@Test
	void test_getDoctorByCity() throws DoctorNotFoundException {
		// Arrange
		List<Doctor> doctors = new ArrayList<>();
		doctors.add(buildTestingDoctor());

		List<DoctorDto> doctorDtos = new ArrayList<>();
		doctorDtos.add(buildTestingDoctorDto());

		Mockito.when(doctorService.getDoctorByCity("Delhi")).thenReturn(doctors);
		Mockito.when(mapper.map(doctors.get(0), DoctorDto.class)).thenReturn(doctorDtos.get(0));

		// Act
		ResponseEntity<List<DoctorDto>> response = doctorController.getDoctorByCity("Delhi");

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(doctorDtos.size(), response.getBody().size());
		assertEquals(doctorDtos.get(0).getDoctorDtoId(), response.getBody().get(0).getDoctorDtoId());

	}

	@Test
	void test_getDoctorByCityAndSpeciality() throws DoctorNotFoundException {
		// Arrange
		List<Doctor> doctors = new ArrayList<>();
		doctors.add(buildTestingDoctor());

		List<DoctorDto> doctorDtos = new ArrayList<>();
		doctorDtos.add(buildTestingDoctorDto());

		Mockito.when(doctorService.getDoctorByCityAndSpeciality("Delhi", "Orthopedic")).thenReturn(doctors);
		Mockito.when(mapper.map(doctors.get(0), DoctorDto.class)).thenReturn(doctorDtos.get(0));

		// Act
		ResponseEntity<List<DoctorDto>> response = doctorController.getDoctorByCityAndSpeciality("Delhi", "Orthopedic");

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(doctorDtos.size(), response.getBody().size());
		assertEquals(doctorDtos.get(0).getDoctorDtoId(), response.getBody().get(0).getDoctorDtoId());

	}

	@Test
	void test_updateNumber() throws Exception {
		Doctor doctor = buildTestingDoctor();
		DoctorDto newDocDto = buildTestingDoctorDto();
		Doctor newDoc = new Doctor();
		newDocDto.setDoctorDtoPhoneNo("1234567890");
		Doctor updatedDoc = new Doctor();
		updatedDoc.setDoctorId(doctor.getDoctorId());
		updatedDoc.setDoctorPhoneNo(newDocDto.getDoctorDtoPhoneNo());
		updatedDoc.setDoctorCity(doctor.getDoctorCity());
		updatedDoc.setDoctorEmail(doctor.getDoctorEmail());
		updatedDoc.setDoctorName(doctor.getDoctorName());
		updatedDoc.setSpeciality(doctor.getSpeciality());
		when(doctorService.getDoctor(Mockito.anyInt())).thenReturn(doctor);
		when(mapper.map(newDocDto, Doctor.class)).thenReturn(newDoc);
		when(doctorService.updateNumber(doctor, newDoc)).thenReturn(updatedDoc);
		when(mapper.map(updatedDoc, DoctorDto.class)).thenReturn(newDocDto);
		// Act
		ResponseEntity<DoctorDto> response = doctorController.updateNumber(1, newDocDto);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1, response.getBody().getDoctorDtoId());
		assertEquals(newDocDto.getDoctorDtoPhoneNo(), response.getBody().getDoctorDtoPhoneNo());
//		when(doctorService.getDoctor(Mockito.anyInt())).thenReturn(null);
//		BodyBuilder response1 = ResponseEntity.status(HttpStatus.NOT_FOUND);
//		assertEquals(HttpStatus.NOT_FOUND, ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@Test
	void test_deleteDoctor() throws DoctorNotFoundException {
		// Act
		ResponseEntity<String> response = doctorController.deleteDoctor(1);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("Deleted");
	}

	@Test
	void test_getDoctorsByPatientSymptoms() throws DoctorNotFoundException {
		// Arrange
		List<Doctor> doctors = new ArrayList<>();
		doctors.add(buildTestingDoctor());

		List<DoctorDto> doctorDtos = new ArrayList<>();
		doctorDtos.add(buildTestingDoctorDto());

		Mockito.when(doctorService.getDoctorsByPatientSymptoms(1)).thenReturn(doctors);
		Mockito.when(mapper.map(doctors.get(0), DoctorDto.class)).thenReturn(doctorDtos.get(0));

		// Act
		ResponseEntity<List<DoctorDto>> response = doctorController.getDoctorsByPatientSymptoms(1);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(doctorDtos.size(), response.getBody().size());
		assertEquals(doctorDtos.get(0).getDoctorDtoId(), response.getBody().get(0).getDoctorDtoId());

	}

}
