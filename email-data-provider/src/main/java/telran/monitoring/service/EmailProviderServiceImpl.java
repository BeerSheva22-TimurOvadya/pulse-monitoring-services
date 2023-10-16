package telran.monitoring.service;

import org.slf4j.*;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.entities.*;

import telran.monitoring.repo.*;

@Service
@Slf4j
public class EmailProviderServiceImpl implements EmailProviderService {
	
	DoctorRepository doctorRepository;
	PatientRepository patientRepository;
	VisitRepository visitRepository;

	@Override
	public EmailNotificationData getEmailNotificationData(long patientId) {
		String doctorEmail = visitRepository.getDoctorEmail(patientId);
		
		log.debug("doctor email is {}", doctorEmail);
		if (doctorEmail == null) {
			throw new RuntimeException("no vists for patient " + patientId);
		}
		Doctor doctor = doctorRepository.findById(doctorEmail).orElse(null);
		if (doctor == null) {
			throw new RuntimeException ("no doctor with email " + doctorEmail);
		}
		String doctorName = doctor.getName();
		Patient patient = patientRepository.findById(patientId).orElse(null);
		if (patient == null) {
			throw new RuntimeException("no  patient " + patientId);
		}
		String patientName = patient.getName();
		return new EmailNotificationData(doctorEmail, doctorName, patientName);
	}

	public EmailProviderServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository,
			VisitRepository visitRepository) {
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.visitRepository = visitRepository;
	}

}