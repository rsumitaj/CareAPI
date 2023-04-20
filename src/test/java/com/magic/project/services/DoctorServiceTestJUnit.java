package com.magic.project.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.magic.project.repository.DoctorRepository;
import com.magic.project.repository.PatientRepository;
import com.magic.project.services.impl.DoctorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTestJUnit {
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
	private DoctorServiceImpl doctorService;
	@Mock
	private DoctorRepository doctorRepository;
	@Mock
	private PatientRepository patientRepository;

	@Test
	void findAll_should_return_doctor_list() {
		// Given
		Doctor doctor = buildTestingDoctor();
		// When
		when(doctorRepository.findAll()).thenReturn(List.of(doctor));
		List<Doctor> doctors = doctorService.getAllDoctors();
		// Then
		assertEquals(1, doctors.size());
		verify(this.doctorRepository).findAll();
	}

	@Test
	void findAll_should_return_doctor_list_False() {
		// When
		when(doctorRepository.findAll()).thenReturn(List.of());
		// Then
		Throwable exception = assertThrows(DoctorNotFoundException.class, () -> {
			doctorService.getAllDoctors();
		});
		assertEquals("Doctors not Found.", exception.getMessage());
	}

	@Test
	void findById_should_return_doctor() {
		Doctor doctor = buildTestingDoctor();
		when(doctorRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(doctor));
		Doctor returnedDoctor = doctorService.getDoctor(1);
		assertEquals(doctor.getDoctorId(), returnedDoctor.getDoctorId());
		verify(this.doctorRepository).findById(1);
	}

	@Test
	void findById_should_return_doctor_NotFound() {
		when(doctorRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Throwable exception = assertThrows(DoctorNotFoundException.class, () -> {
			doctorService.getDoctor(0);
		});
		// Then
		assertEquals("Doctor with ID: 0" + DoctorServiceImpl.NF, exception.getMessage());
	}

	@Test
	void findById_should_return_patient_NotFound() {
		when(patientRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Throwable exception = assertThrows(PatientNotFoundException.class, () -> {
			doctorService.getDoctorsByPatientSymptoms(0);
		});
		// Then
		assertEquals("No Patient with id: 0 found.", exception.getMessage());
	}

	@Test
	void findByCity_should_return_DoctorByCity() {
		// Given
		Doctor doctor = buildTestingDoctor();
		// When
		when(doctorRepository.findAll()).thenReturn(List.of(doctor));
		List<Doctor> doctors = doctorService.getDoctorByCity("Delhi");
		// Then
		assertEquals(1, doctors.size());
		verify(this.doctorRepository).findAll();
	}

	@Test
	void findByCity_should_return_DoctorByCity_Not_Found() {
		// Given
		Doctor doctor = buildTestingDoctor();
		// When
		when(doctorRepository.findAll()).thenReturn(List.of(doctor));
		Throwable exception = assertThrows(DoctorNotFoundException.class, () -> {
			doctorService.getDoctorByCity("Srinagar");
		});
		// Then
		assertEquals("Doctors from Srinagar" + DoctorServiceImpl.NF, exception.getMessage());
	}

	@Test
	void save_should_save_doctor() {
		Doctor doctor = this.buildTestingDoctor();
		doctorService.saveDoctor(doctor);
		verify(doctorRepository).save(doctor);
	}

	@Test
	void test_isValidLocation() {
		// return location.equals("Delhi") || location.equals("Noida") ||
		// location.equals("Faridabad");
		assertTrue(doctorService.isValidLocation("Delhi"));
		assertTrue(doctorService.isValidLocation("Noida"));
		assertTrue(doctorService.isValidLocation("Faridabad"));
		assertFalse(doctorService.isValidLocation("Gurgaon"));
		assertFalse(doctorService.isValidLocation(""));
	}

	@Test
	void findByCity_should_return_DoctorByCityAndSpeciality() {
		// Given
		Doctor doctor = buildTestingDoctor();
		// When
		when(doctorRepository.findAll()).thenReturn(List.of(doctor));
		List<Doctor> doctors = doctorService.getDoctorByCityAndSpeciality("Delhi", "Orthopedic");
		// Then
		assertEquals(1, doctors.size());
		verify(doctorRepository).findAll();
	}

	@Test
	void findByCity_should_return_DoctorByCityAndSpeciality_Not_Found() {
		// Given
		Doctor doctor = buildTestingDoctor();
		// When
		when(doctorRepository.findAll()).thenReturn(List.of(doctor));
		Throwable exception = assertThrows(DoctorNotFoundException.class, () -> {
			doctorService.getDoctorByCityAndSpeciality("TEST_CITY", "TEST_SPECIALITY");
		});
		// Then
		assertEquals("Doctors from TEST_CITY and for TEST_SPECIALITY" + DoctorServiceImpl.NF, exception.getMessage());
	}

	@Test
	void test_updateNumber() {
		Doctor doctor = buildTestingDoctor();
		Doctor newDoctor = new Doctor();
		newDoctor.setDoctorId(1);
		newDoctor.setDoctorName("NAME");
		newDoctor.setDoctorCity("Delhi");
		newDoctor.setDoctorEmail("abc@gmail.com");
		newDoctor.setDoctorPhoneNo("0987654321");
		newDoctor.setSpeciality("Orthopedic");
		when(doctorRepository.save(newDoctor)).thenReturn(newDoctor);
		Doctor result = doctorService.updateNumber(doctor, newDoctor);
		assertEquals(newDoctor, result);
		verify(doctorRepository).save(newDoctor);
	}

	@Test
	void test_deleteDoctor() {
		Doctor doctor = buildTestingDoctor();
		when(doctorRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(doctor));
		doctorService.deleteDoctor(doctor.getDoctorId());
		verify(doctorRepository).deleteById(doctor.getDoctorId());
	}

	@Test
	void test_deleteDoctor_NoDocFound() {
		when(doctorRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Throwable exception = assertThrows(DoctorNotFoundException.class, () -> {
			doctorService.deleteDoctor(0);
		});
		// Then
		assertEquals("Doctor with ID: 0" + DoctorServiceImpl.NF, exception.getMessage());
	}

	@Test
	void test_getDoctorsByPatientSymptoms() {
		Doctor doctor = buildTestingDoctor();
		Patient patient = buildTestingPatient();
		when(patientRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(patient));
		when(doctorRepository.findAll()).thenReturn(List.of(doctor));
		List<Doctor> result = doctorService.getDoctorsByPatientSymptoms(patient.getPatientId());
		assertTrue(result.contains(doctor));
	}

	@Test
	void test_getDoctorsByPatientSymptoms_NotFounnd() {
		Doctor doctor = buildTestingDoctor();
		doctor.setSpeciality("Test_speciaity");
		Patient patient = buildTestingPatient();
		when(patientRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(patient));
		when(doctorRepository.findAll()).thenReturn(List.of(doctor));
		Throwable exception = assertThrows(DoctorNotFoundException.class, () -> {
			doctorService.getDoctorsByPatientSymptoms(patient.getPatientId());
		});
		assertEquals("There isn’t any doctor present at your location for your symptom", exception.getMessage());
	}

	@Test
	void test_isValidLocation_False() {
		Doctor doctor = buildTestingDoctor();
		Patient patient = buildTestingPatient();
		patient.setPatientCity("TEST_CITY");
		when(patientRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(patient));
		when(doctorRepository.findAll()).thenReturn(List.of(doctor));
		Throwable exception = assertThrows(DoctorNotFoundException.class, () -> {
			doctorService.getDoctorsByPatientSymptoms(patient.getPatientId());
		});
		assertEquals("We are still waiting to expand to your location", exception.getMessage());
	}
}
