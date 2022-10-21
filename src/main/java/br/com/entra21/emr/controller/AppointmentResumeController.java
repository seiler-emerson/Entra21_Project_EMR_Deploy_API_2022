package br.com.entra21.emr.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.entra21.emr.model.Appointment;
import br.com.entra21.emr.model.AppointmentList;
import br.com.entra21.emr.model.Doctor;
import br.com.entra21.emr.model.Patient;
import br.com.entra21.emr.repository.IAppointmentRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/resume")
public class AppointmentResumeController {

	@Autowired
	private IAppointmentRepository appointmentRepository;

	@Autowired
	private PatientController patientController;

	@Autowired
	private DoctorController doctorController;
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<AppointmentList> listResume() {
		List<Appointment> response = appointmentRepository.findAll();
		List<AppointmentList> responseList = new ArrayList<>();
		response.forEach(appointment -> {
			Patient patient = patientController.findById(appointment.getPatient().getId());
			Doctor doctor = doctorController.findById(appointment.getDoctor().getId());
			AppointmentList objeto = new AppointmentList(appointment.getId(), appointment.getDate_appointment(),
					patient.getName(), patient.getCpf(), doctor.getName());
			responseList.add(objeto);
		});
		return responseList;
	}
	
	
	
}
