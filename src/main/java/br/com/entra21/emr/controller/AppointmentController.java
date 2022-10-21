package br.com.entra21.emr.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.entra21.emr.model.Appointment;
import br.com.entra21.emr.model.AppointmentList;
import br.com.entra21.emr.model.Doctor;
import br.com.entra21.emr.model.ItemNivel3;
import br.com.entra21.emr.model.Patient;
import br.com.entra21.emr.repository.IAppointmentRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private IAppointmentRepository appointmentRepository;

	@Autowired
	private PatientController patientController;

	@Autowired
	private DoctorController doctorController;

	// LIST ALL
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Appointment> list() {

		List<Appointment> response = appointmentRepository.findAll();
		response.forEach(appointment -> {
			setMaturidadeNivel3(appointment);
		});

		return response;
	}

	@GetMapping("/resume")
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
			setMaturidadeNivel3(appointment);
		});
		return responseList;
	}

	@GetMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public Appointment findById(@PathVariable("id") Integer id) {

		Appointment response = appointmentRepository.returnById(id);

		return response;
	}
	

	// CREATE
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Appointment add(@RequestBody Appointment newAppointment) {

		return getData(newAppointment);
	}

	//UPDATE
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Optional<Appointment> update(@PathVariable("id") int param,
			@RequestBody Appointment newDataAppointment) {

		Appointment current = appointmentRepository.findById(param).get();
		current.setPatient(newDataAppointment.getPatient());
		current.setDoctor(newDataAppointment.getDoctor());
		current.setDate_appointment(LocalDateTime.now());
		current.setAnamnesis(newDataAppointment.getAnamnesis());
		current.setPrescription(newDataAppointment.getPrescription());
		current.setCertificate(newDataAppointment.getCertificate());
		current.setForwarding(newDataAppointment.getForwarding());
		current.setMedicalRelease(newDataAppointment.getMedicalRelease());

		appointmentRepository.save(current);

		return appointmentRepository.findById(param);
	}

	// DELETE
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean delete(@PathVariable("id") int id) {
		appointmentRepository.deleteById(id);

		return !appointmentRepository.existsById(id);
	}

	@GetMapping(value = "/patient/{prefix}")
	public List<Appointment> getPatientIdEquals(@PathVariable("prefix") Integer prefix) {

		return appointmentRepository.findByPatient_idEquals(prefix);
	}

	private Appointment getData(Appointment obj) {
		Appointment newObj = new Appointment();
		newObj.setId(obj.getId());
		newObj.setDate_appointment(obj.getDate_appointment());
		if (newObj.getDate_appointment() == null) {
			newObj.setDate_appointment(LocalDateTime.now());
		}
		newObj.setAnamnesis(obj.getAnamnesis());
		newObj.setPrescription(obj.getPrescription());
		newObj.setCertificate(obj.getCertificate());
		newObj.setForwarding(obj.getForwarding());
		newObj.setMedicalRelease(obj.getMedicalRelease());
		Doctor doctor = doctorController.findById(obj.getDoctor().getId());
		Patient patient = patientController.findById(obj.getPatient().getId());

		newObj.setDoctor(doctor);
		newObj.setPatient(patient);

		return appointmentRepository.save(newObj);
	}

	private void setMaturidadeNivel3(Appointment appointment) {

		final String PATH = "localhost:8080/appointment";

		ArrayList<String> headers = new ArrayList<String>();

		headers.add("Accept : application/json");

		headers.add("Content-type : application/json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); // ESTUDAR
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		mapper.setSerializationInclusion(Include.NON_NULL);

		try {

			Appointment clone = mapper.readValue(mapper.writeValueAsString(appointment), Appointment.class);

			clone.setLinks(null);

			Patient patient = clone.getPatient();
			Doctor doctor = clone.getDoctor();
			LocalDateTime date_appointment = clone.getDate_appointment();
			String anamnesis = clone.getAnamnesis();
			String prescription = clone.getPrescription();
			String certificate = clone.getCertificate();
			String forwarding = clone.getForwarding();
			String medicalRelease = clone.getMedicalRelease();

			clone.setPatient(patient);
			clone.setDoctor(doctor);
			clone.setDate_appointment(LocalDateTime.of(2015, 12, 11, 12, 00));
			clone.setAnamnesis("Different password");
			clone.setPrescription("Different password");
			clone.setCertificate("Different password");
			clone.setForwarding("Different password");
			clone.setMedicalRelease("Different password");

			String jsonUpdate = mapper.writeValueAsString(clone);

			clone.setPatient(patient);
			clone.setDoctor(doctor);
			clone.setDate_appointment(date_appointment);
			clone.setAnamnesis(anamnesis);
			clone.setPrescription(prescription);
			clone.setCertificate(certificate);
			clone.setForwarding(forwarding);
			clone.setMedicalRelease(medicalRelease);

			clone.setId(null);

			String jsonCreate = mapper.writeValueAsString(clone);

			appointment.setLinks(new ArrayList<>());

			appointment.getLinks().add(new ItemNivel3("GET", PATH, null, null));

			appointment.getLinks().add(new ItemNivel3("GET", PATH + "/" + appointment.getId(), null, null));

			appointment.getLinks().add(new ItemNivel3("DELETE", PATH + "/" + appointment.getId(), null, null));

			appointment.getLinks().add(new ItemNivel3("POST", PATH, headers, jsonCreate));

			appointment.getLinks().add(new ItemNivel3("PUT", PATH + "/" + appointment.getId(), headers, jsonUpdate));

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		}

	}

}
