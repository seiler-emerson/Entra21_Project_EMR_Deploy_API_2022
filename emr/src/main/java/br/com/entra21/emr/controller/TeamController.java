package br.com.entra21.emr.controller;

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

import br.com.entra21.emr.model.ItemNivel3;
import br.com.entra21.emr.model.Team;
import br.com.entra21.emr.repository.ITeamRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/team")
public class TeamController {

	@Autowired
	private ITeamRepository teamRepository;
	
//	LIST ALL
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Team> list() {
		
		List<Team> response = teamRepository.findAll();
		response.forEach(team ->{
			setMaturidadeNivel3(team);
		});
		
		return response;
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Team> search(@PathVariable("id") int param) {

		List<Team> response = teamRepository.findById(param).stream().toList();

		return response;
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Team add(@RequestBody Team newTeam) {

		return teamRepository.save(newTeam);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Optional<Team> update(@PathVariable("id") int param,
			@RequestBody Team newDataTeam) {

		Team current = teamRepository.findById(param).get();
		current.setName(newDataTeam.getName());
		current.setGithub(newDataTeam.getGithub());
		current.setLinkedin(newDataTeam.getLinkedin());
		current.setCollege(newDataTeam.getCollege());
		current.setImage(newDataTeam.getImage());
		
		teamRepository.save(current);

		return teamRepository.findById(param);
	}
	
	// DELETE
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean delete(@PathVariable("id") int id) {
		teamRepository.deleteById(id);

		return !teamRepository.existsById(id);
	}
	
	private void setMaturidadeNivel3(Team team) {

		final String PATH = "localhost:8080/team";

		ArrayList<String> headers = new ArrayList<String>();

		headers.add("Accept : application/json");

		headers.add("Content-type : application/json");

		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Include.NON_NULL);

		try {

			Team clone = mapper.readValue(mapper.writeValueAsString(team), Team.class);

			clone.setLinks(null);

			String name = clone.getName();
			String github = clone.getGithub();
			String linkedin = clone.getLinkedin();
			String college = clone.getCollege();
			String image = clone.getImage();
			
			clone.setName("Different name");
			clone.setGithub("Different github");
			clone.setLinkedin("Different Linkedin");
			clone.setCollege("Different college");
			clone.setImage("Different image");
			
			String jsonUpdate = mapper.writeValueAsString(clone);

			clone.setName(name);
			clone.setGithub(github);
			clone.setLinkedin(linkedin);
			clone.setCollege(college);
			clone.setImage(image);
			
			clone.setId(null);

			String jsonCreate = mapper.writeValueAsString(clone);

			team.setLinks(new ArrayList<>());

			team.getLinks().add(new ItemNivel3("GET", PATH, null, null));

			team.getLinks().add(new ItemNivel3("GET", PATH + "/" + team.getId(), null, null));
			
			team.getLinks().add(new ItemNivel3("DELETE", PATH, null, null));
			
			team.getLinks().add(new ItemNivel3("POST", PATH, headers, jsonCreate));

			team.getLinks().add(new ItemNivel3("PUT", PATH + "/" + team.getId(), headers, jsonUpdate));

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		}

	}
}
