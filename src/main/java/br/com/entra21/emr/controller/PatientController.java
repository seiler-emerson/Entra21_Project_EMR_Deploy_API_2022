package br.com.entra21.emr.controller;

import java.time.LocalDate;
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

import br.com.entra21.emr.model.ItemNivel3;
import br.com.entra21.emr.model.Patient;
import br.com.entra21.emr.repository.IPatientRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private IPatientRepository patientRepository;
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Patient> list() {
		
		List<Patient> response = patientRepository.findAll();
		response.forEach(patient ->{
			setMaturidadeNivel3(patient);
		});
		
		return response;
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Patient findById(@PathVariable("id") Integer id) {

		Patient response = patientRepository.returnById(id);
		
		return response;
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Patient add(@RequestBody Patient newPatient) {

		return getData(newPatient);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Optional<Patient> update(@PathVariable("id") int param,
			@RequestBody Patient newDataPatient) {

		Patient current = patientRepository.findById(param).get();
		current.setName(newDataPatient.getName());
		current.setCpf(newDataPatient.getCpf());
		current.setNameMother(newDataPatient.getNameMother());
		current.setNameFather(newDataPatient.getNameFather());
		current.setGenre(newDataPatient.getGenre());
		current.setBirth(newDataPatient.getBirth());
		current.setStreetName(newDataPatient.getStreetName());
		current.setNumberHome(newDataPatient.getNumberHome());
		current.setDistrict(newDataPatient.getDistrict());
		current.setCity(newDataPatient.getCity());
		current.setState(newDataPatient.getState());
		current.setCountry(newDataPatient.getCountry());
		patientRepository.save(current);

		return patientRepository.findById(param);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean delete(@PathVariable("id") int id) {
		patientRepository.deleteById(id);

		return !patientRepository.existsById(id);
	}
	
	private Patient getData(Patient patient) {
		Patient newPatient = new Patient();
		newPatient.setId(patient.getId());
		newPatient.setName(patient.getName());
		newPatient.setCpf(patient.getCpf());
		newPatient.setNameMother(patient.getNameMother());
		newPatient.setNameFather(patient.getNameFather());
		newPatient.setGenre(patient.getGenre());
		newPatient.setBirth(patient.getBirth());
		newPatient.setStreetName(patient.getStreetName());
		newPatient.setNumberHome(patient.getNumberHome());
		newPatient.setDistrict(patient.getDistrict());
		newPatient.setCity(patient.getCity());
		newPatient.setState(patient.getState());
		newPatient.setCountry(patient.getCountry());


		return patientRepository.save(newPatient);
	}
	
	@GetMapping(value = "/start/{prefix}")
	public List<Patient> getStartWith(@PathVariable("prefix") String prefix) {
		
		return patientRepository.findByNameStartingWith(prefix);
	}
	
	private void setMaturidadeNivel3(Patient patient) {

		final String PATH = "localhost:8080/patient";

		ArrayList<String> headers = new ArrayList<String>();

		headers.add("Accept : application/json");

		headers.add("Content-type : application/json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());									//ESTUDAR
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		mapper.setSerializationInclusion(Include.NON_NULL);

		try {

			Patient clone = mapper.readValue(mapper.writeValueAsString(patient), Patient.class);

			clone.setLinks(null);

			String name = clone.getName();
			String cpf = clone.getCpf();
			String nameMother = clone.getNameMother();
			String nameFather = clone.getNameFather();
			String genre = clone.getGenre();
			LocalDate birth = clone.getBirth();
			String streetName = clone.getStreetName();
			Short numberHome = clone.getNumberHome();
			String district = clone.getDistrict();
			String city = clone.getCity();
			String state = clone.getState();
			String country = clone.getCountry();
			
			clone.setName("Different name");
			clone.setCpf("Different login");
			clone.setNameMother("Different email");
			clone.setNameFather("Different password");
			clone.setGenre("Different password");
			clone.setBirth(LocalDate.of(2015, 12, 11));
			clone.setStreetName("Different password");
			clone.setNumberHome((short) 123);
			clone.setDistrict("Different password");
			clone.setCity("Different password");
			clone.setState("Different password");
			clone.setCountry("Different password");
					
			String jsonUpdate = mapper.writeValueAsString(clone);

			clone.setName(name);
			clone.setCpf(cpf);
			clone.setNameMother(nameMother);
			clone.setNameFather(nameFather);
			clone.setGenre(genre);
			clone.setBirth(birth);
			clone.setStreetName(streetName);
			clone.setNumberHome(numberHome);
			clone.setDistrict(district);
			clone.setCity(city);
			clone.setState(state);
			clone.setCountry(country);
			
			clone.setId(null);

			String jsonCreate = mapper.writeValueAsString(clone);

			patient.setLinks(new ArrayList<>());
			patient.getLinks().add(new ItemNivel3("GET", PATH, null, null));
			patient.getLinks().add(new ItemNivel3("GET", PATH + "/" + patient.getId(), null, null));
			patient.getLinks().add(new ItemNivel3("DELETE", PATH + "/" + patient.getId(), null, null));
			patient.getLinks().add(new ItemNivel3("POST", PATH, headers, jsonCreate));
			patient.getLinks().add(new ItemNivel3("PUT", PATH + "/" + patient.getId(), headers, jsonUpdate));

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		}

	}
	
}
