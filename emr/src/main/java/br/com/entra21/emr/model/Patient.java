package br.com.entra21.emr.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="patient_emr")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Patient extends MaturidadeNivel3Richardson {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@CPF
	private String cpf;
	private String nameMother;
	private String nameFather;
	private String genre;
	private LocalDate birth;
	private String streetName;
	private Short numberHome;
	private String district;
	private String city;
	private String state;
	private String country;
	@JsonIgnore
	@OneToMany(mappedBy="patient")
	private List<Appointment> appointment = new ArrayList<>();
	
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Patient(ArrayList<ItemNivel3> links) {
		super(links);
		// TODO Auto-generated constructor stub
	}
	public Patient(Integer id, String name, String cpf, String nameMother, String nameFather, String genre,
			LocalDate birth, String streetName, Short numberHome, String district, String city, String state,
			String country) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.nameMother = nameMother;
		this.nameFather = nameFather;
		this.genre = genre;
		this.birth = birth;
		this.streetName = streetName;
		this.numberHome = numberHome;
		this.district = district;
		this.city = city;
		this.state = state;
		this.country = country;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNameMother() {
		return nameMother;
	}
	public void setNameMother(String nameMother) {
		this.nameMother = nameMother;
	}
	public String getNameFather() {
		return nameFather;
	}
	public void setNameFather(String nameFather) {
		this.nameFather = nameFather;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public LocalDate getBirth() {
		return birth;
	}
	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public Short getNumberHome() {
		return numberHome;
	}
	public void setNumberHome(Short numberHome) {
		this.numberHome = numberHome;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<Appointment> getAppointment() {
		return appointment;
	}
}
