package com.magic.project.controllers;

import com.magic.project.dto.PatientDto;
import com.magic.project.models.Patient;
import com.magic.project.services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/insert")
	public ResponseEntity<PatientDto> addPatient(@Valid @RequestBody PatientDto patientDto) {
		Patient patient = mapper.map(patientDto, Patient.class);
		patientService.savePatient(patient);
		PatientDto patientSend = mapper.map(patient, PatientDto.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(patientSend);
	}

	@GetMapping("/list")
	public ResponseEntity<List<PatientDto>> getAllPatients() {
		List<Patient> patientList = patientService.getAllPatients();
		List<PatientDto> patientDtoList = new ArrayList<>();
		for (Patient patient : patientList) {
			PatientDto doctorDto = mapper.map(patient, PatientDto.class);
			patientDtoList.add(doctorDto);
		}
		return ResponseEntity.ok(patientDtoList);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deletePatient(@RequestParam int id) {
		patientService.deletePatient(id);
		return ResponseEntity.ok("Deleted");
	}

}
