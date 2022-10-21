package br.com.entra21.emr.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="appointment_emr")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Appointment extends MaturidadeNivel3Richardson {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime date_appointment;
	private String anamnesis;
	private String prescription;
	private String certificate;
	private String forwarding;
	private String medicalRelease;
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;
	@ManyToOne
	@JoinColumn(name="doctor_id")
	private Doctor doctor;
	
	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Appointment(ArrayList<ItemNivel3> links) {
		super(links);
		// TODO Auto-generated constructor stub
	}
	public Appointment(Integer id, LocalDateTime date_appointment, String anamnesis, String prescription,
			String certificate, String forwarding, String medicalRelease, Patient patient, Doctor doctor) {
		super();
		this.id = id;
		this.date_appointment = date_appointment;
		this.anamnesis = anamnesis;
		this.prescription = prescription;
		this.certificate = certificate;
		this.forwarding = forwarding;
		this.medicalRelease = medicalRelease;
		this.patient = patient;
		this.doctor = doctor;
	}	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDateTime getDate_appointment() {
		return date_appointment;
	}
	public void setDate_appointment(LocalDateTime date_appointment) {
		this.date_appointment = date_appointment;
	}
	public String getAnamnesis() {
		return anamnesis;
	}
	public void setAnamnesis(String anamnesis) {
		this.anamnesis = anamnesis;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getForwarding() {
		return forwarding;
	}
	public void setForwarding(String forwarding) {
		this.forwarding = forwarding;
	}
	public String getMedicalRelease() {
		return medicalRelease;
	}
	public void setMedicalRelease(String medicalRelease) {
		this.medicalRelease = medicalRelease;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	
		
	
	
	
}
