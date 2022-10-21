package br.com.entra21.emr.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppointmentList extends MaturidadeNivel3Richardson {

	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime date;
	private String patientName;
	private String patientCpf;
	private String doctorName;
	
	public AppointmentList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppointmentList(ArrayList<ItemNivel3> links) {
		super(links);
		// TODO Auto-generated constructor stub
	}
	public AppointmentList(Integer id, LocalDateTime date, String patientName, String patientCpf, String doctorName) {
		super();
		this.id = id;
		this.date = date;
		this.patientName = patientName;
		this.patientCpf = patientCpf;
		this.doctorName = doctorName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientCpf() {
		return patientCpf;
	}
	public void setPatientCpf(String patientCpf) {
		this.patientCpf = patientCpf;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	


	
	
	
}
