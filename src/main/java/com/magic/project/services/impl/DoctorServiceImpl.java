package com.magic.project.services.impl;

import com.magic.project.handler.DoctorNotFoundException;
import com.magic.project.handler.PatientNotFoundException;
import com.magic.project.models.Doctor;
import com.magic.project.models.Patient;
import com.magic.project.repository.DoctorRepository;
import com.magic.project.repository.PatientRepository;
import com.magic.project.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@Service
public class DoctorServiceImpl implements DoctorService {
	public static final String NF = " Not Found.";
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PatientRepository patientRepository;

	public void saveDoctor(Doctor doctor) {
		doctorRepository.save(doctor);
	}

	public List<Doctor> getAllDoctors() {
		List<Doctor> list= doctorRepository.findAll();
		if (list.isEmpty()) {
			throw new DoctorNotFoundException("Doctors not Found.");
		}
		return list;
	}

	public List<Doctor> getDoctorByCity(String city) {
		List<Doctor> doctorList = doctorRepository.findAll();
		List<Doctor> newDoctorList = new ArrayList<>();
		for (Doctor doctor : doctorList) {
			if (doctor.getDoctorCity().equals(city)) {
				newDoctorList.add(doctor);
			}
		}
		if (newDoctorList.isEmpty()) {
			throw new DoctorNotFoundException("Doctors from "+city+NF);
		}
		return newDoctorList;
	}

	public List<Doctor> getDoctorByCityAndSpeciality(String city, String speciality) {
		List<Doctor> doctorList = doctorRepository.findAll();
		List<Doctor> newDoctorList = new ArrayList<>();
		for (Doctor doctor : doctorList) {
			if (doctor.getDoctorCity().equals(city) && doctor.getSpeciality().equals(speciality)) {
				newDoctorList.add(doctor);
			}
		}
		if (newDoctorList.isEmpty()) {
			throw new DoctorNotFoundException("Doctors from "+city+" and for "+speciality+NF);
		}
		return newDoctorList;
	}

	public void deleteDoctor(int id) {
		if (getDoctor(id)!=null) {
			//throw new DoctorNotFoundException("Doctors with ID "+id+NF);
			doctorRepository.deleteById(id);
		}
		
		
		
	}

	public List<Doctor> getDoctorsByPatientSymptoms(int patientId){
		Patient patient = patientRepository.findById(patientId).orElse(null);
		if(patient==null) {
			throw new PatientNotFoundException("No Patient with id: "+patientId+" found.");
		}
		String patientLocation = patient.getPatientCity();
		String patientSymptoms = patient.getSymptom();
		List<Doctor> doctorList = doctorRepository.findAll();
		List<Doctor> l1 = new ArrayList<>();
		List<Doctor> finalList = new ArrayList<>();
		
			if (isValidLocation(patientLocation)) {
				for (Doctor doctor : doctorList) {
					if (doctor.getDoctorCity().equals(patientLocation)) {
						l1.add(doctor);
					}
				}
			} else {
				throw new DoctorNotFoundException("We are still waiting to expand to your location");
			}

			for (Doctor doctor : l1) {
				if (doctor.getSpeciality().equals(map.get(patientSymptoms))) {
					finalList.add(doctor);
				}
			}
		
		if (finalList.isEmpty())
			throw new DoctorNotFoundException("There isn’t any doctor present at your location for your symptom");

		return finalList;

	}

	private static Map<String, String> map = new HashMap<>();
	static {
		map.put("Arthritis", Doctor.ORTHO);
		map.put("Backpain",  Doctor.ORTHO);
		map.put("Tissue injuries",  Doctor.ORTHO);
		map.put("Dysmenorrhea", "Gynecology");
		map.put("Skin infection", "Dermatology");
		map.put("Skin burn", "Dermatology");
		map.put("Ear pain", "ENT");
	}

	public boolean isValidLocation(String location) {
		return location.equals("Delhi") || location.equals("Noida") || location.equals("Faridabad");
	}

	public Doctor getDoctor(int id) {
		Doctor doctor = doctorRepository.findById(id).orElse(null);
		if (doctor == null) {
			throw new DoctorNotFoundException("Doctor with ID: " + id + NF);
		} else
			return doctor;
	}

	public Doctor updateNumber(Doctor doc, @Valid Doctor newDoc) {
		if (newDoc.getDoctorCity() == null) {
			newDoc.setDoctorCity(doc.getDoctorCity());
		}
		if (newDoc.getDoctorEmail() == null) {
			newDoc.setDoctorEmail(doc.getDoctorEmail());
		}
		if (newDoc.getDoctorId() == 0) {
			newDoc.setDoctorId(doc.getDoctorId());
		}
		if (newDoc.getDoctorName() == null) {
			newDoc.setDoctorName(doc.getDoctorName());
		}
		if (newDoc.getDoctorPhoneNo() == null) {
			newDoc.setDoctorPhoneNo(doc.getDoctorPhoneNo());
		}
		if (newDoc.getSpeciality() == null) {
			newDoc.setSpeciality(doc.getSpeciality());
		}
		doctorRepository.save(newDoc);
		return newDoc;
	}
}
