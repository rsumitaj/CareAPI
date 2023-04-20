package com.magic.project.controllers;

import com.magic.project.dto.DoctorDto;
import com.magic.project.models.Doctor;
import com.magic.project.services.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/insert")
	public ResponseEntity<DoctorDto> addDoctor(@Valid @RequestBody DoctorDto doctorDto) {
		Doctor doctor = mapper.map(doctorDto, Doctor.class);
		doctorService.saveDoctor(doctor);
		DoctorDto doctorSend = mapper.map(doctor, DoctorDto.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(doctorSend);
	}

	@GetMapping("/id")
	public ResponseEntity<DoctorDto> getDoctor(@RequestParam int id) {
		Doctor doctor = doctorService.getDoctor(id);
		DoctorDto doctorSend = mapper.map(doctor, DoctorDto.class);
		return ResponseEntity.ok(doctorSend);

	}

	@GetMapping("/list")
	public ResponseEntity<List<DoctorDto>> getAllDoctors() {
		List<Doctor> doctorList = doctorService.getAllDoctors();
		List<DoctorDto> doctorDtoList = new ArrayList<>();
		for (Doctor doctor : doctorList) {
			DoctorDto doctorDto = mapper.map(doctor, DoctorDto.class);
			doctorDtoList.add(doctorDto);
		}
		return ResponseEntity.ok(doctorDtoList);
	}

	@GetMapping("/list/city")
	public ResponseEntity<List<DoctorDto>> getDoctorByCity(@RequestParam String city) {
		List<Doctor> doctorList = doctorService.getDoctorByCity(city);
		List<DoctorDto> doctorDtoList = new ArrayList<>();
		for (Doctor doctor : doctorList) {
			DoctorDto doctorDto = mapper.map(doctor, DoctorDto.class);
			doctorDtoList.add(doctorDto);
		}
		return ResponseEntity.ok(doctorDtoList);
	}

	@GetMapping("/list/city/speciality")
	public ResponseEntity<List<DoctorDto>> getDoctorByCityAndSpeciality(@RequestParam String city,
			@RequestParam String speciality) {
		List<Doctor> doctorList = doctorService.getDoctorByCityAndSpeciality(city, speciality);
		List<DoctorDto> doctorDtoList = new ArrayList<>();
		for (Doctor doctor : doctorList) {
			DoctorDto doctorDto = mapper.map(doctor, DoctorDto.class);
			doctorDtoList.add(doctorDto);
		}
		return ResponseEntity.ok(doctorDtoList);
	}

	@PatchMapping("update/phone")
	public ResponseEntity<DoctorDto> updateNumber(@RequestParam int id, @Valid @RequestBody DoctorDto newDocDto) {
		Doctor newDoc = mapper.map(newDocDto, Doctor.class);
		Doctor doc = doctorService.getDoctor(id);
		if (doc == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		Doctor updatedDoc = doctorService.updateNumber(doc, newDoc);
		DoctorDto doctorSend = mapper.map(updatedDoc, DoctorDto.class);
		return ResponseEntity.ok(doctorSend);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteDoctor(@RequestParam int id) {
		doctorService.deleteDoctor(id);
		return ResponseEntity.ok("Deleted");
	}

	@GetMapping("/list/symptom")
	public ResponseEntity<List<DoctorDto>> getDoctorsByPatientSymptoms(@RequestParam int id) {
		List<Doctor> doctorList = doctorService.getDoctorsByPatientSymptoms(id);
		List<DoctorDto> doctorDtoList = new ArrayList<>();
		for (Doctor doctor : doctorList) {
			DoctorDto doctorDto = mapper.map(doctor, DoctorDto.class);
			doctorDtoList.add(doctorDto);
		}
		return ResponseEntity.ok(doctorDtoList);
	}

}
